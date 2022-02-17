package com.example.desafiofinal

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
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
        val modi = bundle?.getString("Mod").toString()

        if (modi!="Modificar"){
            cbAdmin.isVisible=false
            cbAdmin.isEnabled=false
            cbUsuario.isVisible=false
            cbUsuario.isEnabled=false
            croles.clear()
            croles.add(1)

        }
        //Guardado de datos para toda la aplicación en la sesión.
        val prefs: SharedPreferences.Editor? = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs?.putString("email",bundle?.getString("email"))
        prefs?.apply () //Con estos datos guardados en el fichero de sesión, aunque la app se detenga tendremos acceso a los mismos.

        if (modi!="NONE"){
            db.collection("usuarios").document(email).get().addOnSuccessListener {
                //Si encuentra el documento será satisfactorio este listener y entraremos en él.
                txtDNI.setText(it.get("DNI").toString())
                txtNom.setText(it.get("Nombre").toString())
                txtApel.setText(it.get("Apellidos").toString())
                var aux=it.get("roles") as ArrayList<Int>
                for (i in 0..aux.size-1){
                    if (aux[i].equals("1".toLong())){ cbUsuario.isChecked=true}
                    if (aux[i].equals("2".toLong())){ cbUsuario.isChecked=true}
                }
                //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Algo ha ido mal al recuperar",Toast.LENGTH_SHORT).show()
            }
        }

        btnGuardar.setOnClickListener {
            //Se guardarán en modo HashMap (clave, valor).
            if (modi=="Modificar"){
                croles.clear()
                if (cbUsuario.isChecked){croles.add(1)}
                if (cbAdmin.isChecked){croles.add(2)}
            }
            var user = hashMapOf(
                "DNI" to txtDNI.text.toString(),
                "Nombre" to txtNom.text.toString(),
                "Apellidos" to txtApel.text.toString(),
                "Aceptado" to "Aceptado",
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