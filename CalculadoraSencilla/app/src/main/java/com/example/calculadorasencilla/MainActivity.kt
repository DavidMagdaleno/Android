package com.example.calculadorasencilla

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    var operacion:Int=0

    fun zero(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+0)
    }
    fun uno(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+1)
    }
    fun dos(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+2)
    }
    fun tres(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+3)
    }
    fun cuatro(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+4)
    }
    fun cinco(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+5)
    }
    fun seis(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+6)
    }
    fun siete(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+7)
    }
    fun ocho(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+8)
    }
    fun nueve(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+9)
    }

    fun suma(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+"+")
        operacion=1
    }
    fun resta(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+"-")
        operacion=2
    }
    fun multiplicar(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+"x")
        operacion=3
    }
    fun dividir(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+"/")
        operacion=4
    }
    fun borrar(view: View){

        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+"")

    }
    fun igual(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        val lstValues: List<String> = result.text .split("+").map { it }
        Log.i("Values", "value=${result.text.toString()}")
        //result.text[0].toString()
        var num:Int=0
        when(operacion){
            1 -> lstValues.forEach { it ->
                num=num+it.toInt()
                }
            2 -> lstValues.forEach { it ->
                num=num-it.toInt()
                }
            3 -> lstValues.forEach { it ->
                num=num*it.toInt()
                }
            4 -> lstValues.forEach { it ->
                num=num/it.toInt()
                }
        }
        result.text.append("\r\n"+num.toString())
    }

}