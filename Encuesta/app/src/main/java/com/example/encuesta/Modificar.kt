package com.example.encuesta


import Auxiliar.Fichero
import Auxiliar.Conexion
import Auxiliar.Encuestados
import Modelo.Especialidad
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class Modificar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar)

        //var persona = intent.getSerializableExtra("Personas") as Persona
        var posicion = intent!!.getIntExtra("posicion",-1)
        var persona=Conexion.obtenerPersonas(this)[posicion]
        var espec=Conexion.obtenerEspecialidad(this,persona.getId())


        var anoni:Switch=findViewById(R.id.swAnonimo)
        var nombre:EditText=findViewById(R.id.etxtNombre)
        var sisMac: RadioButton =findViewById(R.id.rbtnMac)
        var sisWin: RadioButton =findViewById(R.id.rbtnWindows)
        var sisLin: RadioButton =findViewById(R.id.rbtnLinux)
        var espeDAM:CheckBox=findViewById(R.id.cbDAM3)
        var espeASIR:CheckBox=findViewById(R.id.cbASIR3)
        var espeDAW:CheckBox=findViewById(R.id.cbDAW3)
        var hora: SeekBar =findViewById(R.id.sbHoras3)
        var numHoras:TextView=findViewById(R.id.txtNhoras2)


        hora.progress=0

        if (persona.getNombre().equals("Anonimo")){
            anoni.isChecked=true
        }else{
            nombre.setText(persona.getNombre())
        }
        if (persona.getSistema().equals("Mac")){
            sisMac.isChecked=true
        }
        if (persona.getSistema().equals("Windows")){
            sisWin.isChecked=true
        }
        if (persona.getSistema().equals("Linux")){
            sisLin.isChecked=true
        }
        if (espec.contains("DAM")){
            espeDAM.isChecked=true
            cogidoDAM=true
        }
        if (espec.contains("ASIR")){
            espeASIR.isChecked=true
            cogidoASIR=true
        }
        if (espec.contains("DAW")){
            espeDAW.isChecked=true
            cogidoDAW=true
        }
        hora.progress=persona.getHoras()
        numHoras.text=persona.getHoras().toString()

        progresoSeekbar()
    }
    var cogidoDAM:Boolean=false
    var cogidoDAW:Boolean=false
    var cogidoASIR:Boolean=false
    fun progresoSeekbar(){
        var hora:SeekBar=findViewById(R.id.sbHoras3)
        var numHoras:TextView=findViewById(R.id.txtNhoras2)
        hora.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
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
    lateinit var p:Persona
    lateinit var e:Especialidad
    var Fichero: Fichero = Fichero(Encuestados.log, this)
    var IdEspeCont=0;

    fun guardar(view:View){
        var posicion = intent!!.getIntExtra("posicion",-1)
        var persona=Conexion.obtenerPersonas(this)[posicion]
        var espec=Conexion.obtenerEspecialidad(this,persona.getId())

        var sisAux:String=""
        var nomAux:String=""
        var anonimo:Switch=findViewById(R.id.swAnonimo)
        var nombre:EditText=findViewById(R.id.etxtNombre)
        var hora:SeekBar=findViewById(R.id.sbHoras3)

        var sisMac:RadioButton=findViewById(R.id.rbtnMac)
        var sisWin:RadioButton=findViewById(R.id.rbtnWindows)
        var sisLin:RadioButton=findViewById(R.id.rbtnLinux)

        var espeDAM:CheckBox=findViewById(R.id.cbDAM3)
        var espeASIR:CheckBox=findViewById(R.id.cbASIR3)
        var espeDAW:CheckBox=findViewById(R.id.cbDAW3)

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
            p = Persona(persona.getId(),nomAux,sisAux,hora.progress)
            Conexion.modPersona(this,persona.getId(),p)

            if(espeDAM.isChecked){
                if(!cogidoDAM){
                    IdEspeCont=1
                    e = Especialidad(persona.getId(),IdEspeCont,espeDAM.text.toString())
                    Conexion.modPersonaEspecialidad(this,persona.getId(),e)
                }
            }
            if(!espeDAM.isChecked){
                if(cogidoDAM){
                    IdEspeCont=1
                    var aux=Conexion.delEspecialiad(this,persona.getId(),IdEspeCont)
                    Toast.makeText(this, "Se han eliminado "+aux+" una especialidad", Toast.LENGTH_SHORT).show()
                }
            }
            if(espeASIR.isChecked){
                if(!cogidoASIR){
                    IdEspeCont=2
                    e = Especialidad(persona.getId(),IdEspeCont,espeASIR.text.toString())
                    Conexion.modPersonaEspecialidad(this,persona.getId(),e)
                }
            }
            if(!espeASIR.isChecked){
                if(cogidoASIR){
                    IdEspeCont=2
                    var aux=Conexion.delEspecialiad(this,persona.getId(),IdEspeCont)
                    Toast.makeText(this, "Se han eliminado "+aux+" una especialidad", Toast.LENGTH_SHORT).show()
                }
            }
            if(espeDAW.isChecked){
                if(cogidoDAW){
                    IdEspeCont=3
                    e = Especialidad(persona.getId(),IdEspeCont,espeDAW.text.toString())
                    Conexion.modPersonaEspecialidad(this,persona.getId(),e)
                }
            }
            if(!espeASIR.isChecked){
                if(cogidoASIR){
                    IdEspeCont=3
                    var aux=Conexion.delEspecialiad(this,persona.getId(),IdEspeCont)
                    Toast.makeText(this, "Se han eliminado "+aux+" una especialidad", Toast.LENGTH_SHORT).show()
                }
            }

            Toast.makeText(this, "Persona Modificada", Toast.LENGTH_SHORT).show()
            Fichero.escribirLinea("Se ha Modificado una Persona",Encuestados.log)
            finish()
        }
    }

    fun cancelar(view:View){
        finish()
    }


}