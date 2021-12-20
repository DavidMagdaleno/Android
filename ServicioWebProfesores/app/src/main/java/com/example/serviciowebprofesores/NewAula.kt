package com.example.serviciowebprofesores

import Api.ServiceBuilder
import Api.UserAPI
import Modelo.Asignacion
import Modelo.Aula
import Modelo.Profesor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewAula : AppCompatActivity() {
    lateinit var id:EditText
    lateinit var dni:EditText
    lateinit var descrip:EditText
    lateinit var operacion:String
    lateinit var au:Aula
    lateinit var spProfe:Spinner
    private var profesNombres=ArrayList<String>()
    private var profesDNI=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_aula)
        id=findViewById(R.id.txtCodAula)
        dni=findViewById(R.id.txtDniProfe)
        descrip=findViewById(R.id.txtDescrip)

        operacion = intent.getStringExtra("opcion").toString()

        if (operacion.equals("modificar")){
            au = intent.getSerializableExtra("modificar") as Aula
            id.setText(au.IdAula.toString())
            dni.setText(au.DNI.toString())
            descrip.setText(au.Descripcion.toString())

            id.isEnabled = false  //No dejamos modificar el id que es la clave del registro.
            //dni.isEnabled = false  //No dejamos modificar el id que es la clave del registro.
        }
    }

    fun NuevoAula(view: View){
        val us = Aula(id.text.toString().toInt(),dni.text.toString(),descrip.text.toString())
        Log.e("Fernando", us.toString())
        if (operacion.equals("nuevo")) {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            if(AulaDNI(dni.text.toString())){
                Toast.makeText(this@NewAula, "Ese Profesor ya tiene un aula asignada.",Toast.LENGTH_LONG).show()
                //alertDialog para quitar la asignacion del aula anterior copiar en modificar

            }else{
                val call = request.addAula(us)
                call.enqueue(object : Callback<ResponseBody> {

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Log.e("Fernando", response.message())
                        Log.e("Fernando", response.code().toString())
                        if (response.code() == 200) {
                            Log.e("Registro", "Registro efectuado con éxito.")
                            Toast.makeText(this@NewAula, "Se ha registrado el aula con éxito.",Toast.LENGTH_LONG).show()
                            finish()
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

        }else{

            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.modAula(us)
            call.enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e("Fernando", response.message())
                    Log.e("Fernando", response.code().toString())
                    if (response.code() == 200) {
                        Log.e("Fernando", "Registro modificado con éxito.")
                        Toast.makeText(this@NewAula, "Se ha modificado el aula con éxito.",Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Log.e("Fernando", "Algo ha fallado en la modificación.")
                        //Toast.makeText(contexto,"Algo ha fallado en la modificación",Toast.LENGTH_LONG).show()
                    }
                    if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                        Log.e("Fernando", "Registro modificado con éxito.")
                        //Toast.makeText(contexto, "Registro modificado con éxito", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Fernando", "Algo ha fallado en la conexión.")
                    //Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    fun AulaDNI(idBusc:String):Boolean{
        var masdeuna:Boolean=false
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnAula(idBusc);
        //si es mas de una
        call.enqueue(object : Callback<MutableList<Aula>> {
            override fun onResponse(call: Call<MutableList<Aula>>, response: Response<MutableList<Aula>>) {
                val post = response.body()
                if (post != null) {
                    if (response.isSuccessful){
                        if (post.size>1){
                            masdeuna=true
                        }
                    }
                    else {
                        Toast.makeText(this@NewAula, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Aula>>, t: Throwable) {
                Toast.makeText(this@NewAula, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        return masdeuna
    }


    fun volver(view: View){
        finish()
    }
}