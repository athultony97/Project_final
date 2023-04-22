package com.example.project_final;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private ActivityMapsBinding binding;
    private BitmapDescriptor coffeeIcon;
    MarkerOptions marker;
    Marker newMarker;
    LinearLayout layout;
    View fragment;
    Button disp;
    TextView lat, longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        layout = findViewById(R.id.two);
        fragment = findViewById(R.id.fragment_location_info);
        disp = findViewById(R.id.display_button_m);
        lat = findViewById(R.id.latitude_text_view_m);
        longi = findViewById(R.id.longitude_text_view_m);
        coffeeIcon = BitmapDescriptorFactory.fromResource(R.drawable.location);
        disp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dd();
            }
        });
    }

    private void dd() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            lat.setText(String.valueOf(location.getLatitude()));
            longi.setText(String.valueOf(location.getLongitude()));
        } else {
            Toast.makeText(this, "Unable to retrieve location", Toast.LENGTH_SHORT).show();
        }

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
        LatLng coffeeLocation = new LatLng(43.01353466038684, -81.19944909985036);
        MarkerOptions coffeeMarker = new MarkerOptions()
                .position(coffeeLocation)
                .title("Coffee Store")
                .icon(coffeeIcon);
        map.addMarker(coffeeMarker);

        LatLng yourLn = new LatLng(43.0207985560236, -81.20654778178458);
        marker = new MarkerOptions()
                .position(yourLn)
                .title("Tony Location");
        map.addMarker(marker);


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLn, 15f));
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (newMarker != null) {
            newMarker.remove();
        }
        newMarker = map.addMarker(new MarkerOptions()
                .position(latLng)
                .title("New Location"));
        double lat = newMarker.getPosition().latitude;
        double lng = newMarker.getPosition().longitude;
        layout.setVisibility(View.GONE);
        fragment.setVisibility(View.VISIBLE);
        LocationFragment locationFragment = LocationFragment.newInstance(lat, lng);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_location_info, locationFragment).commit();

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng latLng = marker.getPosition();
        marker.setSnippet("Latitude: " + latLng.latitude + "\nLongitude: " + latLng.longitude);
        marker.showInfoWindow();
        return true;
    }


}