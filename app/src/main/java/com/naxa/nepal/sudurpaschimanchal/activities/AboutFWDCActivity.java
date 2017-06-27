package com.naxa.nepal.sudurpaschimanchal.activities;
/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.adapter.ExpandableListAdapter;
import com.naxa.nepal.sudurpaschimanchal.fragment.CompletedProjectsFragment;
import com.naxa.nepal.sudurpaschimanchal.fragment.FutureProjectsFragment;
import com.naxa.nepal.sudurpaschimanchal.fragment.OnGoingProjectsFragment;
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
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import at.blogc.android.views.ExpandableTextView;

public class AboutFWDCActivity extends AppCompatActivity {
    private static final String TAG = "AboutFWDCActivity";
    private SwipeRefreshLayout swipeContainer;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> mission = new ArrayList<String>();
    List<String> vision = new ArrayList<String>();
    List<String> objective = new ArrayList<String>();
    List<String> main_works = new ArrayList<String>();
    List<String> gathan_aadesh = new ArrayList<String>();

    private View coordinatorLayoutView;

    //Susan
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    ProgressDialog mProgressDlg;
    public static final String MyPREFERENCES = "dev_activities";
    public static final String MyPREFERENCES1 = "fwdc_json";

    SharedPreferences sharedpreferences;
    SharedPreferences sharedpreferences1;

    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor1;

    private boolean setData;
    String jsonToSend = null;

    TextView tvTitle;
    ImageView ivImageView;

    ExpandableTextView tvDesc ;


    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_fwdc_activity);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("विकास आयोग को बारेमा");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_back_icon);
        upArrow.setColorFilter(getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        //Susan
        //Check internet connection
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        tvTitle = (TextView) findViewById(R.id.textView_label_about_fwdc);
        tvDesc = (ExpandableTextView) findViewById(R.id.textView_about_fwdc);
        ivImageView = (ImageView) findViewById(R.id.backdrop);
        final Button buttonToggle = (Button) this.findViewById(R.id.button_toggle);
//        =========================================== expandable text view ========================================//
// set animation duration via code, but preferable in your layout files by using the animation_duration attribute
        tvDesc.setAnimationDuration(750L);

        // set interpolators for both expanding and collapsing animations
        tvDesc.setInterpolator(new OvershootInterpolator());

        // or set them separately
        tvDesc.setExpandInterpolator(new OvershootInterpolator());
        tvDesc.setCollapseInterpolator(new OvershootInterpolator());

        // toggle the ExpandableTextView
        buttonToggle.setOnClickListener(new View.OnClickListener()
        {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(final View v)
            {
                tvDesc.toggle();
                buttonToggle.setText(tvDesc.isExpanded() ? R.string.collapse : R.string.expand);
            }
        });

        // but, you can also do the checks yourself
        buttonToggle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                if (tvDesc.isExpanded())
                {
                    tvDesc.collapse();
                    buttonToggle.setText(R.string.expand);
                }
                else
                {
                    tvDesc.expand();
                    buttonToggle.setText(R.string.collapse);
                }
            }
        });

        // listen for expand / collapse events
        tvDesc.setOnExpandListener(new ExpandableTextView.OnExpandListener()
        {
            @Override
            public void onExpand(final ExpandableTextView view)
            {
                Log.d(TAG, "ExpandableTextView expanded");
            }

            @Override
            public void onCollapse(final ExpandableTextView view)
            {
                Log.d(TAG, "ExpandableTextView collapsed");
            }
        });

