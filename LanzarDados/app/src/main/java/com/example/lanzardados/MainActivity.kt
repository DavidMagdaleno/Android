package com.example.lanzardados

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import java.util.*
import kotlin.collections.RandomAccess
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    var contjugador:Int=0
    var contPc:Int=0
    var cont=0


    fun TirarDado(view: View){
        cont++
        var imagePc:ImageView=findViewById(R.id.imgDadoPc)
        var imagenJugador:ImageView=findViewById(R.id.imgDadojugador)

            var alea = Random.nextInt(1,6)
            var alea2 = Random.nextInt(1,6)
            when(alea){
                1->{imagenJugador.setImageResource(R.drawable.dado1)
                    contjugador++}
                2->{imagenJugador.setImageResource(R.drawable.dado2)
                    contjugador+2}
                3->{imagenJugador.setImageResource(R.drawable.dado3)
                    contjugador+3}
                4->{imagenJugador.setImageResource(R.drawable.dado4)
                    contjugador+4}
                5->{imagenJugador.setImageResource(R.drawable.dado5)
                    contjugador+5}
                6->{imagenJugador.setImageResource(R.drawable.dado6)
                    contjugador+6}
            }
            when(alea2){
                1->{imagePc.setImageResource(R.drawable.dado1)
                    contPc++}
                2->{imagePc.setImageResource(R.drawable.dado2)
                    contPc+2}
                3->{imagePc.setImageResource(R.drawable.dado3)
                    contPc+3}
                4->{imagePc.setImageResource(R.drawable.dado4)
                    contPc+4}
                5->{imagePc.setImageResource(R.drawable.dado5)
                    contPc+5}
                6->{imagePc.setImageResource(R.drawable.dado6)
                    contPc+6}
            }
        if(cont==3){
            if(contjugador>contPc){}
        }
    }
}