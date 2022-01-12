package com.example.nuevoproyecfirebase

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        title = "Inicio"


        val bundle:Bundle? = intent.extras
        txtEmail.text = bundle?.getString("email")
        val prov:String = bundle?.getString("provider").toString()
        txtProveedor.text = bundle?.getString("provider").toString()

        //Guardado de datos para toda la aplicación en la sesión.
        val prefs: SharedPreferences.Editor? = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs?.putString("email",bundle?.getString("email"))
        prefs?.putString("provider",bundle?.getString("provider"))
        prefs?.apply () //Con estos datos guardados en el fichero de sesión, aunque la app se detenga tendremos acceso a los mismos.

        btCerrarSesion.setOnClickListener {
            val prefs: SharedPreferences.Editor? = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs?.clear() //Al cerrar sesión borramos los datos
            prefs?.apply ()
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}