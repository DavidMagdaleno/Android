package com.example.desafiofinal

import MiAdaptador.AdaptadorAdminUser
import MiAdaptador.AdaptadorAsistencia
import Model.Comentario
import Model.User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class Asistencia : AppCompatActivity() {
    lateinit var miRecyclerView : RecyclerView
    var miArray:ArrayList<User> = ArrayList()
    private val db = FirebaseFirestore.getInstance()
    var context=this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asistencia)

        val bundle:Bundle? = intent.extras
        val ev = bundle?.getString("tituloEvento").toString()

        miRecyclerView = findViewById(R.id.rvAsistencia)// as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)


        db.collection("eventos").document(ev).get().addOnSuccessListener {
            var a=it.get("asistentes") as ArrayList<User>
            for (i in 0..a.size-1){
                var asi= a[i] as Map<String,Any>
                miArray.add(User(asi.get("dni").toString(),asi.get("nombre").toString(),asi.get("apellidos").toString(),asi.get("aceptado").toString(),asi.get("email").toString(),asi.get("ubicacion").toString(),asi.get("hora").toString(),asi.get("roles") as ArrayList<Int>))
            }
            var miAdapter = AdaptadorAsistencia(miArray, this)
            miRecyclerView.adapter = miAdapter
        }.addOnFailureListener{
            Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
        }


        /*try {
            recuperarDatos(object : Asistencia.RolCallback {
                override fun datosRecibidos(datosnuevos: MutableList<DocumentSnapshot>) {
                    obtenerDatos(datosnuevos)
                }
            })
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }*/

    }

    fun recuperarDatos( callback:RolCallback){
        db.collection("usuarios").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //poner los demas para recuperar todos los datos
                    var usuarios= task.result.documents
                    if (callback != null) {
                        callback.datosRecibidos(usuarios);
                    }
                } else {
                    Log.e("wh", "Error getting documents.", task.exception)
                }
            }
    }

    interface RolCallback {
        fun datosRecibidos(miArray: MutableList<DocumentSnapshot>)
    }

    private fun obtenerDatos(datos: MutableList<DocumentSnapshot>) {
        for(dc in datos){
            var roles : ArrayList<Int>
            if (dc["roles"] != null){
                roles = dc.get("roles") as ArrayList<Int>
            }else {
                roles = arrayListOf()
            }
            var al = User(
                dc.get("DNI").toString(),
                dc.get("Nombre").toString(),
                dc.get("Apellidos").toString(),
                dc.get("Aceptado").toString(),
                dc.get("email").toString(),
                dc.get("Ubicacion").toString(),
                dc.get("Hora").toString(),
                roles
            )
            //Log.e(TAG, al.toString())
            miArray.add(al)
        }
        var miAdapter = AdaptadorAsistencia(miArray, this)
        miRecyclerView.adapter = miAdapter
    }

    fun volver(view: View){
        finish()
    }
}