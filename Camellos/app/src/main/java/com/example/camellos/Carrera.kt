package com.example.camellos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.camellos.Auxiliar.*
import kotlin.math.max

class Carrera : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrera)

        var c1:TextView=findViewById(R.id.lblC1)
        var c2:TextView=findViewById(R.id.lblC2)
        var c3:TextView=findViewById(R.id.lblC3)
        var c4:TextView=findViewById(R.id.lblC4)

        c1.text=intent.getStringExtra("camel1")
        c2.text=intent.getStringExtra("camel2")
        c3.text=intent.getStringExtra("camel3")
        c4.text=intent.getStringExtra("camel4")

        var podio= arrayListOf<String>()
    }

    var h1:HiloC1?=null
    var h2:HiloC2?=null
    var h3:HiloC3?=null
    var h4:HiloC4?=null
    //var podio= arrayListOf<String>()

    fun Iniciar(view: View){
        var c1:TextView=findViewById(R.id.lblC1)
        var c2:TextView=findViewById(R.id.lblC2)
        var c3:TextView=findViewById(R.id.lblC3)
        var c4:TextView=findViewById(R.id.lblC4)


        var ganadores:TextView=findViewById(R.id.txtPodio)
        h1= HiloC1(findViewById(R.id.pbC1))
        h2= HiloC2(findViewById(R.id.pbC2))
        h3= HiloC3(findViewById(R.id.pbC3))
        h4= HiloC4(findViewById(R.id.pbC4))

        h1!!.start()
        h2!!.start()
        h3!!.start()
        h4!!.start()

        while(h1!!.isAlive || h2!!.isAlive || h3!!.isAlive || h4!!.isAlive){
            Log.i("valorTodoMueros", "Muertos")
            for (ani in Podio.podio) {
                Log.i("valorAni", ani)
                if (ani.equals("c1")) {
                    ganadores.append(c1.text.toString() + "\r\n")
                }
                if (ani.equals("c2")) {
                    ganadores.append(c2.text.toString() + "\r\n")
                }
                if (ani.equals("c3")) {
                    ganadores.append(c3.text.toString() + "\r\n")
                }
                if (ani.equals("c4")) {
                    ganadores.append(c4.text.toString() + "\r\n")
                }
            }
        }
    }
}