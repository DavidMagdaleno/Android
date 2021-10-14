package com.example.encuesta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private var lista= arrayListOf<Persona>()
    private var text:TextView=findViewById(R.id.txtResumen)

    fun validar(view:View){
        var sisAux:String=""
        var espeAux:String=""
        var nombre:EditText=findViewById(R.id.etxtNombre)

        var sisMac:RadioButton=findViewById(R.id.rbtnMac)
        var sisWin:RadioButton=findViewById(R.id.rbtnWindows)
        var sisLin:RadioButton=findViewById(R.id.rbtnLinux)

        var espeDAM:CheckBox=findViewById(R.id.cbDAM)
        var espeASIR:CheckBox=findViewById(R.id.cbASIR)
        var espeDAW:CheckBox=findViewById(R.id.cbDAW)

        //var hora:View=findViewById(R.id.sbHoras)

        if(sisMac.isChecked){sisAux=sisMac.toString()}
        if(sisWin.isChecked){sisAux=sisWin.toString()}
        if(sisLin.isChecked){sisAux=sisLin.toString()}

        if(espeDAM.isChecked){espeAux=espeAux+","+espeDAM.toString()}
        if(espeASIR.isChecked){espeAux=espeAux+","+espeASIR.toString()}
        if(espeDAW.isChecked){espeAux=espeAux+","+espeDAW.toString()}

        var persona=Persona(nombre.toString(),sisAux,espeAux,1)

        lista.add(persona)
    }
    //fun reiniciar(view: View){}

    fun cuantas(view:View){
        text.text="Hay "+lista.size.toString()+" personas en la lista"
    }
    fun resumen(view: View){
        var textAux:String=""
        for(any in lista){
            textAux=text.toString()
            text.text= textAux+"\r\n"+any.getNombre()+","+any.getSistema()+","+any.getEspecialidad()+","+any.getHoras()
        }
    }

}