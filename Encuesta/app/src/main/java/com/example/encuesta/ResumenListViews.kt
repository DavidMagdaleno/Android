package com.example.encuesta

import Auxiliar.Encuestados
import MiAdaptador.MiAdaptador
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class ResumenListViews : AppCompatActivity() {
var primera:Boolean=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen_list_views)

       /*var intentMain: Intent =  Intent(this,Modificar::class.java)
        //var persona = intent.getStringArrayListExtra("Personas") as ArrayList<Persona>
        var ml: ListView = findViewById(R.id.lstResumen)
        var miAdaptadorModificado: MiAdaptador = MiAdaptador(this,R.layout.resumenxml,Encuestados.lista,seleccionado)
        ml.adapter = miAdaptadorModificado
        primera=false

        ml.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {
                //val texto = ml.getItemAtPosition(pos)
                var p = Encuestados.lista.get(pos)
                Log.e("pasar persona",p.toString())
                if(pos==seleccionado){
                    seleccionado=-1
                }else{
                    seleccionado=pos
                    intentMain.putExtra("posicion",pos)
                    //intentMain.putExtra("Personas",p)
                    //registro(intentMain)
                    startActivity(intentMain)
                }
                var miAdaptadorModificado: MiAdaptador = MiAdaptador(ventanaactual,R.layout.resumenxml,Encuestados.lista,seleccionado)
                ml.adapter = miAdaptadorModificado
                //Toast.makeText(applicationContext, texto.toString(), Toast.LENGTH_SHORT).show()
            }
        }*/

    }


    override fun onStart() {
        super.onStart()
        var intentMain: Intent =  Intent(this,Modificar::class.java)
        var ml: ListView = findViewById(R.id.lstResumen)
        var miAdaptadorModificado: MiAdaptador = MiAdaptador(ventanaactual,R.layout.resumenxml,Encuestados.lista,seleccionado)
        ml.adapter = miAdaptadorModificado

        ml.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {
                var p = Encuestados.lista.get(pos)
                Log.e("pasar persona",p.toString())
                if(pos==seleccionado){
                    seleccionado=-1
                }else{
                    seleccionado=pos
                    intentMain.putExtra("posicion",pos)
                    //intentMain.putExtra("Personas",p)
                    //registro(intentMain)
                    startActivity(intentMain)
                }
                var miAdaptadorModificado: MiAdaptador = MiAdaptador(ventanaactual,R.layout.resumenxml,Encuestados.lista,seleccionado)
                ml.adapter = miAdaptadorModificado
                //Toast.makeText(applicationContext, texto.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }


    var seleccionado:Int=-1
    var ventanaactual:ResumenListViews=this

    /*fun registro(intentMain:Intent){
        resultLauncher.launch(intentMain)
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            var persona = intent.getStringArrayListExtra("Personas") as ArrayList<Persona>
            var ml: ListView = findViewById(R.id.lstResumen)
            val data: Intent? = result.data
            val returnString:Persona = data!!.getSerializableExtra("Personas") as Persona
            persona.removeAt(seleccionado)
            persona.add(seleccionado,returnString)
            var miAdaptadorModificado: MiAdaptador = MiAdaptador(ventanaactual,R.layout.resumenxml,persona,seleccionado)
            ml.adapter = miAdaptadorModificado
        }
    }*/

    fun regreso(view: View){
        finish()
    }

}