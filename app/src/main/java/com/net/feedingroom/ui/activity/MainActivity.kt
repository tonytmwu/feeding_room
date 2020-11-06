package com.net.feedingroom.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.libraries.maps.model.LatLng
import com.net.feedingroom.R
import com.net.feedingroom.listener.LocationListener
import com.net.feedingroom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LocationListener {

    private val vm: MainActivityViewModel by viewModels()
    private var _vb: ActivityMainBinding? = null
    private val vb get() = _vb!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)
        val navView: BottomNavigationView = vb.navView
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _vb = null
    }

    override fun getCurrentLocation(lat: Double, lng: Double) {
        broadcastCurrentLocation(lat, lng)
    }

    private fun broadcastCurrentLocation(lat: Double, lng: Double) {
        vm.updateCurrentLocation(LatLng(lat, lng))
    }
}