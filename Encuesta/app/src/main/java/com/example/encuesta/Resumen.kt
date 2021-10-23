package com.example.encuesta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class Resumen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen)

        var lista= arrayListOf<Persona>()
        var resumen:TextView=findViewById(R.id.txtResu)

        var persona:Persona=intent.getSerializableExtra("personas") as Persona

        lista.add(persona)

        for(any in lista){
            //Log.e("David",any.toString())---para comprobar la salida
            resumen.append(any.toString()+"\r\n")
        }

    }


    fun volver(view:View){
        finish()
    }


}