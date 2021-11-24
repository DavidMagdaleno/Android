package Auxiliar

import Adaptador.MiAdaptadorTarea
import Conexion.AdminSQLIteConexion
import Modelo.Notas
import Modelo.Tarea
import android.content.ContentValues
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
        registro.put("Imagen",e.getImagen())
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
    fun delTarea(contexto: AppCompatActivity, Id: Int, IdTarea: Int):Int{////////////////////////////////////////////////////////////////
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("nTareas", "Id='${Id}' AND  IdTarea='${IdTarea}'", null)
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
    fun ultimoIDTarea(contexto: AppCompatActivity):Int{
        var cantidad:Int=0
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select Max(IdTarea) from nTareas",
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
        registro.put("Id", p.getId())
        registro.put("Asunto", p.getAsunto())
        registro.put("Tipo",p.getTipo())
        registro.put("Fecha",p.getFecha())
        registro.put("Hora",p.getHora())
        registro.put("Texto", p.getTexto())
        val cant = bd.update("notas", registro, "Id='${Id}'", null)
        bd.close()
        return cant
    }
    fun modTarea(contexto: AppCompatActivity, Id:Int, IdTarea:Int,e:Tarea):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("Id", e.getId())
        registro.put("IdTarea", e.getIdTarea())
        registro.put("Tarea",e.getTarea())
        registro.put("Imagen",e.getImagen())
        val cant = bd.update("nTareas", registro, "Id='${Id}' AND  IdTarea='${IdTarea}'", null)
        bd.close()
        return cant
    }

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
        }
        bd.close()
        return e

    }
    fun buscarTarea(contexto: AppCompatActivity, Id:Int, IdTarea:Int):Tarea? {
        var p:Tarea? = null
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select Id, IdTarea, Tarea, Imagen from nTareas where Id='${Id}' AND IdTarea='${IdTarea}'",
            null
        )
        if (fila.moveToNext()) {
            p = Tarea(fila.getInt(0),fila.getInt(1),fila.getString(2),fila.getString(3))
        }
        bd.close()
        return p

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
            "select Id, IDTarea, Tarea, Imagen from nTareas where Id='${Id} '",
            null
        )
        while (fila.moveToNext()) {
            var e:Tarea=Tarea(fila.getInt(0),fila.getInt(1),fila.getString(2),fila.getString(3))
            //var e:String = fila.getString(0)
            tarea.add(e)
        }
        bd.close()
        return tarea
    }

}