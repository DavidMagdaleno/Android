package com.example.ejercicioregistro

import java.io.Serializable


class Persona :Serializable{
    private var nombre:String=""
    private var DNI:String=""
    //private var especialidad:String=""
    //private var horas:Int=0
    //var listaAsig= arrayListOf<String>()

    fun getNombre(): String {
        return nombre
    }
    fun getDni(): String {
        return DNI
    }

    /*constructor(n:String, s:String,e:String,h:Int){
        this.nombre = n
        this.sistema = s
        this.especialidad = e
        this.horas = h
    }*/
    constructor(n:String, d:String){
        this.nombre = n
        this.DNI = d
    }

    override fun toString(): String {
        return this.getNombre()+","+this.getDni()
    }
}