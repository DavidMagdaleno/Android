package Modelo


import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.util.*

class Notas : Serializable{

    private var Id:Int = 0
    private var asunto:String=""
    private var tipo:String=""
    private var fecha:String=""
    private var hora:String=""
    private var texto:String=""

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
        fecha=LocalDate.now().format(DateTimeFormatter.ofPattern("d/M/y")).toString()
        hora= LocalTime.now().format(DateTimeFormatter.ofPattern("H:mm:ss")).toString()
        this.texto = t
    }
    constructor(i:Int, n:String, s:String,f:String,h:String,t:String){
        this.Id=i
        this.asunto = n
        this.tipo = s
        this.fecha= f
        this.hora= h
        this.texto = t
    }

        override fun toString(): String {
            return this.getId().toString()+","+this.getAsunto()+","+this.getTipo()+","+fecha+","+hora
        }


}