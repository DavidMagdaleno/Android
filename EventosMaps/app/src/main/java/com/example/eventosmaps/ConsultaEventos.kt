package com.example.eventosmaps

import Adapter.MiAdapterConsultas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ConsultaEventos : AppCompatActivity() {
    lateinit var miRecyclerView : RecyclerView
    private val db = FirebaseFirestore.getInstance()
    var miArray2:ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_eventos)

        miRecyclerView = findViewById(R.id.rvConsultas) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)


        db.collection("eventos").get().addOnSuccessListener {
            //Si encuentra el documento será satisfactorio este listener y entraremos en él
            //Log.e("llaves", it.documents[0].id)
            //Log.e("llaves", it.documents.single().id)
            for (i in 0..it.documents.size-1){
                miArray2.add(it.documents[i].id)
            }
            var miAdapter = MiAdapterConsultas(miArray2, this)
            miRecyclerView.adapter = miAdapter

        }.addOnFailureListener{
            Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
        }


    }
}