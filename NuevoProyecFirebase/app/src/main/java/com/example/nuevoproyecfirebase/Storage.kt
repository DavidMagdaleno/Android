package com.example.nuevoproyecfirebase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_storage.*
import java.io.File
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import java.io.InputStream
import com.firebase.ui.storage.images.FirebaseImageLoader

import com.bumptech.glide.module.AppGlideModule




class Storage : AppCompatActivity() {

    private val File=1
    private val database=Firebase.storage
    val ref=database.getReference("user")
    val ref2=database.getReference()
    val desRef = Firebase.storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        btCargar.setOnClickListener{
            fileUpload()
        }

        btDescargar.setOnClickListener{
            mostrarImagen()
        }
        //import com.google.firebase.storage.ktx.component1 and component2 se usan para progreso y pausa de la tarea
        //EJEMPLO
        /*uploadTask.addOnProgressListener { (bytesTransferred, totalByteCount) ->
            val progress = (100.0 * bytesTransferred) / totalByteCount
            Log.d(TAG, "Upload is $progress% done")
        }.addOnPausedListener {
            Log.d(TAG, "Upload is paused")
        }*/
    }

    fun fileUpload(){
        val intent=Intent(Intent.ACTION_GET_CONTENT)
        intent.type="*/*"
        startActivityForResult(intent,File)
    }
    //-------------------------Metodo 1-----------------------------------------------------------------------------------
    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==File){
            if(resultCode== RESULT_OK){
                val FileUri=data!!.data
                //para decirle la carpeta donde se almacenara
                val folder:StorageReference=FirebaseStorage.getInstance().getReference().child("User")
                //para cambiar el nombre de la imagen
                val file_name:StorageReference=folder.child("file"+FileUri!!.lastPathSegment)
                //con esto subimos la imagen
                file_name.putFile(FileUri).addOnSuccessListener { taskSnapshoot ->
                    //hay cojemos la url de la imagen
                    file_name.downloadUrl.addOnSuccessListener { uri ->
                        val hashMap=HashMap<String,String>()
                        hashMap["link"]=java.lang.String.valueOf(uri)
                        ref.setValue(hashMap)//-----------------------------------------ERROR setValue
                        Log.e("Conseguido","Archivo subido con exito")
                    }
                }
            }
        }
    }*/
    //-----------------------------------------------------------------------------------------------------------------------
    //--------------------------Metodo 2(web android)------------------------------------------------------------------------
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==File){
            if(resultCode== RESULT_OK){
                val FileUri=data!!.data
                //para decirle la carpeta donde se almacenara y su nombre
                //si en el ref se lo pone un path se crean subcarpertas
                //val imagenRef = ref2.child("images/${FileUri!!.lastPathSegment}")
                val imagenRef = ref2.child("${FileUri!!.lastPathSegment}")
                //con esto subimos la imagen
                 var uploadTask = imagenRef.putFile(FileUri)
                /*
                //para conseguir la url
                val urlTask = uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    ref.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                    } else {
                        // Handle failures
                        // ...
                    }
                }
                 */
                uploadTask.addOnFailureListener {
                    // Handle unsuccessful uploads
                }.addOnSuccessListener { taskSnapshot ->
                    Log.e("Conseguido","Archivo subido con exito")
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...
                }

            }
        }
    }
    //-------------------Metodo para descargar de cloud(web android)--------------------------------
    fun mostrarImagen(){
        Glide.with(this /* context */)
            .load(desRef)
            .centerCrop()
            .into(img)
    }

        /*@GlideModule
        class AppGlide: AppGlideModule(){

            override fun registerComponents(
            context: android.content.Context,
            glide: Glide,
            registry: Registry
            ) {
                super.registerComponents(context, glide, registry)
                registry.append(
                    StorageReference::class.java, InputStream::class.java,
                    FirebaseImageLoader.Factory()
                )
            }
        }*/
}