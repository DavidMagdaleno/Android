package com.example.nuevoproyecfirebase

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance() //Variable con la que accederemos a Firestore. Será una instancia a la bd.
    var croles = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        title = "Inicio"


        val bundle:Bundle? = intent.extras
        val email = bundle?.getString("email").toString()
        txtEmail.text = bundle?.getString("email")
        val prov:String = bundle?.getString("provider").toString()
        txtProveedor.text = bundle?.getString("provider").toString()
        val rol:Int = bundle?.getInt("rol").toString().toInt()


        //Guardado de datos para toda la aplicación en la sesión.
        val prefs: SharedPreferences.Editor? = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs?.putString("email",bundle?.getString("email"))
        prefs?.putString("provider",bundle?.getString("provider"))
        bundle?.getInt("rol")?.let { prefs?.putInt("rol", it) }
        prefs?.apply () //Con estos datos guardados en el fichero de sesión, aunque la app se detenga tendremos acceso a los mismos.

        btCerrarSesion.setOnClickListener {
            val prefs: SharedPreferences.Editor? = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs?.clear() //Al cerrar sesión borramos los datos
            prefs?.apply ()
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
        btnGuardar.setOnClickListener {
            //Se guardarán en modo HashMap (clave, valor).
            if(ckUser.isChecked){croles.add(1)}
            if(ckAdmin.isChecked){croles.add(2)}
            if(ckEncargado.isChecked){croles.add(3)}

            var user = hashMapOf(
                "provider" to prov,
                "DNI" to txtDNI.text.toString(),
                "Nombre" to txtNombre.text.toString(),
                "Apellidos" to txtApel.text.toString(),
                "roles" to croles
            )

            db.collection("users")//añade o sebreescribe
                .document(email) //Será la clave del documento.
                .set(user).addOnSuccessListener {
                    Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }

            //Otra forma (siempre añade uno nuevo)
            /*
            db.collection("users")
                .add(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Almacenado",Toast.LENGTH_SHORT).show()
                    Log.e("Fernando", "Documento añadido con ID: ${it.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("Fernando", "Error adding document", e.cause)
                }
             */
        }

        btnRecuperar.setOnClickListener {
            db.collection("users").document(txtMail.text.toString()).get().addOnSuccessListener {
                //Si encuentra el documento será satisfactorio este listener y entraremos en él.
                txtDNI.setText(it.get("DNI") as String?)
                txtNombre.setText(it.get("Nombre") as String?)
                txtApel.setText(it.get("Apellidos") as String?)
                var vroles = it.get("roles") as ArrayList<Int>
                for(i in 0..vroles.size-1){
                    if (vroles[i].equals(1.toLong())){ckUser.isChecked=true}
                    if (vroles[i].equals(2.toLong())){ckAdmin.isChecked=true}
                    if (vroles[i].equals(3.toLong())){ckEncargado.isChecked=true}
                }
                Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Algo ha ido mal al recuperar",Toast.LENGTH_SHORT).show()
            }
        }

        btnEliminar.setOnClickListener {
            db.collection("users").document(email).delete()
            Toast.makeText(this, "Eliminado",Toast.LENGTH_SHORT).show()
        }


    }
}