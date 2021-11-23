package Api

import Modelo.Perros
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {

    /*
    Métodos usados para el acceso sin ViewModel.
     */
    @GET("hound/images")
    fun getUsuarioss(): Call<Perros>


    @GET("{message}/images")
    fun getUnUsuario(@Path("message") message:String): Call<Perros>



    /*
    https://dog.ceo/api/breed/african/images
    Métodos usados para el acceso con ViewModel.
     */
    @GET("{message}/images")//IMPORTANTE cambiar segun este diseñado el json para acceder al contenido
    suspend fun getFotoPerro(@Path("message") message:String): Perros

    @GET("hound/images")
    suspend fun getPerros(): List<Perros>
}