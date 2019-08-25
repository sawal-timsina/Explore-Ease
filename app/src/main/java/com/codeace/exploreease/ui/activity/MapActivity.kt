package com.codeace.exploreease.ui.activity


import android.Manifest
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.View.TRANSLATION_X
import android.view.View.TRANSLATION_Y
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.codeace.exploreease.R
import com.codeace.exploreease.helpers.placeList
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.compat.GeoDataClient
import com.google.android.libraries.places.compat.Places
import kotlinx.android.synthetic.main.activity_maps.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var mCameraPosition: CameraPosition? = null
    private var isOff: Boolean = false
    private var mGeoDataClient: GeoDataClient? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private val mDefaultLocation = LatLng(-33.8523341, 151.2106085)
    private var mLocationPermissionGranted: Boolean = false
    private var mLastKnownLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }

        setContentView(R.layout.activity_maps)
        mGeoDataClient = Places.getGeoDataClient(this)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)

        myLocationButton.setOnClickListener {
            getDeviceLocation()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap!!.cameraPosition)
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation)
            var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
            if (mapViewBundle == null) {
                mapViewBundle = Bundle()
                outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
            }
            mapView.onSaveInstanceState(mapViewBundle)
            super.onSaveInstanceState(outState)
        }
    }

    private fun pxToDp(px: Float): Float {
        return px * (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    }

    private fun aniButton(start: Float, end: Float) {
        val x = PropertyValuesHolder.ofFloat(TRANSLATION_X, start, end)
        val y = PropertyValuesHolder.ofFloat(TRANSLATION_Y, start, end)
        val objAni = ObjectAnimator.ofPropertyValuesHolder(myLocationButton, x, y)
        objAni.duration = 300
        objAni.start()
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map

        placeList.forEach { placeLocation ->
            mMap!!.addMarker(
                MarkerOptions().position(
                    LatLng(
                        placeLocation.lat,
                        placeLocation.long
                    )
                ).title(placeLocation.LocationName)
            )
        }

        mMap!!.setOnMapLongClickListener {
            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(it, DEFAULT_ZOOM.toFloat()))
        }
        mMap!!.setOnCameraMoveStartedListener {
            if (!isOff) {
                if (!isOff) {
                    aniButton(pxToDp(0f), pxToDp(100f))
                }
                isOff = true
            }
        }

        mMap!!.setOnMapClickListener {
            isOff = if (isOff) {
                aniButton(pxToDp(100f), pxToDp(0f))
                !isOff
            } else {
                aniButton(pxToDp(0f), pxToDp(100f))
                !isOff
            }
        }


        mMap!!.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {

            override fun getInfoWindow(arg0: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                val infoWindow = layoutInflater.inflate(
                    R.layout.custom_info_contents,
                    findViewById<MapView>(R.id.mapView), false
                )

                val title = (infoWindow.findViewById(R.id.title) as TextView)
                title.text = marker.title

                val snippet = (infoWindow.findViewById(R.id.snippet) as TextView)
                snippet.text = marker.snippet

                return infoWindow
            }
        })

        getLocationPermission()

        updateLocationUI()

        getDeviceLocation()
    }

    private fun getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                val locationResult = mFusedLocationProviderClient!!.lastLocation
                locationResult.addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        mLastKnownLocation = task.result
                        mMap!!.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    mLastKnownLocation!!.latitude,
                                    mLastKnownLocation!!.longitude
                                ), DEFAULT_ZOOM.toFloat()
                            )
                        )

                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        mMap!!.animateCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                        mMap!!.uiSettings.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message!!)
        }

    }

    private fun getLocationPermission() {
        /*
                * Request location permission, so that we can get the location of the
                * device. The result of the permission request is handled by a callback,
                * onRequestPermissionsResult.
                */
        if ((ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        ) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    mLocationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    private fun updateLocationUI() {
        if (mMap == null) {
            return
        }
        try {
            if (mLocationPermissionGranted) {
                mMap!!.isMyLocationEnabled = true
                mMap!!.uiSettings.isMyLocationButtonEnabled = false
                mMap!!.uiSettings.isMapToolbarEnabled = false
            } else {
                mMap!!.isMyLocationEnabled = false
                mMap!!.uiSettings.isMyLocationButtonEnabled = false
                mMap!!.uiSettings.isMapToolbarEnabled = false
                mLastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message!!)
        }

    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {

        private val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
        private val TAG = MapActivity::class.java.simpleName
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        // Keys for storing activity state.
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"

    }
}