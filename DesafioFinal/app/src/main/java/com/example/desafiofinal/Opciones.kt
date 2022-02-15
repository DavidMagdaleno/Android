package com.example.desafiofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_opciones.*

class Opciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones)


        val bundle:Bundle? = intent.extras
        val email = bundle?.getString("email").toString()
        val rol:String = bundle?.getString("surol").toString()
        if (rol=="Administrador"){
            btnAdminUser.isVisible=true
            btnAdminUser.isEnabled=true
            btnBanner.isVisible=true
            btnBanner.isEnabled=true
        }


        btnModificar.setOnClickListener(){
            val homeIntent = Intent(this, DatosUsuario::class.java).apply {
                putExtra("email",email)
            }
            startActivity(homeIntent)
        }

        btnEventos.setOnClickListener(){
            val homeIntent = Intent(this, Eventos::class.java).apply {
                putExtra("email",email)
            }
            startActivity(homeIntent)
        }

        btnAdminUser.setOnClickListener(){
            val homeIntent = Intent(this, AdminUsers::class.java).apply {
                //putExtra("email",email)
            }
            startActivity(homeIntent)
        }

        btnBanner.setOnClickListener(){
            val homeIntent = Intent(this, BanUsers::class.java).apply {
                //putExtra("email",email)
            }
            startActivity(homeIntent)
        }

    }
}