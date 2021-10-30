package com.example.ejercicioregistro

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
    }

    fun guardarPersona(view: View){
        var nom:EditText=findViewById(R.id.etxtNom)
        var dni:EditText=findViewById(R.id.etxtDNI)
        var p=Persona(nom.text.toString(),dni.text.toString())

        // Put the String to pass back into an Intent and close this activity
        val intent = Intent()
        intent.putExtra("Personas",p)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun cancelar(view:View){
        finish()
    }





}