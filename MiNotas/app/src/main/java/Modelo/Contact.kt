package Modelo

class Contact{
    private var numero:String = ""
    private var nombre:String=""

    fun getNumero(): String {
        return numero
    }
    fun setNumero(i:String) {
        numero=i
    }
    fun getNombre(): String {
        return nombre
    }
    fun setNombre(e:String) {
        nombre=e
    }
    constructor(){
    }
    constructor(i:String, n:String){
        this.numero=i
        this.nombre = n
    }
}