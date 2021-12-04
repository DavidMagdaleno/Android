package com.example.clienteandroidserviciopython

import Api.ServiceBuilder
import Api.UserAPI
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clienteandroidserviciopython.Adaptadores.MiAdaptadorRV
import com.example.clienteandroidserviciopython.Modelo.Usuario
import okhttp3.FormBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.jetbrains.annotations.NotNull




/*
Esta ventana nos valdrá para los registros nuevos y para modificar.
 */
class NuevoActivity : AppCompatActivity() {
    lateinit var nuevoDNI : TextView
    lateinit var nuevoNombre : TextView
    lateinit var nuevoTfno : TextView
    lateinit var botonAceptar : Button
    var contexto = this
    lateinit var operacion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)

        nuevoDNI = findViewById(R.id.edDNINuevo)
        nuevoNombre = findViewById(R.id.edNombreNuevo)
        nuevoTfno = findViewById(R.id.edTfnoNuevo)
        botonAceptar = findViewById(R.id.btnAceptarNuevo)

        operacion = intent.getStringExtra("operacion").toString()
        val idBuscar = intent.getStringExtra("dni").toString()
        if (operacion.equals("modificar")){
            getBuscarUnUsuario(idBuscar)
            nuevoDNI.isEnabled = false  //No dejamos modificar el DNI que es la clave del registro.
        }
    }

    //-----------------------------------------------------------------------------------------
    fun cancelar(view: View){
        finish()
    }



    //-----------------------------------------------------------------------------------------
    fun aceptar(view:View) {
        val us = Usuario(
            "",
            nuevoDNI.text.toString(),
            nuevoNombre.text.toString(),
            nuevoTfno.text.toString()
        )


        //--------------- Añadir nuevo registro -----------------
        if (operacion.equals("nuevo")) {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.addUsuario(us)
            call.enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e("Fernando", response.message())
                    Log.e("Fernando", response.code().toString())
                    if (response.code() == 200) {
                        Log.e("Fernando", "Registro efectuado con éxito.")
                        Toast.makeText(contexto, "Registro efectuado con éxito", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Log.e("Fernando", "Algo ha fallado en la inserción: clave duplicada.")
                        Toast.makeText(
                            contexto,
                            "Algo ha fallado en la inserción: clave duplicada",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                        Log.e("Fernando", "Registro efectuado con éxito.")
                        Toast.makeText(contexto, "Registro efectuado con éxito", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Fernando", "Algo ha fallado en la conexión.")
                    Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }
        //---------------- Modificar un registro -----------------
        else {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.modUsuario(us)
            call.enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e("Fernando", response.message())
                    Log.e("Fernando", response.code().toString())
                    if (response.code() == 200) {
                        Log.e("Fernando", "Registro modificado con éxito.")
                        Toast.makeText(contexto, "Registro modificado con éxito", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Log.e("Fernando", "Algo ha fallado en la modificación.")
                        Toast.makeText(
                            contexto,
                            "Algo ha fallado en la modificación",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                        Log.e("Fernando", "Registro modificado con éxito.")
                        Toast.makeText(contexto, "Registro modificado con éxito", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Fernando", "Algo ha fallado en la conexión.")
                    Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }
    }


    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    fun getBuscarUnUsuario(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnUsuario(idBusc);

        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                val post = response.body()
                if (post != null) {
                    nuevoDNI.append(post.DNI)
                    nuevoNombre.append(post.Nombre)
                    nuevoTfno.append(post.Tfno)
                }
                else {
                    Toast.makeText(this@NuevoActivity, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    botonAceptar.isEnabled = false
                }
            }
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Toast.makeText(this@NuevoActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}