package com.example.ejercicio3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class Resultado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        var cajaresultado:TextView=findViewById(R.id.txtResult)
        cajaresultado.text=intent.getStringExtra("resultadofinal")
    }

    fun volver(view:View){
        finish()
    }

}