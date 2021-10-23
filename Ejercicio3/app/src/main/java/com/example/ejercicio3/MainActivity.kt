package com.example.ejercicio3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun alpulsarboton(view: View){
        var intentMain: Intent = Intent(this,Resultado::class.java)
        var sumar:RadioButton=findViewById(R.id.rbtnSumar)
        var restar:RadioButton=findViewById(R.id.rbtnRestar)
        var multiplicar:RadioButton=findViewById(R.id.rbtnMulti)
        var dividir:RadioButton=findViewById(R.id.rbtnDivi)
        var num1:EditText=findViewById(R.id.etxtN1)
        var num2:EditText=findViewById(R.id.etxtN2)
        var cajaresult:TextView=findViewById(R.id.txtResultado)
        if(sumar.isChecked){
            var result=Integer.parseInt(num1.text.toString())+Integer.parseInt(num2.text.toString())
            intentMain.putExtra("resultadofinal",result.toString())
            //cajaresult.setText(result.toString())
            Toast.makeText(this, "Sumando...", Toast.LENGTH_SHORT).show()
            startActivity(intentMain)
        }
        if(restar.isChecked){
            var result=Integer.parseInt(num1.text.toString())-Integer.parseInt(num2.text.toString())
            intentMain.putExtra("resultadofinal",result.toString())
            //cajaresult.setText(result.toString())
            Toast.makeText(this, "Restando...", Toast.LENGTH_SHORT).show()
            startActivity(intentMain)
        }
        if(multiplicar.isChecked){
            var result=Integer.parseInt(num1.text.toString())*Integer.parseInt(num2.text.toString())
            intentMain.putExtra("resultadofinal",result.toString())
            //cajaresult.setText(result.toString())
            Toast.makeText(this, "Multiplicando...", Toast.LENGTH_SHORT).show()
            startActivity(intentMain)
        }
        if(dividir.isChecked){
            if(Integer.parseInt(num2.text.toString())>0){
                var result=Integer.parseInt(num1.text.toString())/Integer.parseInt(num2.text.toString())
                intentMain.putExtra("resultadofinal",result.toString())
                //cajaresult.setText(result.toString())
                Toast.makeText(this, "Dividiendo...", Toast.LENGTH_SHORT).show()
                startActivity(intentMain)
            }else{
                intentMain.putExtra("resultadofinal","ERROR Division por 0")
                //cajaresult.setText("ERROR Division por 0")
                startActivity(intentMain)
            }

        }

    }
}