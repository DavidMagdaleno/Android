package com.example.eventosmaps

import Adapter.MiAdapterAsistentes
import Model.User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

class Asistentes : AppCompatActivity() {
    lateinit var miRecyclerView : RecyclerView
    private val db = FirebaseFirestore.getInstance()
    var miArray:ArrayList<User> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asistentes)

        var evento = intent.getStringExtra("evento")

        miRecyclerView = findViewById(R.id.rvAsis) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)


        if (evento != null) {
            db.collection("eventos").document(evento).get().addOnSuccessListener {
                //Si encuentra el documento será satisfactorio este listener y entraremos en él.
                var asistentes = it.get("asistentes") as ArrayList<Any>

                for (i in 0..asistentes.size-1){
                    var m=asistentes[i] as Map<String, String>
                    //Log.e("asis2", m["nombre"].toString())

                    var asist=User(m["nombre"].toString(),m["apellidos"].toString(),m["asistencia"].toString(),m["ubicacion"].toString(),m["hora"].toString())
                    miArray.add(asist)
                }
                var miAdapter = MiAdapterAsistentes(miArray, this)
                miRecyclerView.adapter = miAdapter
            }.addOnFailureListener {
                //Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
            }
        }

    }

}