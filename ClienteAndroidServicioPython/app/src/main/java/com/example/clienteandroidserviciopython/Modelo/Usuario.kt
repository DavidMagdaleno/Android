package com.example.clienteandroidserviciopython.Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Usuario(@SerializedName("Clave")
                   val Clave: String? = null,

                   @SerializedName("DNI")
                   val DNI: String? = null,

                   @SerializedName("Nombre")
                   val Nombre: String? = null,

                   @SerializedName("Tfno")
                   val Tfno: String? = null) :Serializable{
}