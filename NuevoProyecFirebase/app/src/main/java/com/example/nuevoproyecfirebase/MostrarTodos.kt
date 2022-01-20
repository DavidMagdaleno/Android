package com.example.nuevoproyecfirebase

import Adaptador.MiAdaptadorRecycler
import Modelo.Users
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.nuevoproyecfirebase.MainActivity.Companion.miArray
import com.google.firebase.firestore.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class MostrarTodos : AppCompatActivity() {
    lateinit var miRecyclerView : RecyclerView
    var miArray:ArrayList<Users> = ArrayList()
    private val db = FirebaseFirestore.getInstance() //Variable con la que accederemos a Firestore. Será una instancia a la bd.
    private val TAG = "Fernando"
    var context=this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_todos)


        miRecyclerView = findViewById(R.id.rvUser)// as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)

        //----------------------Metodo Propio-------------------------------------------
        try {
            recuperarDatos(object : RolCallback2 {
                override fun datosRecibidos(datosnuevos: MutableList<DocumentSnapshot>) {
                    obtenerDatos(datosnuevos)
                }
            })
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        //

        //----------------------Metodos Fernando-------------------------------------------
         //-------------------primera forma-------------------------------------------
        //getTodos()
         //-------------------segunda forma--------------------------------------------
        /*runBlocking {
            val job : Job = launch(context = Dispatchers.Default) {
                val datos : QuerySnapshot = getDataFromFireStore() as QuerySnapshot //Obtenermos la colección
                obtenerDatos2(datos as QuerySnapshot?)  //'Destripamos' la colección y la metemos en nuestro ArrayList
            }
            //Con este método el hilo principal de onCreate se espera a que la función acabe y devuelva la colección con los datos.
            job.join() //Esperamos a que el método acabe: https://dzone.com/articles/waiting-for-coroutines
        }

        Log.e(TAG,"----------------")
        for(e in miArray){
            Log.e(TAG,e.toString())
        }
        Log.e(TAG,"----------------")
        var miAdapter = MiAdaptadorRecycler(miArray, this)
        miRecyclerView.adapter = miAdapter
        //Aquí se pondría en el setAdapter del RecyclerView.
        */

    }

    fun recuperarDatos( callback:RolCallback2){
        db.collection("users").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //poner los demas para recuperar todos los datos
                    var usuarios= task.result.documents
                    if (callback != null) {
                        callback.datosRecibidos(usuarios);
                    }
                } else {
                    Log.e("wh", "Error getting documents.", task.exception)
                }
            }
    }

    interface RolCallback2 {
        fun datosRecibidos(miArray: MutableList<DocumentSnapshot>)
    }

    private fun obtenerDatos(datos: MutableList<DocumentSnapshot>) {
        for(dc in datos){
            var roles : ArrayList<Int>
            if (dc["roles"] != null){
                roles = dc.get("roles") as ArrayList<Int>
            }else {
                roles = arrayListOf()
            }
            var al = Users(
                dc.get("DNI").toString(),
                dc.get("Nombre").toString(),
                dc.get("Apellidos").toString(),
                dc.get("provider").toString(),
                roles
            )
            //Log.e(TAG, al.toString())
            miArray.add(al)
        }
        var miAdapter = MiAdaptadorRecycler(miArray, this)
        miRecyclerView.adapter = miAdapter
    }



    //-------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Este método carga los datos, exactamente igual que el de antes, pero usando otro listener: addSnapshotListener.
     * Dentro de esta función se rellena en el listener el arrayList que deberemos asignar al RecyclerView y, dentro del listener, cuando acabe
     * la carga de datos, notificaremos los cambios del adaptador para que cargue el RV (comentado el punto indicado de la notificación).
     */
    fun getTodos() {
        db.collection("users").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    Log.e(TAG,error.message.toString())
                }
                else {
                    for(dc: DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            //miAr.add(dc.document.toObject(User::class.java))
                            var roles : ArrayList<Int>

                            if (dc.document.get("roles") != null){
                                roles = dc.document.get("roles") as ArrayList<Int>
                            }
                            else {
                                roles = arrayListOf()
                            }
                            var al = Users(
                                dc.document.get("DNI").toString(),
                                dc.document.get("Nombre").toString(),
                                dc.document.get("Apellidos").toString(),
                                dc.document.get("provider").toString(),
                                roles
                            )
                            //Log.e(TAG, al.toString())
                            miArray.add(al)
                        }
                    }
                    //Log.e(TAG,miArray.toString())
                    var miAdapter = MiAdaptadorRecycler(miArray, context)
                    miRecyclerView.adapter = miAdapter
                    for(i in 0..miArray.size-1){
                        Log.e("datos", miArray[i].DNI+", "+miArray[i].Nombre)
                    }
                    miAdapter.notifyDataSetChanged()
                    //Aquí se llamaría a: miAdaptador.notifyDataSetChanged() !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                }
            }
        })
    }

    /**
     * Este método es una función suspend que esperará a que la consulta se realiza. Será llamada
     * en un scope (entorno) de corrutinas. Hilos (ver onCreate, runBlocking).
     */
    suspend fun getDataFromFireStore()  : QuerySnapshot? {
        return try{
            val data = db.collection("users")
                //.whereEqualTo("age", 41)
                .whereGreaterThanOrEqualTo("age",40)  //https://firebase.google.com/docs/firestore/query-data/order-limit-data?hl=es-419
                .orderBy("age", Query.Direction.DESCENDING)
                //.limit(4) //Limita la cantidad de elementos mostrados.
                .get()
                .await()
            data
        }catch (e : Exception){
            null
        }
    }

    private fun obtenerDatos2(datos: QuerySnapshot?) {
        for(dc:DocumentChange in datos?.documentChanges!!){
            if (dc.type == DocumentChange.Type.ADDED){
                //miAr.add(dc.document.toObject(User::class.java))
                var roles : ArrayList<Int>

                if (dc.document.get("roles") != null){
                    roles = dc.document.get("roles") as ArrayList<Int>
                }
                else {
                    roles = arrayListOf()
                }
                var al = Users(
                    dc.document.get("DNI").toString(),
                    dc.document.get("Nombre").toString(),
                    dc.document.get("Apellidos").toString(),
                    dc.document.get("provider").toString(),
                    roles
                )
                //Log.e(TAG, al.toString())
                miArray.add(al)
            }
        }
    }
}