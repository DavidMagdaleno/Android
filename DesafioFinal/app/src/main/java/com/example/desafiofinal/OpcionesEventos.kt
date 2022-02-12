package com.example.desafiofinal

import MiAdaptador.AdaptadorEventos
import MiAdaptador.AdaptadorUsuarioEvento
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_opciones_eventos.*

class OpcionesEventos : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance() //Variable con la que accederemos a Firestore. Será una instancia a la bd.
    companion object{
        var rol:Int=-1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones_eventos)

        val bundle:Bundle? = intent.extras
        val ev = bundle?.getString("tituloEvento").toString()
        var email = intent.getStringExtra("user")

        if (rol==2){
            btnUsuEve.isEnabled=true
            btnUsuEve.isVisible=true
        }

        btnIrMaps.setOnClickListener(){
            var intentmais: Intent = Intent(this, Maps::class.java)
            intentmais.putExtra("tituloEvento",ev)
            intentmais.putExtra("user", email)
            startActivity(intentmais)
        }

        btnUsuEve.setOnClickListener(){
            AdaptadorUsuarioEvento.tituloevento=ev
            var intentmais: Intent = Intent(this, TodosUsuarios::class.java)
            intentmais.putExtra("tituloEvento",ev)
            intentmais.putExtra("user", email)
            startActivity(intentmais)
        }

        btnFotos.setOnClickListener(){
            db.collection("eventos").document(ev).get().addOnSuccessListener {
                var f=it.get("fotos").toString()
                var intentmais: Intent = Intent(this, Galeria::class.java)
                intentmais.putExtra("tituloEvento",ev)
                intentmais.putExtra("user", email)
                intentmais.putExtra("storagefoto", f)
                startActivity(intentmais)
            }.addOnFailureListener{
                Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
            }
        }
        btnComent.setOnClickListener(){}
        btnLoca.setOnClickListener(){}


    }
}