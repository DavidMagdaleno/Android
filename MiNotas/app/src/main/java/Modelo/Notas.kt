package Modelo


import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class Notas : Serializable{

    private var Id:Int = 0
    private var asunto:String=""
    private var tipo:String=""
    private lateinit var fecha:LocalDate
    private lateinit var hora:LocalTime
    private var fech:String=""
    private var hr:String=""
    private var texto:String=""
        //var listaAsig= arrayListOf<String>()

    fun getId(): Int {
        return Id
    }
    fun getAsunto(): String {
        return asunto
    }
    fun getTipo(): String {
        return tipo
    }
    fun getTexto(): String {
        return texto
    }
    fun getFecha(): String {
        return fecha.toString()
    }
    fun getHora(): String {
        return hora.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(i:Int, n:String, s:String,t:String){
        this.Id=i
        this.asunto = n
        this.tipo = s
        fecha=LocalDate.now()
        hora= LocalTime.now()
        this.texto = t
    }
    constructor(i:Int, n:String, s:String,f:String,h:String,t:String){
        this.Id=i
        this.asunto = n
        this.tipo = s
        this.fech= f
        this.hr= h
        this.texto = t
    }
        /*fun asig(e: String){
            listaAsig.add(e)
        }*/

        override fun toString(): String {
            return this.getId().toString()+","+this.getAsunto()+","+this.getTipo()+","+fecha+","+hora
        }


}