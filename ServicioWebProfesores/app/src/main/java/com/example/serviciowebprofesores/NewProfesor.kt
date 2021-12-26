package com.example.serviciowebprofesores

import Api.ServiceBuilder
import Api.UserAPI
import Modelo.Asignacion
import Modelo.Aula
import Modelo.Profesor
import Modelo.Rol
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
    lateinit var operacion:String
    lateinit var po:Profesor
    var roles = ArrayList<String>()
    var rolProfeM:Boolean=false
    var rolEncargM:Boolean=false
    var rolJefeM:Boolean=false



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



        operacion = intent.getStringExtra("opcion").toString()

        if (operacion.equals("modificar")){
            po = intent.getSerializableExtra("modificar") as Profesor
            dni.setText(po.DNIProfesor.toString())
            nom.setText(po.Nombre.toString())
            apel.setText(po.Apellidos.toString())
            pass.setText(po.Password.toString())

            conocerRoles(dni.text.toString())



            dni.isEnabled = false  //No dejamos modificar el id que es la clave del registro.
            pass.isEnabled = false //Si ya esta creado se cambia la contraseña con el boton de cambiar
        }

    }

    fun NuevoProfesor(view: View){
        val us = Profesor(pass.text.toString(),dni.text.toString(),nom.text.toString(),apel.text.toString())

        if (operacion.equals("nuevo")){
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

        }else{
            //aqui el modificar
            val us = Profesor(pass.text.toString(),dni.text.toString(),nom.text.toString(),apel.text.toString())
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.modUsuario(us)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.e("Fernando", response.message())
                    Log.e("Fernando", response.code().toString())
                    if (response.code() == 200) {
                        Log.e("Fernando", "Registro modificado con éxito.")
                        Toast.makeText(this@NewProfesor, "Se ha modificado el aula con éxito.",Toast.LENGTH_LONG).show()
                    } else {
                        Log.e("Fernando", "Algo ha fallado en la modificación.")
                        Toast.makeText(this@NewProfesor,"Algo ha fallado en la modificación",Toast.LENGTH_LONG).show()
                    }
                    if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                        Log.e("Fernando", "Registro modificado con éxito.")
                        //Toast.makeText(contexto, "Registro modificado con éxito", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Fernando", "Algo ha fallado en la conexión.")
                    Toast.makeText(this@NewProfesor, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
                }
            })
            //modificar roles
            if(!rolProfeM && rolProfe.isChecked){añadirRol(1)}
            if(rolProfeM && !rolProfe.isChecked){
                roles.add(dni.text.toString())
                roles.add("1")
                borrarRol(roles)
            }
            if(!rolEncargM && rolEncarg.isChecked){añadirRol(2)}
            if(rolEncargM && !rolEncarg.isChecked){
                roles.add(dni.text.toString())
                roles.add("2")
                borrarRol(roles)
            }
            if(!rolJefeM && rolJefe.isChecked){añadirRol(3)}
            if(rolJefeM && !rolJefe.isChecked){
                roles.add(dni.text.toString())
                roles.add("3")
                borrarRol(roles)
            }
        }
    }


    fun añadirRol(Idrol:Int){
        val us = Asignacion(dni.text.toString(),Idrol)
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.addAsignacion(us)
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

    fun conocerRoles(dni:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getRolId(dni)
        call.enqueue(object : Callback<MutableList<Rol>> {
            override fun onResponse(call: Call<MutableList<Rol>>, response: Response<MutableList<Rol>>) {
                val post = response.body()
                if (response.isSuccessful){
                    if (post != null) {
                        for(it in post){
                            if(it.rol.toString().equals("Profesor")){
                                rolProfe.isChecked=true
                                antiguoRolProf()
                            }
                            if(it.rol.toString().equals("Encargado")){
                                rolEncarg.isChecked=true
                                antiguoRolEncarg()
                            }
                            if(it.rol.toString().equals("Jefe")){
                                rolJefe.isChecked=true
                                antiguoRoljefe()
                            }
                        }
                    }
                }else {
                    Toast.makeText(this@NewProfesor, "El profesor no tiene ningun rol", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<MutableList<Rol>>, t: Throwable) {
                Toast.makeText(this@NewProfesor, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun antiguoRolProf(){
        rolProfeM=true
    }
    fun antiguoRolEncarg(){
        rolEncargM=true
    }
    fun antiguoRoljefe(){
        rolJefeM=true
    }

    fun borrarRol(id:ArrayList<String>){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.borrarRol(id)
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("Fernando",response.message())
                Log.e ("Fernando", response.code().toString())
                if (response.code() == 200) {
                    Log.e("Fernando","Registro eliminado con éxito.")
                    Toast.makeText(this@NewProfesor, "Rol eliminado con éxito", Toast.LENGTH_SHORT).show()
                }
                else {
                    Log.e("Fernando","Algo ha fallado en el borrado: Rol no encontrado.")
                    Toast.makeText(this@NewProfesor, "Algo ha fallado en el borrado: Rol no encontrado",Toast.LENGTH_LONG).show()
                }
                if (response.isSuccessful){ //Esto es otra forma de hacerlo en lugar de mirar el código.
                    Log.e("Fernando","Registro eliminado con éxito.")
                    //Toast.makeText(this@MainActivity, "Registro eliminado con éxito", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Fernando","Algo ha fallado en la conexión.")
                Toast.makeText(this@NewProfesor, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
            }
        })
    }
}