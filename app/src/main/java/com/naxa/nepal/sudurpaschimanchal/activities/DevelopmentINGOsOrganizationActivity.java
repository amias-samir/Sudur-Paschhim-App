package com.naxa.nepal.sudurpaschimanchal.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.adapter.NGO_INGO_DevelopmentList_Adapter;
import com.naxa.nepal.sudurpaschimanchal.fragment.Nagarpalika_RepresentativeFragment;
import com.naxa.nepal.sudurpaschimanchal.model.District;
import com.naxa.nepal.sudurpaschimanchal.model.INGO_NGO_Model;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by susan on 6/26/2017.
 */
public class DevelopmentINGOsOrganizationActivity extends AppCompatActivity{
    //Susan
    private SwipeRefreshLayout swipeContainer;

    //Susan
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog mProgressDlg;

    NGO_INGO_DevelopmentList_Adapter ca;
    public static List<INGO_NGO_Model> resultCur = new ArrayList<>();
    public static List<INGO_NGO_Model> filteredList = new ArrayList<>();
    public static final String MyPREFERENCES = "development_ingo_ngo";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor ;

    private boolean setData;
    String jsonToSend = null;
    private String date, time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.development_ingo_ngo_org);

        initializeUI();

        //Susan
        //Check internet connection
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        recyclerView = (RecyclerView) findViewById(R.id.NewsList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        createList();

        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

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
                    Intent intent = new Intent(DevelopmentINGOsOrganizationActivity.this, NGODetailsActivity.class);

                    intent.putExtra("title_np", resultCur.get(position).getName());
                    intent.putExtra("desc_np", resultCur.get(position).getDesc());
                    intent.putExtra("email_np", resultCur.get(position).getEmail());
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
    }

    private void createList() {
        resultCur.clear();
        INGO_NGO_Model districtListModel = new INGO_NGO_Model("USAID", "The United States Agency for International Development (USAID) is the United States Government agency which is primarily responsible for administering civilian foreign aid.\n" +
                "\n" +
                "President John F. Kennedy created USAID from its predecessor agencies in 1961 by executive order. USAID's programs are authorized by the Congress in the Foreign Assistance Act,[4] which the Congress supplements through directions in annual funding appropriation acts and other legislation. Although it is technically an independent agency, USAID operates subject to the foreign policy guidance of the President, Secretary of State, and the National Security Council.[5] USAID operates in Africa, Asia, Latin America, the Middle East, and Eastern Europe.", "https://www.usaid.gov/");
        resultCur.add(districtListModel);
        districtListModel = new INGO_NGO_Model("WWF", "WWF started working in Nepal from 1967 when it launched a rhino conservation program in Chitwan. To keep up with the evolving face of conservation and environmental movement, WWF Nepal’s focus progressed from its localized efforts in conservation of single species in 1960s, integrated conservation and development approach in 1990s, to a new horizon of landscape level conservation encompassing national, regional and global scales of complexity in early 2000s. WWF Nepal is focused in the Terai Arc Landscape (TAL) and Sacred Himalayan Landscape (SHL), including Koshi river Basin, and Chitwan Annapurna Landscape (CHAL) under the USAID-funded Hariyo Ban program. WWF Nepal works to conserve flagship and priority key species, forests, freshwater, and to mitigate the pervasive threat of climate change to communities, species and their habitats.", "http://www.wwfnepal.org/");
        resultCur.add(districtListModel);
        districtListModel = new INGO_NGO_Model("United Mission to Nepal", "United Mission to Nepal (UMN) strives to address root causes of poverty as it serves the people of Nepal in the name and spirit of Jesus Christ. Established in 1954, UMN is a cooperative effort between the people of Nepal and a large number of Christian organisations from nearly 20 countries on 4 continents. Multicultural teams of Nepali nationals and volunteer expatriate staff work alongside local organisations in less developed areas of the country, building partnerships that lead to healthy, strong and empowered individuals, families, and communities.", "http://www.umn.org.np/page/about-umn");
        resultCur.add(districtListModel);
        fillTable();
    }

    private void initializeUI(){
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("स्तिनिय तहका प्रतिनिधि");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_back_icon);
        upArrow.setColorFilter(getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

    }

    public void fillTable() {
        filteredList = resultCur;
        ca = new NGO_INGO_DevelopmentList_Adapter(this, filteredList);
        recyclerView.setAdapter(ca);
    }
}