package com.example.encuesta

import Auxiliar.Encuestados
import Auxiliar.Fichero
import MiAdaptador.MiAdaptador
import Auxiliar.Conexion
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen_list_views)
    }

    var Fichero: Fichero = Fichero(Encuestados.log, this)

    override fun onStart() {
        super.onStart()
        var intentMain: Intent =  Intent(this,Modificar::class.java)
        var ml: ListView = findViewById(R.id.lstResumen)
        var miAdaptadorModificado: MiAdaptador = MiAdaptador(ventanaactual,R.layout.resumenxml,Conexion.obtenerPersonas(this),seleccionado)
        ml.adapter = miAdaptadorModificado

        ml.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {
                var p = Conexion.obtenerPersonas(ventanaactual).get(pos)
                Log.e("pasar persona",p.toString())
                if(pos==seleccionado){
                    seleccionado=-1
                }else{
                    Fichero.escribirLinea("Se ha seleccionado a una Persona",Encuestados.log)
                    seleccionado=pos
                    //var aux=Conexion.obtenerEspecialidad(ventanaactual,p.getId())
                    //Encuestados.listaespe=aux
                    intentMain.putExtra("posicion",pos)
                    startActivity(intentMain)
                }
                var miAdaptadorModificado: MiAdaptador = MiAdaptador(ventanaactual,R.layout.resumenxml,Conexion.obtenerPersonas(ventanaactual),seleccionado)
                ml.adapter = miAdaptadorModificado
                //Toast.makeText(applicationContext, texto.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }


    var seleccionado:Int=-1
    var ventanaactual:ResumenListViews=this

    fun regreso(view: View){
        finish()
    }

}