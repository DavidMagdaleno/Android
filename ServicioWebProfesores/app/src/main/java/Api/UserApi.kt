package Api

import Modelo.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {

    @GET("listado")
    fun getProfesores(): Call<MutableList<Profesor>>

    @GET("listado/aulas")
    fun getAulas(): Call<MutableList<Aula>>

    @GET("listado/equipos")
    fun getEquipos(): Call<MutableList<Equipo>>



    @GET("listado/{id}")
    fun getUnProfesor(@Path("id") id:String): Call<Profesor>

    @GET("/listado/aulas2/{id}")
    fun getUnAula(@Path("id") id:String): Call<MutableList<Aula>>

    @GET("/listado/aulas/{id}")
    fun getUnAula2(@Path("id") id:String): Call<Aula>

    @GET("/listado/aulas/id/{id}")
    fun getAulaId(@Path("id") id:Int): Call<Aula>

    @GET("/listado/equipos/{id}")
    fun getEquiposAula(@Path("id") id:Int): Call<MutableList<Equipo>>



    //--------------------------------------------------
    @Headers("Content-Type:application/json")
    @POST("registrar")
    fun addProfesor(@Body info: Profesor) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("registrar/aula")
    fun addAula(@Body info: Aula) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("registrar/equipo")
    fun addEquipo(@Body info: Equipo) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("registrar/asignacion")
    fun addAsignacion(@Body info: Asignacion) : Call<ResponseBody>
    //--------------------------------------------------

    @Headers("Content-Type:application/json")
    @POST("login")
    fun loginUsuario(@Body info: Profesor) : Call<MutableList<Rol>>

    @GET("/listado/roles/{id}")
    fun getRolId(@Path("id") id:String): Call<MutableList<Rol>>

    //--------------------------------------------------

    @DELETE("borrar/{dni}")
    fun borrarUsuario(@Path("dni") id:String) : Call<ResponseBody>

    @DELETE("borrar/aula/{id}")
    fun borrarAula(@Path("id") id:Int) : Call<ResponseBody>

    @DELETE("/borrar/rol/{id}")
    fun borrarRol(@Path("id") id:ArrayList<String>) : Call<ResponseBody>

    //--------------------------------------------------

    @Headers("Content-Type:application/json")
    @PUT("modificar")
    fun modUsuario(@Body info: Profesor) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("modificar/aula")
    fun modAula(@Body info: Aula) : Call<ResponseBody>



}