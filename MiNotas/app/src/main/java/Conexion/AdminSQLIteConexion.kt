package Conexion

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLIteConexion(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table notas(Id int primary key autoincrement, Asunto text, Tipo text, Fecha date, Hora time, Texto text)")
        db.execSQL("create table nTareas(IdNotas int, IdTarea int, Tarea text(160), primary key(IdNotas,IdTarea))")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}