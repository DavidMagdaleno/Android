package com.example.eventosmaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registro.*

class Registro : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)


        val bundle:Bundle? = intent.extras
        val email = bundle?.getString("email").toString()
        //txtEmail.text = bundle?.getString("email")
        val prov:String = bundle?.getString("provider").toString()

        btGuardar.setOnClickListener {
            //Se guardarán en modo HashMap (clave, valor).

            var user = hashMapOf(
                "Nombre" to txtNom.text.toString(),
                "Apellidos" to txtApel.text.toString()
            )

            db.collection("Usuarios")//añade o sebreescribe
                .document(email) //Será la clave del documento.
                .set(user).addOnSuccessListener {
                    Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }

        }
    }

    fun volver(view:View){
        finish()
    }
}