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
import android.widget.*
import androidx.core.view.get
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Response.success
import kotlin.Result.Companion.success

class NewAula : AppCompatActivity() {
    lateinit var id:EditText
    lateinit var dni:EditText
    lateinit var descrip:EditText
    lateinit var operacion:String
    lateinit var au:Aula
    lateinit var spProfe:Spinner
    lateinit var PAA:TextView
    private var profesNombres=ArrayList<String>()
    private var profesDNI=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_aula)
        id=findViewById(R.id.txtCodAula)
        PAA=findViewById(R.id.lblPAA)
        descrip=findViewById(R.id.txtDescrip)
        spProfe=findViewById(R.id.spProfe)
        Profe()
        operacion = intent.getStringExtra("opcion").toString()

        if (operacion.equals("modificar")){
            au = intent.getSerializableExtra("modificar") as Aula
            id.setText(au.IdAula.toString())
            PAA.setText(au.DNI.toString())
            descrip.setText(au.Descripcion.toString())

            id.isEnabled = false  //No dejamos modificar el id que es la clave del registro.
        }
    }

    fun comprobarIdAula(view: View){
        var isnumeric:Boolean=true
        for(it in id.text){
            if(!it.isDigit()){
                isnumeric=false
            }
        }
        if(id.text.isNullOrEmpty() || !isnumeric){
            Toast.makeText(this@NewAula, "El Identificador de Aula debe ser Numerico", Toast.LENGTH_SHORT).show()
        }else{
            NuevoAula()
        }
    }

    fun NuevoAula(){
        val us = Aula(id.text.toString().toInt(),profesDNI[spProfe.selectedItemPosition],descrip.text.toString())
        if (operacion.equals("nuevo")) {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.getAulaId(id.text.toString().toInt());

            call.enqueue(object : Callback<Aula> {
                override fun onResponse(call: Call<Aula>, response: Response<Aula>) {
                    //val post = response.body()
                    if (response.isSuccessful){
                        Toast.makeText(this@NewAula, "El Identificador de Aula ya Existe", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        val call = request.getUnAula(profesDNI[spProfe.selectedItemPosition]);
                        //si es mas de una
                        call.enqueue(object : Callback<MutableList<Aula>> {
                            override fun onResponse(call: Call<MutableList<Aula>>, response: Response<MutableList<Aula>>) {
                                val post = response.body()
                                if (response.isSuccessful){
                                    if (post != null){
                                        if (post.size>0) {
                                            Toast.makeText(this@NewAula,"Ese Profesor ya tiene un aula asignada.",Toast.LENGTH_LONG).show()
                                            //alertDialog para quitar la asignacion del aula anterior copiar en modificar
                                            createSimpleDialog()
                                            //si no tiene ningun aula
                                        }
                                    }
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
                                                //finish()
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
                            override fun onFailure(call: Call<MutableList<Aula>>, t: Throwable) {
                                Toast.makeText(this@NewAula, "${t.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }
                override fun onFailure(call: Call<Aula>, t: Throwable) {
                    Toast.makeText(this@NewAula, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }else{
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.getUnAula(profesDNI[spProfe.selectedItemPosition])
            call.enqueue(object : Callback<MutableList<Aula>> {
                override fun onResponse(call: Call<MutableList<Aula>>, response: Response<MutableList<Aula>>) {
                    val post = response.body()
                    if (response.isSuccessful){
                        if (post != null) {
                            if (post.size>0){
                                Toast.makeText(this@NewAula, "Ese Profesor ya tiene un aula asignada.",Toast.LENGTH_LONG).show()
                                createSimpleDialog()
                            }
                        }
                    }else {
                        //si no tiene ningun aula
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
                                } else {
                                    Log.e("Fernando", "Algo ha fallado en la modificación.")
                                    Toast.makeText(this@NewAula,"Algo ha fallado en la modificación",Toast.LENGTH_LONG).show()
                                }
                                if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                                    Log.e("Fernando", "Registro modificado con éxito.")
                                    //Toast.makeText(contexto, "Registro modificado con éxito", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Log.e("Fernando", "Algo ha fallado en la conexión.")
                                Toast.makeText(this@NewAula, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
                            }
                        })
                    }
                }
                override fun onFailure(call: Call<MutableList<Aula>>, t: Throwable) {
                    Toast.makeText(this@NewAula, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }

   fun realizaCambio(idOtroaula:Int){
       val request = ServiceBuilder.buildService(UserAPI::class.java)
       val us = Aula(id.text.toString().toInt(),profesDNI[spProfe.selectedItemPosition],descrip.text.toString())
       val us2 = Aula(idOtroaula,"Nadie",descrip.text.toString())
       val call = request.modAula(us2)

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
                   //finish()
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
       if (operacion.equals("nuevo")){
           val call2 = request.addAula(us)
           call2.enqueue(object : Callback<ResponseBody> {
               override fun onResponse(
                   call: Call<ResponseBody>,
                   response: Response<ResponseBody>
               ) {
                   Log.e("Fernando", response.message())
                   Log.e("Fernando", response.code().toString())
                   if (response.code() == 200) {
                       Log.e("Registro", "Registro efectuado con éxito.")
                       Toast.makeText(this@NewAula, "Se ha registrado el aula con éxito.",Toast.LENGTH_LONG).show()
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
       }else{
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
                   } else {
                       Log.e("Fernando", "Algo ha fallado en la modificación.")
                       Toast.makeText(this@NewAula,"Algo ha fallado en la modificación",Toast.LENGTH_LONG).show()
                   }
                   if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                       Log.e("Fernando", "Registro modificado con éxito.")
                       //Toast.makeText(contexto, "Registro modificado con éxito", Toast.LENGTH_SHORT).show()
                   }
               }

               override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                   Log.e("Fernando", "Algo ha fallado en la conexión.")
                   Toast.makeText(this@NewAula, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
               }
           })
       }

   }


    fun createSimpleDialog(): Boolean {
        val dialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        val Myview=layoutInflater.inflate(R.layout.item_borrar, null)
        //var basunto = Myview.findViewById<EditText>(R.id.txtBorra)
        dialogo.setView(Myview)
        dialogo.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                val request = ServiceBuilder.buildService(UserAPI::class.java)
                val call = request.getUnAula2(profesDNI[spProfe.selectedItemPosition]);

                call.enqueue(object : Callback<Aula> {
                    override fun onResponse(call: Call<Aula>, response: Response<Aula>) {
                        val post = response.body()
                        if (post != null) {
                            if (response.isSuccessful){
                                realizaCambio(post.IdAula.toString().toInt())
                            }
                            else {
                                Toast.makeText(this@NewAula, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    override fun onFailure(call: Call<Aula>, t: Throwable) {
                        Toast.makeText(this@NewAula, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            })
        dialogo.setNegativeButton("CANCELAR",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
        dialogo.setTitle("Cambiar Aula")
        dialogo.setMessage("¿Deseas quitar el aula asignada y establecer la nueva?")
        dialogo.show()
        return true
    }

    fun Profe(){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getProfesores();
        call.enqueue(object : Callback<MutableList<Profesor>> {
            override fun onResponse(call: Call<MutableList<Profesor>>, response: Response<MutableList<Profesor>>) {
                val post = response.body()
                if (post != null) {
                    if (response.isSuccessful){
                        if(profesDNI.isEmpty() && profesNombres.isEmpty()){
                            profesDNI.add("nada")
                            profesNombres.add("Seleccionar Profesor")
                            for(ani in post){
                                profesDNI.add(ani.DNIProfesor.toString())
                                profesNombres.add(ani.Apellidos.toString()+" "+ani.Nombre.toString())
                            }
                        }
                        val adaptador = ArrayAdapter(this@NewAula, R.layout.item_cspinner,R.id.addopcion,profesNombres)
                        spProfe.adapter = adaptador
                    }
                    else {
                        Toast.makeText(this@NewAula, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Profesor>>, t: Throwable) {
                Toast.makeText(this@NewAula, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun volver(view: View){
        finish()
    }
}