package com.example.minotas

import Adaptador.MiAdaptadorTarea
import Auxiliar.Conexion
import Auxiliar.Fichero
import Modelo.Notas
import Modelo.Tarea
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotaTarea : AppCompatActivity() {

    lateinit var miRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nota_tarea)
    }
    var cont:Int=0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        var nom:EditText=findViewById(R.id.etxtLista)
        var posicion = intent!!.getIntExtra("posicion",-1)

        if(posicion==-1){
            cont=Conexion.ultimoID(this)
            cont++
            miRecyclerView = findViewById(R.id.rvTarea) as RecyclerView
            miRecyclerView.setHasFixedSize(true)
            miRecyclerView.layoutManager = LinearLayoutManager(this)
            var miAdapter = MiAdaptadorTarea(Conexion.obtenerTarea(this,cont),this)
            miRecyclerView.adapter = miAdapter
        }else{
            var tare=Conexion.obtenerNotas(this)[posicion]
            nom.setText(tare.getAsunto())
            cont=tare.getId()
            miRecyclerView = findViewById(R.id.rvTarea) as RecyclerView
            miRecyclerView.setHasFixedSize(true)
            miRecyclerView.layoutManager = LinearLayoutManager(this)
            var miAdapter = MiAdaptadorTarea(Conexion.obtenerTarea(this,tare.getId()),this)
            miRecyclerView.adapter = miAdapter
        }
    }

    fun regreso(view: View){
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun add(view: View){
        var contTarea:Int=0
        var titulo:EditText=findViewById(R.id.etxtTarea)
        contTarea=Conexion.ultimoIDTarea(this)
        contTarea++
        if(!titulo.text.toString().equals("")){
            var e: Tarea =Tarea(cont,contTarea,titulo.text.toString())
            Conexion.addNotaTarea(this,e)
        }
        miRecyclerView = findViewById(R.id.rvTarea) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        var miAdapter = MiAdaptadorTarea(Conexion.obtenerTarea(this,cont),this)
        miRecyclerView.adapter = miAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun guardar(view: View){
        var n: Notas
        var nom:EditText=findViewById(R.id.etxtLista)
        var posicion = intent!!.getIntExtra("posicion",-1)
        var tare:Notas
        if(posicion==-1){
            cont=Conexion.ultimoID(this)
            cont++
        }else{
            tare=Conexion.obtenerNotas(this)[posicion]
            if(!nom.text.toString().equals(tare.getAsunto())){
                Log.e("sin titulo","modificado")
                n = Notas(tare.getId(),nom.text.toString(),"Tarea",tare.getFecha(),tare.getHora(),"")
                Conexion.modNota(this,tare.getId(),n)
            }
        }
        if(!nom.text.toString().equals("") && posicion<0){
            n = Notas(cont,nom.text.toString(),"Tarea","")
            Conexion.addNotaSimple(this,n)
        }
        if(nom.text.toString().equals("") && posicion<0){
            n = Notas(cont,"Sin Titulo","Tarea","")
            Conexion.addNotaSimple(this,n)
            Log.e("sin titulo","n"+n.getAsunto())
        }
        borrar()
    }
    fun borrar(){
        if(MiAdaptadorTarea.paraborrar){
            var posicion = intent!!.getIntExtra("posicion",-1)
            var tare=Conexion.obtenerNotas(this)[posicion]
            var e=Conexion.delTarea(this,tare.getId(),MiAdaptadorTarea.IddelaTarea)
            MiAdaptadorTarea.IddelaTarea=-1
        }
    }
}