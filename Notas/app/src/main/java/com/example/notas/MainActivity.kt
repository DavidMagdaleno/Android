package com.example.notas

import Auxiliar.Fichero
import Auxiliar.MiAdaptador
import Auxiliar.Notas
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        archivos()
    }
    //var lista= arrayListOf<String>()
    lateinit var intentMain: Intent
    override fun onStart() {
        super.onStart()
        intentMain =  Intent(this,verNotas::class.java)
        //archivos()
        var ml: ListView = findViewById(R.id.lstNotas)
        var miAdaptadorModificado: MiAdaptador = MiAdaptador(ventanaactual,R.layout.notas,Notas.lista,seleccionado)
        ml.adapter = miAdaptadorModificado

        ml.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {

                var p = Notas.lista.get(pos)
                Log.e("pasar nota",p)
                if(pos==seleccionado){
                    seleccionado=-1
                }else{
                    seleccionado=pos
                    existe=true
                    intentMain.putExtra("posicion",pos)
                    intentMain.putExtra("existe",existe)
                    startActivity(intentMain)
                }
                var miAdaptadorModificado: MiAdaptador = MiAdaptador(ventanaactual,R.layout.notas,Notas.lista,seleccionado)
                ml.adapter = miAdaptadorModificado
                //Toast.makeText(applicationContext, texto.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }
    var existe:Boolean=false

    var seleccionado:Int=-1
    var ventanaactual:MainActivity=this


    fun archivos(){
        for(i in 0..this.fileList().size-1)
        {
            Notas.lista.add(this.fileList().get(i))
        }
    }

    fun crearArchivo(view: View){
        var nombre:EditText=findViewById(R.id.etxtNombre)
        var manejoFichero:Fichero = Fichero(nombre.text.toString(), this)
        if(!manejoFichero.existeFichero()){
            Notas.lista.add(nombre.text.toString())
            intentMain =  Intent(this,verNotas::class.java)
            intentMain.putExtra("nombreFichero",nombre.text.toString())
            Log.e("nombre",nombre.text.toString())
            startActivity(intentMain)
        }
    }
}