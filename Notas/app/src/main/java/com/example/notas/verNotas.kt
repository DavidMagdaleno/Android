package com.example.notas

import Auxiliar.Fichero
import Auxiliar.Notas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast

class verNotas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_notas)

        lectura()
    }


    fun nuevaNota(view:View){
        var nomFich:String=intent.getStringExtra("nombreFichero")!!
        var Fichero: Fichero = Fichero(nomFich, this)
        var nota:EditText=findViewById(R.id.emvNotas)
        Fichero.escribirLinea(nota.text.toString(),nomFich)
        Toast.makeText(applicationContext, "Nota Guardada", Toast.LENGTH_SHORT).show()
    }

    fun lectura(){
        //var nomFich: String? =intent.getStringExtra("nombreFichero")
        var existe:Boolean=intent.getBooleanExtra("existe",false)
        if (existe) {
            var posicion: Int = intent.getIntExtra("posicion", -1)
            var nomFich: String = Notas.lista[posicion]
            Log.e("existe", existe.toString())
            Log.e("Nombre", nomFich)
            if (nomFich != null) {
                var Fichero: Fichero = Fichero(nomFich, this)
                var nota: EditText = findViewById(R.id.emvNotas)
                nota.text.append(Fichero.leerFichero())
            }
        }
    }

    fun volver(view: View){
        finish()
    }

}