package com.example.minotas

import Adaptador.MiAdaptador
import Adaptador.MiAdaptadorContactos
import Auxiliar.Conexion
import Auxiliar.Fichero
import Auxiliar.NombreFoto
import Modelo.Contact
import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import android.R.id
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.AdapterView

class Contactos : AppCompatActivity() {

    val REQUEST_READ_CONTACTS = 79
    var mobileArray: ArrayList<Contact> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        var ventanaactual:Contactos=this
        var seleccionado:Int=-1
        var Fichero: Fichero = Fichero(NombreFoto.log, this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactos)

        var lista: ListView = findViewById(R.id.lstContactos)

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            mobileArray = this.obtenerContactos()
        } else {
            requestPermission();
        }
        var miAdaptadorModificado: MiAdaptadorContactos = MiAdaptadorContactos(this,R.layout.item_contactos,mobileArray,seleccionado)
        lista.adapter = miAdaptadorModificado

        lista.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {
                var p = mobileArray.get(pos)
                if(pos==seleccionado){
                    seleccionado=-1
                }else{
                    Fichero.escribirLinea("Se ha seleccionado un movil",NombreFoto.log)
                    seleccionado=pos
                    val intent=Intent()
                    intent.putExtra("numero",p.getNumero())
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                }
            }
        }
    }
    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS),REQUEST_READ_CONTACTS)
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS),REQUEST_READ_CONTACTS)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mobileArray = this.obtenerContactos()
            } else {
                Toast.makeText(
                    this, "No tienes los permisos requeridos...",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
    fun obtenerContactos():ArrayList<Contact> {
        var listaNombre:ArrayList<Contact>? = ArrayList()
        var p: Contact = Contact()
        var cr = this.contentResolver
        var cur: Cursor? = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null,null)
        if (cur != null){
            if (cur.count > 0){
                while(cur!=null && cur.moveToNext()){
                    var id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID).toInt())
                    var nombre = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME).toInt())
                    p.setNombre(nombre)
                    if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER).toInt()) > 0) {
                        val pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null)
                        while (pCur!!.moveToNext()) {
                            val phoneNo = pCur!!.getString(pCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER).toInt())
                            p.setNumero(phoneNo)
                        }
                        pCur!!.close()
                    }
                    listaNombre!!.add(p)
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        return listaNombre!!
    }
    fun volver(view: View){
        finish();
    }
}