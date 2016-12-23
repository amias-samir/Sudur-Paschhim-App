package com.naxa.nepal.sudurpaschimanchal.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.activities.FWDRCompletedProjectsActivity;
import com.naxa.nepal.sudurpaschimanchal.activities.FWDRFutureProjectsActivity;
import com.naxa.nepal.sudurpaschimanchal.activities.FWDROngoingProjectActivity;
import com.naxa.nepal.sudurpaschimanchal.adapter.GridSpacingItemDecorator;
import com.naxa.nepal.sudurpaschimanchal.adapter.Project_Status_Adapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevStatusSudurPaschhimFragment extends Fragment {



    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    MyReceiver r;

    private SharedPreferences wmbPreference;
    private boolean setData;


    public DevStatusSudurPaschhimFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView = inflater.inflate(R.layout.fragment_project_status, container, false);
        wmbPreference = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        setData = wmbPreference.getBoolean("SET_ENGLISH_ON", true);


        return rootView;


    }
    public void refresh() {
        //yout code in refresh.
        Log.i("Refresh", "YES");
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            DevStatusSudurPaschhimFragment.this.refresh();
        }
    }

    //
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }

    @Override
    public void onResume() {
        super.onResume();

        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r,
                new IntentFilter("HOME_REFRESH"));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Calling the RecyclerView

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_grid_status);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        int spanCount = 1;
        int spacing = 5;
        boolean includeEdge = true;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecorator(spanCount, spacing, includeEdge));

        mAdapter = new Project_Status_Adapter(getActivity(), getActivity());
        mRecyclerView.setAdapter(mAdapter);

        final GestureDetector mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

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
                    callFragment(position);
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

    private void callFragment(int position) {

        Fragment mFragment = null;
        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();

        switch (position) {
            case 0:
                Intent intent1 = new Intent(getActivity(), FWDRCompletedProjectsActivity.class);
                startActivity(intent1);
                break;
            case 1:
                Intent intent2 = new Intent(getActivity(), FWDROngoingProjectActivity.class);
                startActivity(intent2);
                break;
            case 2:
                Intent intent3 = new Intent(getActivity(), FWDRFutureProjectsActivity.class);
                startActivity(intent3);
                break;
        }

        if (mFragment != null) {
            mFragmentManager.beginTransaction().addToBackStack("home").replace(R.id.containerView, mFragment).commit();
        }
    }
}
