package com.example.desafiofinal

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var roles = ArrayList<Int>()
    var tipoRol = ArrayList<String>()
    lateinit var intentMain: Intent
    private var rolEscogido:Int=-1
    private var RC_SIGN_IN = 1
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance() //Variable con la que accederemos a Firestore. Será una instancia a la bd.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Con esto lanzamos eventos personalizados a GoogleAnalytics que podemos ver en nuestra consola de FireBase.
        val analy: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message","Integración completada")
        analy.logEvent("InitScreen",bundle)

        title = "Autenticación"
        btRegistro.setOnClickListener(){
            if (txtEmail.text.isNotEmpty() && txtPassw.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(txtEmail.text.toString(),txtPassw.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        irRegistro(it.result?.user?.email?:"")  //Esto de los interrogantes es por si está vacío el email. se envia a recoger los datos del usuario
                    } else {
                        showAlert()
                    }
                }
            }
        }

        btLogin.setOnClickListener(){
            if (txtEmail.text.isNotEmpty() && txtPassw.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(txtEmail.text.toString(),txtPassw.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        try {
                            recuperarRoles(object : RolCallback {
                                override fun rolRecibido(rolNuevo: ArrayList<Int>) {
                                    roles = rolNuevo
                                    tipoRol.add("selecciona Perfil")
                                    for(i in 0..roles.size-1){
                                        if(roles[i].equals(1.toLong())){tipoRol.add("Usuario")}
                                        if(roles[i].equals(2.toLong())){tipoRol.add("Administrador")}
                                        if(roles[i].equals(3.toLong())){tipoRol.add("Encargado")}
                                    }
                                    DialogLogin2(it)
                                }
                            })
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                        //irHome(it.result?.user?.email?:"",ProviderType.BASIC)  //Esto de los interrogantes es por si está vacío el email.
                    } else {
                        showAlert()
                    }
                }
            }
        }

        btGoogle.setOnClickListener {
            //Configuración
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.request_id_token)) //Esto se encuentra en el archivo google-services.json: client->oauth_client -> client_id
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this,googleConf) //Este será el cliente de autenticación de Google.
            googleClient.signOut() //Con esto salimos de la posible cuenta  de Google que se encuentre logueada.
            val signInIntent = googleClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        //session()
    }
    //******************************** Para la sesión ***************************
    /*private fun session(){
        val prefs: SharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE) //Aquí no invocamos al edit, es solo para comprobar si tenemos datos en sesión.
        val email:String? = prefs.getString("email",null)
        if (email != null){
            //Tenemos iniciada la sesión.
        }
    }*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Si la respuesta de esta activity se corresponde con la inicializada es que viene de la autenticación de Google.
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("Fernando", "firebaseAuthWithGoogle:" + account.id)
                //Ya tenemos la id de la cuenta. Ahora nos autenticamos con FireBase.
                if (account != null) {
                    val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful){
                            DialogLogin(account)
                            //irHome(account.email?:"",ProviderType.GOOGLE)  //Esto de los interrogantes es por si está vacío el email.
                        } else {
                            showAlert()
                        }
                    }
                }
                //firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("Fernando", "Google sign in failed", e)
                showAlert()
            }
        }
    }

    /*
    Cuando un usuario accede por primera vez, se crea una cuenta de usuario nueva y se la vincula con las credenciales (el nombre de usuario y
    la contraseña, el número de teléfono o la información del proveedor de autenticación) que el usuario utilizó para acceder. Esta cuenta nueva
    se almacena como parte de tu proyecto de Firebase y se puede usar para identificar a un usuario en todas las apps del proyecto, sin importar cómo acceda.
     */
    //*********************************************************************************
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //*********************************************************************************
    private fun irRegistro(email:String){
        val homeIntent = Intent(this, DatosUsuario::class.java).apply {
            putExtra("email",email)
            putExtra("Mod","NONE")
            //putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }

    private fun irOpciones(email:String,rol:String){
        val homeIntent = Intent(this, Opciones::class.java).apply {
            putExtra("email",email)
            putExtra("surol",rol)
        }
        startActivity(homeIntent)
    }

    interface RolCallback {
        fun rolRecibido(roles: ArrayList<Int>)
    }

    fun recuperarRoles( callback:RolCallback){
        db.collection("usuarios").document(txtEmail.text.toString()).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //poner los demas para recuperar todos los datos
                    var croles= task.result.data?.get("roles") as ArrayList<Int>
                    if (callback != null) {
                        callback.rolRecibido(croles);
                    }
                } else {
                    Log.e("wh", "Error getting documents.", task.exception)
                }
            }
    }


    interface RolCallback2 {
        fun aceptacion(acept: String)
    }

    fun EsAceptado( callback:RolCallback2){
        db.collection("usuarios").document(txtEmail.text.toString()).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var ac=task.result.data?.get("Aceptado") as String
                    //poner los demas para recuperar todos los datos
                    if (callback != null) {
                        callback.aceptacion(ac);
                    }
                } else {
                    Log.e("wh", "Error getting documents.", task.exception)
                }
            }
    }

    fun DialogLogin(account: GoogleSignInAccount): Boolean {
        val dialogo: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        val Myview=layoutInflater.inflate(R.layout.item_login, null)
        var srol = Myview.findViewById<Spinner>(R.id.spinRol)
        dialogo.setView(Myview)
        val adaptador = ArrayAdapter(this, R.layout.item_spiner,R.id.txtOpcion,tipoRol)
        srol.adapter = adaptador
        srol.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                var p = tipoRol.get(pos)
                if(p.equals("Usuario")){
                    rolEscogido=1
                    irOpciones(account.email!!,"Usuario")
                    //irHome(account.email?:"")  //Esto de los interrogantes es por si está vacío el email.-----------ir a eventos
                }
                if(p.equals("Administrador")){
                    rolEscogido=2
                    //irHome(account.email?:"")  //Esto de los interrogantes es por si está vacío el email.
                    irOpciones(account.email!!,"Administrador")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
        dialogo.setPositiveButton("Entrar",
            DialogInterface.OnClickListener { dialog, which ->
            })
        dialogo.setNegativeButton("Salir",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
        dialogo.show()
        return true
    }

    fun DialogLogin2(it: Task<AuthResult>): Boolean {
        val dialogo: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        val Myview=layoutInflater.inflate(R.layout.item_login, null)
        var srol = Myview.findViewById<Spinner>(R.id.spinRol)
        dialogo.setView(Myview)
        val adaptador = ArrayAdapter(this, R.layout.item_spiner,R.id.txtOpcion,tipoRol)
        srol.adapter = adaptador
        srol.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                var p = tipoRol.get(pos)
                if(p.equals("Usuario")){
                    try {
                        EsAceptado(object : RolCallback2 {
                            override fun aceptacion(estado: String) {
                                if(estado=="Aceptado"){
                                    tipoRol.clear()
                                    irOpciones(it.result?.user?.email?:"","Usuario")
                                }else{
                                    lbStatus.setText("Su solicitud "+estado)
                                }
                            }
                        })
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    OpcionesEventos.rol=1
                }
                if(p.equals("Administrador")){
                    tipoRol.clear()
                    OpcionesEventos.rol=2
                    irOpciones(it.result?.user?.email?:"","Administrador")  //Esto de los interrogantes es por si está vacío el email.
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
        dialogo.setPositiveButton("Entrar",
            DialogInterface.OnClickListener { dialog, which ->
                tipoRol.clear()
            })
        dialogo.setNegativeButton("Salir",
            DialogInterface.OnClickListener { dialog, which ->
                tipoRol.clear()
                dialog.dismiss()
            })
        dialogo.show()
        return true
    }



}