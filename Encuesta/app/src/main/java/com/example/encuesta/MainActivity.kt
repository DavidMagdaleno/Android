package com.example.encuesta

import Auxiliar.Encuestados
import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progresoSeekbar()

    }
    //var lista= arrayListOf<Persona>()
    lateinit var p:Persona
    lateinit var intentMain: Intent

    fun validar(view:View){

        //intentMain=  Intent(this,Resumen::class.java)--para el ejerccio anterior
        intentMain=  Intent(this,ResumenListViews::class.java)
        var sisAux:String=""
        var nomAux:String=""
        var anonimo:Switch=findViewById(R.id.swAnonimo)
        var nombre:EditText=findViewById(R.id.etxtNombre)


        var sisMac:RadioButton=findViewById(R.id.rbtnMac)
        var sisWin:RadioButton=findViewById(R.id.rbtnWindows)
        var sisLin:RadioButton=findViewById(R.id.rbtnLinux)

        var espeDAM:CheckBox=findViewById(R.id.cbDAM)
        var espeASIR:CheckBox=findViewById(R.id.cbASIR)
        var espeDAW:CheckBox=findViewById(R.id.cbDAW)

        if(sisMac.isChecked){sisAux=sisMac.text.toString()}
        if(sisWin.isChecked){sisAux=sisWin.text.toString()}
        if(sisLin.isChecked){sisAux=sisLin.text.toString()}


        if(anonimo.isChecked){
            nomAux="Anonimo"
        }else{
            nomAux=nombre.text.toString()
        }

        p=Persona(nomAux,sisAux)
        //var persona=Persona(nomAux,sisAux)


        if(espeDAM.isChecked){p.asig(espeDAM.text.toString())}
        if(espeASIR.isChecked){p.asig(espeASIR.text.toString())}
        if(espeDAW.isChecked){p.asig(espeDAW.text.toString())}

        var hora:SeekBar=findViewById(R.id.sbHoras)
        p.setHoras(hora.progress)

        //Log.e("David",persona.toString())---para comprobar la salida

        Encuestados.lista.add(p)


        anonimo.isChecked=false
        nombre.setText("")
        sisMac.isChecked=false
        sisWin.isChecked=false
        sisLin.isChecked=false
        espeDAM.isChecked=false
        espeDAW.isChecked=false
        espeASIR.isChecked=false
        hora.progress=0

    }
    fun progresoSeekbar(){
        var hora:SeekBar=findViewById(R.id.sbHoras)
        var numHoras:TextView=findViewById(R.id.txtNhoras)
        hora.setOnSeekBarChangeListener(
            object : OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar,
                    progress: Int, fromUser: Boolean
                ) {
                    numHoras.setText("$progress")
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}

                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
    }

    fun reiniciar(view: View){
        var intentActual=Intent()
        finish()
        startActivity(intentActual)
    }

    fun cuantas(view:View){
        var texto:TextView=findViewById(R.id.txtResumen)
        texto.text=""
        texto.text="Hay "+Encuestados.lista.size.toString()+" personas en la lista"
    }
    fun resumen(view: View){
        //intentMain.putExtra("Personas",lista)
        startActivity(intentMain)
    }

}