package com.example.probandolistas

import Modelo.Persona
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*
1. Después de pintar todo para acceder: reescribe los cuatro métodos básicos:
getCount (): obtiene el número total de datos y devuelve un resultado de tipo int;
getItem (posición int): obtiene los datos en la posición especificada y devuelve los datos;
getItemId (int position): obtiene la identificación de los datos de posición especificados,
devuelve la identificación de los datos, generalmente usa la posición de los datos como su identificación;
getView (int position, View convertView, ViewGroup parent): el método clave se utiliza para determinar
el elemento de la lista

2. Crear ViewHolder
Si desea utilizar ListView, debe escribir un Adaptador para adaptar los datos a ListView. Para ahorrar
recursos y mejorar la eficiencia operativa, generalmente la clase ViewHolder personalizada para reducir
el uso de findViewById () y evitar Inflar la vista en varios lugares para lograr el objetivo
 */

    }

    fun listasSencillasListView(view:View){
        var intentListasSEncillas = Intent(this,ListaSencilla::class.java)
        startActivity(intentListasSEncillas)
    }

    fun llamarSpinners(view:View){
        var intentSpin = Intent(this,Spinners::class.java)
        startActivity(intentSpin)
    }
}
