package com.example.minotas

import Adaptador.MiAdaptadorTarea
import Auxiliar.Conexion
import Auxiliar.Fichero
import Auxiliar.NombreFoto
import Modelo.Notas
import Modelo.Tarea
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream

class NotaTarea : AppCompatActivity() {
    private val cameraRequest = 1888
    lateinit var miRecyclerView : RecyclerView
    lateinit var imagen:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nota_tarea)
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)
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
    var nuevaTarea:Boolean=false
    var contTarea:Int=0
    @RequiresApi(Build.VERSION_CODES.O)
    fun add(view: View){
        nuevaTarea=true
        var titulo:EditText=findViewById(R.id.etxtTarea)
        contTarea=Conexion.ultimoIDTarea(this)
        contTarea++
        if(!titulo.text.toString().equals("")){
            var e: Tarea =Tarea(cont,contTarea,titulo.text.toString(),"")
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

        }else{
            tare=Conexion.obtenerNotas(this)[posicion]
            if(!nom.text.toString().equals(tare.getAsunto())){
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

    fun tomarFoto(view: View){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, cameraRequest)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var nom:EditText=findViewById(R.id.etxtLista)
        var titulo:EditText=findViewById(R.id.etxtTarea)
        var ruta:String="/data/data/com.example.minotas/files"
        var posicion = intent!!.getIntExtra("posicion",-1)
        var tare:Notas
        var fotoFichero:File
        try {
            if (requestCode == cameraRequest) {
                val photo: Bitmap = data?.extras?.get("data") as Bitmap
                if(posicion==-1){//no haria falta distinguir
                    if(nom.text.toString().equals("")){
                        fotoFichero = File(ruta, NombreFoto.getContador()+".png")
                        var t:Tarea=Tarea(cont,contTarea,"Sin Titulo",ruta+NombreFoto.getContador())
                        Conexion.modTarea(this,cont,contTarea,t)
                    }else{
                        fotoFichero = File(ruta, NombreFoto.getContador()+".png")
                        var t:Tarea=Tarea(cont,contTarea,titulo.text.toString(),ruta+NombreFoto.getContador())
                        Conexion.modTarea(this,cont,contTarea,t)
                    }
                }else{
                    tare=Conexion.obtenerNotas(this)[posicion]
                    fotoFichero = File(ruta, NombreFoto.getContador()+".png")
                    if(nuevaTarea){
                        var t:Tarea=Tarea(tare.getId(),contTarea,titulo.text.toString(),ruta+NombreFoto.getContador())
                        Conexion.modTarea(this,tare.getId(),contTarea,t)
                    }else{
                        var t:Tarea=Tarea(tare.getId(),MiAdaptadorTarea.IddelaTarea,MiAdaptadorTarea.nombreTarea,ruta+NombreFoto.getContador())
                        Conexion.modTarea(this,tare.getId(),MiAdaptadorTarea.IddelaTarea,t)
                        MiAdaptadorTarea.IddelaTarea=-1
                        nuevaTarea=false
                    }
                }
                var uri = Uri.fromFile(fotoFichero)
                var fileOutStream = FileOutputStream(fotoFichero)
                photo.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
                fileOutStream.flush();
                fileOutStream.close();
            }
        }catch(e: Exception){
            Log.e("David",e.toString())
        }
    }
}