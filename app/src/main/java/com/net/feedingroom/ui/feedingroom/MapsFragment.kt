package com.net.feedingroom.ui.feedingroom

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.OnMapReadyCallback
import com.google.android.libraries.maps.SupportMapFragment
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.Marker
import com.google.android.libraries.maps.model.MarkerOptions
import com.net.feedingroom.R
import com.net.feedingroom.databinding.FragmentMapsBinding
import com.net.feedingroom.listener.LocationListener
import com.net.feedingroom.ui.activity.MainActivityViewModel
import com.net.feedingroom.ui.view.DividerItemDecoration
import com.net.feedingroom.ui.view.FeedingRoomPhotoAdapter


class MapsFragment : Fragment() {

    private var _vb: FragmentMapsBinding? = null
    private val vb get() = _vb!!

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private var locationListener: LocationListener? = null
    private val vmMainActivity by activityViewModels<MainActivityViewModel>()
    private var marker: Marker? = null
    private val adapter by lazy { FeedingRoomPhotoAdapter() }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        permissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        gotoDefaultLocation()
    }

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if(isGranted) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(
                    requireActivity()
                )
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
        _vb = FragmentMapsBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _vb = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        setRvPhotos()
        bindLiveData()
    }

    private fun setRvPhotos() {
        vb.rvPhotos.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        vb.rvPhotos.addItemDecoration(DividerItemDecoration(5, 10, 5, 0))
        vb.rvPhotos.adapter = adapter
    }

    private fun bindLiveData() {
        vmMainActivity.selectedFeedingRoomLiveData.observe(viewLifecycleOwner) {
            if(it.latitude != null && it.longitude != null) {
                val latLng = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                gotoPosition(latLng)
                addMarker(latLng, it.name)
                adapter.submitList(it.photos)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun gotoCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            it?.let { currentLocation ->
                locationListener?.getCurrentLocation(
                    currentLocation.latitude,
                    currentLocation.longitude
                )
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

    private fun addMarker(latLng: LatLng, title: String?) {
        marker?.remove()
        marker = googleMap.addMarker(MarkerOptions().position(latLng)
            .title(title))
    }

    private fun gotoDefaultLocation() {
        val sydney = LatLng(-34.0, 151.0)
        gotoPosition(sydney, 12f)
    }

}