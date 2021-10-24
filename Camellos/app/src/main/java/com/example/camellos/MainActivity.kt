package com.example.camellos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    fun aceptar(view:View){
        var camello1:EditText=findViewById(R.id.etxtCamel1)
        var camello2:EditText=findViewById(R.id.etxtCamel2)
        var camello3:EditText=findViewById(R.id.etxtCamel3)
        var camello4:EditText=findViewById(R.id.etxtCamel4)
        var intentMain: Intent = Intent(this,Carrera::class.java)
        intentMain.putExtra("camel1",camello1.text.toString())
        intentMain.putExtra("camel2",camello2.text.toString())
        intentMain.putExtra("camel3",camello3.text.toString())
        intentMain.putExtra("camel4",camello4.text.toString())
        startActivity(intentMain)
    }


}