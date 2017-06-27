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
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import com.naxa.nepal.sudurpaschimanchal.adapter.MajorDevelopment_List_Adapter;
import com.naxa.nepal.sudurpaschimanchal.model.NewsAndEventsModel;
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

public class MajorDevelopmentProjectsActivity extends AppCompatActivity{

    //Susan
    private SwipeRefreshLayout swipeContainer;

    //Susan
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    MajorDevelopment_List_Adapter ca;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    ProgressDialog mProgressDlg;
    public static List<NewsAndEventsModel> resultCur = new ArrayList<>();
    public static List<NewsAndEventsModel> filteredList = new ArrayList<>();
    public static final String MyPREFERENCES = "major_development_projects";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor ;

    private boolean setData;
    String jsonToSend = null;
    private String date, time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.major_development_projects);

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
        convertDataToJson();

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
//                    Intent intent = new Intent(MajorDevelopmentProjectsActivity.this, NewsDetailsActivity.class);
//
//                    intent.putExtra("news_title_np", resultCur.get(position).news_title_np);
//                    intent.putExtra("news_desc_np", resultCur.get(position).news_desc_np);
//                    intent.putExtra("news_date_np", resultCur.get(position).news_date_np);
//                    intent.putExtra("news_image", resultCur.get(position).mThumbnail);
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

        //Swipe Refresh Action
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainerNews);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (networkInfo != null && networkInfo.isConnected()) {

                    editor.clear();
                    editor.commit();

                    refreshContent();
                    swipeContainer.setRefreshing(false);
                } else {
                    Snackbar.make(swipeContainer, "ईन्टरनेट कनेक्सन छैन । ", Snackbar.LENGTH_LONG)
                            .setAction("Retry", null).show();
                    swipeContainer.setRefreshing(false);
                }
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
    }

    private void refreshContent() {
        createList();
        fillTable();
    }

    private void initializeUI(){
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("मुख्य विकास परियोजनाहरु");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_back_icon);
        upArrow.setColorFilter(getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

    }

    // data convert
    public void convertDataToJson() {
        //function in the activity that corresponds to the layout button
        try {
            JSONObject header = new JSONObject();
            header.put("token", "bf5d483811");
            jsonToSend = header.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createList() {
        resultCur.clear();

//        if (setData) {
//        mProgressDlg = new ProgressDialog(getActivity());
//        mProgressDlg.setMessage("कृपया पर्खनुहोस्...");
//        mProgressDlg.setIndeterminate(false);
//        mProgressDlg.show();
        MajorDevelopmentAPI restApi = new MajorDevelopmentAPI();
        restApi.execute();

//        } else {
//
//            mProgressDlg = new ProgressDialog(getActivity());
//            mProgressDlg.setMessage("Loading please Wait...");
//            mProgressDlg.setIndeterminate(false);
//            mProgressDlg.show();
//            PoticianListService restApi = new PoticianListService();
//            restApi.execute();
//        }
    }

    private class MajorDevelopmentAPI extends AsyncTask<String, Void, String> {
        JSONArray data = null;

        protected String getASCIIContentFromEntity(HttpURLConnection entity)
                throws IllegalStateException, IOException {
            InputStream in = (InputStream) entity.getContent();

            StringBuffer out = new StringBuffer();
            int n = 1;
            while (n > 0) {
                byte[] b = new byte[4096];
                n = in.read(b);

                if (n > 0)
                    out.append(new String(b, 0, n));
            }
            return out.toString();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String text = "";

            if (sharedpreferences.getString("major_development_projects", "").trim().isEmpty()) {
                if (networkInfo != null && networkInfo.isConnected()) {
                    text = POST(UrlClass.URL_MAJOR_DEVELOPMENT);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("major_development_projects", text);
                    editor.commit();
                } else {
                    //Snackbar.make(getView(), "ईन्टरनेट कनेक्सन छैन । ", Snackbar.LENGTH_LONG)
                    //   .setAction("Retry", null).show();
                }
            } else {
                text = sharedpreferences.getString("major_development_projects", "");
            }
            Log.e("DATA", "" + text.toString());

            JSONArray list;
            String txtDisp = null;
            ArrayList<String> question = new ArrayList<>();
            try {

                JSONObject jsonObj = new JSONObject(text);
                data = jsonObj.getJSONArray("data");
                Log.e("DATA", "" + data.toString());
                for (int i = 0; i < data.length(); i++) {
                    JSONObject c = data.getJSONObject(i);
                    NewsAndEventsModel newData = new NewsAndEventsModel();
                    newData.setNews_title_en(c.getString("title"));
                    newData.setNews_desc_en(c.getString("description"));
                    newData.setmThumbnail(c.getString("img_url"));

                    //clean date time from sever
//                    fixDate(c.getString("sudur_news_date"));
//                    newData.news_date_np = date;
//                    newData.news_time_np = time;

                    resultCur.add(newData);
                }
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }

            return text.toString();
        }

        private void fixDate(String rawDateTime) {
            String[] dtparts = rawDateTime.split(" ");
            date = dtparts[0];
            String badTime = dtparts[1];
            DateFormat f1 = new SimpleDateFormat("HH:mm:ss");
            try {
                Date d = f1.parse(badTime);
                DateFormat f2 = new SimpleDateFormat("h:mma");
                time = f2.format(d).toLowerCase();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.d("Samir", date);
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //Log.e("ONPOSTEXECUTE", "ONPOST");
//            mProgressDlg.dismiss();
            if (result != null) {
                fillTable();
                swipeContainer.setRefreshing(false);
            }
        }

        public String POST(String myurl) {

            URL url;
            String response = "";
            try {
                url = new URL(myurl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("data", jsonToSend);
                String query = builder.build().getEncodedQuery();
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                } else {
                    response = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    public void fillTable() {
        filteredList = resultCur;
        ca = new MajorDevelopment_List_Adapter(this, filteredList);
        recyclerView.setAdapter(ca);
    }
}