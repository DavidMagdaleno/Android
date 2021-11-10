package Auxiliar

import Conexion.AdminSQLIteConexion
import Modelo.Especialidad
//import Modelo.Persona
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import com.example.encuesta.Persona

object Conexion {
    var nombreBD = "EncuestaBD.db3"

    fun cambiarBD(nombreBD:String){
        this.nombreBD = nombreBD
    }

    fun addPersona(contexto: AppCompatActivity, p: Persona){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("Id", p.getId())
        registro.put("Nombre", p.getNombre())
        registro.put("Sistema",p.getSistema())
        registro.put("Imagen",p.getImagen())
        registro.put("Horas",p.getHoras())
        bd.insert("personas", null, registro)
        bd.close()
    }

    fun addPersonaEspecialidad(contexto: AppCompatActivity, e: Especialidad){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("IdPersona", e.getidPersona())
        registro.put("IdEspecialidad", e.getidEspecialidad())
        registro.put("especialidad",e.getespecialidad())
        bd.insert("especiali", null, registro)
        bd.close()
    }


    fun delPersona(contexto: AppCompatActivity, Id: Int):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("personas", "Id='${Id}'", null)
        val canti = bd.delete("especiali", "IdPersona='${Id}'", null)
        bd.close()
        return cant
    }
    fun delEspecialiad(contexto: AppCompatActivity, Id: Int, Ide: Int):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val canti = bd.delete("especiali", "IdPersona='${Id}' AND  IdEspecialidad='${Ide}'", null)
        bd.close()
        return canti
    }

    fun numeroPersona(contexto: AppCompatActivity):Int{
        var cantidad:Int=0
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select count(*) from personas",
            null
        )
        while (fila.moveToNext()) {
            cantidad = fila.getInt(0)
        }
        bd.close()
        return cantidad
    }

    fun ultimoID(contexto: AppCompatActivity):Int{
        var cantidad:Int=0
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select Max(Id) from personas",
            null
        )
        while (fila.moveToNext()) {
            cantidad = fila.getInt(0)
        }
        bd.close()
        return cantidad
    }


    fun modPersona(contexto:AppCompatActivity, Id:Int, p:Persona):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("Nombre", p.getNombre())
        registro.put("Sistema",p.getSistema())
        registro.put("Imagen",p.getImagen())
        registro.put("Horas",p.getHoras())
        val cant = bd.update("personas", registro, "Id='${Id}'", null)
        bd.close()
        return cant
    }
    fun modPersonaEspecialidad(contexto:AppCompatActivity, Id:Int, e:Especialidad):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("IdPersona", e.getidPersona())
        registro.put("IdEspecialidad", e.getidEspecialidad())
        registro.put("especialidad",e.getespecialidad())
        val cant = bd.update("especiali", registro, "IdPersona='${Id}'", null)
        bd.close()
        return cant
    }

    fun buscarPersona(contexto: AppCompatActivity, Id:Int):Persona? {
        var p:Persona? = null
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select nombre,sistema,imagen,horas from personas where Id='${Id} '",
            null
        )
        if (fila.moveToFirst()) {
            p = Persona(Id, fila.getString(0), fila.getString(1), fila.getString(2), fila.getInt(3))
        }
        bd.close()
        return p
    }

    fun obtenerPersonas(contexto: AppCompatActivity):ArrayList<Persona>{
        var personas:ArrayList<Persona> = ArrayList(1)

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("select Id,nombre,sistema,imagen,horas from personas", null)
        while (fila.moveToNext()) {
            var p:Persona = Persona(fila.getInt(0),fila.getString(1),fila.getString(2),fila.getString(3),fila.getInt(4))
            personas.add(p)
        }
        bd.close()
        return personas
    }
    fun obtenerEspecialidad(contexto: AppCompatActivity,Id:Int):ArrayList<String>{
        var espe:ArrayList<String> = ArrayList(1)

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        val fila = bd.rawQuery(
            "select especialidad from especiali where IdPersona='${Id} '",
            null
        )
        while (fila.moveToNext()) {
            var e:String = fila.getString(0)
            espe.add(e)
        }
        bd.close()
        return espe
    }

}