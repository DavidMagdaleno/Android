package com.example.eventosmaps

import Adapter.MiAdapterCrear
import Model.Evento
import Model.User
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_crear_evento.*
import kotlin.collections.ArrayList

class CrearEvento : AppCompatActivity() {
    lateinit var miRecyclerView : RecyclerView
    private val db = FirebaseFirestore.getInstance()
    var miArray2:ArrayList<String> = ArrayList()
    var asistentes = ArrayList<User>()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_evento)

        miRecyclerView = findViewById(R.id.rvCrear) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)

        //val sdf = SimpleDateFormat("dd/M/yyyy")
        //val currentDate = sdf.format(Date())

        val bundle:Bundle? = intent.extras
        val email = bundle?.getString("email").toString()
        MiAdapterCrear.email=email

        db.collection("eventos").get().addOnSuccessListener {
            //Si encuentra el documento será satisfactorio este listener y entraremos en él
            //Log.e("llaves", it.documents[0].id)
            //Log.e("llaves", it.documents.single().id)
            for (i in 0..it.documents.size-1){
                miArray2.add(it.documents[i].id)
            }
            var miAdapter = MiAdapterCrear(miArray2, this)
            miRecyclerView.adapter = miAdapter

        }.addOnFailureListener{
            Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
        }

        db.collection("Usuarios").document(email).get().addOnSuccessListener {
            //Si encuentra el documento será satisfactorio este listener y entraremos en él
            if(it.get("Nombre") as String!=""){
                asistentes.add(User(it.get("Nombre") as String,it.get("Apellidos") as String,"SI","",""))
            }else{
                Toast.makeText(this, "El usuario no tiene nombre",Toast.LENGTH_SHORT).show()
            }
            //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Algo ha ido mal al recuperar",Toast.LENGTH_SHORT).show()
        }

        btCrear.setOnClickListener(){
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
            val data:Intent?=result.data
            val returString=data!!.getStringExtra("ubica")
            var e:Evento =Evento(returString.toString())
            //Se guardarán en modo HashMap (clave, valor).
            var user = hashMapOf(
                "Ubicacion" to e,
                "asistentes" to asistentes
            )
            db.collection("eventos")//añade o sebreescribe
                .document(txtEvento.text.toString()) //Será la clave del documento.
                .set(user).addOnSuccessListener {
                    Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }
        }
    }
}