package Api

import Modelo.Perros
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {


    @GET("hound/images")
    fun getPerros(): Call<Perros>

    @GET("{message}/images")
    fun getFotoPerro(@Path("message") message:String): Call<Perros>



}
