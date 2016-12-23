package com.naxa.nepal.sudurpaschimanchal.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.activities.ProjectDetailsActivity;
import com.naxa.nepal.sudurpaschimanchal.adapter.DevActivityRecyclerViewAdapter;
import com.naxa.nepal.sudurpaschimanchal.model.DevActivity_Pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedProjectsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ProgressDialog mProgressDlg;
    public static final String MyPREFERENCES = "dev_activities";
    SharedPreferences sharedpreferences;
    private boolean setData;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ImageView imageView;

    DevActivityRecyclerViewAdapter ca;
    public static List<DevActivity_Pojo> resultCur = new ArrayList<>();
    public static List<DevActivity_Pojo> filteredList = new ArrayList<>();

    String text = null;
    JSONArray data = null;

    // TODO: Rename and change types and number of parameters
    public static CompletedProjectsFragment newInstance(String param1, String param2) {
        CompletedProjectsFragment fragment = new CompletedProjectsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CompletedProjectsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_completed_projects, container, false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.NewsList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        mProgressDlg = new ProgressDialog(getActivity());
        mProgressDlg.setMessage("कृपया पर्खनुहोस्...");
        mProgressDlg.setIndeterminate(false);
        mProgressDlg.setCancelable(false);
        mProgressDlg.show();
        createList();
        mProgressDlg.dismiss();

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
//                    Drawer.closeDrawers();
                    int position = recyclerView.getChildPosition(child);


                    Intent intent = new Intent(getActivity(), ProjectDetailsActivity.class);
                    intent.putExtra("project_title_np", resultCur.get(position).dev_title_np);
                    intent.putExtra("project_desc_np", resultCur.get(position).dev_desc_np);

                    intent.putExtra("project_district_np", resultCur.get(position).district_name_np);
                    intent.putExtra("project_contractor_np", resultCur.get(position).dev_contractor_np);
                    intent.putExtra("project_budget_np", resultCur.get(position).dev_budget_np);
                    intent.putExtra("project_image", resultCur.get(position).mThumbnail);

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

        return rootView;
    }

    private void createList() {
        resultCur.clear();
        jsonParse();
        fillTable();
    }

    public void jsonParse(){

        String dev_status_id = null, dev_title_en = null, dev_title_np = null,
                dev_desc_en = null, dev_desc_np = null, dev_contractor_en = null,
                dev_contractor_np = null, dev_budget = null, district_name_en = null,
                district_name_np = null;

        JSONObject jsonObj = null;
        try {

            text = sharedpreferences.getString("dev_activities", "");
            if (text!= null){
                Log.e("M_JSON", "" + text.toString());
                jsonObj = new JSONObject(text);

                data = jsonObj.getJSONArray("data");

                Log.e("DATA", "" + data.toString());
                for (int i = 0; i < data.length(); i++) {
                    JSONObject c = data.getJSONObject(i);
                    dev_status_id = c.getString("dev_status_id");
                    Log.e("DEV_STATUS", "" + dev_status_id.toString());

                    if(dev_status_id.equals("1")){
                        DevActivity_Pojo newData = new DevActivity_Pojo();
//                newData.set(c.getString("dev_status_id"));
                        newData.setDev_title_en(c.getString("dev_title_en"));
                        newData.setDev_title_np(c.getString("dev_title_np"));
                        newData.setDev_desc_en(c.getString("dev_desc_en"));
                        newData.setDev_desc_np(c.getString("dev_desc_np"));
                        newData.setDev_contractor_en(c.getString("dev_contractor_en"));
                        newData.setDev_contractor_np(c.getString("dev_contractor_np"));
                        newData.setDev_budget_en(c.getString("dev_budget"));
                        newData.setDev_budget_np(c.getString("dev_budget"));
                        newData.setDistrict_name_en(c.getString("district_name_en"));
                        newData.setDistrict_name_np(c.getString("district_name_np"));

                        newData.setmThumbnail(c.getString("dev_img"));

                        resultCur.add(newData);
                    Log.e("POJO", "" + newData.toString());

                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void fillTable() {

        filteredList = resultCur;
        ca = new DevActivityRecyclerViewAdapter(getActivity(), filteredList);
        recyclerView.setAdapter(ca);

    }

}

