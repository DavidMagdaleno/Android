package com.example.clienteandroidserviciopython

import Api.ServiceBuilder
import Api.UserAPI
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.clienteandroidserviciopython.Modelo.Rol
import com.example.clienteandroidserviciopython.Modelo.Usuario
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var ed: EditText
    lateinit var edPass: EditText
    var contexto = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ed = findViewById(R.id.edtBuscarId)
        edPass = findViewById(R.id.edPass)
    }

    fun listarTodos(view: View){
        var intentV1 = Intent(this, ListadoActivity::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }

    //-----------------------------------------------------------------------------------------
    fun login(view:View){
        val us = Usuario(
            edPass.text.toString(),
            ed.text.toString(),
            "",
            ""
        )
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.loginUsuario(us)

        var intentV1 = Intent(this, Bienvenido::class.java)


        call.enqueue(object : Callback<MutableList<Rol>> {

            override fun onResponse(
                call: Call<MutableList<Rol>>,
                response: Response<MutableList<Rol>>
            ) {
                Log.e ("Fernando", response.code().toString())

                if (response.code() == 200) {
                    var roles = ArrayList<String>()
                    for (post in response.body()!!) {
                        roles.add(post.rol.toString())
                        Log.e("Fernando",post.rol.toString())
                    }
                    Log.e("Fernando", "Login efectuado con éxito.")
                    Toast.makeText(contexto, response.message().toString(), Toast.LENGTH_SHORT).show()
                    intentV1.putExtra("roles",roles)
                    startActivity(intentV1)
                } else {
                    Log.e("Fernando", "Algo ha fallado en el login.")
                    Toast.makeText(contexto, response.message().toString(), Toast.LENGTH_LONG).show()
                }
                /*if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                    Log.e("Fernando", "Registro efectuado con éxito.")
                    Toast.makeText(contexto, "Registro efectuado con éxito", Toast.LENGTH_SHORT)
                        .show()
                }*/
            }

            override fun onFailure(call: Call<MutableList<Rol>>, t: Throwable) {
                Log.e("Fernando", "Algo ha fallado en la conexión.")
                Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun buscarPorID(view: View){
        var intentV1 = Intent(this, ListadoActivity::class.java)

        if (ed.text.trim().isEmpty()){
            Toast.makeText(this, "Rellene el campo con un DNI. Ejemplo: 1A, 2B, etc...", Toast.LENGTH_SHORT).show()
        }
        else {
            intentV1.putExtra("operacion","buscar")
            intentV1.putExtra("valorBuscar",ed.text.toString())
            startActivity(intentV1)
        }
    }

    fun nuevoRegistro(view:View){
        var intentV1 = Intent(this, NuevoActivity::class.java)
        intentV1.putExtra("operacion","nuevo")
        startActivity(intentV1)
    }

    fun modificarRegistro(view:View){
        var intentV1 = Intent(this, NuevoActivity::class.java)
        intentV1.putExtra("operacion","modificar")
        intentV1.putExtra("dni",ed.text.toString())
        startActivity(intentV1)
    }

    fun borrarPorID(view:View){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.borrarUsuario(ed.text.toString())
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("Fernando",response.message())
                Log.e ("Fernando", response.code().toString())
                if (response.code() == 200) {
                    Log.e("Fernando","Registro eliminado con éxito.")
                    Toast.makeText(this@MainActivity, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                }
                else {
                    Log.e("Fernando","Algo ha fallado en el borrado: DNI no encontrado.")
                    Toast.makeText(this@MainActivity, "Algo ha fallado en el borrado: DNI no encontrado",Toast.LENGTH_LONG).show()
                }
                if (response.isSuccessful){ //Esto es otra forma de hacerlo en lugar de mirar el código.
                    Log.e("Fernando","Registro eliminado con éxito.")
                    Toast.makeText(this@MainActivity, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Fernando","Algo ha fallado en la conexión.")
                Toast.makeText(this@MainActivity, "Algo ha fallado en la conexión.",Toast.LENGTH_LONG).show()
            }
        })
    }
}