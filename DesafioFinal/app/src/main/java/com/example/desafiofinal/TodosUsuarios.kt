package com.example.desafiofinal

import MiAdaptador.AdaptadorAdminUser
import MiAdaptador.AdaptadorUsuarioEvento
import Model.User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class TodosUsuarios : AppCompatActivity() {
    lateinit var miRecyclerView : RecyclerView
    var miArray:ArrayList<User> = ArrayList()
    private val db = FirebaseFirestore.getInstance() //Variable con la que accederemos a Firestore. Será una instancia a la bd.
    private val TAG = "David"
    var context=this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todos_usuarios)

        miRecyclerView = findViewById(R.id.rvUsuEv)// as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)

        try {
            recuperarDatos(object : TodosUsuarios.RolCallback {
                override fun datosRecibidos(datosnuevos: MutableList<DocumentSnapshot>) {
                    obtenerDatos(datosnuevos)
                }
            })
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
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
        var miAdapter = AdaptadorUsuarioEvento(miArray, this)
        miRecyclerView.adapter = miAdapter
    }

    fun volver(view: View){
        finish()
    }
}