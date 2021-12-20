package com.example.serviciowebprofesores

import Api.ServiceBuilder
import Api.UserAPI
import Modelo.Profesor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewProfesor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_profesor)
    }

    fun NuevoProfesor(view: View){
        var dni:EditText=findViewById(R.id.txtDNIA)
        var nom:EditText=findViewById(R.id.txtNom)
        var apel:EditText=findViewById(R.id.txtApelli)
        var pass:EditText=findViewById(R.id.txtPass)

        val us = Profesor(pass.text.toString(),dni.text.toString(),nom.text.toString(),apel.text.toString())

        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.addProfesor(us)
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.e("Fernando", response.message())
                Log.e("Fernando", response.code().toString())
                if (response.code() == 200) {
                    Log.e("Registro", "Registro efectuado con éxito.")
                } else {
                    Log.e("Registro", "Algo ha fallado en la inserción: clave duplicada.")
                }
                if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                    Log.e("Registro", "Registro efectuado con éxito.")
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Registro", "Algo ha fallado en la conexión.")
            }
        })
    }


}