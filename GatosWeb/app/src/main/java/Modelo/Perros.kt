package Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Perros(@SerializedName("message")
                 val message: ArrayList<String>,

                  @SerializedName("status")
                 val status: String? = null):Serializable{
}
