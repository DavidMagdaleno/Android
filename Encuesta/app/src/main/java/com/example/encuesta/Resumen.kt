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

        var lista= arrayListOf<Persona>()
        var resumen:TextView=findViewById(R.id.txtResu)

        var persona:Persona=intent.getSerializableExtra("personas") as Persona
        //var persona:Persona
        //var conta: String? =intent.getStringExtra("contador")
        //Log.i("valor", conta!!)

        /*for(i in 1..conta.toInt()){
            Log.i("valor2", i.toString())
            var persona:Persona=intent.getSerializableExtra(i.toString()) as Persona
            Log.i("valor3","recogido")
            lista.add(persona)
        }*/

        /*var bun:Bundle?//=intent.extras

        bun = intent.getExtras()

        if (bun != null) {
            val keys = bun.keySet()
            val it: Iterator<String> = keys.iterator()
            Log.i("valor", it.toString())
            while (it.hasNext()) {
                val key
                bun.get(key)
                persona=bun!!.getSerializable(key) as Persona
                Log.i("valor2", persona.toString())
                lista.add(persona)
                Log.i("valor3", lista[0].toString())
                //val key = it.next()
                Log.i("valor", key)

            }
        }*/



        //bun!!.getSerializable("personas") as Persona

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