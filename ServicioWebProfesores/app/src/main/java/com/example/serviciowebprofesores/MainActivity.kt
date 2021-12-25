package com.example.serviciowebprofesores

import Api.ServiceBuilder
import Api.UserAPI
import Auxiliar.MiAdaptador
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
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),FragmentVer.OnFragmentInteractionListener {
    lateinit var verProfe:Button
    lateinit var masProfe:Button
    lateinit var masAula:Button
    lateinit var masEquipo:Button
    var contexto = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DialogLogin()
    }
    var r=""
    lateinit var dni:TextView
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
        call.enqueue(object : Callback<MutableList<Rol>> {
            override fun onResponse(
                call: Call<MutableList<Rol>>,
                response: Response<MutableList<Rol>>
            ) {
                if (response.code() == 200) {
                    var roles = ArrayList<String>()
                    for (post in response.body()!!) {
                        roles.add(post.rol.toString())
                    }
                    if(roles.size>0){//saltar para cuando solo tiene un rol
                        DialogLogin2(roles,user)
                    }
                    //Log.e("David", "Login efectuado con éxito.")
                    Toast.makeText(contexto, response.message().toString(), Toast.LENGTH_SHORT).show()
                } else {
                    //Log.e("Fernando", "Login Incorrecto.")
                    Toast.makeText(contexto, response.message().toString(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<MutableList<Rol>>, t: Throwable) {
                //Log.e("Fernando", "Algo ha fallado en la conexión.")
                Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun DialogLogin2(roles:ArrayList<String>,user:String): Boolean {
        verProfe= findViewById<Button>(R.id.btnMostrarPro)
        masProfe= findViewById<Button>(R.id.btnAñadirProfe)
        masAula= findViewById<Button>(R.id.btnAñadirAula)
        masEquipo= findViewById<Button>(R.id.btnAñadirEquipo)
        var intentMain:Intent
        var rol:TextView=findViewById(R.id.txtRol)
        val dialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        val Myview=layoutInflater.inflate(R.layout.item_login2, null)
        var srol = Myview.findViewById<Spinner>(R.id.spinRol)
        dialogo.setView(Myview)
        val adaptador = ArrayAdapter(this, R.layout.item_cspinner,R.id.addopcion,roles)
        srol.adapter = adaptador
        buscarProfesor(user)
        srol.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                var p = roles.get(pos)
                if(p.equals("Profesor")){
                    rol.setText("Profesor")
                    //r=rol.text.toString()
                    FragmentRV.role="P"
                    FragmentRV.Dni=dni.text.toString()
                    verProfe.isEnabled=false
                    masProfe.isEnabled=false
                    masAula.isEnabled=false
                    masEquipo.isEnabled=false
                    FragmentCabecera.rol=p
                }
                if(p.equals("Jefe")){
                    rol.setText("Jefe")
                    //r=rol.text.toString()
                    FragmentRV.role="J"
                    verProfe.isEnabled=true
                    masProfe.isEnabled=true
                    masAula.isEnabled=true
                    masEquipo.isEnabled=true
                    FragmentCabecera.rol=p
                }
                if(p.equals("Encargado")){
                    rol.setText("Encargado")
                    //r=rol.text.toString()
                    FragmentRV.role="E"
                    FragmentCabecera.rol=p
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
        dialogo.setPositiveButton("Entrar",
            DialogInterface.OnClickListener { dialog, which ->
                intentMain=Intent(this,SplashBienvenida::class.java)
                startActivity(intentMain)
            })
        dialogo.setNegativeButton("Salir",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
        dialogo.show()
        return true
    }
    fun buscarProfesor(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnProfesor(idBusc);

        call.enqueue(object : Callback<Profesor> {
            override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                val post = response.body()
                if (post != null) {
                    if (response.isSuccessful){
                        FragmentCabecera.nombre=post.Nombre.toString()
                        FragmentCabecera.apellidos=post.Apellidos.toString()
                        FragmentCabecera.dni=post.DNIProfesor.toString()

                        var nom:TextView=findViewById(R.id.txtNomCabez)
                        var apel:TextView=findViewById(R.id.txtApelCabez)
                        dni=findViewById(R.id.txtDNI)
                        nom.setText(post.Nombre.toString())
                        apel.setText(post.Apellidos.toString())
                        dni.setText(post.DNIProfesor.toString())
                    }
                    else {
                        Toast.makeText(contexto, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<Profesor>, t: Throwable) {
                Toast.makeText(contexto, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Los fragments en realidad no se hablan. El Main se usa como un intermediario que es donde se realiza el intercambio de información.
     */
    override fun onFragmentInteraction(texto: String) {
        var manager: FragmentManager = supportFragmentManager
        var fragment:FragmentRV = manager?.findFragmentById(R.id.fragmentRecycler) as FragmentRV
        fragment.onFragmentInteraction(texto)
    }


}


