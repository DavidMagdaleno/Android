package com.example.encuesta

import Auxiliar.Encuestados
import Auxiliar.Fichero
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class Log : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        var log:TextView=findViewById(R.id.txtLog)
        var Fichero: Fichero = Fichero(Encuestados.log, this)
        log.append(Fichero.leerFichero(Encuestados.log))
    }

    fun volverLog(view:View){
        finish()
    }
}