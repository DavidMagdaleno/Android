package com.example.ejemplofichero

import Auxiliar.Fichero
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.io.*

class MainActivity : AppCompatActivity() {

    var manejoFichero:Fichero = Fichero("otro.txt", this)
    lateinit var edContenido:EditText
    lateinit var edLinea:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edContenido = findViewById(R.id.edContenidoFichero)
        edLinea = findViewById(R.id.edLinea)
        /* //Con estas líneas podemos ver los ficheros definidos en la aplicación.
        var ed:EditText = findViewById(R.id.edContenidoFichero)
        for(i in 0..this.fileList().size-1)
        {
            ed.append(this.fileList().get(i) + "\n")
        }
         */
    }

    fun grabar(view: View){
        manejoFichero.escribirLinea(edLinea.text.toString())
        this.leer(view)
    }

    fun leer(view: View){
        edContenido.setText("")
        if(!manejoFichero.existeFichero()) {
            edContenido.setText("El fichero no existe.")
        }
        else {
            edContenido.setText(manejoFichero.leerFichero())
        }
    }

    fun borrarContenidoFichero(view:View){
        val dialog = AlertDialog.Builder(this)
            .setTitle("Borrado del contenido.")
            .setMessage("¿Estás seguro?")
            .setNegativeButton("No") { view, _ ->
                Toast.makeText(this, "Se ha cancelado el borrado", Toast.LENGTH_SHORT).show()
                view.dismiss()
            }
            .setPositiveButton("Sí") { view, _ ->
                Toast.makeText(this, "Se ha borrado el contenido del fichero", Toast.LENGTH_SHORT).show()
                this.manejoFichero.borrarFichero()
                this.edContenido.setText("")
                view.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }

    fun borrarFicheroFisico(view: View){
        val dialog = AlertDialog.Builder(this)
            .setTitle("Borrado del fichero físico.")
            .setMessage("¿Estás seguro?")
            .setNegativeButton("No") { view, _ ->
                Toast.makeText(this, "Se ha cancelado el borrado", Toast.LENGTH_SHORT).show()
                view.dismiss()
            }
            .setPositiveButton("Sí") { view, _ ->
                Toast.makeText(this, "Se ha borrado el fichero físico", Toast.LENGTH_SHORT).show()
                manejoFichero.borrarFicheroFisico()
                val inte = intent
                Toast.makeText(this,"Fichero borrado",Toast.LENGTH_LONG).show()
                finish()
                startActivity(inte)
                view.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()



    }
}