package com.example.ejercicio3

import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var vecOperador= ArrayList<String>()
        var vecOperador2= ArrayList<String>()
        vecOperador.add("SUMAR")
        vecOperador.add("RESTAR")
        vecOperador.add("MULTIPLICAR")
        vecOperador.add("DIVIDIR")

        vecOperador2.add(" ")
        vecOperador2.add("SUMAR")
        vecOperador2.add("RESTAR")
        vecOperador2.add("MULTIPLICAR")
        vecOperador2.add("DIVIDIR")

        var ml:ListView=findViewById(R.id.lstOperador)
        var sp:Spinner=findViewById(R.id.spItem)
        val adaptador=ArrayAdapter(this,R.layout.item_lista,R.id.txtItem,vecOperador)
        val adaptadorSpinner=ArrayAdapter(this,R.layout.item_lista,R.id.txtItem,vecOperador2)
        ml.adapter=adaptador
        sp.adapter=adaptadorSpinner

        var intentMain: Intent = Intent(this,Resultado::class.java)
        var num1:EditText=findViewById(R.id.etxtN1)
        var num2:EditText=findViewById(R.id.etxtN2)

        ml.onItemClickListener= object : AdapterView.OnItemClickListener{

            override fun onItemClick( parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                var p=vecOperador.get(position)//me muestra el texto que hay dentro
                Log.e("valor", id.toString())
                Log.e("valor2", position.toString())
                Log.e("valor3", p)
                if(position==0){
                    var result=Integer.parseInt(num1.text.toString())+Integer.parseInt(num2.text.toString())
                    intentMain.putExtra("resultadofinal",result.toString())
                    Toast.makeText(applicationContext, "Sumando...", Toast.LENGTH_SHORT).show()
                    startActivity(intentMain)
                }
                if(position==1){
                    var result=Integer.parseInt(num1.text.toString())-Integer.parseInt(num2.text.toString())
                    intentMain.putExtra("resultadofinal",result.toString())
                    Toast.makeText(applicationContext, "Restando...", Toast.LENGTH_SHORT).show()
                    startActivity(intentMain)
                }
                if(position==2){
                    var result=Integer.parseInt(num1.text.toString())*Integer.parseInt(num2.text.toString())
                    intentMain.putExtra("resultadofinal",result.toString())
                    Toast.makeText(applicationContext, "Multiplicando...", Toast.LENGTH_SHORT).show()
                    startActivity(intentMain)
                }

                if(position==3){
                    if(Integer.parseInt(num2.text.toString())>0){
                        var result=Integer.parseInt(num1.text.toString())/Integer.parseInt(num2.text.toString())
                        intentMain.putExtra("resultadofinal",result.toString())
                        Toast.makeText(applicationContext, "Dividiendo...", Toast.LENGTH_SHORT).show()
                        startActivity(intentMain)
                    }else{
                        intentMain.putExtra("resultadofinal","ERROR Division por 0")
                        startActivity(intentMain)
                    }
                }
            }
        }
        sp.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected( parent: AdapterView<*>?, view: View?, position: Int,  id: Long ) {

                var p=vecOperador.get(position)//me muestra el texto que hay dentro
                Log.e("valor", id.toString())
                Log.e("valor2", position.toString())
                Log.e("valor3", p)
                if(position==1){
                    var result=Integer.parseInt(num1.text.toString())+Integer.parseInt(num2.text.toString())
                    intentMain.putExtra("resultadofinal",result.toString())
                    Toast.makeText(applicationContext, "Sumando...", Toast.LENGTH_SHORT).show()
                    startActivity(intentMain)
                }
                if(position==2){
                    var result=Integer.parseInt(num1.text.toString())-Integer.parseInt(num2.text.toString())
                    intentMain.putExtra("resultadofinal",result.toString())
                    Toast.makeText(applicationContext, "Restando...", Toast.LENGTH_SHORT).show()
                    startActivity(intentMain)
                }
                if(position==3){
                    var result=Integer.parseInt(num1.text.toString())*Integer.parseInt(num2.text.toString())
                    intentMain.putExtra("resultadofinal",result.toString())
                    Toast.makeText(applicationContext, "Multiplicando...", Toast.LENGTH_SHORT).show()
                    startActivity(intentMain)
                }

                if(position==4){
                    if(Integer.parseInt(num2.text.toString())>0){
                        var result=Integer.parseInt(num1.text.toString())/Integer.parseInt(num2.text.toString())
                        intentMain.putExtra("resultadofinal",result.toString())
                        Toast.makeText(applicationContext, "Dividiendo...", Toast.LENGTH_SHORT).show()
                        startActivity(intentMain)
                    }else{
                        intentMain.putExtra("resultadofinal","ERROR Division por 0")
                        startActivity(intentMain)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        })


    }
}