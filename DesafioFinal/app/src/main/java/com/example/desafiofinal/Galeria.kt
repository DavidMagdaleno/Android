package com.example.desafiofinal

import MiAdaptador.AdaptadorEventos
import MiAdaptador.AdaptadorFotos
import Model.Imagenes
import Model.User
import android.R.attr
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_eventos.*
import kotlinx.android.synthetic.main.activity_galeria.*
import java.io.ByteArrayOutputStream
import android.R.attr.bitmap
import android.annotation.SuppressLint
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import android.graphics.drawable.Drawable
import android.media.ImageReader
import android.provider.ContactsContract
import androidx.core.net.toFile
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.NonCancellable.join
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking


class Galeria : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance() //Variable con la que accederemos a Firestore. Será una instancia a la bd.
    private val File=1
    private val database= Firebase.storage
    //val ref=database.getReference("user")
    val ref2=database.getReference()
    //val desRef = Firebase.storage.reference
    var miArray2:ArrayList<Imagenes> = ArrayList()
    lateinit var miRecyclerView : RecyclerView
    val ONE_MEGABYTE: Long = 1024 * 1024


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galeria)

        miRecyclerView = findViewById(R.id.rvFotos) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)


        val bundle:Bundle? = intent.extras
        val ev = bundle?.getString("tituloEvento").toString()
        var email = intent.getStringExtra("user")

        btnSubirFoto.setOnClickListener(){
            fileUpload()
            db.collection("eventos").document(ev).get().addOnSuccessListener {
                var f=it.get("fotos").toString()
                var user = hashMapOf(
                    "Ubicacion" to it.get("Ubicacion").toString(),
                    "asistentes" to it.get("asistentes") as ArrayList<User>,
                    "comentarios" to it.get("comentarios").toString(),
                    "fotos" to ev
                )
                db.collection("eventos")//añade o sebreescribe
                    .document(ev) //Será la clave del documento.
                    .set(user).addOnSuccessListener {
                        Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                    }
            }.addOnFailureListener{
                Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //llamar al recyclerview usando onStart y sacar las imagenes del storage para pasarle el arraylist de images----------------------------------------------------------------

    override fun onStart() {
        super.onStart()

        try {
            mostarimagenes(object : Galeria.RolCallback {
                override fun imagenes(imaNuevo: ArrayList<Imagenes>) {
                    //var miAdapter = AdaptadorFotos(imaNuevo, this@Galeria)
                    //miRecyclerView.adapter = miAdapter
                }
            })
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    interface RolCallback {
        fun imagenes(ima: ArrayList<Imagenes>)
    }

    fun mostarimagenes( callback:RolCallback){
        val bundle:Bundle? = intent.extras
        val ev = bundle?.getString("tituloEvento").toString()
        var email = intent.getStringExtra("user")
        AdaptadorFotos.titulo=ev
        AdaptadorFotos.email=email!!
        val desRef = Firebase.storage.reference.child(ev+"/")//.child(email+"/")
        miArray2.clear()

        desRef.listAll().addOnCompleteListener() { lista ->
            if(lista.isSuccessful){
                for(i in lista.result.prefixes){
                    i.listAll().addOnCompleteListener() { jj ->
                        if(jj.isSuccessful){
                            for(j in jj.result.items){
                                j.getBytes(ONE_MEGABYTE).addOnCompleteListener() {
                                    if (it.isSuccessful){
                                        val img = getBitmap(it.result)!!
                                        miArray2.add(Imagenes(j.name,img))
                                        var miAdapter = AdaptadorFotos(miArray2, this@Galeria)
                                        miRecyclerView.adapter = miAdapter
                                        //AdaptadorFotos.notifyDataSetChanged()//se debería comentar esta línea en caso de usar el runBlocking
                                        //}.await()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (callback != null) {
            callback.imagenes(miArray2);
        }
    }



    fun fileUpload(){
        val intent= Intent(Intent.ACTION_GET_CONTENT)
        intent.type="*/*"
        startActivityForResult(intent,File)
    }

    fun getBitmap(image: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(image, 0, image.size)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val bundle:Bundle? = intent.extras
        val ev = bundle?.getString("tituloEvento").toString()
        var email = intent.getStringExtra("user")
        //var f = intent.getStringExtra("storagefoto")

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==File){
            if(resultCode== RESULT_OK){
                val FileUri=data!!.data
                //para decirle la carpeta donde se almacenara y su nombre
                //si en el ref se lo pone un path se crean subcarpertas
                val imagenRef = ref2.child(ev+"/"+email+"/${FileUri!!.lastPathSegment}")
                //val imagenRef = ref2.child("${FileUri!!.lastPathSegment}")
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

}