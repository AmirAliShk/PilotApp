package com.example.myapplication.activity

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.app.MyApplication
import com.example.myapplication.database.MyRoom
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragment.AddPointFragment
import com.example.myapplication.fragment.PointsListFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        val dataBase = MyRoom.getDatabase(MyApplication.context)
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MyApplication.currentActivity = this

        val mapFragment =
            supportFragmentManager.findFragmentById(com.example.myapplication.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.imgGPS.setOnClickListener {
            if (isLocationPermissionGranted()) checkGPS()

            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                val loc = LatLng(location!!.latitude, location.longitude)
                val cameraPosition = CameraPosition.Builder().target(
                    loc
                ).zoom(16f).build()

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
        }

        binding.imgRecordGPS.setOnClickListener {
            if (isLocationPermissionGranted()) fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                val manager: FragmentManager = supportFragmentManager
                val transaction: FragmentTransaction = manager.beginTransaction()
                transaction.add(
                    R.id.content, AddPointFragment(
                        location?.latitude.toString(),
                        location?.longitude.toString(),
                        location?.bearing.toString()
                    )
                )
                transaction.addToBackStack("")
                transaction.commit()
            }
        }

        binding.imgGpsList.setOnClickListener {
            val manager: FragmentManager = supportFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            transaction.add(
                R.id.content, PointsListFragment()
            )
            transaction.addToBackStack("null")
            transaction.commit()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isMyLocationButtonEnabled = false

        val tehran = LatLng(35.7219, 51.3347)
        val cameraPosition = CameraPosition.Builder().target(tehran).zoom(11f).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mMap.isMyLocationEnabled = true
    }

    private fun checkGPS() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this@MainActivity, "GPS is enable", Toast.LENGTH_LONG).show()
        } else {
            val locationDialog = MaterialAlertDialogBuilder(this@MainActivity)
            locationDialog.setTitle("Attention")
            locationDialog.setMessage("Location settings must be enabled from the settings to use the application")
            locationDialog.setCancelable(false)
            locationDialog.setPositiveButton(
                "Open settings"
            ) { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
            locationDialog.create().show()
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ), 109
            )
            checkGPS()
            false
        } else {
            true
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            Toast.makeText(
                MyApplication.context, "Goodbye :D", Toast.LENGTH_LONG
            ).show()
            super.onBackPressed()
        }
    }
}