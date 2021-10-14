package com.example.lanzardados

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var rand = Random()

    fun TirarDado(){
        var alea = rand.nextInt(6)+1
        when(alea){
            1->
        }

    }
}