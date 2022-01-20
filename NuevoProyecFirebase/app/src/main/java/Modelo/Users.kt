package Modelo

data class Users(var DNI:String, var Nombre:String, var Apellidos:String, var provider:String, val roles: ArrayList<Int>)