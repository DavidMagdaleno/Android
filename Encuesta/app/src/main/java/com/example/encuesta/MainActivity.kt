package com.example.encuesta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    var lista= arrayListOf<Persona>()

    fun validar(view:View){
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


        var persona=Persona(nomAux,sisAux)

        if(espeDAM.isChecked){persona.asig(espeDAM.text.toString())}
        if(espeASIR.isChecked){persona.asig(espeASIR.text.toString())}
        if(espeDAW.isChecked){persona.asig(espeDAW.text.toString())}

        var hora:SeekBar=findViewById(R.id.sbHoras)
        var numHoras:TextView=findViewById(R.id.txtNhoras)
        numHoras.text= hora.progress.toString()
        persona.setHoras(hora.progress)

        //Log.e("David",persona.toString())---para comprobar la salida
        lista.add(persona)

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

    //fun reiniciar(view: View){}

    fun cuantas(view:View){
        var texto:TextView=findViewById(R.id.txtResumen)
        texto.text=""
        texto.text="Hay "+lista.size.toString()+" personas en la lista"
    }
    fun resumen(view: View){
        var texto:TextView=findViewById(R.id.txtResumen)
        texto.text=""
        for(any in lista){
               //Log.e("David",any.toString())---para comprobar la salida
            texto.append(any.toString()+"\r\n")
        }
    }

}