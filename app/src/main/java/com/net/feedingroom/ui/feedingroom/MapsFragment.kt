package com.net.feedingroom.ui.feedingroom

import android.Manifest
import android.annotation.SuppressLint
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.OnMapReadyCallback
import com.google.android.libraries.maps.SupportMapFragment
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.GoogleMap
import com.net.feedingroom.R

class MapsFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        permissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        gotoDefaultLocation()
    }

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if(isGranted) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
                gotoCurrentLocation()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    @SuppressLint("MissingPermission")
    private fun gotoCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { currentLocation ->
            showCurrentLocation()
            val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
            val update = CameraUpdateFactory.newLatLngZoom(latLng, 12f)
            googleMap.animateCamera(update)
        }
    }

    @SuppressLint("MissingPermission")
    private fun showCurrentLocation() {
        googleMap.apply {
            isMyLocationEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }
    }

    private fun gotoDefaultLocation() {
        val sydney = LatLng(-34.0, 151.0)
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

}