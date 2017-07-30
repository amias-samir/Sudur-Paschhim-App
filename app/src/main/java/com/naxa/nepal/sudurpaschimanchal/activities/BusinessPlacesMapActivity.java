package com.naxa.nepal.sudurpaschimanchal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.model.local.DatabaseHelper;
import com.naxa.nepal.sudurpaschimanchal.model.rest.ApiClient;
import com.naxa.nepal.sudurpaschimanchal.model.rest.ApiInterface;
import com.naxa.nepal.sudurpaschimanchal.model.rest.Data;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BusinessPlacesMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.business_list_spinner)
    Spinner businessListSpinner;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_places_map);
        ButterKnife.bind(this);
        setToolbar();
        tryToSetSpinner();
        String lastSyncDate = DatabaseHelper.getInstance(getApplicationContext()).getLastSyncDate(DatabaseHelper.TABLE_BUSINESS_PLACES);
        fetchMenuFromServer(lastSyncDate);


    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        String lastSyncDate = DatabaseHelper.getInstance(getApplicationContext()).getLastSyncDate(DatabaseHelper.TABLE_BUSINESS_PLACES);
        fetchMenuFromServer(lastSyncDate);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//>>>>>>> d0cf99c097c0aa4db9abbdc724106a88ad306a66
//    }

    private void fetchMenuFromServer(String lastSyncDateTime) {


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Data> call = apiService.getMenu(lastSyncDateTime);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                handleResponse(response);
            }

            private void handleResponse(Response<Data> response) {
                if (response.code() != 200 || response.body().getData() == null) {
                    showToast("Something went wrong");
                    return;
                }

                Data data = response.body();
                DatabaseHelper.getInstance(getApplicationContext()).addBushinessList(data.getData());
                tryToSetSpinner();


                showToast(data.getData().size() + " new places received");
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

                showToast(t.getMessage());
            }
        });

    }


    private void setSudurCamera(GoogleMap myMap) {

        final LatLngBounds SUDUR = new LatLngBounds(new LatLng(28.248326, 80.046272), new LatLng(30.771248, 82.296098));


        myMap.setLatLngBoundsForCameraTarget(SUDUR);
        myMap.setMinZoomPreference(8f);

        LatLng mCenterLocation = new LatLng(29.319998, 81.096780);

        CameraPosition cameraPositon = CameraPosition.builder()
                .target(mCenterLocation)
                .zoom(8f)
                .bearing(0.0f)
                .tilt(20f)
                .build();

        myMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPositon), null);

        myMap.getUiSettings().setZoomControlsEnabled(true);

    }

    private void tryToSetSpinner() {
        List<String> foodList = DatabaseHelper.getInstance(getApplicationContext()).getBusinessCategories();
        if (foodList == null || foodList.size() == 0) {
            showToast("Failed to load categories");
            return;
        }


        ArrayList<String> bussinesscategorieslist = DatabaseHelper.getInstance(getApplicationContext()).getBusinessCategories();


        String[] bussinesscategories = bussinesscategorieslist.toArray(new String[bussinesscategorieslist.size()]);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bussinesscategories);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        businessListSpinner.setAdapter(spinnerArrayAdapter);


    }

    private void showToast(String s) {

        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.floatingActionButton)
    public void toBusinessActivity() {

        Intent intent = new Intent(BusinessPlacesMapActivity.this, AddYourBusinessActivity.class);
        startActivity(intent);
    }

}
