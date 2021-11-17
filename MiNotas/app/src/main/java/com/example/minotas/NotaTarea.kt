package com.example.minotas

import Adaptador.MiAdaptadorTarea
import Auxiliar.Conexion
import Auxiliar.Fichero
import Modelo.Notas
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
    /**
     * El recyclerview lo tenemos que instanciar en el método setUpRecyclerView() por lo que tenemos que
     * ponerle la propiedad lateinit a la variable, indicándole a Kotlin que la instanciaremos más tarde.
     */
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
            var n: Notas =Notas(cont,"Sin Titulo","Tarea","")
            Conexion.addNotaSimple(this,n)

            miRecyclerView = findViewById(R.id.rvTarea) as RecyclerView
            miRecyclerView.setHasFixedSize(true)
            miRecyclerView.layoutManager = LinearLayoutManager(this)
            var miAdapter = MiAdaptadorTarea(Conexion.obtenerTarea(this,n.getId()),this)
            miRecyclerView.adapter = miAdapter
        }else{
            var tare=Conexion.obtenerNotas(this)[posicion]
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

    fun Modificar(view: View){
        //var intentMain: Intent =  Intent(this,Modificar::class.java)
        //var Fichero: Fichero = Fichero(Encuestados.log, this)
        if (MiAdaptadorTarea.seleccionado>=0){
            //Fichero.escribirLinea("Se ha seleccionado a una Persona",Encuestados.log)
            //intentMain.putExtra("posicion",MiAdaptadorRecycler.seleccionado)
            //startActivity(intentMain)
        }
    }
}