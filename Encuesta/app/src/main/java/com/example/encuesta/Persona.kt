package com.example.encuesta

import java.io.Serializable

class Persona : Serializable{
    private var Id:Int=0
    private var nombre:String=""
    private var sistema:String=""
    private var especialidad:String=""
    private var horas:Int=0
    var listaAsig= arrayListOf<String>()

    fun getNombre(): String {
        return nombre
    }
    fun getSistema(): String {
        return sistema
    }

    fun getHoras(): Int {
        return horas
    }
    fun setHoras(h:Int) {
         horas=h
    }
    fun getId(): Int {
        return Id
    }
    fun setId(i:Int) {
        Id=i
    }
    constructor(i:Int, n: String, s:String, h:Int){
        this.Id=i
        this.nombre = n
        this.sistema = s
        this.horas = h
    }
    constructor(i:Int,n:String, s:String){
        this.Id=i
        this.nombre = n
        this.sistema = s
    }
    fun asig(e: String){
        listaAsig.add(e)
    }

    override fun toString(): String {
        return this.getNombre()+","+this.getSistema()+","+this.listaAsig+","+this.getHoras()
    }

}