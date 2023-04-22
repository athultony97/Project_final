package com.example.project_final;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.Toast;

public class LocationFragment extends Fragment {

    private TextView latT;
    private TextView longT;
    private Button displayButton;
    private static final String ARG_LATITUDE = "latitude";
    private static final String ARG_LONGITUDE = "longitude";

    private double mLatitude;
    private double mLongitude;

    public static LocationFragment newInstance(double latitude, double longitude) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_LATITUDE, latitude);
        args.putDouble(ARG_LONGITUDE, longitude);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLatitude = getArguments().getDouble(ARG_LATITUDE);
            mLongitude = getArguments().getDouble(ARG_LONGITUDE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);

        latT = rootView.findViewById(R.id.latitude_text_view);
        longT = rootView.findViewById(R.id.longitude_text_view);
        displayButton = rootView.findViewById(R.id.display_button);
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dd();

            }
        });

        return rootView;
    }

    private void dd() {
        latT.setText("");
        longT.setText("");
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            latT.setText(String.valueOf(mLatitude));
            longT.setText(String.valueOf(mLongitude));
        } else {
            Toast.makeText(getActivity(), "Unable to retrieve location", Toast.LENGTH_SHORT).show();
        }


    }
}



