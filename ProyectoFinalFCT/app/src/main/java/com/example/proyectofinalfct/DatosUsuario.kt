package com.example.proyectofinalfct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.proyectofinalfct.databinding.ActivityDatosUsuarioBinding
import com.example.proyectofinalfct.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class DatosUsuario : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var binding: ActivityDatosUsuarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDatosUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle:Bundle? = intent.extras
        val email = bundle?.getString("email").toString()
        val modi = bundle?.getString("Mod").toString()
        if (modi=="Modificar"){

            db.collection("usuarios").document(email).get().addOnSuccessListener {
                //Si encuentra el documento será satisfactorio este listener y entraremos en él.
                binding.txtDNI.setText(it.get("DNI").toString())
                binding.txtName.setText(it.get("Nombre").toString())
                binding.txtApe.setText(it.get("Apellidos").toString())
                binding.txtDire.setText(it.get("Direccion").toString())
                binding.txtNac.setText(it.get("FechaNac").toString())
                //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Algo ha ido mal al recuperar",Toast.LENGTH_SHORT).show()
            }

            binding.txtDNI.isActivated=false
            binding.txtDNI.isClickable=false

        }

        binding.btnAcept.setOnClickListener {
            //Se guardarán en modo HashMap (clave, valor).
            var user = hashMapOf(
                "DNI" to binding.txtDNI.text.trim().toString(),
                "Nombre" to binding.txtName.text.toString(),
                "Apellidos" to binding.txtApe.text.toString(),
                "Direccion" to binding.txtDire.text.toString(),
                "FechaNac" to binding.txtNac.text.toString()
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