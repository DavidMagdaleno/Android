package com.example.desafiofinal

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_datos_usuario.*

class DatosUsuario : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance() //Variable con la que accederemos a Firestore. Será una instancia a la bd.
    var croles = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_usuario)
        title = "Inicio"


        val bundle:Bundle? = intent.extras
        val email = bundle?.getString("email").toString()

        //Guardado de datos para toda la aplicación en la sesión.
        val prefs: SharedPreferences.Editor? = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs?.putString("email",bundle?.getString("email"))
        prefs?.apply () //Con estos datos guardados en el fichero de sesión, aunque la app se detenga tendremos acceso a los mismos.

        db.collection("usuarios").document(email).get().addOnSuccessListener {
            //Si encuentra el documento será satisfactorio este listener y entraremos en él.
            txtDNI.setText(it.get("DNI") as String?)
            txtNom.setText(it.get("Nombre") as String?)
            txtApel.setText(it.get("Apellidos") as String?)
            //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Algo ha ido mal al recuperar",Toast.LENGTH_SHORT).show()
        }



        btnGuardar.setOnClickListener {
            //Se guardarán en modo HashMap (clave, valor).
            croles.add(1)
            var user = hashMapOf(
                "DNI" to txtDNI.text.toString(),
                "Nombre" to txtNom.text.toString(),
                "Apellidos" to txtApel.text.toString(),
                "Aceptado" to "En Espera",
                "email" to email,
                "Ubicacion" to "",
                "Hora" to "",
                "roles" to croles
            )

            db.collection("usuarios")//añade o sebreescribe
                .document(email) //Será la clave del documento.
                .set(user).addOnSuccessListener {
                    Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }

        }
    }

    fun volver(view: View){
        finish()
    }


}