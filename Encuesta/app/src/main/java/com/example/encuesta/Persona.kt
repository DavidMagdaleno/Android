package com.example.encuesta

class Persona {
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
    fun getEspecialidad(): String {
        return especialidad
    }
    fun getHoras(): Int {
        return horas
    }
    fun setHoras(h:Int) {
         horas=h
    }
    constructor(n:String, s:String,e:String,h:Int){
        this.nombre = n
        this.sistema = s
        this.especialidad = e
        this.horas = h
    }
    constructor(n:String, s:String){
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