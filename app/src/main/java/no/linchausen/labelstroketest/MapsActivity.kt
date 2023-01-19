package no.linchausen.labelstroketest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import de.p72b.maps.animation.AnimatedPolyline
import no.linchausen.labelstroketest.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val TAG = "MapsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(applicationContext, MapsInitializer.Renderer.LATEST) {
            when (it) {
                MapsInitializer.Renderer.LATEST -> Log.d(
                    TAG,
                    "The latest version of the renderer is used."
                )
                MapsInitializer.Renderer.LEGACY -> Log.d(
                    TAG,
                    "The legacy version of the renderer is used."
                )
            }
        }

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(59.909480, 10.638224)
        val points = mutableListOf<LatLng>()
        for (i in 0..1) {
            points.add(LatLng(sydney.latitude + (0.01 + i), sydney.longitude + (0.01 + i)))
        }
        Log.d(
            TAG,
            "$points"
        )

        mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(this, R.raw.maps_style_night)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 8f))

        val polyline = AnimatedPolyline(
            mMap,
            points,
            PolylineOptions()
                .clickable(false)
                .color(ContextCompat.getColor(this, R.color.white))
                .width(10f),
            duration = 10000
        )
        polyline.start()
    }
}