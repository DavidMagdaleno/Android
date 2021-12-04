package Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Rol (@SerializedName("Descripcion")
           val rol: String? = null,
) : Serializable {
}