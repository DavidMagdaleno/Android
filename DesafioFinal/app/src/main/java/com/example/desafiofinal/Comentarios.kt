package com.example.desafiofinal

import MiAdaptador.AdaptadorComentarios
import Model.Comentario
import Model.User
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_comentarios.*
import kotlinx.android.synthetic.main.activity_main.*

class Comentarios : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var miRecyclerView : RecyclerView
    var arrayComent:ArrayList<Comentario> = ArrayList()
    var arrayComentaux:ArrayList<Comentario> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comentarios)

        miRecyclerView = findViewById(R.id.rvComent) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onStart() {
        super.onStart()

        val bundle:Bundle? = intent.extras
        val ev = bundle?.getString("tituloEvento").toString()
        val email = bundle?.getString("user").toString()

        try {
            mostarcomentarios(object : Comentarios.RolCallback {
                override fun comenta(cmtNuevo: ArrayList<Comentario>) {
                    for(i in 0..cmtNuevo.size-1){
                        var c= cmtNuevo[i] as Map<String,String>
                        arrayComentaux.add(Comentario(c.get("em")!!,c.get("comen")!!))
                    }
                    var miAdapter = AdaptadorComentarios(arrayComentaux, this@Comentarios)
                    miRecyclerView.adapter = miAdapter
                }
            })
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        btnAgreComent.setOnClickListener(){
            val dialogo: AlertDialog.Builder = AlertDialog.Builder(this)
            val Myview= RecyclerView.inflate(this,R.layout.item_comentario, null)
            var comt = Myview.findViewById<EditText>(R.id.txtComent)
            dialogo.setView(Myview)
            dialogo.setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, which ->
                    arrayComent.add(Comentario(email,comt.text.toString()))

                    db.collection("eventos").document(ev).get()
                        .addOnSuccessListener {
                            var user = hashMapOf(
                                "Ubicacion" to it.get("Ubicacion").toString(),
                                "asistentes" to it.get("asistentes") as ArrayList<User>,
                                "comentarios" to arrayComent,
                                "fotos" to it.get("fotos").toString(),
                                "localizacion" to it.get("localizacion") as ArrayList<String>
                            )
                            db.collection("eventos")//añade o sebreescribe
                                .document(ev) //Será la clave del documento.
                                .set(user).addOnSuccessListener {
                                    //Toast.makeText(Context, "Almacenado", Toast.LENGTH_SHORT).show()
                                }.addOnFailureListener {
                                    //Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                                }
                            //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            //Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
                        }
                })
            dialogo.setNegativeButton("CANCELAR",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
            dialogo.show()
        }
    }

    fun volver(view: View){
        finish()
    }

    interface RolCallback {
        fun comenta(cmt: ArrayList<Comentario>)
    }

    fun mostarcomentarios( callback:RolCallback){
        val bundle:Bundle? = intent.extras
        val ev = bundle?.getString("tituloEvento").toString()
        var email = intent.getStringExtra("user")
        AdaptadorComentarios.titulo=ev
        AdaptadorComentarios.email=email!!
        db.collection("eventos").document(ev).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.data?.get("comentarios").toString()!="null"){
                        //poner los demas para recuperar todos los datos
                        var ccmt= task.result.data?.get("comentarios") as ArrayList<Comentario>
                        if (callback != null) {
                            callback.comenta(ccmt);
                        }
                    }
                } else {
                    Log.e("wh", "Error getting documents.", task.exception)
                }
            }
    }
}