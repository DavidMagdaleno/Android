package com.example.eventosmaps

import Adapter.MiAdapterConsultas
import Model.User
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


/**
 * Implementamos:
 * GoogleMap.OnMyLocationButtonClickListener --> Dispara el evento al pulsar en el punto negro arriba a la derecha que centra el mapa en la localización acual.
 * GoogleMap.OnMyLocationClickListener --> Dispara el evento al pulsar en la localización actual, punto azul.
 * GoogleMap.OnPoiClickListener --> Dispara el evento al pulsar en puntos de interés (POI).
 * GoogleMap.OnMapLongClickListener --> Lanza el evento al pulsar en cualquier parte del mapa.
 * GoogleMap.OnMarkerClickListener --> Dispara el evento al hacer click en un marcador.
 */
class Maps : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnPoiClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener,GoogleMap.OnMapClickListener {
    private val LOCATION_REQUEST_CODE: Int = 0
    private lateinit var map: GoogleMap
    private val db = FirebaseFirestore.getInstance()

    /**
     * Esta función nos devolverá un objeto GoogleMap que será muy útil, es por ello que debemos guardarlo en una variable. ¿Cómo lo
     * haremos? Muy sencillo, crearemos una variable en la parte superior de la clase y le asignaremos el objeto GoogleMap cuando lo
     * recibamos.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        //Se pueden seleccionar varios tiops de mapas:
        //  None --> no muestra nada, solo los marcadores. (MAP_TYPE_NONE)
        //  Normal --> El mapa por defecto. (MAP_TYPE_NORMAL)
        //  Satélite --> Mapa por satélite.  (MAP_TYPE_SATELLITE)
        //  Híbrido --> Mapa híbrido entre Normal y Satélite. (MAP_TYPE_HYBRID) Muestra satélite y mapas de carretera, ríos, pueblos, etc... asociados.
        //  Terreno --> Mapa de terrenos con datos topográficos. (MAP_TYPE_TERRAIN)
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        map.setOnPoiClickListener(this)
        map.setOnMapLongClickListener (this)
        map.setOnMarkerClickListener(this)
        map.setOnMapClickListener (this)
        enableMyLocation() //--> Hanilita, pidiendo permisos, la localización actual.
        createMarker() //--> Nos coloca varios marcadores en el mapa y nos coloca en el CIFP Virgen de Gracia con un Zoom.
        //irubicacioActual() //--> Nos coloca en la ubicación actual directamente. Comenta createMarker par ver esto. al arrancar
        //pintarRutaAlCentro()
        //pintarCirculoCentro()
    }
    /**
     * Nos coloca en la ubicación actual.
     */
    @SuppressLint("MissingPermission")
    private fun irubicacioActual() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val miUbicacion = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val latLng = LatLng(miUbicacion!!.latitude, miUbicacion.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f)) //--> Mueve la cámara a esa posición, sin efecto. El valor real indica el nivel de Zoom, de menos a más.
    }

    /**
     * Método en el que crearemos algunos marcadores de ejemplo.
     */
    private fun createMarker() {
        val favoritePlace = LatLng(38.69332,-4.10860)
        /*
        Los markers se crean de una forma muy sencilla, basta con crear una instancia de un objeto LatLng() que recibirá dos
        parámetros, la latitud y la longitud. Yo en este ejemplo he puesto las coordenadas de mi playa favorita.
        */
        //map.addMarker(MarkerOptions().position(favoritePlace).title("Mi CIFP favorito!"))
        //Si queremos cambiar el color del icono, en este caso azul cyan, con un subtexto.
        map.addMarker(
            MarkerOptions().position(favoritePlace).title("Mi CIFP favorito!").icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).snippet("CIFP Virgen de Gracia"))


        val paris = LatLng(48.86028, 2.29496)
        //map.addMarker(MarkerOptions().position(paris).title("Paris").icon(sizeIcon(R.drawable.paris)))
        /*
        Otros atributos útiles:
            alpha(0.4f) --> Con un valor real indica la semitransparencia del icono.
            draggable(true)  --> Permite arrastralo.
            snippet("Otro texto") --> Añade un subtexto al título.
         */
        map.addMarker(MarkerOptions().position(paris).title("Paris").icon(sizeIcon(R.drawable.paris)).alpha(0.8f).draggable(true))
        /*
        La función animateCamera() recibirá tres parámetros:

            Un CameraUpdateFactory que a su vez llevará otros dos parámetros, el primero las coordenadas donde queremos hacer zoom
                y el segundo valor (es un float) será la cantidad de zoom que queremos hacer en dichas coordenadas.
            La duración de la animación en milisegundos, por ejemplo 4000 milisegundos son 4 segundos.
            Un listener que no vamos a utilizar, simplemente añadimos null.
         */
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(favoritePlace, 18f),
            4000,
            null
        )
    }

    /**
     * Con este método vamos a ajustar el tamaño de todos los iconos que usemos en los marcadores.
     */
    fun sizeIcon(idImage:Int): BitmapDescriptor {
        val altura = 60
        val anchura = 60

        var draw = ContextCompat.getDrawable(this,idImage) as BitmapDrawable
        val bitmap = draw.bitmap  //Aquí tenemos la imagen.

        //Le cambiamos el tamaño:
        val smallBitmap = Bitmap.createScaledBitmap(bitmap, anchura, altura, false)
        return BitmapDescriptorFactory.fromBitmap(smallBitmap)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        createMapFragment()
    }

    fun createMapFragment() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
        /*
        getMapAsync(this) necesita que nuestra activity implemente la función onMapReady() y para ello tenemos que añadir la interfaz
        OnMapReadyCallback.
         */
    }

    /**
     * función que usaremos a lo largo de nuestra app para comprobar si el permiso ha sido aceptado o no.
     */
    fun isPermissionsGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED


    /**
     * función que primero compruebe si el mapa ha sido inicializado, si no es así saldrá de la función gracias
     * a la palabra return, si por el contrario map ya ha sido inicializada, es decir que el mapa ya ha cargado,
     * pues comprobaremos los permisos.
     */
    @SuppressLint("MissingPermission")
    fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (isPermissionsGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    /**
     * Método que solicita los permisos.
     */
    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE)
        }
    }

    @SuppressLint("MissingSuperCall", "MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    /**
     * Se dispara cuando pulsamos la diana que nos centra en el mapa (punto negro, arriba a la derecha).
     */
    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "Boton pulsado", Toast.LENGTH_SHORT).show()
        return false
    }

    /**
     * Se dispara cuando pulsamos en nuestra localización exacta donde estámos ahora (punto azul).
     */
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this, "Estás en ${p0.latitude}, ${p0.longitude}", Toast.LENGTH_SHORT).show()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        var evento = intent.getStringExtra("marcarPosicion")
        var email = intent.getStringExtra("user")

        db.collection("eventos").document(evento!!).get().addOnSuccessListener {
            var miArray:ArrayList<User> = ArrayList()
            var ev=it.get("Ubicacion")
            //sacar todos los asistentes
            var asistentes = it.get("asistentes") as ArrayList<Any>
            for (i in 0..asistentes.size-1){
                var m=asistentes[i] as Map<String, String>
                var asist=User(m["nombre"].toString(),m["apellidos"].toString(),m["asistencia"].toString(),m["ubicacion"].toString(),m["hora"].toString())
                miArray.add(asist)
            }
            db.collection("Usuarios").document(email!!).get().addOnSuccessListener {
                //Si encuentra el documento será satisfactorio este listener y entraremos en él
                var u:User=User(it.get("Nombre") as String,it.get("Apellidos") as String,"SI",p0.latitude.toString()+","+p0.longitude.toString(),currentDate)
                for (i in 0..miArray.size-1){
                    if (miArray[i].Nombre.equals(u.Nombre)){
                        miArray[i]=u
                    }else{
                        miArray.add(u)
                    }
                }
                var user = hashMapOf(
                    "Ubicacion" to ev,
                    "asistentes" to miArray
                )
                db.collection("eventos")//añade o sebreescribe
                    .document(evento!!) //Será la clave del documento.
                    .set(user).addOnSuccessListener {
                        //Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        //Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                    }
                //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Algo ha ido mal al recuperar",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this, "Algo ha ido mal al recuperar",Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Con el parámetro podremos obtener información del punto de interés.
     */

    override fun onPoiClick(p0: PointOfInterest) {
        Toast.makeText(this@Maps, "Pulsado.", Toast.LENGTH_LONG).show()
        val dialogBuilder = AlertDialog.Builder(this@Maps)
        dialogBuilder.run {
            setTitle("Información del lugar.")
            setMessage("Id: " + p0!!.placeId + "\nNombre: " + p0!!.name + "\nLatitud: " + p0!!.latLng.latitude.toString() + " \nLongitud: " + p0.latLng.longitude.toString())
            setPositiveButton("Aceptar"){ dialog: DialogInterface, i:Int ->
                Toast.makeText(this@Maps, "Salir", Toast.LENGTH_LONG).show()
            }
        }
        dialogBuilder.create().show()
    }

    /**
     * Dibuja una línea recta desde nuestra ubicación actual al CIFP Virgen de Gracia.
     */
    @SuppressLint("MissingPermission")
    fun pintarRutaAlCentro(){
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val miUbicacion = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val latLng = LatLng(miUbicacion!!.latitude, miUbicacion.longitude)
        val markerCIFP = LatLng(38.69332,-4.10860)

        map.addPolyline(PolylineOptions().run{
            add(latLng, markerCIFP)
            color(Color.BLUE)
            width(9f)
        })

        val loc1 = Location("")
        loc1.latitude = latLng.latitude
        loc1.longitude = latLng.longitude
        val loc2 = Location("")
        loc2.latitude = markerCIFP.latitude
        loc2.longitude = markerCIFP.longitude
        val distanceInMeters = loc1.distanceTo(loc2)
        Log.e("Fernando", distanceInMeters.toString())
    }

    /**
     * Dibuja una línea recta desde nuestra ubicación actual al CIFP Virgen de Gracia.
     */
    fun pintarCirculoCentro(){
        val markerCIFP = LatLng(38.69332,-4.10860)

        map.addCircle(CircleOptions().run{
            center(markerCIFP)
            radius(9.0)
            strokeColor(Color.BLUE)
            fillColor(Color.GREEN)
        })
    }
    /**
     * Con el parámetro crearemos un marcador nuevo. Este evento se lanzará al hacer un long click en alguna parte del mapa.
     */
    //Este------------------------------------------------------------------------------
    override fun onMapLongClick(p0: LatLng) {
        map.addMarker(MarkerOptions().position(p0!!).title("Nuevo marcador"))
        //Log.e("que es", p0.toString())
        val intent=Intent()
        intent.putExtra("ubica",p0.toString())
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
    /**
     * Este evento se lanza cuando hacemos click en un marcador.
     */
    //Este que te de la opcion de poner o quitar el marcador-------------------------------------------------
    override fun onMarkerClick(p0: Marker): Boolean {
        val dialogo: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        val Myview=layoutInflater.inflate(R.layout.item_dialogo, null)
        var btCrear = Myview.findViewById<Button>(R.id.btnCrearMarcador)
        var btquit = Myview.findViewById<Button>(R.id.btnQuitar)
        dialogo.setView(Myview)
        btCrear.setOnClickListener {
            Toast.makeText(this, "Estás en ${p0!!.title}, ${p0!!.position}", Toast.LENGTH_SHORT).show()
        }
        btquit.setOnClickListener {
            //map.clear()
            p0.remove()
        }
        dialogo.show()
        return true;
    }

    override fun onMapClick(p0: LatLng) {
        //Toast.makeText(this, "Estás Aqui", Toast.LENGTH_SHORT).show()-------------------------------------------------------------------------------------
        map.addMarker(MarkerOptions().position(p0!!).title("Nuevo marcador"))
        val intent=Intent()
        intent.putExtra("ubica",p0.toString())
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    //Investigar los polígonos: triángulos...
}