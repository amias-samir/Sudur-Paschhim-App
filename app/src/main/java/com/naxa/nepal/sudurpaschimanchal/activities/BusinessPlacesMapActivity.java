package com.naxa.nepal.sudurpaschimanchal.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.model.local.DatabaseHelper;
import com.naxa.nepal.sudurpaschimanchal.model.rest.ApiClient;
import com.naxa.nepal.sudurpaschimanchal.model.rest.ApiInterface;
import com.naxa.nepal.sudurpaschimanchal.model.rest.Data;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        String lastSyncDate = DatabaseHelper.getInstance(getApplicationContext()).getLastSyncDate(DatabaseHelper.TABLE_BUSINESS_PLACES);
        fetchMenuFromServer(lastSyncDate);

        tryToSetSpinner();
    }

    @Override
    public void onMapReady(GoogleMap map) {

        String lastSyncDate = DatabaseHelper.getInstance(getApplicationContext()).getLastSyncDate(DatabaseHelper.TABLE_BUSINESS_PLACES);
        fetchMenuFromServer(lastSyncDate);

    }

    private void fetchMenuFromServer(String lastSyncDateTime) {


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Data> call = apiService.getMenu(lastSyncDateTime);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                handleResponse(response);
            }

            private void handleResponse(Response<Data> response) {
                if (response.code() != 200 || response.body() == null) {
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


}
