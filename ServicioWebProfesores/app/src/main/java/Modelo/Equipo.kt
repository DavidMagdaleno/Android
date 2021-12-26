package Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Equipo(@SerializedName("IdAula")
                val IdAula: Int? = null,

                @SerializedName("IdEquipo")
                val IdEquipo: Int? = null,

                @SerializedName("Procesador")
                val Procesador: String? = null,

                @SerializedName("RAM")
                val RAM: String? = null,

                @SerializedName("Pantalla")
                val Pantalla: Int? = null) : Serializable {
}