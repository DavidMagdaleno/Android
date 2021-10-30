package com.example.ejercicioregistro

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    fun registro(view:View){
        val intentMain: Intent = Intent(this,Registro::class.java)
        resultLauncher.launch(intentMain)
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            // Get String data from Intent
            //val returnString = data!!.getStringExtra("valorEdicionV2")
            val returnString = data!!.getSerializableExtra("Personas")
            // Set text view with string
            val textView: TextView = findViewById(R.id.txtUsuarios)
            textView.append(returnString.toString()+"\r\n")
        }
    }

}