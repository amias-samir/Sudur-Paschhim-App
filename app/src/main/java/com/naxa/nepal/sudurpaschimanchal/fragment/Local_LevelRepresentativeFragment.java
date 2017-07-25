package com.naxa.nepal.sudurpaschimanchal.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.activities.DevelopmentINGOsOrganizationActivity;
import com.naxa.nepal.sudurpaschimanchal.activities.NameListOfRepresentativeActivity;
import com.naxa.nepal.sudurpaschimanchal.adapter.DistrictList_Adapter;
import com.naxa.nepal.sudurpaschimanchal.model.District;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by susan on 6/26/2017.
 */

public class Local_LevelRepresentativeFragment extends Fragment {

    //Susan
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    DistrictList_Adapter ca;
    public static List<District> resultCur = new ArrayList<>();
    public static List<District> filteredList = new ArrayList<>();

    public Local_LevelRepresentativeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.local_chief_member_fragment, container, false);

        //Susan
        //Check internet connection
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        recyclerView = (RecyclerView) view.findViewById(R.id.NewsList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        createList();

        final GestureDetector mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    int position = recyclerView.getChildPosition(child);

                    Intent intent = new Intent(getActivity(), NameListOfRepresentativeActivity.class);
                    intent.putExtra("district_np", resultCur.get(position).getNpName());

                    startActivity(intent);

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

        return view;
    }

    private void createList() {
        District districtListModel = new District("", "", "कैलाली");
        resultCur.add(districtListModel);
        districtListModel = new District("", "", "आछाम");
        resultCur.add(districtListModel);
        districtListModel = new District("", "", "बझांग");
        resultCur.add(districtListModel);
        districtListModel = new District("", "", "बाजुरा");
        resultCur.add(districtListModel);
        districtListModel = new District("", "", "कन्चनपुर");
        resultCur.add(districtListModel);
        districtListModel = new District("", "", "डडेल्धुरा");
        resultCur.add(districtListModel);
        districtListModel = new District("", "", "बैतडी");
        resultCur.add(districtListModel);
        districtListModel = new District("", "", "दार्चुला");
        resultCur.add(districtListModel);
        districtListModel= new District("", "", "डोटी");
        resultCur.add(districtListModel);
        fillTable();

    }

    public void fillTable() {
        filteredList = resultCur;
        ca = new DistrictList_Adapter(getActivity(), filteredList);
        recyclerView.setAdapter(ca);
        ca.notifyDataSetChanged();
    }
}
