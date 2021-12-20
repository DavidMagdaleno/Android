package Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Profesor(@SerializedName("Password")
                   val Password: String? = null,

                   @SerializedName("DNIProfesor")
                   val DNIProfesor: String? = null,

                   @SerializedName("Nombre")
                   val Nombre: String? = null,

                   @SerializedName("Apellidos")
                   val Apellidos: String? = null) :Serializable{
}