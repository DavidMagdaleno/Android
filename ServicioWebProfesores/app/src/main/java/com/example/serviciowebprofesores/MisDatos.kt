package com.example.serviciowebprofesores

import Api.ServiceBuilder
import Api.UserAPI
import Modelo.Aula
import Modelo.Profesor
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MisDatos : AppCompatActivity() {
    lateinit var dni: EditText
    lateinit var nom: EditText
    lateinit var apel: EditText
    lateinit var pass: EditText
    lateinit var r:TextView
    var newPass:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_datos)

        dni=findViewById(R.id.txtDN)
        nom=findViewById(R.id.txtN)
        apel=findViewById(R.id.txtA)
        pass=findViewById(R.id.txtP)
        r=findViewById(R.id.lblRoles)

        r.setText(FragmentCabecera.rol)

        buscarProfesor(FragmentCabecera.dni)
    }


    fun buscarProfesor(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnProfesor(idBusc);

        call.enqueue(object : Callback<Profesor> {
            override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                val post = response.body()
                if (post != null) {
                    if (response.isSuccessful){
                        nom.setText(post.Nombre.toString())
                        apel.setText(post.Apellidos.toString())
                        dni.setText(post.DNIProfesor.toString())
                        pass.setText(post.Password.toString())

                        nom.isEnabled=false
                        apel.isEnabled=false
                        dni.isEnabled=false
                        pass.isEnabled=false
                    }
                    else {
                        Toast.makeText(this@MisDatos, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<Profesor>, t: Throwable) {
                Toast.makeText(this@MisDatos, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun cambiarPass(view: View){
        createSimpleDialog()
    }

    fun cambio(){
        val us = Profesor(newPass,dni.text.toString(),nom.text.toString(),apel.text.toString())
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.modClave(us)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("Fernando", response.message())
                Log.e("Fernando", response.code().toString())
                if (response.code() == 200) {
                    Log.e("Fernando", "Registro modificado con éxito.")
                    Toast.makeText(this@MisDatos, "Se ha modificado la contraseña con éxito.",Toast.LENGTH_LONG).show()
                } else {
                    Log.e("Fernando", "Algo ha fallado en la modificación.")
                    Toast.makeText(this@MisDatos,"Algo ha fallado en la modificación",Toast.LENGTH_LONG).show()
                }
                if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                    Log.e("Fernando", "Registro modificado con éxito.")
                    //Toast.makeText(contexto, "Registro modificado con éxito", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Fernando", "Algo ha fallado en la conexión.")
                Toast.makeText(this@MisDatos, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
            }
        })
    }


    fun createSimpleDialog(): Boolean {
        val dialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        val Myview=layoutInflater.inflate(R.layout.item_pass, null)
        var nPass = Myview.findViewById<EditText>(R.id.txtPa)
        dialogo.setView(Myview)
        dialogo.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                newPass=nPass.text.toString()
                cambio()
            })
        dialogo.setNegativeButton("CANCELAR",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
        dialogo.setTitle("Cambiar Contraseña")
        dialogo.setMessage("Escribe la Nueva Contraseña")
        dialogo.show()
        return true
    }



    fun volver(view: View){
        finish()
    }
}