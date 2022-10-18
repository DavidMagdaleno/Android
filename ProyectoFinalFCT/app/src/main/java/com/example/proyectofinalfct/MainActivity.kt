package com.example.proyectofinalfct

import android.content.Intent
import android.icu.text.UnicodeSetSpanner
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import androidx.appcompat.app.AlertDialog
import androidx.core.text.trimmedLength
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
        btnPruebaGuardar.setOnClickListener(){

            if (txtUser.text.trimmedLength()!=0 && (!isEmpty(txtUser.text.toString())) && txtPwd.text.trimmedLength()!=0 && (!isEmpty(txtPwd.text.toString()))){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(txtUser.text.trim().toString(),txtPwd.text.trim().toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        irRegistro(it.result?.user?.email?:"")  //Esto de los interrogantes es por si está vacío el email. se envia a recoger los datos del usuario
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }

    /*private fun isEmpty(s:String):Boolean{

        for (i in 0..s.length){
            if (s[i]==' '){}
        }
    }*/

    private fun irRegistro(email:String){
        val homeIntent = Intent(this, DatosUsuario::class.java).apply {
            putExtra("email",email)
            putExtra("Mod","NONE")
            //putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}