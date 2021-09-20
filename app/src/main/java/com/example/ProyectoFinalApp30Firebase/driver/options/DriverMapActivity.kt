package com.example.ProyectoFinalApp30Firebase.driver.options

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.ProyectoFinalApp30Firebase.GeoCodingLocation
import com.example.ProyectoFinalApp30Firebase.MainActivity
import com.example.ProyectoFinalApp30Firebase.driver.DriverActivity
import com.example.ProyectoFinalApp30Firebase.latitud
import com.example.ProyectoFinalApp30Firebase.longitud
import com.example.ProyectoFinalApp30Firebase.user.PassengerActivity
import com.example.ProyectoFinalApp30Firebase.user.options.PassengerMapActivity
import com.example.buttonnavigation.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import java.text.DecimalFormat
import java.util.jar.Manifest
import kotlin.math.roundToInt


class DriverMapActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var userMap: GoogleMap
    val locationAddress = GeoCodingLocation()
    private var mapReady = false
    private var apiKey = "AIzaSyBuI2ax9ZEL08s6pl-bwmNj50z2xnkvTfs"
    private lateinit var myLocationLatitude: String
    private lateinit var myLocationLongitude: String
    private lateinit var destinyLocationLatitude: String
    private lateinit var destinyLocationLongitude: String


    var dist = 0.0
    var cash = 0.0

    companion object{
        const val REQUEST_CODE_LOCATION = 0
        private class GeoCoderHandler(private val driverapActivity: DriverMapActivity) : Handler() {
            override fun handleMessage(message: Message) {
                val locationAddress: String?
                locationAddress = when (message.what) {
                    1 -> {
                        val bundle = message.data
                        bundle.getString("address")
                    }
                    else -> null
                }


            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.driver_map_fragment)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

        val map = supportFragmentManager.findFragmentById(R.id.userMap1) as SupportMapFragment
        map.getMapAsync {
                googleMap -> userMap = googleMap
            mapReady = true
            enableLocation()
        }


        findViewById<Button>(R.id.driverButton).setOnClickListener(){
            val i = Intent(this, DriverActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }




    }

    private fun confirmTravel(cash: Double) {

        val alertDialog: AlertDialog? = let{
            val builder = AlertDialog.Builder(it)
            builder.apply{
                setTitle("Confirmar Viaje")
                setMessage("El destino actual tiene un valor de: ${(3600 + cash).roundToInt()} pesos, ¿desea realizarlo?")
                setPositiveButton("Si"){ dialog, id ->

                    //TODO

                }
                setNegativeButton("No"){ dialog, id ->
                    findViewById<Button>(R.id.search_button).setText("Buscar")
                    findViewById<EditText>(R.id.search_text).setText("")
                    findViewById<EditText>(R.id.search_text2).setText("")

                }
            }
            builder.create()
        }
        alertDialog?.show()

    }


    private fun setLocation() {
        val coordinates = LatLng(myLocationLatitude.toDouble(),myLocationLongitude.toDouble())
        val marker = MarkerOptions().position(coordinates)


        userMap.addMarker(marker.title("1"))


        userMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates,18f),4000,null
        )
    }


    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun enableLocation(){
        if(!::userMap.isInitialized) return
        if(isLocationPermissionGranted()){
            userMap.isMyLocationEnabled = true

        }
        else{
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this,"Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
        }
    }
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                userMap.isMyLocationEnabled = true
            }else{
                Toast.makeText(this,"Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else ->{ }
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        userMap = googleMap

    }

    private fun setDestiny() {
        val coordinates = LatLng(destinyLocationLatitude.toDouble(),destinyLocationLongitude.toDouble())
        val marker = MarkerOptions().position(coordinates)
        userMap.addMarker(marker.title("2"))


        userMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates,18f),4000,null
        )
    }











}