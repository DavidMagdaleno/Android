package Modelo

class Especialidad {
    var IdPersona:Int=0
    var IdEspecialidad:Int=0
    var especialidad:String=""

    //var especialidad = mapOf<String, Int>("DAM" to 1, "ASIR" to 2, "DAW" to 3)

    fun getidPersona(): Int {
        return IdPersona
    }
    fun setidPersona(e:Int) {
        IdPersona=e
    }
    fun getidEspecialidad(): Int {
        return IdEspecialidad
    }
    fun setidEspecialidad(e:Int) {
        IdEspecialidad=e
    }
    fun getespecialidad(): String {
        return especialidad
    }
    fun setespecialidad(e:String) {
        especialidad=e
    }

    constructor(e:Int,ie:Int,s:String){
        this.IdPersona = e
        this.IdEspecialidad = ie
        this.especialidad = s
    }


}