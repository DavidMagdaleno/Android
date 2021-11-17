package Modelo

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime
import java.io.Serializable

class Tarea: Serializable{
    private var Id:Int = 0
    private var IdTarea:Int = 0
    private var Tarea:String=""
    //var listaAsig= arrayListOf<String>()

    fun getId(): Int {
        return Id
    }
    fun getIdTarea(): Int {
        return IdTarea
    }
    fun getTarea(): String {
        return Tarea
    }
    //fun getTipo(): String {
        //return tipo
    //}

    constructor(i:Int, j:Int,n:String){
        this.Id=i
        this.IdTarea=j
        this.Tarea = n
    }
    /*fun asig(e: String){
        listaAsig.add(e)
    }*/

    override fun toString(): String {
        return this.getId().toString()+" "+this.getIdTarea().toString()+","+this.getTarea()
    }
}