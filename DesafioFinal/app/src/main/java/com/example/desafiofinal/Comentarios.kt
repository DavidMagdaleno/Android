package com.example.desafiofinal

import MiAdaptador.AdaptadorEventos
import Model.Comentario
import Model.Imagenes
import Model.User
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_comentarios.*

class Comentarios : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    var arrayComent:ArrayList<Comentario> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comentarios)

        val bundle:Bundle? = intent.extras
        val ev = bundle?.getString("tituloEvento").toString()
        val email = bundle?.getString("email").toString()

        btnAgreComent.setOnClickListener(){
            val dialogo: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            val Myview= RecyclerView.inflate(this,R.layout.item_comentario, null)
            var comt = Myview.findViewById<Button>(R.id.txtComent)
            dialogo.setView(Myview)
            dialogo.setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, which ->
                    arrayComent.add(Comentario(email,comt.text.toString()))

                    db.collection("eventos").document(ev).get()
                        .addOnSuccessListener {
                            var p = it.get("Ubicacion").toString()
                            var user = hashMapOf(
                                "Ubicacion" to p,
                                "asistentes" to it.get("asistentes") as ArrayList<User>,
                                "comentarios" to arrayComent,
                                "fotos" to it.get("fotos").toString()
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

            dialogo.setNegativeButton("CANCELAR",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
            dialogo.show()
        })

        }
    }

    fun volver(view: View){
        finish()
    }

}