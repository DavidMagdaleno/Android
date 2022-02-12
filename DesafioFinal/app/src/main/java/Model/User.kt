package Model

data class User(var DNI:String, var Nombre:String, var Apellidos:String, var Aceptado:String, var email:String, var Ubicacion:String, var Hora:String, var roles: ArrayList<Int>)