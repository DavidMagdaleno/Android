package com.example.ejercicio3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun alpulsarboton(view: View){
        var num1:EditText=findViewById(R.id.etxtN1)
        var num2:EditText=findViewById(R.id.etxtN2)
        var result=Integer.parseInt(num1.text.toString())+Integer.parseInt(num2.text.toString())
        var cajaresult:TextView=findViewById(R.id.txtResultado)
        cajaresult.setText(result.toString())
        Toast.makeText(this, "Sumando...", Toast.LENGTH_SHORT).show()
    }
}