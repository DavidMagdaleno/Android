package com.example.encuesta

import Auxiliar.Encuestados
import Auxiliar.Fichero
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
        var botonReusmen:Button=findViewById(R.id.btnResumen)
        if(Encuestados.lista.isEmpty()){
            botonReusmen.isEnabled=false
        }

    }
    var Fichero: Fichero = Fichero(Encuestados.log, this)
    lateinit var p:Persona
    lateinit var intentMain: Intent

    fun validar(view:View){

        intentMain=  Intent(this,ResumenListViews::class.java)

        var botonReusmen:Button=findViewById(R.id.btnResumen)
        botonReusmen.isEnabled=true

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

        Encuestados.lista.add(p)

        Fichero.escribirLinea("Se ha validado una Persona",Encuestados.log)


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
        Fichero.escribirLinea("Se ha reiniciado la Encuesta",Encuestados.log)
        var anonimo:Switch=findViewById(R.id.swAnonimo)
        var nombre:EditText=findViewById(R.id.etxtNombre)
        var sisMac:RadioButton=findViewById(R.id.rbtnMac)
        var sisWin:RadioButton=findViewById(R.id.rbtnWindows)
        var sisLin:RadioButton=findViewById(R.id.rbtnLinux)
        var espeDAM:CheckBox=findViewById(R.id.cbDAM)
        var espeASIR:CheckBox=findViewById(R.id.cbASIR)
        var espeDAW:CheckBox=findViewById(R.id.cbDAW)
        var hora:SeekBar=findViewById(R.id.sbHoras)
        anonimo.isChecked=false
        nombre.setText("")
        sisMac.isChecked=false
        sisWin.isChecked=false
        sisLin.isChecked=false
        espeDAM.isChecked=false
        espeDAW.isChecked=false
        espeASIR.isChecked=false
        hora.progress=0
        //var intentActual=Intent()
        //finish()
        //startActivity(intentActual)
    }

    fun cuantas(view:View){
        var texto:TextView=findViewById(R.id.txtResumen)
        texto.text=""
        texto.text="Hay "+Encuestados.lista.size.toString()+" personas en la lista"
        Fichero.escribirLinea("Se ha visto el numero de Personas encuestadas",Encuestados.log)
    }
    fun resumen(view: View){
        //intentMain.putExtra("Personas",lista)
        Fichero.escribirLinea("Se ha pasado a la vista Resumen",Encuestados.log)
        startActivity(intentMain)
    }

    fun verLog(view: View){
        //intentMain=  Intent(this,Log::class.java)--no se puede modificar el intent?, que desde el main te puedas dirigir a varias ventanas distintas
        //startActivity(intentMain)
        var texto:TextView=findViewById(R.id.txtResumen)
        Fichero = Fichero(Encuestados.log, this)
        texto.append(Fichero.leerFichero(Encuestados.log))
    }

}