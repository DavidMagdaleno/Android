package Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Perros(@SerializedName("message")
                 val message: ArrayList<String>,//Arraylist de String si tiene dentro varios valores

                //@SerializedName("userId")
                //val userId: Int? = null,

                  @SerializedName("status")
                 val status: String? = null):Serializable{
}

                  //@SerializedName("body")
                 //val tags: ArrayList<String>? = null) :Serializable{
//}