package com.example.minotas

import Auxiliar.Conexion
import Modelo.Notas
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class NotaSimple : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nota_simple)

        var asunto:EditText=findViewById(R.id.etxtAsunto)
        var texto:EditText=findViewById(R.id.etxtSimple)
        var posicion = intent!!.getIntExtra("posicion",-1)
        if(posicion!=-1){
            var nota= Conexion.obtenerNotas(this)[posicion]
            asunto.setText(nota.getAsunto())
            texto.setText(nota.getTexto())
        }
    }
    var cont:Int=0
    @RequiresApi(Build.VERSION_CODES.O)
    fun guardar(view:View){
        cont=Conexion.ultimoID(this)
        cont++
        var asunto:EditText=findViewById(R.id.etxtAsunto)
        var texto:EditText=findViewById(R.id.etxtSimple)
        var posicion = intent!!.getIntExtra("posicion",-1)
        if(posicion!=-1){
            var nota= Conexion.obtenerNotas(this)[posicion]
            if(texto.text.isEmpty()){
                //avisar de si quieres borrarla
                Conexion.delNota(this, nota.getId())
            }else{
                var n: Notas = Notas(nota.getId(),asunto.text.toString(),"Nota Simple",texto.text.toString())
                Conexion.modNota(this,nota.getId(),n)
            }
        }else{
            if(texto.text.isEmpty()){
                //avisar crear nota vacia
                var n: Notas = Notas(cont,asunto.text.toString(),"Nota Simple",texto.text.toString())
                Conexion.addNotaSimple(this,n)
            }else{
                var n: Notas = Notas(cont,asunto.text.toString(),"Nota Simple",texto.text.toString())
                Conexion.addNotaSimple(this,n)
            }
        }
    }
    fun cancel(view: View){
        finish()
    }
    fun sms(view: View){
        var texto:EditText=findViewById(R.id.etxtSimple)
        var intentMensaje = Intent(this, SMS::class.java)
        intentMensaje.putExtra("Mensaje",texto.text.toString())
        startActivity(intentMensaje)
    }
}