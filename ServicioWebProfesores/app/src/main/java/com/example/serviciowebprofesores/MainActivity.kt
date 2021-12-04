package com.example.serviciowebprofesores

import Api.ServiceBuilder
import Api.UserAPI
import Modelo.Profesor
import Modelo.Rol
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var contexto = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DialogLogin()
    }

    fun DialogLogin(): Boolean {
        val dialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        val Myview=layoutInflater.inflate(R.layout.item_login, null)
        var user = Myview.findViewById<EditText>(R.id.txtUserLogin)
        var pass = Myview.findViewById<EditText>(R.id.txtPassLogin)
        dialogo.setView(Myview)

        dialogo.setPositiveButton("Entrar",
            DialogInterface.OnClickListener { dialog, which ->
                login(user.text.trim().toString(),pass.text.trim().toString())

            })
        dialogo.setNegativeButton("Salir",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
        dialogo.show()

        return true
    }

    fun login(user:String, pass:String){
        val pr = Profesor(pass,user,"","" )
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.loginUsuario(pr)

        //var intentV1 = Intent(this, Bienvenido::class.java)

        call.enqueue(object : Callback<MutableList<Rol>> {

            override fun onResponse(
                call: Call<MutableList<Rol>>,
                response: Response<MutableList<Rol>>
            ) {
                //Log.e ("Fernando", response.code().toString())
                if (response.code() == 200) {
                    var roles = ArrayList<String>()
                    for (post in response.body()!!) {
                        roles.add(post.rol.toString())
                    }
                    if(roles.size>0){
                        DialogLogin2(roles)
                    }
                    Log.e("David", "Login efectuado con éxito.")
                    Toast.makeText(contexto, response.message().toString(), Toast.LENGTH_SHORT).show()
                    //intentV1.putExtra("roles",roles)
                    //startActivity(intentV1)
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

    fun DialogLogin2(roles:ArrayList<String>): Boolean {
        var intentMain:Intent
        val dialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        val Myview=layoutInflater.inflate(R.layout.item_login2, null)
        var srol = Myview.findViewById<Spinner>(R.id.spinRol)
        dialogo.setView(Myview)
        val adaptador = ArrayAdapter(this, R.layout.item_cspinner,R.id.addopcion,roles)
        srol.adapter = adaptador
        srol.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                var p = roles.get(pos)
                if(p.equals("Profesor")){
                    Log.e("click","rol profesor")
                    //Fichero.escribirLinea("Se ha creado una nota simple",NombreFoto.log)
                    //intentMain=  Intent(ventanaactual,NotaSimple::class.java)
                }
                if(pos==1){
                    Log.e("click","rol 1")
                    //Fichero.escribirLinea("Se ha creado una tarea",NombreFoto.log)
                    //intentMain=  Intent(ventanaactual,NotaTarea::class.java)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
        dialogo.setPositiveButton("Entrar",
            DialogInterface.OnClickListener { dialog, which ->
                intentMain=Intent(this,SplashBienvenida::class.java)
                //intentMain.putExtra("hola",) hay que buscar por id
                startActivity(intentMain)
            })
        dialogo.setNegativeButton("Salir",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
        dialogo.show()

        return true
    }




}


