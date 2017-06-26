package com.naxa.nepal.sudurpaschimanchal.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.naxa.nepal.sudurpaschimanchal.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nishon Tandukar on 26 Jun 2017 .
 *
 * @email nishon.tan@gmail.com
 */

public class BikashGatibitiActivity extends ListActivity {





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gatibity_bikash);
        ButterKnife.bind(this);


    }
}



