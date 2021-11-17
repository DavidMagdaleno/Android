package Auxiliar

import Conexion.AdminSQLIteConexion
import Modelo.Notas
import Modelo.Tarea
import android.content.ContentValues
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

object Conexion {
    var nombreBD = "MiNotasBD.db3"

    fun cambiarBD(nombreBD:String){
        this.nombreBD = nombreBD
    }

    fun addNotaSimple(contexto: AppCompatActivity, p: Notas){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("Id", p.getId())
        registro.put("Asunto", p.getAsunto())
        registro.put("Tipo",p.getTipo())
        registro.put("Fecha",p.getFecha())
        registro.put("Hora",p.getHora())
        //registro.put("Imagen",p.getImagen())
        registro.put("Texto",p.getTexto())
        bd.insert("notas", null, registro)
        bd.close()
    }

    fun addNotaTarea(contexto: AppCompatActivity, e: Tarea){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("Id", e.getId())
        registro.put("IdTarea", e.getIdTarea())
        registro.put("Tarea",e.getTarea())
        bd.insert("nTareas", null, registro)
        bd.close()
    }


    fun delNotaTarea(contexto: AppCompatActivity, Id: Int):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("notas", "Id='${Id}'", null)
        val canti = bd.delete("nTareas", "Id='${Id}'", null)
        bd.close()
        return cant
    }
    fun delNota(contexto: AppCompatActivity, Id: Int):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("notas", "Id='${Id}'", null)
        bd.close()
        return cant
    }

    /*fun numeroPersona(contexto: AppCompatActivity):Int{
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
    }*/

    fun ultimoID(contexto: AppCompatActivity):Int{
        var cantidad:Int=0
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select Max(Id) from notas",
            null
        )
        while (fila.moveToNext()) {
            cantidad = fila.getInt(0)
        }
        bd.close()
        return cantidad
    }


    fun modNota(contexto: AppCompatActivity, Id:Int, p:Notas):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("Asunto", p.getAsunto())
        registro.put("Tipo",p.getTipo())
        registro.put("Fecha",p.getFecha())
        registro.put("Hora",p.getHora())
        registro.put("Texto", p.getTexto())
        val cant = bd.update("notas", registro, "Id='${Id}'", null)
        bd.close()
        return cant
    }
    /*fun modPersonaEspecialidad(contexto: AppCompatActivity, Id:Int, e:Especialidad):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("IdPersona", e.getidPersona())
        registro.put("IdEspecialidad", e.getidEspecialidad())
        registro.put("especialidad",e.getespecialidad())
        val cant = bd.update("especiali", registro, "IdPersona='${Id}'", null)
        bd.close()
        return cant
    }*/

    fun buscarNotas(contexto: AppCompatActivity, asunto:String):Int? {
        var p:Notas? = null
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select Id from notas where Asunto='${asunto} '",
            null
        )
        var e: Int=0
        if (fila.moveToFirst()) {
            e = fila.getInt(0)
            //p = Notas(fila.getInt(0))}
        }
        bd.close()
        return e

    }

    fun obtenerNotas(contexto: AppCompatActivity):ArrayList<Notas>{
        var nota:ArrayList<Notas> = ArrayList(1)

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("select Id,Asunto,Tipo,Fecha,Hora,Texto from notas", null)
        while (fila.moveToNext()) {
            var p:Notas = Notas(fila.getInt(0),fila.getString(1),fila.getString(2),fila.getString(3),fila.getString(4),fila.getString(5))
            nota.add(p)
        }
        bd.close()
        return nota
    }
    fun obtenerTarea(contexto: AppCompatActivity, Id:Int):ArrayList<Tarea>{
        var tarea:ArrayList<Tarea> = ArrayList(1)

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        val fila = bd.rawQuery(
            "select Id, IDTarea, Tarea from nTareas where Id='${Id} '",
            null
        )
        while (fila.moveToNext()) {
            var e:Tarea=Tarea(fila.getInt(0),fila.getInt(1),fila.getString(2))
            //var e:String = fila.getString(0)
            tarea.add(e)
        }
        bd.close()
        return tarea
    }

}