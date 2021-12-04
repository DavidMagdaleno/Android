package Api

import com.example.clienteandroidserviciopython.Modelo.Rol
import com.example.clienteandroidserviciopython.Modelo.Usuario
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {

    @GET("listado")
    fun getUsuarioss(): Call<MutableList<Usuario>>

    @GET("listado/{id}")
    fun getUnUsuario(@Path("id") id:String): Call<Usuario>

    @Headers("Content-Type:application/json")
    @POST("registrar")
    fun addUsuario(@Body info: Usuario) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("login")
    fun loginUsuario(@Body info: Usuario) : Call<MutableList<Rol>>

    @DELETE("borrar/{dni}")
    fun borrarUsuario(@Path("dni") id:String) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("modificar")
    fun modUsuario(@Body info: Usuario) : Call<ResponseBody>
}