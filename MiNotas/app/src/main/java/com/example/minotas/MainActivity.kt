package com.example.minotas

import Adaptador.MiAdaptador
import Auxiliar.Conexion
import Auxiliar.Fichero
import Modelo.Notas
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var opciones= ArrayList<String>()
        opciones.add("")
        opciones.add("Nota Simple")
        opciones.add("Tareas")
        var intentMain: Intent
        var sp: Spinner = findViewById(R.id.spNotas)
        val adaptador = ArrayAdapter(this, R.layout.add,R.id.txtOpcionNotas,opciones)
        sp.adapter = adaptador

        sp.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                var p = opciones.get(pos)
                if(pos==1){
                    //Fichero.escribirLinea("Se ha creado una nota simple",Encuestados.log)
                    intentMain=  Intent(ventanaactual,NotaSimple::class.java)
                    startActivity(intentMain)
                    Log.e("Opcione 0","Nota simple")
                }
                if(pos==2){
                    //Fichero.escribirLinea("Se ha creado una tarea",Encuestados.log)
                    intentMain=  Intent(ventanaactual,NotaTarea::class.java)
                    startActivity(intentMain)
                    Log.e("Opcione 1","Tarea")
                }
                //Toast.makeText(applicationContext, texto.toString(), Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })
    }

    var ventanaactual:MainActivity=this
    var seleccionado:Int=-1
    override fun onStart() {
        super.onStart()
        var intentMain: Intent
        var ml: ListView = findViewById(R.id.lstNotas)
        var miAdaptadorModificado: MiAdaptador = MiAdaptador(this,R.layout.notas,Conexion.obtenerNotas(this),seleccionado)
        ml.adapter = miAdaptadorModificado

        ml.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {
                var p = Conexion.obtenerNotas(ventanaactual).get(pos)
                Log.e("pasar nota",p.toString())
                if(pos==seleccionado){
                    seleccionado=-1
                }else{
                    //Fichero.escribirLinea("Se ha seleccionado una nota",Encuestados.log)
                    seleccionado=pos
                    if(p.getTipo().equals("Nota Simple")){
                        //Fichero.escribirLinea("Se ha seleccionado una nota simple",Encuestados.log)
                        intentMain =  Intent(ventanaactual,NotaSimple::class.java)
                        intentMain.putExtra("posicion",pos)
                        startActivity(intentMain)
                    }
                    if(p.getTipo().equals("Tarea")){
                        //Fichero.escribirLinea("Se ha seleccionado una tarea",Encuestados.log)
                        intentMain=  Intent(ventanaactual,NotaTarea::class.java)
                        intentMain.putExtra("posicion",pos)
                        startActivity(intentMain)
                    }
                }
                var miAdaptadorModificado: MiAdaptador = MiAdaptador(ventanaactual,R.layout.notas,Conexion.obtenerNotas(ventanaactual),seleccionado)
                ml.adapter = miAdaptadorModificado
                //Toast.makeText(applicationContext, texto.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        ml.onItemLongClickListener = object: AdapterView.OnItemLongClickListener{
            override fun onItemLongClick( parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
                var p = Conexion.obtenerNotas(ventanaactual).get(position)

                if(p.getTipo().equals("Nota Simple")){
                    //Fichero.escribirLinea("Se ha eliminado una nota simple",Encuestados.log)
                    Conexion.delNota(ventanaactual,p.getId())
                }
                if(p.getTipo().equals("Tarea")){
                    //Fichero.escribirLinea("Se ha eliminado una tarea",Encuestados.log)
                    Conexion.delNotaTarea(ventanaactual,p.getId())
                }
                return true
                var miAdaptadorModificado: MiAdaptador = MiAdaptador(ventanaactual,R.layout.notas,Conexion.obtenerNotas(ventanaactual),seleccionado)
                ml.adapter = miAdaptadorModificado
            }
        }

    }
}