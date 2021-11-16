package Auxiliar

import Conexion.AdminSQLIteConexion
import Modelo.Notas
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
        //registro.put("Id", p.getId())
        registro.put("Asunto", p.getAsunto())
        registro.put("Tipo",p.getTipo())
        registro.put("Fecha",p.getFecha())
        registro.put("Hora",p.getHora())
        //registro.put("Imagen",p.getImagen())
        registro.put("Texto",p.getTexto())
        bd.insert("notas", null, registro)
        bd.close()
    }

    fun addNotaTarea(contexto: AppCompatActivity, e: Especialidad){
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


    fun modNota(contexto: AppCompatActivity, Id:Int, p:Notas):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("Texto", p.getTexto())
        //registro.put("Sistema",p.getSistema())
        //registro.put("Imagen",p.getImagen())
        //registro.put("Horas",p.getHoras())
        val cant = bd.update("notas", registro, "Id='${Id}'", null)
        bd.close()
        return cant
    }
    fun modPersonaEspecialidad(contexto: AppCompatActivity, Id:Int, e:Especialidad):Int {
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

    fun buscarNotas(contexto: AppCompatActivity, asunto:String):Notas? {
        var p:Notas? = null
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select select Id,Asunto,Tipo,Fecha,Hora from notas where Asunto='${asunto} '",
            null
        )
        if (fila.moveToFirst()) {
            p = Notas(fila.getInt(0),fila.getString(1),fila.getString(2),fila.getString(3),fila.getString(4),fila.getString(5))
        }
        bd.close()
        return p
    }

    fun obtenerNotas(contexto: AppCompatActivity):ArrayList<Notas>{
        var nota:ArrayList<Notas> = ArrayList(1)

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("select Id,Asunto,Tipo,Fecha,Hora from notas", null)
        while (fila.moveToNext()) {
            var p:Notas = Notas(fila.getInt(0),fila.getString(1),fila.getString(2),fila.getString(3),fila.getString(4),fila.getString(5))
            nota.add(p)
        }
        bd.close()
        return nota
    }
    fun obtenerEspecialidad(contexto: AppCompatActivity, Id:Int):ArrayList<String>{
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