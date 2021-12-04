package Api

import Modelo.Profesor
import Modelo.Rol
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {

    @GET("listado")
    fun getUsuarioss(): Call<MutableList<Profesor>>

    @GET("listado/{id}")
    fun getUnUsuario(@Path("id") id:String): Call<Profesor>

    @Headers("Content-Type:application/json")
    @POST("registrar")
    fun addUsuario(@Body info: Profesor) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("login")
    fun loginUsuario(@Body info: Profesor) : Call<MutableList<Rol>>

    @DELETE("borrar/{dni}")
    fun borrarUsuario(@Path("dni") id:String) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("modificar")
    fun modUsuario(@Body info: Profesor) : Call<ResponseBody>
}