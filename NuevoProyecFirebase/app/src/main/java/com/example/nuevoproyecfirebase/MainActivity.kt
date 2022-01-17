package com.example.nuevoproyecfirebase

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //Atributos necesarios para el login con Google.
    var roles = ArrayList<Int>()
    var tipoRol = ArrayList<String>()
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
        btRegistrar.setOnClickListener(){
            if (edEmail.text.isNotEmpty() && edPass.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(edEmail.text.toString(),edPass.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        irHome(it.result?.user?.email?:"",ProviderType.BASIC)  //Esto de los interrogantes es por si está vacío el email.
                    } else {
                        showAlert()
                    }
                }
            }
        }

        btLogin.setOnClickListener(){
            if (edEmail.text.isNotEmpty() && edPass.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(edEmail.text.toString(),edPass.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        DialogLogin2(it)
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

        session()
    }
    //******************************** Para la sesión ***************************
    private fun session(){
        val prefs: SharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE) //Aquí no invocamos al edit, es solo para comprobar si tenemos datos en sesión.
        val email:String? = prefs.getString("email",null)
        val provider:String? = prefs.getString("provider", null)
        if (email != null){
            //Tenemos iniciada la sesión.
            //irHome(email, ProviderType.valueOf(provider))
            irHome(email, ProviderType.BASIC)
        }
    }
    //*************************************************************************
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
    private fun irHome(email:String, provider:ProviderType){
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
            putExtra("rol",rolEscogido)
        }
        startActivity(homeIntent)
    }

    fun recuperarRoles(){
        db.collection("users").document(edEmail.text.toString()).get().addOnSuccessListener {
            //Si encuentra el documento será satisfactorio este listener y entraremos en él.
            //txtDNI.setText(it.get("DNI") as String?)
            //txtNombre.setText(it.get("Nombre") as String?)
            //txtApel.setText(it.get("Apellidos") as String?)
            roles = it.get("roles") as ArrayList<Int>
            for(i in 0..roles.size-1){
                if(roles[i].equals(1.toLong())){tipoRol.add("Usuario")}
                if(roles[i].equals(2.toLong())){tipoRol.add("Administrador")}
                if(roles[i].equals(3.toLong())){tipoRol.add("Encargado")}
            }
            Log.e("p3 ",tipoRol.size.toString())

            //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            //Toast.makeText(this, "Algo ha ido mal al recuperar",Toast.LENGTH_SHORT).show()
        }
    }

    fun DialogLogin(account: GoogleSignInAccount): Boolean {
        recuperarRoles()
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
                    irHome(account.email?:"",ProviderType.GOOGLE)  //Esto de los interrogantes es por si está vacío el email.
                }
                if(p.equals("Administrador")){
                    rolEscogido=2
                    irHome(account.email?:"",ProviderType.GOOGLE)  //Esto de los interrogantes es por si está vacío el email.
                }
                if(p.equals("Encargado")){
                    rolEscogido=3
                    irHome(account.email?:"",ProviderType.GOOGLE)  //Esto de los interrogantes es por si está vacío el email.
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

    fun DialogLogin2(it:Task<AuthResult>): Boolean {
        recuperarRoles()//realiza el dialog result antes de que devuelva el valor-------------------------------------------------------------------------------------------------
        val dialogo: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        val Myview=layoutInflater.inflate(R.layout.item_login, null)
        var srol = Myview.findViewById<Spinner>(R.id.spinRol)
        dialogo.setView(Myview)
        val adaptador = ArrayAdapter(this, R.layout.item_spiner,R.id.txtOpcion,tipoRol)
        srol.adapter = adaptador
        Log.e("p2 ",tipoRol.size.toString())
        srol.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                var p = tipoRol.get(pos)
                Log.e("p ",p)
                if(p.equals("Usuario")){
                    rolEscogido=1
                    tipoRol.clear()
                    irHome(it.result?.user?.email?:"",ProviderType.BASIC)  //Esto de los interrogantes es por si está vacío el email.
                }
                if(p.equals("Administrador")){
                    rolEscogido=2
                    tipoRol.clear()
                    irHome(it.result?.user?.email?:"",ProviderType.BASIC)  //Esto de los interrogantes es por si está vacío el email.
                }
                if(p.equals("Encargado")){
                    rolEscogido=3
                    tipoRol.clear()
                    irHome(it.result?.user?.email?:"",ProviderType.BASIC)  //Esto de los interrogantes es por si está vacío el email.
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