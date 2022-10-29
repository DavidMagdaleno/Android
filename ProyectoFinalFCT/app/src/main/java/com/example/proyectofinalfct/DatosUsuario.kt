package com.example.proyectofinalfct

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.proyectofinalfct.databinding.ActivityDatosUsuarioBinding
import com.example.proyectofinalfct.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class DatosUsuario : AppCompatActivity() {
    lateinit var auxPhoto:Bitmap
    private val cameraRequest=1888
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
                binding.imgFoto.setImageBitmap(it.get("Foto") as Bitmap)//----------------------------------------??????
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
                "FechaNac" to binding.txtNac.text.toString(),
                "Foto" to auxPhoto.toString()
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

        binding.btnSfoto.setOnClickListener{
            if(ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),cameraRequest)
            }
            val cameraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent,cameraRequest)
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==cameraRequest){
            val photo:Bitmap=data?.extras?.get("data") as Bitmap
            auxPhoto=photo
            binding.imgFoto.setImageBitmap(photo)
        }
    }

    fun volver(view: View){
        finish()
    }
}