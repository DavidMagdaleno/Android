package Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Profesor(@SerializedName("Password")
                   val Pass: String? = null,

                   @SerializedName("DNIProfesor")
                   val DNI: String? = null,

                   @SerializedName("Nombre")
                   val Nombre: String? = null,

                   @SerializedName("Apellidos")
                   val Apellido: String? = null) :Serializable{
}