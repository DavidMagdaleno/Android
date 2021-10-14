package com.example.encuesta

class Persona {
    private var nombre:String=""
    private var sistema:String=""
    private var especialidad:String=""
    private var horas:Int=0

    fun getNombre(): String {
        return nombre
    }
    fun setNombre(n: String) {
        nombre = n
    }
    fun getSistema(): String {
        return sistema
    }
    fun setSistema(s: String) {
        sistema = s
    }
    fun getEspecialidad(): String {
        return especialidad
    }
    fun setEspecialidad(e: String) {
        especialidad = e
    }
    fun getHoras(): Int {
        return horas
    }
    fun setHoras(h: Int) {
        horas = h
    }
    constructor(n:String, s:String,e:String,h:Int){
        this.nombre = n
        this.sistema = s
        this.especialidad = e
        this.horas = h
    }

}