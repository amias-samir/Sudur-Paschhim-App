package com.naxa.nepal.sudurpaschimanchal.activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.adapter.DistrictAdapter;
import com.naxa.nepal.sudurpaschimanchal.model.District;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nishon Tandukar on 27 Jun 2017 .
 *
 * @email nishon.tan@gmail.com
 */

public class NagarBudgetDistrict extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_district_budget_nagar_activity)
    RecyclerView rvDistrictBudgetNagarActivity;


    ArrayList<District> districts;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.district_budget_nagar_activity);
        ButterKnife.bind(this);
        setToolbar();
        setupRecycleView();
    }

    private void setupRecycleView() {

        districts = District.createdistrictsList(20);
        DistrictAdapter adapter = new DistrictAdapter(this, districts);
        rvDistrictBudgetNagarActivity.setAdapter(adapter);
        rvDistrictBudgetNagarActivity.setLayoutManager(new LinearLayoutManager(this));


        adapter.setOnItemClickListener(new DistrictAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String name = districts.get(position).getEnName();

            }
        });
    }


    private void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("जिल्ला");
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_back_icon);
        upArrow.setColorFilter(getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

    }
}
