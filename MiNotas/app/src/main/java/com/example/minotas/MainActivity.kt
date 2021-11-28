package com.example.minotas

import Adaptador.MiAdaptador
import Adaptador.MiAdaptadorTarea
import Auxiliar.Conexion
import Auxiliar.Fichero
import Auxiliar.NombreFoto
import Modelo.Notas
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import android.widget.EditText
import com.google.android.material.internal.ContextUtils.getActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var opciones= ArrayList<String>()
        opciones.add("                  Crear")
        opciones.add("              Nota Simple")
        opciones.add("                 Tareas")
        var Fichero: Fichero = Fichero(NombreFoto.log, this)
        var intentMain: Intent
        var sp: Spinner = findViewById(R.id.spNotas)
        val adaptador = ArrayAdapter(this, R.layout.add,R.id.txtOpcionNotas,opciones)
        sp.adapter = adaptador

        sp.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                var p = opciones.get(pos)
                if(pos==1){
                    Fichero.escribirLinea("Se ha creado una nota simple",NombreFoto.log)
                    intentMain=  Intent(ventanaactual,NotaSimple::class.java)
                    startActivity(intentMain)
                }
                if(pos==2){
                    Fichero.escribirLinea("Se ha creado una tarea",NombreFoto.log)
                    intentMain=  Intent(ventanaactual,NotaTarea::class.java)
                    startActivity(intentMain)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })
    }

    var ventanaactual:MainActivity=this
    var seleccionado:Int=-1
    override fun onStart() {
        super.onStart()
        var Fichero: Fichero = Fichero(NombreFoto.log, this)
        var intentMain: Intent
        var ml: ListView = findViewById(R.id.lstNotas)
        var miAdaptadorModificado: MiAdaptador = MiAdaptador(this,R.layout.notas,Conexion.obtenerNotas(this),seleccionado)
        ml.adapter = miAdaptadorModificado

        ml.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {
                var p = Conexion.obtenerNotas(ventanaactual).get(pos)
                if(pos==seleccionado){
                    seleccionado=-1
                }else{
                    Fichero.escribirLinea("Se ha seleccionado una nota",NombreFoto.log)
                    seleccionado=pos
                    if(p.getTipo().equals("Nota Simple")){
                        Fichero.escribirLinea("Se ha seleccionado una nota simple",NombreFoto.log)
                        intentMain =  Intent(ventanaactual,NotaSimple::class.java)
                        intentMain.putExtra("posicion",pos)
                        startActivity(intentMain)
                    }
                    if(p.getTipo().equals("Tarea")){
                        Fichero.escribirLinea("Se ha seleccionado una tarea",NombreFoto.log)
                        intentMain=  Intent(ventanaactual,NotaTarea::class.java)
                        intentMain.putExtra("posicion",pos)
                        startActivity(intentMain)
                    }
                }
                var miAdaptadorModificado: MiAdaptador = MiAdaptador(ventanaactual,R.layout.notas,Conexion.obtenerNotas(ventanaactual),seleccionado)
                ml.adapter = miAdaptadorModificado
            }
        }
        ml.onItemLongClickListener = object: AdapterView.OnItemLongClickListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
                var p = Conexion.obtenerNotas(ventanaactual).get(position)

                val dialogo: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                dialogo.setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, which ->
                        if(p.getTipo().equals("Nota Simple")){
                            Fichero.escribirLinea("Se ha eliminado una nota simple",NombreFoto.log)
                            Conexion.delNota(ventanaactual,p.getId())
                        }
                        if(p.getTipo().equals("Tarea")){
                            Fichero.escribirLinea("Se ha eliminado una tarea",NombreFoto.log)
                            Conexion.delNotaTarea(ventanaactual,p.getId())
                        }
                    })
                dialogo.setNegativeButton(
                    "CANCELAR",
                    DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                dialogo.setTitle("¿Borrar Elemento?")
                dialogo.setMessage("¿Deseas eliminar este elemento?")
                dialogo.show()

                return true
                var miAdaptadorModificado: MiAdaptador = MiAdaptador(ventanaactual,R.layout.notas,Conexion.obtenerNotas(ventanaactual),seleccionado)
                ml.adapter = miAdaptadorModificado
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun buscar(view: View){
        createSimpleDialog()
    }
    @SuppressLint("RestrictedApi")
    @RequiresApi(Build.VERSION_CODES.O)
    fun createSimpleDialog(): Boolean {
        val dialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        val Myview=layoutInflater.inflate(R.layout.buscar, null)
        var basunto = Myview.findViewById<EditText>(R.id.asunto)
        dialogo.setView(Myview)
        dialogo.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                var e= Conexion.buscarNotas(this,basunto.text.toString())
                var intentMain: Intent =  Intent(ventanaactual,NotaSimple::class.java)
                if (e != null) {
                    intentMain.putExtra("posicion",e-1)
                    startActivity(intentMain)
                }
            })
        dialogo.setNegativeButton("CANCELAR",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
        dialogo.setTitle("Buscar Nota")
        dialogo.setMessage("Escribe el titulo de la nota")
        dialogo.show()

        return true
    }
}