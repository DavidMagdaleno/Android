package com.example.minotas

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SMS : AppCompatActivity() {
    private val permissionRequest = 101
    var numero: EditText=findViewById(R.id.etxtNumero)
    var msg: EditText=findViewById(R.id.etxtMsg)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)

        var texto = intent.getStringExtra("Mensaje")
        //msg=findViewById(R.id.etxtMsg)
        msg.setText(texto)

    }
    fun enviar(view: View){
        val pm = this.packageManager
        //Esta es una comprobación previa para ver si mi dispositivo puede enviar sms o no.
        if (!pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY) && !pm.hasSystemFeature(
                PackageManager.FEATURE_TELEPHONY_CDMA)) {
            Toast.makeText(this,"Lo sentimos, tu dispositivo probablemente no pueda enviar SMS...",
                Toast.LENGTH_SHORT).show()
        }
        else {
            val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                sms()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), permissionRequest)
            }
        }
    }
    fun sms(){
        //numero=findViewById(R.id.etxtNumero)
        //msg=findViewById(R.id.etxtMsg)
        val myNumber: String = numero.text.toString().trim()
        val myMsg: String = msg.text.toString()
        if (myNumber == "" || myMsg == "") {
            Toast.makeText(this, "El campo telefono y el campo texto no pueden estar vacios", Toast.LENGTH_SHORT).show()
        } else {
            if(myMsg.length>160){
                Toast.makeText(this, "Es un Mensaje no un libro", Toast.LENGTH_SHORT).show()
            }else{
                if (TextUtils.isDigitsOnly(myNumber)) {
                    val smsManager: SmsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(myNumber, null, myMsg, null, null)
                    Toast.makeText(this, "Mensaje enviado...", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "El número no es correcto...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    /**
     * Esta función se lanzará automáticamente cuando se acepten/denieguen, la primera vez, los permisos.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionRequest) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sms()
            } else {
                Toast.makeText(this, "No tienes los permisos requeridos...", Toast.LENGTH_SHORT).show();
            }
        }
    }
    fun listarContactos(view : View){
        var intentListado = Intent(this, Contactos::class.java)
        startActivity(intentListado)
    }
    fun cancel(view: View){
        finish()
    }
}