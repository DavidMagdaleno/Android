package com.example.notas

import Auxiliar.Fichero
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class verNotas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_notas)

        lectura()
    }

    var nomFich:String=intent.getStringExtra("nombreFichero")!!
    var existe:Boolean=intent.getBooleanExtra("existe",false)
    var Fichero: Fichero = Fichero(nomFich, this)
    var nota:EditText=findViewById(R.id.emvNotas)

    fun nuevaNota(view:View){
        Fichero.escribirLinea(nota.text.toString(),nomFich)
    }

    fun lectura(){
        if(existe){
            nota.setText(Fichero.leerFichero(nomFich))
        }
    }

    fun volver(view: View){
        finish()
    }

}