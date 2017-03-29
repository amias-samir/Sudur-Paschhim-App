package com.naxa.nepal.sudurpaschimanchal.activities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.naxa.nepal.sudurpaschimanchal.adapter.ProjectList_Dist_Adapter;
import com.naxa.nepal.sudurpaschimanchal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReligiousActivity extends AppCompatActivity {
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String title_english;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    MyReceiver r;

    private SharedPreferences wmbPreference;
    private boolean setData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.religious_activity);
        wmbPreference = PreferenceManager
                .getDefaultSharedPreferences(this);
        setData = wmbPreference.getBoolean("SET_ENGLISH_ON", true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_list_Local_attraction_religious_district);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        int spanCount = 2;
        int spacing = 5;
        boolean includeEdge = true;

        // mRecyclerView.addItemDecoration(new GridSpacingItemDecorator(spanCount, spacing, includeEdge));

        mAdapter = new ProjectList_Dist_Adapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });


        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    int position = recyclerView.getChildPosition(child);
//
                    return true;
                }

                return false;
            }


            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

        });
    }

    public void refresh() {
        //yout code in refresh.
        Log.i("Refresh", "YES");
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ReligiousActivity.this.refresh();
        }
    }

}
