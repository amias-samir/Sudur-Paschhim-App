package com.naxa.nepal.sudurpaschimanchal.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.naxa.nepal.sudurpaschimanchal.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nishon Tandukar on 26 Jun 2017 .
 *
 * @email nishon.tan@gmail.com
 */

public class BikashGatibitiActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.frame_layout_introduction_bikash_kshetra)
    FrameLayout frameLayoutIntroductionBikashKshetra;
    @BindView(R.id.frame_layout_district_program)
    FrameLayout frameLayoutDistrictProgram;
    @BindView(R.id.frame_layout_local_budget)
    FrameLayout frameLayoutLocalBudget;
    @BindView(R.id.frame_layout_dev_agencies)
    FrameLayout frameLayoutDevAgencies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gatibity_bikash);
        ButterKnife.bind(this);


        setToolbar();


    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("विकासका गतिविधिहरु");
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_back_icon);
        upArrow.setColorFilter(getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

    }


    @OnClick({R.id.frame_layout_introduction_bikash_kshetra, R.id.frame_layout_district_program, R.id.frame_layout_local_budget, R.id.frame_layout_dev_agencies})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.frame_layout_introduction_bikash_kshetra:
                break;
            case R.id.frame_layout_district_program:

                startActivity(new Intent(this, DistrictProgramActivity.class));
                break;
            case R.id.frame_layout_local_budget:
                startActivity(new Intent(this, NagarpalikaActivity.class));
                break;
            case R.id.frame_layout_dev_agencies:
                break;
        }
    }
}



