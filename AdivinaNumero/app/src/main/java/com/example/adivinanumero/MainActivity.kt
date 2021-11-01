package com.example.adivinanumero

import Adaptador.MiAdaptador
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


        var seleccionado:Int=-1
        var ventanaactual:MainActivity=this
        var vector = arrayOf("Alto","Medio","Bajo")
        var ml: ListView = findViewById(R.id.lstDificultad)
        var miAdaptadorModificado: MiAdaptador = MiAdaptador(this,R.layout.dificultad,vector,seleccionado)
        ml.adapter = miAdaptadorModificado

        ml.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {
                //val texto = ml.getItemAtPosition(pos)
                //var p = vec.get(pos)
                //Log.e("Fernando",p.toString())
                if(pos==seleccionado){
                    seleccionado=-1
                }else{
                    if(pos==0){
                        alea = Random.nextInt(1,1000)
                        Log.i("Alto", "value=${alea}")
                    }
                    if(pos==1){
                        alea = Random.nextInt(1,100)
                        Log.i("Medio", "value=${alea}")
                    }
                    if(pos==2){
                        alea = Random.nextInt(1,10)
                        Log.i("Bajo", "value=${alea}")
                    }
                    seleccionado=pos
                }
                var miAdaptadorModificado: MiAdaptador = MiAdaptador(ventanaactual,R.layout.dificultad,vector,seleccionado)
                ml.adapter = miAdaptadorModificado
                //Toast.makeText(applicationContext, texto.toString(), Toast.LENGTH_SHORT).show()
            }
        }


    }

    var alea:Int=0


    fun boton(view: View){
        var vidas:ProgressBar=findViewById(R.id.pbVidas)
        var num:EditText=findViewById(R.id.etxtNumero)
        var nuevoboton:Button=findViewById(R.id.btnProbar)
        var nuevoIntento:Button=findViewById(R.id.btnIntento)
        //alea = Random.nextInt(1,100)
        //Log.i("Values", "value=${alea}")
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
                num.setText("")
                num.isEnabled=false
                nuevoboton.isEnabled=true
                nuevoIntento.isEnabled=false
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