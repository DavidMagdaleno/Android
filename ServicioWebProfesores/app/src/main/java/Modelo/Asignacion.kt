package Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Asignacion(@SerializedName("DNIProfesor")
                val DNI: String? = null,

                @SerializedName("IdRol")
                val IdRol: Int? = null) : Serializable {
}