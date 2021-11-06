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
        bd.insert("especialidad", null, registro)
        bd.close()
    }


    fun delPersona(contexto: AppCompatActivity, Id: Int):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("personas", "Id='${Id}'", null)
        val canti = bd.delete("especialidad", "IdPersona='${Id}'", null)
        bd.close()
        return cant
    }

    fun modPersona(contexto:AppCompatActivity, Id:Int, p:Persona):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("Nombre", p.getNombre())
        registro.put("Sistema",p.getSistema())
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
        val cant = bd.update("especialidad", registro, "IdPersona='${Id}'", null)
        bd.close()
        return cant
    }

    fun buscarPersona(contexto: AppCompatActivity, Id:Int):Persona? {
        var p:Persona? = null
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select nombre,sistema,horas from personas where Id='${Id} '",
            null
        )
        if (fila.moveToFirst()) {
            p = Persona(Id, fila.getString(0), fila.getString(1), fila.getInt(2))
        }
        bd.close()
        return p
    }

    fun obtenerPersonas(contexto: AppCompatActivity):ArrayList<Persona>{
        var personas:ArrayList<Persona> = ArrayList(1)

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("select Id,nombre,sistema,horas from personas", null)
        while (fila.moveToNext()) {
            var p:Persona = Persona(fila.getInt(0),fila.getString(0),fila.getString(1),fila.getInt(2))
            personas.add(p)
        }
        bd.close()
        return personas
    }

}