package com.example.eventosmaps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_opciones.*

class Opciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones)

        var intentOp:Intent

        val bundle:Bundle? = intent.extras
        val email = bundle?.getString("email").toString()

        btConsultar.setOnClickListener(){
            intentOp=Intent(this, ConsultaEventos::class.java)
            intentOp.putExtra("email",email)
            startActivity(intentOp)
        }
        btCreacion.setOnClickListener(){
            intentOp=Intent(this, CrearEvento::class.java)
            intentOp.putExtra("email",email)
            startActivity(intentOp)
        }
    }
}