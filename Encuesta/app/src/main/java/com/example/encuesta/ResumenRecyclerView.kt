package com.example.encuesta

import Auxiliar.Conexion
import Auxiliar.Encuestados
import Auxiliar.Fichero
import MiAdaptador.MiAdaptador
import MiAdaptador.MiAdaptadorRecycler
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ResumenRecyclerView : AppCompatActivity() {

    /**
     * El recyclerview lo tenemos que instanciar en el método setUpRecyclerView() por lo que tenemos que
     * ponerle la propiedad lateinit a la variable, indicándole a Kotlin que la instanciaremos más tarde.
     */
    lateinit var miRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen_recycler_view)
    }

    override fun onStart() {
        super.onStart()
        miRecyclerView = findViewById(R.id.reciclerviewEncuesta) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        var miAdapter = MiAdaptadorRecycler(Conexion.obtenerPersonas(this), this)
        miRecyclerView.adapter = miAdapter
    }

    fun regreso(view: View){
        finish()
    }


    fun Modificar(view: View){
        var intentMain: Intent =  Intent(this,Modificar::class.java)
        var Fichero: Fichero = Fichero(Encuestados.log, this)
        if (MiAdaptadorRecycler.seleccionado>=0){
            Fichero.escribirLinea("Se ha seleccionado a una Persona",Encuestados.log)
            intentMain.putExtra("posicion",MiAdaptadorRecycler.seleccionado)
            startActivity(intentMain)
        }
    }
}