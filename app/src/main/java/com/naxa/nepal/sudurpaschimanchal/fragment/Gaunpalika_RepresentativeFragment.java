package com.naxa.nepal.sudurpaschimanchal.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.adapter.LocalLevelRepresentative_Adapter;
import com.naxa.nepal.sudurpaschimanchal.model.Local_Level_Representative_Model;
import com.naxa.nepal.sudurpaschimanchal.model.UrlClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by susan on 6/26/2017.
 */

public class Gaunpalika_RepresentativeFragment extends Fragment {

    View view;

    //Susan
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    //Susan
    private SwipeRefreshLayout swipeContainer;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    ProgressDialog mProgressDlg;

    LocalLevelRepresentative_Adapter ca;
    public static List<Local_Level_Representative_Model> resultCur = new ArrayList<>();
    public static List<Local_Level_Representative_Model> filteredList = new ArrayList<>();

    public static final String MyPREFERENCES = "gaunpalika_representative";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String jsonToSend = null;

    public Gaunpalika_RepresentativeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.local_chief_member_fragment, container, false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

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

//                    Intent intent = new Intent(getActivity(), NameListOfRepresentativeActivity.class);
//                    intent.putExtra("district_np", resultCur.get(position).get_name_np());
//                    startActivity(intent);

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
        Local_Level_Representative_Model districtListModel = new Local_Level_Representative_Model("Susan Lama", "Indrasarowor Gaupalika", "9841563258");
        resultCur.add(districtListModel);
        districtListModel = new Local_Level_Representative_Model("Nishon Tandukar", "Nakhu Gaunpalika", "9874586958");
        resultCur.add(districtListModel);
        districtListModel = new Local_Level_Representative_Model("Samir Dangal", "Kharpi Gaunpalika", "98942563258");
        resultCur.add(districtListModel);
        districtListModel = new Local_Level_Representative_Model("Upen Oli", "Bardu Gaunpalika", "9851073265");
        resultCur.add(districtListModel);
        fillTable();

    }

    public void fillTable() {
        filteredList = resultCur;
        ca = new LocalLevelRepresentative_Adapter(getActivity(), filteredList);
        recyclerView.setAdapter(ca);
        ca.notifyDataSetChanged();
    }
}
