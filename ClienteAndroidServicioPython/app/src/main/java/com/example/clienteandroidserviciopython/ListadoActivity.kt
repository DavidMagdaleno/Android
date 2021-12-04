package com.example.clienteandroidserviciopython

import Api.ServiceBuilder
import Api.UserAPI
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clienteandroidserviciopython.Adaptadores.MiAdaptadorRV
import com.example.clienteandroidserviciopython.Modelo.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListadoActivity : AppCompatActivity() {
    var personas: ArrayList<Usuario> = ArrayList()

    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)

        recyclerView = findViewById<RecyclerView>(R.id.RVListaPersonas)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager

        val operacion = intent.getStringExtra("operacion").toString()


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


    /**
     * Acceso a los usuarios sin viewModel.
     */
    fun getUsers2() {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUsuarioss()

        call.enqueue(object : Callback<MutableList<Usuario>> {
            override fun onResponse(call: Call<MutableList<Usuario>>, response: Response<MutableList<Usuario>>) {
                Log.e ("Fernando", response.code().toString())
                for (post in response.body()!!) {
                    personas.add(Usuario(post.Clave,post.DNI,post.Nombre,post.Tfno))
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoActivity)
                        adapter = MiAdaptadorRV(this@ListadoActivity, personas)
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Usuario>>, t: Throwable) {
                Toast.makeText(this@ListadoActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    /**
     * Buscar un usuario.
     */
    //https://howtodoinjava.com/retrofit2/query-path-parameters/
    fun getBuscarUnUsuario2(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnUsuario(idBusc);

        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                val post = response.body()
                if (post != null) {
                    personas.add(post)
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoActivity)
                        adapter = MiAdaptadorRV(this@ListadoActivity, personas)
                    }
                }
                else {
                    Toast.makeText(this@ListadoActivity, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Toast.makeText(this@ListadoActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}