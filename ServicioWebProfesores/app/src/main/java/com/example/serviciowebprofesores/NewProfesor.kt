package com.example.serviciowebprofesores

import Api.ServiceBuilder
import Api.UserAPI
import Modelo.Asignacion
import Modelo.Profesor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewProfesor : AppCompatActivity() {
    lateinit var dni:EditText
    lateinit var nom:EditText
    lateinit var apel:EditText
    lateinit var pass:EditText
    lateinit var rolProfe:CheckBox
    lateinit var rolEncarg:CheckBox
    lateinit var rolJefe:CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_profesor)

        dni=findViewById(R.id.txtDNIA)
        nom=findViewById(R.id.txtNom)
        apel=findViewById(R.id.txtApelli)
        pass=findViewById(R.id.txtPass)
        rolProfe=findViewById(R.id.cbProfe)
        rolEncarg=findViewById(R.id.cbEncargado)
        rolJefe=findViewById(R.id.cbJefe)
    }

    fun NuevoProfesor(view: View){
        val us = Profesor(pass.text.toString(),dni.text.toString(),nom.text.toString(),apel.text.toString())

        val request = ServiceBuilder.buildService(UserAPI::class.java)

        //se comprueba que no exista ya un profesor con ese dni
        val call = request.getUnProfesor(dni.text.toString())
        call.enqueue(object : Callback<Profesor> {
            override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                //val post = response.body()
                if (response.isSuccessful){
                    Toast.makeText(this@NewProfesor, "Ya Existe un Profesor con ese DNI", Toast.LENGTH_SHORT).show()
                }else {
                    //Si no hay nadie con ese dni entonces añade al profesor
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
                                Toast.makeText(this@NewProfesor, "Registro efectuado con éxito.", Toast.LENGTH_SHORT).show()
                            } else {
                                Log.e("Registro", "Algo ha fallado en la inserción: clave duplicada.")
                                Toast.makeText(this@NewProfesor, "Algo ha fallado en la inserción: clave duplicada.", Toast.LENGTH_SHORT).show()
                            }
                            if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                                Log.e("Registro", "Registro efectuado con éxito.")
                            }
                        }
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.e("Registro", "Algo ha fallado en la conexión.")
                        }
                    })
                    //Se añaden también el/los roles que tendra
                    if(rolProfe.isChecked){añadirRol(1)}
                    if(rolEncarg.isChecked){añadirRol(2)}
                    if(rolJefe.isChecked){añadirRol(3)}
                }
            }
            override fun onFailure(call: Call<Profesor>, t: Throwable) {
                Toast.makeText(this@NewProfesor, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun añadirRol(Idrol:Int){
        Log.e("entra","puede crear2")
        val us = Asignacion(dni.text.toString(),Idrol)
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.addAsignacion(us)
        Log.e("entra","puede crear3")
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.e("Fernando", response.message())
                Log.e("Fernando", response.code().toString())
                if (response.code() == 200) {
                    Log.e("Registro", "Se ha incorporado el rol con exito.")
                    Toast.makeText(this@NewProfesor, "Se ha incorporado el rol con exito.",Toast.LENGTH_LONG).show()
                } else {
                    Log.e("Registro", "Algo ha fallado en la inserción: clave duplicada.")
                    Toast.makeText(this@NewProfesor, "Algo ha fallado en la inserción: clave duplicada.",Toast.LENGTH_LONG).show()
                }
                if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                    Log.e("Registro", "Registro efectuado con éxito.")
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Registro", "Algo ha fallado en la conexión.")
                Toast.makeText(this@NewProfesor, "Algo ha fallado en la conexión.",Toast.LENGTH_LONG).show()
            }
        })
    }



}