package Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Aula(@SerializedName("IdAula")
                    val IdAula: Int? = null,

                    @SerializedName("DNIProfesor")
                    val DNI: String? = null,

                    @SerializedName("Descripcion")
                    val Descripcion: String? = null) : Serializable {
}