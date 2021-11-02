package com.example.encuesta

import Auxiliar.Encuestados
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
        var persona=Encuestados.lista[posicion]

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
        if (persona.listaAsig.contains("DAM")){
            espeDAM.isChecked=true
        }
        if (persona.listaAsig.contains("ASIR")){
            espeASIR.isChecked=true
        }
        if (persona.listaAsig.contains("DAW")){
            espeDAW.isChecked=true
        }
        hora.progress=persona.getHoras()
        numHoras.text=persona.getHoras().toString()

        progresoSeekbar()
    }

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

    fun guardar(view: View){
        var posicion = intent!!.getIntExtra("posicion",-1)
        var sisAux:String=""
        var nomAux:String=""
        var anonimo:Switch=findViewById(R.id.swAnonimo)
        var nombre:EditText=findViewById(R.id.etxtNombre)


        var sisMac:RadioButton=findViewById(R.id.rbtnMac)
        var sisWin:RadioButton=findViewById(R.id.rbtnWindows)
        var sisLin:RadioButton=findViewById(R.id.rbtnLinux)

        var espeDAM:CheckBox=findViewById(R.id.cbDAM3)
        var espeASIR:CheckBox=findViewById(R.id.cbASIR3)
        var espeDAW:CheckBox=findViewById(R.id.cbDAW3)

        if(sisMac.isChecked){sisAux=sisMac.text.toString()}
        if(sisWin.isChecked){sisAux=sisWin.text.toString()}
        if(sisLin.isChecked){sisAux=sisLin.text.toString()}


        if(anonimo.isChecked){
            nomAux="Anonimo"
        }else{
            nomAux=nombre.text.toString()
        }

        p=Persona(nomAux,sisAux)

        if(espeDAM.isChecked){p.asig(espeDAM.text.toString())}
        if(espeASIR.isChecked){p.asig(espeASIR.text.toString())}
        if(espeDAW.isChecked){p.asig(espeDAW.text.toString())}

        var hora: SeekBar =findViewById(R.id.sbHoras3)
        p.setHoras(hora.progress)

        /*val intent = Intent()
        intent.putExtra("Personas",p)

        setResult(Activity.RESULT_OK, intent)

         */
        Encuestados.lista.removeAt(posicion)
        Encuestados.lista.add(posicion,p)

        finish()
    }

    fun cancelar(view:View){
        finish()
    }


}