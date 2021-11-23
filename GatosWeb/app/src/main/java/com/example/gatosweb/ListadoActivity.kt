package com.example.gatosweb

import Adaptadores.MiAdaptadorRV
import Api.MainActivityViewModel
import Api.ServiceBuilder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.nio.charset.Charset
import Api.UserAPI
import Modelo.Perros
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * En este ejercicio tendremos dos formas de llamar a Retrofit para acceder a los datos:
 *     con corrutinas ViewModel.
 *     con llamadas a funciones call.
 */
class ListadoActivity : AppCompatActivity() {
    var message: ArrayList<String> = ArrayList()
    var status: String = ""
    lateinit var customAdapter : MiAdaptadorRV
    lateinit var recyclerView : RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)

        recyclerView = findViewById<RecyclerView>(R.id.RVListaPersonas)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager

        val operacion = intent.getStringExtra("operacion").toString()

        if(operacion.equals("leerArchivo")){
            leeFicheroJSON()
        }
        if(operacion.equals("listar")){
            //getUsers()
            getUsers2()
        }
        if(operacion.equals("buscar")){
            val idBuscar = intent.getStringExtra("valorBuscar").toString()
            //getBuscarUnUsuario(idBuscar)
            getBuscarUnUsuario2(idBuscar)
        }

    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************

    fun leeFicheroJSON(){
        try {
            val obj = JSONObject(loadJSONFromAsset())//cambiar nombres de variables para que quede mas con el proyecto
            val DogArray = obj.getJSONArray("Perros")
            for (i in 0 until DogArray.length()) {
                val dogDetail = DogArray.getJSONObject(i)
                message.add(dogDetail.getString("message"))
                status = dogDetail.getString("status")
            }
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }

        customAdapter = MiAdaptadorRV(this@ListadoActivity, message, status)
        recyclerView.adapter = customAdapter
    }

    //https://www.tutorialspoint.com/how-to-parse-json-objects-on-android-using-kotlin
    //Otra opción es guardarlo en res/raw folder: https://medium.com/mobile-app-development-publication/assets-or-resource-raw-folder-of-android-5bdc042570e0
    private fun loadJSONFromAsset(): String {
        val json: String?
        try {
            val inputStream = assets.open("perros_list.json")//cambiar nombre del fichero
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        }
        catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }



    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************
    //Api's públicas:
    //https://pokeapi.co/
    //https://randomuser.me/api/
    //https://cataas.com/#/
    //https://jsonplaceholder.typicode.com/posts
    //https://dog.ceo/dog-api/
    //https://rapidapi.com/collection/list-of-free-apis
    //https://apipheny.io/free-api/#apis-without-key

    //https://medium.com/android-news/consuming-rest-api-using-retrofit-library-in-android-ed47aef01ecb
    //https://ed.team/blog/como-consumir-una-api-rest-desde-android
    //https://dev.to/theimpulson/making-get-requests-with-retrofit2-on-android-using-kotlin-4e4c
    //https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter/issues/24
    fun getUsers() {
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.getPosts()
        viewModel.myResponseList.observe(this, {

            for (user in it) {
                //Log.d("Fernando", user.userId.toString())
                Log.d("Fernando", user.message.toString())
                Log.d("Fernando", user.status.toString())
                //Log.d("Fernando", user.tags.toString())
                //runOnUiThread(){
                message.add(user.message.toString())
                status = user.status.toString()
                //mobileNumbers.add(user.tags.toString())
                //}
            }
            //Log.e("Fernando",message.toString())
            customAdapter = MiAdaptadorRV(this@ListadoActivity, message, status)
            recyclerView.adapter = customAdapter
        })

    }

    //--------------------------------------------------------------------------------------------------------

    //https://dev.to/paulodhiambo/kotlin-and-retrofit-network-calls-2353
    fun getUsers2() {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUsuarioss()

        call.enqueue(object : Callback<Perros> {
            override fun onResponse(call: Call<Perros>, response: Response<Perros>) {
                Log.e ("Fernando", response.code().toString())
                val post = response.body()//se pone con un for si es un arraylist (mirar ejemplo ClienteApiRest)
                if (post != null) {
                    message = post.message
                    status = post.status.toString()//cambiar si es un arraylist
                }
                //mobileNumbers.add(post.tags.toString())

                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoActivity)
                        adapter = MiAdaptadorRV(this@ListadoActivity, message, status)
                    }
                }
            }
            override fun onFailure(call: Call<Perros>, t: Throwable) {
                Toast.makeText(this@ListadoActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************

    fun getBuscarUnUsuario(idBusc:String){
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.getPost(idBusc)
        viewModel.myResponse.observe(this, {
            Log.d("Fernando", it.message.toString())
            Log.d("Fernando", it.status.toString())
            //Log.d("Fernando", it.id.toString())
            //Log.d("Fernando", it.userId.toString())

            message.add(it.message.toString())
            status = it.status.toString()//cambiar si es un arraylist
            //mobileNumbers.add(it.tags.toString())
            customAdapter = MiAdaptadorRV(this@ListadoActivity, message, status)
            recyclerView.adapter = customAdapter
        })
    }



    //https://howtodoinjava.com/retrofit2/query-path-parameters/
    fun getBuscarUnUsuario2(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnUsuario(idBusc);

        call.enqueue(object : Callback<Perros> {
            override fun onResponse(call: Call<Perros>, response: Response<Perros>) {
                val post = response.body()//se pone con un for si es un arraylist (mirar ejemplo ClienteApiRest)
                if (post != null) {
                    message.add(post.message.toString())
                    status= post.status.toString()//cambiar si es un arraylist
                    //mobileNumbers.add(post.tags.toString())
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoActivity)
                        adapter = MiAdaptadorRV(this@ListadoActivity, message, status)
                    }
                }
                else {
                    Toast.makeText(this@ListadoActivity, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Perros>, t: Throwable) {
                Toast.makeText(this@ListadoActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}