package com.example.gatosweb

import Adaptadores.MiAdaptadorRV
import Api.ServiceBuilder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.nio.charset.Charset
import Api.UserAPI
import Modelo.Perros
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
            getUsers2()
        }
        if(operacion.equals("buscar")){
            val idBuscar = intent.getStringExtra("valorBuscar").toString()
            getBuscarUnUsuario2(idBuscar)
        }

    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************

    fun leeFicheroJSON(){
        try {
            val obj = JSONObject(loadJSONFromAsset())
            val DogArray = obj.getJSONArray("Perros")
            for (i in 0 until DogArray.length()) {
            //for (i in 0 until 100) {
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
            val inputStream = assets.open("perros_list.json")
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
    //https://dog.ceo/dog-api/

    fun getUsers2() {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getPerros()
        var message2=ArrayList<String>()
        call.enqueue(object : Callback<Perros> {
            override fun onResponse(call: Call<Perros>, response: Response<Perros>) {
                val post = response.body()
                    if (post != null) {
                        message = post.message
                        status = post.status.toString()
                    }
                for(i in 0..50){
                    message2.add(message[i])
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoActivity)
                        adapter = MiAdaptadorRV(this@ListadoActivity, message2, status)
                    }
                }
            }
            override fun onFailure(call: Call<Perros>, t: Throwable) {
                Toast.makeText(this@ListadoActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getBuscarUnUsuario2(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)//saber a que clase y metodo llama
        val call = request.getFotoPerro(idBusc);
        var message2=ArrayList<String>()
        call.enqueue(object : Callback<Perros> {
            override fun onResponse(call: Call<Perros>, response: Response<Perros>) {
                val post = response.body()
                if (post != null) {
                    message= post.message
                    status= post.status.toString()
                }
                for(i in 0..50){
                    message2.add(message[i])
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoActivity)
                        adapter = MiAdaptadorRV(this@ListadoActivity, message2, status)
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