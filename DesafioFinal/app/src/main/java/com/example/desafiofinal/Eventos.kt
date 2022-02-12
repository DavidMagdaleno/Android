package com.example.desafiofinal

import MiAdaptador.AdaptadorEventos
import Model.Evento
import Model.User
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_eventos.*

class Eventos : AppCompatActivity() {
    lateinit var miRecyclerView : RecyclerView
    private val db = FirebaseFirestore.getInstance()
    var miArray2:ArrayList<String> = ArrayList()
    var asistentes = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        miRecyclerView = findViewById(R.id.rvEventos) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    var flag:Boolean=true
    override fun onStart() {
        super.onStart()
        val bundle:Bundle? = intent.extras
        val email = bundle?.getString("email").toString()
        AdaptadorEventos.email=email

        db.collection("eventos").get().addOnSuccessListener {
            for (i in 0..it.documents.size-1){
                if (flag){
                    miArray2.add(it.documents[i].id)
                }
                if(!flag && it.documents[i].id.equals(txtEv.text.toString())){
                    miArray2.add(it.documents[i].id)
                }
            }
            if (!miArray2.isEmpty()){ flag=false}
            var miAdapter = AdaptadorEventos(miArray2, this)
            miRecyclerView.adapter = miAdapter

        }.addOnFailureListener{
            Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
        }

        db.collection("usuarios").document(email).get().addOnSuccessListener {
            //Si encuentra el documento será satisfactorio este listener y entraremos en él
            if(it.get("DNI") as String!=""){
                asistentes.add(User(it.get("DNI") as String, it.get("Nombre") as String, it.get("Apellidos") as String, it.get("Aceptado") as String, it.get("email") as String, it.get("Ubicacion") as String, it.get("Hora") as String,
                    it.get("roles") as ArrayList<Int>
                ))
            }else{
                Toast.makeText(this, "El usuario no tiene DNI", Toast.LENGTH_SHORT).show()
            }
            //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
        }

        btnCrearEv.setOnClickListener(){
            DialogLogin()
        }
    }

    fun volver(view: View){
        finish()
    }

    fun DialogLogin(): Boolean {
        var intentmais: Intent = Intent(this, Maps::class.java)
        val dialogo: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        val Myview=layoutInflater.inflate(R.layout.item_dailogo2, null)
        dialogo.setView(Myview)
        dialogo.setPositiveButton("Aceptar",
            DialogInterface.OnClickListener { dialog, which ->
                resultLauncher.launch(intentmais)
            })
        dialogo.setNegativeButton("Salir",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
        dialogo.show()
        return true
    }

    var resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode== Activity.RESULT_OK){
            val data: Intent?=result.data
            val returString=data!!.getStringExtra("ubica")
            var e: Evento =Evento(returString.toString())
            //Se guardarán en modo HashMap (clave, valor).
            var user = hashMapOf(
                "Ubicacion" to e,
                "asistentes" to asistentes,
                "comentarios" to "",
                "fotos" to ""
            )
            db.collection("eventos")//añade o sebreescribe
                .document(txtEv.text.toString()) //Será la clave del documento.
                .set(user).addOnSuccessListener {
                    Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }
        }
    }

}