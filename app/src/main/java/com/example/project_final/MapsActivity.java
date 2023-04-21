package com.example.project_final;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.project_final.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener{

    private GoogleMap map;
    private ActivityMapsBinding binding;
    private BitmapDescriptor coffeeIcon;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        coffeeIcon = BitmapDescriptorFactory.fromResource(R.drawable.adj);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.normal_map) {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            return true;
        } else if (id == R.id.hybrid_map) {
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            return true;
        } else if (id == R.id.satellite_map) {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            return true;
        } else if (id == R.id.terrain_map) {
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng coffeeLocation = new LatLng(37.423, -122.084);
        MarkerOptions coffeeMarker = new MarkerOptions()
                .position(coffeeLocation)
                .title("Coffee Store")
                .icon(coffeeIcon);
        map.addMarker(coffeeMarker);

        LatLng yourLn = new LatLng(43.0207985560236, -81.20654778178458);
        MarkerOptions marker = new MarkerOptions()
                .position(yourLn)
                .title("Tony Location");
        map.addMarker(marker);


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLn, 15f));
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);
    }
    @Override
    public void onMapClick(LatLng latLng) {
        map.addMarker(new MarkerOptions().position(latLng).title("New Marker"));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng latLng = marker.getPosition();
        marker.setSnippet("Latitude: " + latLng.latitude + "\nLongitude: " + latLng.longitude);
        marker.showInfoWindow();
        return true;
    }





}