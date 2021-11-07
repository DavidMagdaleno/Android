package com.example.encuesta

import Auxiliar.Conexion
import Auxiliar.Encuestados
import Auxiliar.Fichero
import Modelo.Especialidad
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener


class MainActivity : AppCompatActivity() {
    lateinit var sisAux:String
    lateinit var nomAux:String
    lateinit var anonimo:Switch
    lateinit var nombre:EditText
    lateinit var sisMac:RadioButton
    lateinit var sisWin:RadioButton
    lateinit var sisLin:RadioButton
    lateinit var espeDAM:CheckBox
    lateinit var espeASIR:CheckBox
    lateinit var espeDAW:CheckBox
    lateinit var botonReusmen:Button
    lateinit var hora:SeekBar
    lateinit var texto:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sisAux=""
        nomAux=""
        anonimo =findViewById(R.id.swAnonimo)
        nombre=findViewById(R.id.etxtNombre)
        sisMac=findViewById(R.id.rbtnMac)
        sisWin=findViewById(R.id.rbtnWindows)
        sisLin=findViewById(R.id.rbtnLinux)
        espeDAM=findViewById(R.id.cbDAM)
        espeASIR=findViewById(R.id.cbASIR)
        espeDAW=findViewById(R.id.cbDAW)
        botonReusmen=findViewById(R.id.btnResumen)
        hora=findViewById(R.id.sbHoras)
        texto=findViewById(R.id.txtResumen)


        progresoSeekbar()
        if(Conexion.numeroPersona(this)==0){
            botonReusmen.isEnabled=false
        }
    }
    var Fichero: Fichero = Fichero(Encuestados.log, this)
    lateinit var p:Persona
    lateinit var espe: Especialidad
    lateinit var intentMain: Intent
    var Idcont=0;
    var IdEspeCont=0;

    fun validar(view:View){
        Idcont=Conexion.ultimoID(this)
        Idcont++
        if(anonimo.isChecked){
            nomAux="Anonimo"
        }else{
            nomAux=nombre.text.toString()
        }
        if(sisMac.isChecked){sisAux=sisMac.text.toString()}
        if(sisWin.isChecked){sisAux=sisWin.text.toString()}
        if(sisLin.isChecked){sisAux=sisLin.text.toString()}

        if ((nombre.text.toString().trim().isEmpty() && !anonimo.isChecked) || (!sisMac.isChecked && !sisWin.isChecked && !sisLin.isChecked)
            || (!espeDAM.isChecked && !espeASIR.isChecked && !espeDAW.isChecked)){
            Toast.makeText(this, "Campos en blanco", Toast.LENGTH_SHORT).show()
        }
        else {
            p = Persona(Idcont,nomAux,sisAux,hora.progress)
            Conexion.addPersona(this, p)

            if(espeDAM.isChecked){
                IdEspeCont=1
                espe = Especialidad(Idcont,IdEspeCont,espeDAM.text.toString())
                Conexion.addPersonaEspecialidad(this,espe)
            }
            if(espeASIR.isChecked){
                IdEspeCont=2
                espe = Especialidad(Idcont,IdEspeCont,espeASIR.text.toString())
                Conexion.addPersonaEspecialidad(this,espe)
            }
            if(espeDAW.isChecked){
                IdEspeCont=3
                espe = Especialidad(Idcont,IdEspeCont,espeDAW.text.toString())
                Conexion.addPersonaEspecialidad(this,espe)
            }

            Toast.makeText(this, "Persona insertada", Toast.LENGTH_SHORT).show()
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

            botonReusmen.isEnabled=true
        }
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
        var num=Conexion.numeroPersona(this)
        texto.text=""
        texto.text="Hay "+num+" personas en la lista"
        Fichero.escribirLinea("Se ha visto el numero de Personas encuestadas",Encuestados.log)
    }
    fun resumen(view: View){
        var intentMain: Intent =  Intent(this,ResumenListViews::class.java)
        Fichero.escribirLinea("Se ha pasado a la vista Resumen",Encuestados.log)
        startActivity(intentMain)
    }

    fun verLog(view: View){
        //intentMain=  Intent(this,Log::class.java)--no se puede modificar el intent?, que desde el main te puedas dirigir a varias ventanas distintas
        //startActivity(intentMain)
        Fichero = Fichero(Encuestados.log, this)
        texto.append(Fichero.leerFichero(Encuestados.log))
    }

}