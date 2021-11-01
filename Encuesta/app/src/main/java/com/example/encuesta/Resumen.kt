package com.example.encuesta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class Resumen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen)

        var resumen:TextView=findViewById(R.id.txtResu)

        var persona = intent.getStringArrayListExtra("Personas") as ArrayList<Persona>

        if (persona != null) {
            for(ani in persona){
                Log.e("David", ani.toString())
                resumen.append(ani.toString()   +"\r\n")
            }
        }
    }


    fun volver(view:View){
        finish()
    }


}