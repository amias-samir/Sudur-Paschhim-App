package com.naxa.nepal.sudurpaschimanchal.activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.naxa.nepal.sudurpaschimanchal.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusinessPlacesMapActivity extends Activity implements OnMapReadyCallback {

    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.business_list_spinner)
    Spinner businessListSpinner;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_places_map);
        ButterKnife.bind(this);
    }


    public void onMapReady(GoogleMap map) {


    }



}
