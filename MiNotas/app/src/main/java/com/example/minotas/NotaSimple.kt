package com.example.minotas

import Auxiliar.Conexion
import Modelo.Notas
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi

class NotaSimple : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nota_simple)

        var texto:EditText=findViewById(R.id.etxtSimple)
        var posicion = intent!!.getIntExtra("posicion",-1)
        if(posicion!=-1){
            var nota= Conexion.obtenerNotas(this)[posicion]
            texto.setText(nota.getTexto())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun guardar(view:View){
        var texto:EditText=findViewById(R.id.etxtSimple)
        var posicion = intent!!.getIntExtra("posicion",-1)
        if(posicion!=-1){
            var nota= Conexion.obtenerNotas(this)[posicion]
            if(texto.text.isEmpty()){
                //avisar y borrar
            }else{
                //hacer un update con el nuevo texto
            }
        }else{
            if(texto.text.isEmpty()){
                //avisar y borrar
            }else{
                //Popup asunto
                var n: Notas = Notas("asunto","Nota Simple",texto.text.toString())
                Conexion.addNotaSimple(this,n)
            }
        }
    }
    fun cancel(view: View){
        finish()
    }
    fun sms(view: View){
        //proximamente
    }

}