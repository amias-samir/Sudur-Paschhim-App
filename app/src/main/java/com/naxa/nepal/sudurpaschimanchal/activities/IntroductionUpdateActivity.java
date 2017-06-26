package com.naxa.nepal.sudurpaschimanchal.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.naxa.nepal.sudurpaschimanchal.R;

public class IntroductionUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;

    FrameLayout flIntroductionRegion, flIntroductionDevelopment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_update);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("परिचय");
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_back_icon);
        upArrow.setColorFilter(getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        flIntroductionRegion = (FrameLayout) findViewById(R.id.introduction_bikash_kshetra);
        flIntroductionDevelopment = (FrameLayout) findViewById(R.id.introduction_bikash_aayog);

        flIntroductionRegion.setOnClickListener(this);
        flIntroductionDevelopment.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){
            case (R.id.introduction_bikash_kshetra):
                Intent regionIntent = new Intent(IntroductionUpdateActivity.this, IntroductionRegionActivity.class);
                startActivity(regionIntent);
                break;

            case (R.id.introduction_bikash_aayog):
                Intent devIntent = new Intent(IntroductionUpdateActivity.this, IntroductionDevelopmentActivity.class);
                startActivity(devIntent);
                break;
        }

    }
}
