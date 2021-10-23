package com.example.adivinanumero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var alea:Int=0


    fun boton(view: View){
        var vidas:ProgressBar=findViewById(R.id.pbVidas)
        var num:EditText=findViewById(R.id.etxtNumero)
        var nuevoboton:Button=findViewById(R.id.btnProbar)
        var nuevoIntento:Button=findViewById(R.id.btnIntento)
        alea = Random.nextInt(1,100)
        Log.i("Values", "value=${alea}")
        num.isEnabled=true
        nuevoboton.isEnabled=false
        nuevoIntento.isEnabled=true
        vidas.setProgress(5)
    }
    fun intento(view: View){
        var nuevoboton:Button=findViewById(R.id.btnProbar)
        var nuevoIntento:Button=findViewById(R.id.btnIntento)
        var num:EditText=findViewById(R.id.etxtNumero)
        var pista:ImageView=findViewById(R.id.imgPista)
        var ganador:TextView=findViewById(R.id.txtGanador)
        var vidas:ProgressBar=findViewById(R.id.pbVidas)
        if(vidas.progress>0){
            if(num.text.toString().toInt()<alea){
                pista.setImageResource(R.drawable.arriba)
            }
            if(num.text.toString().toInt()>alea){
                pista.setImageResource(R.drawable.abajo)
            }
            if(num.text.toString().toInt()==alea){
                pista.setImageResource(R.drawable.igual)
                ganador.setText("Has Acertado")
            }
        }else{
            pista.setImageResource(R.drawable.igual)
            ganador.setText("Has Perdido")
            num.isEnabled=false
            nuevoboton.isEnabled=true
            nuevoIntento.isEnabled=false
        }
        vidas.setProgress(vidas.progress-1)

    }
}