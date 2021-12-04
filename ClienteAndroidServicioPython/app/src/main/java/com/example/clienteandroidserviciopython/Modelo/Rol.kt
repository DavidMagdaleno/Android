package com.example.clienteandroidserviciopython.Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Rol (@SerializedName("descripcion")
           val rol: String? = null,
           ) : Serializable {
}