//        =========================================== ends of expandable text view ==================================//





        //SharedPreferences-DEV_ACTIVITIES
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        //SharedPreferences-About_FWDC
        sharedpreferences1 = this.getSharedPreferences(MyPREFERENCES1, Context.MODE_PRIVATE);
        editor1 = sharedpreferences1.edit();


        if (sharedpreferences.getString("dev_activities", "").trim().isEmpty()) {

            if (networkInfo != null && networkInfo.isConnected()) {

                mProgressDlg = new ProgressDialog(AboutFWDCActivity.this);
                mProgressDlg.setMessage("कृपया पर्खनुहोस्...");
                mProgressDlg.setIndeterminate(false);
                mProgressDlg.setCancelable(false);
                mProgressDlg.show();
                //Susan
                convertDataToJson();
                ApiCall apiCall = new ApiCall();
                apiCall.execute();

                //Samir
                //==============about fwdc call====================//
                convertDataToJson();
                AboutFWDCApiCall apiCall1 = new AboutFWDCApiCall();
                apiCall1.execute();

            } else {
                coordinatorLayoutView = findViewById(R.id.main_content);
                Snackbar.make(coordinatorLayoutView, "ईन्टरनेट कनेक्सन छैन । ", Snackbar.LENGTH_LONG)
                        .setAction("Retry", null).show();
            }
        }else {

            ApiCall apiCall = new ApiCall();
            apiCall.execute();

            //Samir
            //==============about fwdc call====================//
            AboutFWDCApiCall apiCall1 = new AboutFWDCApiCall();
            apiCall1.execute();
        }


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        //Swipe Refresh Action
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (networkInfo != null && networkInfo.isConnected()) {
                    editor.clear();
                    editor.commit();

                    editor1.clear();
                    editor1.commit();

                    convertDataToJson();
                    ApiCall apiCall = new ApiCall();
                    apiCall.execute();

                    //==============about fwdc call====================//
                    convertDataToJson();
                    AboutFWDCApiCall apiCall1 = new AboutFWDCApiCall();
                    apiCall1.execute();

                    viewPager.clearFocus();
                    tabLayout.removeAllTabs();
                    tabLayout.setupWithViewPager(viewPager);
                    tabLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            tabLayout.setupWithViewPager(viewPager);
                        }
                    });

                    if (apiCall.equals(null)) {
                        swipeContainer.setRefreshing(false);
                        Log.d("DEBUG", "Fetch timeline error: ");
                    }
                } else {
                    coordinatorLayoutView = findViewById(R.id.main_content);
                    Snackbar.make(coordinatorLayoutView, "ईन्टरनेट कनेक्सन छैन । ", Snackbar.LENGTH_LONG)
                            .setAction("Retry", null).show();
                    swipeContainer.setRefreshing(false);
                }

                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);


        ScrollView scroll = (ScrollView) findViewById(R.id.scrollView);
        scroll.setFocusableInTouchMode(true);
        scroll.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                if(groupPosition == 4 ){
                    switch (childPosition){
                        case 0 :
                            Toast.makeText(getApplicationContext(),groupPosition + ","+childPosition+":"+"गठन आदेश", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AboutFWDCActivity.this, GathanAadeshPdfActivity.class);
                            startActivity(intent);
                            break;
                    }

                }

                return false;
            }
        });
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new CompletedProjectsFragment(), "सम्पन्न \n परियोजना ");
        adapter.addFragment(new OnGoingProjectsFragment(), "हुदैगरेका \n परियोजना ");
        adapter.addFragment(new FutureProjectsFragment(), "भावि \n परियोजना ");
        viewPager.setAdapter(adapter);

    }

    public class ApiCall extends AsyncTask<String, Void, String> {
        JSONArray data = null;
        String dev_status_id = null, dev_title_en = null, dev_title_np = null,
                dev_desc_en = null, dev_desc_np = null, dev_contractor_en = null,
                dev_contractor_np = null, dev_budget = null, district_name_en = null,
                district_name_np = null;

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
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String text = "";

            try {
                if (sharedpreferences.getString("dev_activities", "").trim().isEmpty()) {

                    if (networkInfo != null && networkInfo.isConnected()) {

                        text = POST(UrlClass.URL_DEV_ACTIVITIES);
                        Log.e("MAIN_JSON", "" + text.toString());
                        editor.putString("dev_activities", text);
                        editor.commit();
                    } else {
                        coordinatorLayoutView = findViewById(R.id.main_content);
                        Snackbar.make(coordinatorLayoutView, "ईन्टरनेट कनेक्सन छैन । ", Snackbar.LENGTH_LONG)
                                .setAction("Retry", null).show();
                    }


                } else {

                    text = sharedpreferences.getString("dev_activities", "");
                    Log.e("dev_activities", "" + text.toString());

                }

            } catch (Exception e) {
                return e.getLocalizedMessage();
            }

            return text.toString();
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //Log.e("ONPOSTEXECUTE", "ONPOST");
//            mProgressDlg.dismiss();
            if (result != null) {
                //Success

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

    //About fwdc
    public class AboutFWDCApiCall extends AsyncTask<String, Void, String> {
        JSONArray data = null;


        String fwdc_desc_en = null, fwdc_title_en = null, fwdc_desc_np = null, fwdc_title_np = null, office_img = "";


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
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            String text1 = "";

            try {
                if (sharedpreferences1.getString("fwdc_json", "").trim().isEmpty()) {
                    if (networkInfo != null && networkInfo.isConnected()) {
                        text1 = POST(UrlClass.URL_ABOUT_FWDC);
                        Log.e("fwdc_json", "" + text1.toString());
                        editor1.putString("fwdc_json", text1);
                        editor1.commit();
                    } else {
                        coordinatorLayoutView = findViewById(R.id.main_content);
                        Snackbar.make(coordinatorLayoutView, "ईन्टरनेट कनेक्सन छैन । ", Snackbar.LENGTH_LONG)
                                .setAction("Retry", null).show();
                    }
                } else {
                    text1 = sharedpreferences1.getString("fwdc_json", "");
                    Log.e("fwdc_json", "" + text1.toString());

                }
                JSONObject jsonObj = new JSONObject(text1);
                data = jsonObj.getJSONArray("data");

                Log.e("DATA", "" + data.toString());
                for (int i = 0; i < data.length(); i++) {
                    JSONObject c = data.getJSONObject(i);

                    fwdc_desc_en = c.getString("desc_en");
                    fwdc_title_en = c.getString("title_en");

                    fwdc_desc_np = c.getString("desc_np");
                    fwdc_title_np = c.getString("title_np");
                    office_img = c.getString("office_photo");
                }



            } catch (Exception e) {
                return e.getLocalizedMessage();
            }

            return data.toString();
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //Log.e("ONPOSTEXECUTE", "ONPOST");
            if ((mProgressDlg != null) && mProgressDlg.isShowing()) {
                mProgressDlg.dismiss();
            }
            if(swipeContainer != null && swipeContainer.isRefreshing()){
                swipeContainer.setRefreshing(false);
            }
            if (result != null) {
//                //Success
                tvTitle.setText(fwdc_title_np);
                tvDesc.setText(fwdc_desc_np);
                Glide.with(getApplicationContext())
                        .load(office_img)
                        .thumbnail(0.5f)
                        .override(200, 400)
                        .into(ivImageView);

//                swipeContainer.setRefreshing(false);

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

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    /*
   * Preparing the list data
   */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("ध्येय ");
        listDataHeader.add("लक्ष्य ");
        listDataHeader.add("उधेश्य");
        listDataHeader.add("मुख्य कार्यहरु");
        listDataHeader.add("गठन आदेश");

        mission.add(getString(R.string.mission));
        vision.add(getString(R.string.vision));
        objective.add(getString(R.string.objective));
        objective.add(getString(R.string.objective1));
        objective.add(getString(R.string.objective2));
        main_works.add(getString(R.string.main_works));
        main_works.add(getString(R.string.main_works1));
        main_works.add(getString(R.string.main_works2));
        main_works.add(getString(R.string.main_works3));
        main_works.add(getString(R.string.main_works4));
        main_works.add(getString(R.string.main_works5));
        main_works.add(getString(R.string.main_works6));
        main_works.add(getString(R.string.main_works7));
        main_works.add(getString(R.string.main_works8));
        main_works.add(getString(R.string.main_works9));
        main_works.add(getString(R.string.main_works10));
        main_works.add(getString(R.string.main_works11));

        gathan_aadesh.add(" गठन आदेश पि.डी.एफ हेर्नको लागि यहाँ क्लिक गर्नुहोस");



        listDataChild.put(listDataHeader.get(0), mission); // Header, Child data
        listDataChild.put(listDataHeader.get(1), vision);
        listDataChild.put(listDataHeader.get(2), objective);
        listDataChild.put(listDataHeader.get(3), main_works);
        listDataChild.put(listDataHeader.get(4), gathan_aadesh);



    }

}



