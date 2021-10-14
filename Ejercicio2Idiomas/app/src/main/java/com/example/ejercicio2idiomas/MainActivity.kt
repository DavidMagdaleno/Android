package com.example.ejercicio2idiomas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    var resultado:Int=0

    fun sumar(){
        var num1=R.id.etxtN1.toInt()
        var num2=R.id.etxtN2.toInt()
        var Resultado=num1+num2


    }
}