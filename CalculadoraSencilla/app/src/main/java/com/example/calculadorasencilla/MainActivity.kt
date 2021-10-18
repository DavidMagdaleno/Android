package com.example.calculadorasencilla

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    var operacion:Int=0

    fun zero(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+0)
    }
    fun uno(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+1)
    }
    fun dos(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+2)
    }
    fun tres(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+3)
    }
    fun cuatro(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+4)
    }
    fun cinco(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+5)
    }
    fun seis(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+6)
    }
    fun siete(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+7)
    }
    fun ocho(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+8)
    }
    fun nueve(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+9)
    }

    fun suma(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+"+")
        operacion=1
    }
    fun resta(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+"-")
        operacion=2
    }
    fun multiplicar(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+"x")
        operacion=3
    }
    fun dividir(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+"/")
        operacion=4
    }
    fun coma(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.setText(result.text.toString()+".")
    }
    fun borrar(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        result.text.clear()
    }

    fun borrarUltimo(view: View){
        var result:EditText=findViewById(R.id.etxtResultado)
        var resultAux=result.text.lines()[result.text.lines().count()-1]
        resultAux=resultAux.removeSuffix(resultAux[resultAux.length-1].toString())
        result.setText(resultAux)
    }
    fun igual(view: View){
        var valido:Boolean=true
        var result:EditText=findViewById(R.id.etxtResultado)
        //Log.i("Values2", "value=${result.text.lines().count()}")
        var resultAux=result.text.lines()[result.text.lines().count()-1]
        //Log.i("Values", "value=${resultAux}")
        var lstValues: List<String> = emptyList()
        when(operacion){
            1-> lstValues=resultAux .split("+").map { it }
            2-> lstValues=resultAux .split("-").map { it }
            3-> lstValues=resultAux .split("x").map { it }
            4-> lstValues=resultAux .split("/").map { it }
        }
        try{
            var num:Float= 0F
            var num2:Float= 0F
            var cont:Int=0
            when(operacion){
                1 ->  lstValues.forEach { it ->  num=num+it.toFloat()  }
                2 -> lstValues.forEach { it ->   num=num-it.toFloat()  }
                3 -> lstValues.forEach { it ->   num=num*it.toFloat()  }
                4 -> lstValues.forEach { it ->   try{   num=it.toFloat()
                                                        if(cont==0){
                                                            num2=num
                                                            cont++
                                                        }
                                                        if(num>0){num=num2/num}else{
                                                            result.text.append("\r\n ERROR: division entre 0 \r\n")
                                                            valido=false
                                                        }
                                                    }catch (e: ArithmeticException){ result.text.append("\r\n"+e.message+"\r\n") }
                                        }
            }
            if(valido){
                if(result.text.lines().count()>1){
                    resultAux=result.text.lines()[1]
                    result.text.clear()
                    result.text.append(resultAux)
                }
                result.text.append("\r\n"+num.toString())
            }
        }catch(e: Exception){
            result.text.append("\r\n ERROR: Mas de una operacion\r\n")
        }
    }


}