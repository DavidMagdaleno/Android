package com.example.camellos.Auxiliar

import android.util.Log
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.camellos.Carrera
import kotlin.random.Random

class HiloC4(var bp: ProgressBar, var camello: TextView, var podio:EditText):Thread() {

    var j=Carrera()
    override fun run() {
        while(bp.progress!=bp.max){
            bp.progress+=pasos()
            Thread.sleep(1000)
            if(bp.progress==bp.max){
                Podio.podio.add("c4")
                Log.i("valor4", "ha llegado")

                //for(ani in Podio.podio){
                    if(Podio.podio.size>=4){
                        Log.i("valorfinal", Podio.podio.toString())

                        j.ganadores()
                        Log.i("valor14", "lo ha llamado")
                        //podio.append(ani + "\r\n")
                    }
                //}

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