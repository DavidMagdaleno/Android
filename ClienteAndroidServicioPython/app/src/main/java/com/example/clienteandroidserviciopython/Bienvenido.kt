package com.example.clienteandroidserviciopython

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Bienvenido : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenido)

        var txtRoles = findViewById<TextView>(R.id.txtRoles)
        val roles: ArrayList<String> = intent.getSerializableExtra("roles") as ArrayList<String>
        if (roles.size > 0) {
            for (rol in roles!!) {
                txtRoles.append(rol)
            }
        }
        else {
            txtRoles.text = "Sin roles asignados"
        }

    }
}