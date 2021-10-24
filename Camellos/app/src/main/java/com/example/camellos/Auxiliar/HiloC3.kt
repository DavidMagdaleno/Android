package com.example.camellos.Auxiliar

import android.util.Log
import android.widget.ProgressBar
import com.example.camellos.Carrera
import kotlin.random.Random

class HiloC3(var bp: ProgressBar):Thread() {

    var llegada:Boolean=false

    override fun run() {
        while(bp.progress!=bp.max){
            bp.progress+=pasos()
            Thread.sleep(1000)
            if(bp.progress==bp.max){
                llegada=true
                Podio.podio.add("c3")
            }
        }
    }

    fun pasos():Int{
        var alea = Random.nextInt(0,100)
        var paso:Int=0
        if(alea<=20){
            paso=10
        }
        if(alea>20 && alea<=50){
            paso=20
        }
        if(alea>50 && alea<=80){
            paso=30
        }
        if(alea>80 && alea<=100){
            paso=15
        }
        return paso
    }
}