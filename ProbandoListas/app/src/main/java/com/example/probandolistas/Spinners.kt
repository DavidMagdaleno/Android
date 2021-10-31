package com.example.probandolistas

import Modelo.Persona
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import java.util.ArrayList

class Spinners : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spinners)

        var vector = arrayOf(1,2)
        var vec = ArrayList<Persona>(4)
        vec.add(Persona("Fernando",38))
        vec.add(Persona("Aranzabe",71))

        var miSp: Spinner = findViewById(R.id.spMispinner)
        val adaptador = ArrayAdapter(this, R.layout.itemlo,R.id.txtItem,vec)
        miSp.adapter = adaptador

        miSp.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val texto = miSp.getItemAtPosition(pos)
                var p = vec.get(pos)
                Log.e("Fernando",p.toString())
                Toast.makeText(applicationContext, texto.toString(), Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })


    }


}