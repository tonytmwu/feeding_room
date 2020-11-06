package com.net.feedingroom.ui.feedingroom

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.OnMapReadyCallback
import com.google.android.libraries.maps.SupportMapFragment
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.GoogleMap
import com.net.feedingroom.R
import com.net.feedingroom.listener.LocationListener
import com.net.feedingroom.ui.activity.MainActivityViewModel

class MapsFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private var locationListener: LocationListener? = null
    private val vmMainActivity by activityViewModels<MainActivityViewModel>()

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? LocationListener)?.let {
            locationListener = it
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
        bindLiveData()
    }

    private fun bindLiveData() {
        vmMainActivity.selectedFeedingRoomLiveData.observe(viewLifecycleOwner) {
            if(it.latitude != null && it.longitude != null) {
                gotoPosition(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun gotoCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            it?.let { currentLocation ->
                locationListener?.getCurrentLocation(currentLocation.latitude, currentLocation.longitude)
                showCurrentLocation()
                gotoPosition(LatLng(currentLocation.latitude, currentLocation.longitude), 15f)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun showCurrentLocation() {
        googleMap.apply {
            isMyLocationEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }
    }

    private fun gotoPosition(latLng: LatLng, zoomLevel: Float = 17f) {
        val update = CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel)
        googleMap.animateCamera(update)
    }

    private fun gotoDefaultLocation() {
        val sydney = LatLng(-34.0, 151.0)
        gotoPosition(sydney, 12f)
    }

}