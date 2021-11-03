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
    }
    //var lista= arrayListOf<String>()
    var intentMain: Intent =  Intent(this,verNotas::class.java)
    override fun onStart() {
        super.onStart()
        archivos()
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
    var nombre:EditText=findViewById(R.id.etxtNombre)
    var seleccionado:Int=-1
    var ventanaactual:MainActivity=this
    var manejoFichero:Fichero = Fichero(nombre.text.toString(), this)

    fun archivos(){
        //Con estas líneas podemos ver los ficheros definidos en la aplicación.
        for(i in 0..this.fileList().size-1)
        {
            Notas.lista.add(this.fileList().get(i))
        }
    }

    fun crearArchivo(view: View){
        if(!manejoFichero.existeFichero()){
            intentMain.putExtra("nombreFichero",nombre.text.toString())
            startActivity(intentMain)
        }
    }
}