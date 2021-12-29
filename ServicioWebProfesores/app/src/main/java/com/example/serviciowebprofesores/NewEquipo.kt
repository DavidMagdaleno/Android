package com.example.serviciowebprofesores

import Api.ServiceBuilder
import Api.UserAPI
import Modelo.Aula
import Modelo.Equipo
import Modelo.Profesor
import Modelo.Rol
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewEquipo : AppCompatActivity() {
    lateinit var idA: Spinner
    lateinit var AulaA: TextView
    lateinit var idE: EditText
    lateinit var proc: EditText
    lateinit var Ram: EditText
    lateinit var mSi: RadioButton
    lateinit var mNo: RadioButton
    lateinit var operacion:String
    lateinit var eq: Equipo
    var aus = ArrayList<String>()
    var mSiM:Boolean=false
    var mNoM:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_equipo)

        idA=findViewById(R.id.spAula)
        AulaA=findViewById(R.id.txtAulaA)
        idE=findViewById(R.id.txtIdEquipo)
        proc=findViewById(R.id.txtProc)
        Ram=findViewById(R.id.txtRam)
        mSi=findViewById(R.id.rbSi)
        mNo=findViewById(R.id.rbNo)

        operacion = intent.getStringExtra("opcion").toString()

        conocerAulas()

        if (operacion.equals("modificar")){
            eq = intent.getSerializableExtra("modificar") as Equipo
            AulaA.setText(eq.IdAula.toString())
            idE.setText(eq.IdEquipo.toString())
            proc.setText(eq.Procesador.toString())
            Ram.setText(eq.RAM.toString())
            if(eq.Pantalla.toString().equals("1")){
                mSi.isChecked=true
                mSiM=true
            }else{
                mNo.isChecked=true
                mNoM=true
            }

            idE.isEnabled = false //No dejamos modificar el id que es la clave del registro.
        }
    }

    fun comprobarIdEquipo(view: View){
        var isnumeric:Boolean=true
        for(it in idE.text){
            if(!it.isDigit()){
                isnumeric=false
            }
        }
        if(idE.text.isNullOrEmpty() || !isnumeric){
            Toast.makeText(this@NewEquipo, "El Identificador de Equipo debe ser Numerico", Toast.LENGTH_SHORT).show()
        }else{
            NuevoEquipo()
        }
    }

    fun NuevoEquipo(){
        var aux:Int=0
        if(mSi.isChecked){
            aux=1
        }else{
            if(!mSi.isChecked && !mNo.isChecked){
                Toast.makeText(this@NewEquipo, "Confirmar si tiene Monitor", Toast.LENGTH_SHORT).show()
            }else{
                aux=0
            }
        }
        if(aus[idA.selectedItemPosition].equals("Seleccionar Aula")){
            Toast.makeText(this@NewEquipo, "Debes Asignarle un Aula", Toast.LENGTH_SHORT).show()
        }else{
            val us = Equipo(aus[idA.selectedItemPosition].toInt(),idE.text.toString().toInt(),proc.text.toString(),Ram.text.toString(),aux)

            if (operacion.equals("nuevo")){
                //El encargado solo puede añadir a su aula
                if(FragmentCabecera.rol.equals("Encargado")){
                    val request = ServiceBuilder.buildService(UserAPI::class.java)
                    val call = request.getUnAula2(FragmentCabecera.dni);
                    call.enqueue(object : Callback<Aula> {
                        override fun onResponse(call: Call<Aula>, response: Response<Aula>) {
                            val post = response.body()
                            if (post != null) {
                                if (response.isSuccessful){
                                    if(aus[idA.selectedItemPosition].toInt()==post.IdAula){

                                        val request = ServiceBuilder.buildService(UserAPI::class.java)
                                        //se comprueba que no exista ya un Equipo con ese id
                                        val call = request.getEquipo(idE.text.toString().toInt())
                                        call.enqueue(object : Callback<Equipo> {
                                            override fun onResponse(call: Call<Equipo>, response: Response<Equipo>) {
                                                //val post = response.body()
                                                if (response.isSuccessful){
                                                    Toast.makeText(this@NewEquipo, "Ya Existe un Equipo con ese id", Toast.LENGTH_SHORT).show()
                                                }else {
                                                    //Si no ningun equipo con ese id entonces se añade
                                                    val call = request.addEquipo(us)
                                                    call.enqueue(object : Callback<ResponseBody> {

                                                        override fun onResponse(
                                                            call: Call<ResponseBody>,
                                                            response: Response<ResponseBody>
                                                        ) {
                                                            Log.e("Fernando", response.message())
                                                            Log.e("Fernando", response.code().toString())
                                                            if (response.code() == 200) {
                                                                Log.e("Registro", "Registro efectuado con éxito.")
                                                                Toast.makeText(this@NewEquipo, "Registro efectuado con éxito.", Toast.LENGTH_SHORT).show()
                                                            } else {
                                                                Log.e("Registro", "Algo ha fallado en la inserción: clave duplicada.")
                                                                Toast.makeText(this@NewEquipo, "Algo ha fallado en la inserción: clave duplicada.", Toast.LENGTH_SHORT).show()
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
                                            override fun onFailure(call: Call<Equipo>, t: Throwable) {
                                                Toast.makeText(this@NewEquipo, "${t.message}", Toast.LENGTH_SHORT).show()
                                            }
                                        })
                                    }else{
                                        Toast.makeText(this@NewEquipo, "Solo puedes añadir Equipos en tu Aula", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                else {
                                    Log.e("Fernando","No se han encontrado resultados")
                                    Toast.makeText(this@NewEquipo, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        override fun onFailure(call: Call<Aula>, t: Throwable) {
                            Toast.makeText(this@NewEquipo, "${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })

                }else{
                    val request = ServiceBuilder.buildService(UserAPI::class.java)
                    //se comprueba que no exista ya un Equipo con ese id
                    val call = request.getEquipo(idE.text.toString().toInt())
                    call.enqueue(object : Callback<Equipo> {
                        override fun onResponse(call: Call<Equipo>, response: Response<Equipo>) {
                            //val post = response.body()
                            if (response.isSuccessful){
                                Toast.makeText(this@NewEquipo, "Ya Existe un Equipo con ese id", Toast.LENGTH_SHORT).show()
                            }else {
                                //Si no ningun equipo con ese id entonces se añade
                                val call = request.addEquipo(us)
                                call.enqueue(object : Callback<ResponseBody> {

                                    override fun onResponse(
                                        call: Call<ResponseBody>,
                                        response: Response<ResponseBody>
                                    ) {
                                        Log.e("Fernando", response.message())
                                        Log.e("Fernando", response.code().toString())
                                        if (response.code() == 200) {
                                            Log.e("Registro", "Registro efectuado con éxito.")
                                            Toast.makeText(this@NewEquipo, "Registro efectuado con éxito.", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Log.e("Registro", "Algo ha fallado en la inserción: clave duplicada.")
                                            Toast.makeText(this@NewEquipo, "Algo ha fallado en la inserción: clave duplicada.", Toast.LENGTH_SHORT).show()
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
                        override fun onFailure(call: Call<Equipo>, t: Throwable) {
                            Toast.makeText(this@NewEquipo, "${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }else{
                //modificar
                if(!mSi.isChecked && !mNo.isChecked){
                    Toast.makeText(this@NewEquipo, "Confirmar si tiene Monitor", Toast.LENGTH_SHORT).show()
                }else{
                    if(!mSiM && mSi.isChecked){aux=1}
                    if(mSiM && !mSi.isChecked){aux=0}

                    val us:Equipo
                    if(aus[idA.selectedItemPosition].equals("Seleccionar Aula")){
                        us = Equipo(AulaA.text.toString().toInt(),idE.text.toString().toInt(),proc.text.toString(),Ram.text.toString(),aux)
                    }else{
                        us = Equipo(aus[idA.selectedItemPosition].toInt(),idE.text.toString().toInt(),proc.text.toString(),Ram.text.toString(),aux)
                    }
                    val request = ServiceBuilder.buildService(UserAPI::class.java)
                    val call = request.modEquipo(us)
                    call.enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            Log.e("Fernando", response.message())
                            Log.e("Fernando", response.code().toString())
                            if (response.code() == 200) {
                                Log.e("Fernando", "Registro modificado con éxito.")
                                Toast.makeText(this@NewEquipo, "Se ha modificado el Equipo con éxito.",Toast.LENGTH_LONG).show()
                            } else {
                                Log.e("Fernando", "Algo ha fallado en la modificación.")
                                Toast.makeText(this@NewEquipo,"Algo ha fallado en la modificación",Toast.LENGTH_LONG).show()
                            }
                            if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                                Log.e("Fernando", "Registro modificado con éxito.")
                                //Toast.makeText(contexto, "Registro modificado con éxito", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.e("Fernando", "Algo ha fallado en la conexión.")
                            Toast.makeText(this@NewEquipo, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
                        }
                    })
                }
            }
        }
    }


    fun conocerAulas(){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getAulas();
        call.enqueue(object : Callback<MutableList<Aula>> {
            override fun onResponse(call: Call<MutableList<Aula>>, response: Response<MutableList<Aula>>) {
                val post = response.body()
                if (post != null) {
                    if (response.isSuccessful){
                        if(aus.isEmpty()){
                            aus.add("Seleccionar Aula")
                            for(ani in post){
                                aus.add(ani.IdAula.toString())
                            }
                        }
                        val adaptador = ArrayAdapter(this@NewEquipo, R.layout.item_cspinner,R.id.addopcion,aus)
                        idA.adapter = adaptador
                    }
                    else {
                        Toast.makeText(this@NewEquipo, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Aula>>, t: Throwable) {
                Toast.makeText(this@NewEquipo, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun volver(view: View){
        finish()
    }

}