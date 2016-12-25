package com.naxa.nepal.sudurpaschimanchal.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.Utils.ChartColor;
import com.naxa.nepal.sudurpaschimanchal.connection.ConnectonDetector;
import com.naxa.nepal.sudurpaschimanchal.fragment.PoliticalPartiesFragment;
import com.naxa.nepal.sudurpaschimanchal.fragment.PolticianListFragment;
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
 * Created by Samir on 10/27/2016.
 */
public class RajnitikAwasthaActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private PieChart mChart;

    PieChart pieChart;

    private static String stat_name = "राजनीतिक अवस्था";

    public static final String EXTRA_NAME = "cheese_name";

    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();

    public static final String MyPREFERENCES = "parties_chart_json";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    private boolean setData;
    String jsonToSend = null;
    String party_name_np, rawseats;
    Float seat;

    //Samir
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    ConnectonDetector conectionDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rajnitik_awastha_activity);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("राजनीतिक अवस्था");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_back_icon);
        upArrow.setColorFilter(getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

//connection detector
        conectionDetector = new ConnectonDetector(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        convertDataToJson();
        createList();

        ScrollView scroll = (ScrollView) findViewById(R.id.scrollView);
        scroll.setFocusableInTouchMode(true);
        scroll.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);


//=================================Pie Chart Draw==============================//

        pieChart = (PieChart) findViewById(R.id.chart1);

        pieChart.setCenterText( "निर्वाचित\n सिट संख्या");
        entries.add(new Entry(1f, 0));
        entries.add(new Entry(9f, 1));
        entries.add(new Entry(2f, 2));
        entries.add(new Entry(9f, 3));

//        entries.add(new Entry(25f, 4));
//        entries.add(new Entry(15f, 5));
//        entries.add(new Entry(25f, 6));
//        entries.add(new Entry(30f, 7));
//        entries.add(new Entry(25f, 8));

        PieDataSet dataset = new PieDataSet(entries,"");
        labels.add("माओवादी");
        labels.add("एमाले");
        labels.add("फोरम लोकतान्त्रिक");
        labels.add("काँग्रेस");

//        labels.add("जनमोर्चा");
//        labels.add("माले");
//        labels.add("चुरे भावोर");
//        labels.add("राप्रपा");
//        labels.add("नेमकिपा");

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ChartColor.COLORFUL_COLORS); //
        pieChart.setDescription("निर्वाचित राजनीतिक पार्टीहरु");
        pieChart.setData(data);

        pieChart.animateY(2000);

        pieChart.saveToGallery("/sd/mychart.jpg", 85); // 85 is the quality of the image

        //===================================End Of PieChart=========================================//


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

        TextView rajnitik_bibaran = (TextView) findViewById(R.id.textView_rajnitik_bibaran);
        rajnitik_bibaran.setText("सुदूरपश्चिम बिकाश क्षेत्रमा क्रियाशिल राजनीतिक पार्टीहरू र राजनीतिक व्यक्तिहरुको विवरण निम्न अनुसार छन् ।");


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        final SharedPreferences wmbPreference = PreferenceManager
//                .getDefaultSharedPreferences(getActivity());

        adapter.addFragment(new PolticianListFragment(), "राजनीतिक \n व्यक्तिहरू");
        adapter.addFragment(new PoliticalPartiesFragment(), "राजनीतिक \n पार्टीहरु");
        viewPager.setAdapter(adapter);
//
//        }


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

        PoticalPartiesListService restApi = new PoticalPartiesListService();
        restApi.execute();
    }

    private class PoticalPartiesListService extends AsyncTask<String, Void, String> {
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

            if (sharedpreferences.getString("parties_chart_json", "").trim().isEmpty()) {
                if (networkInfo != null && networkInfo.isConnected()) {
                    text = POST(UrlClass.URL_PARTY_LIST);
                    editor.putString("parties_chart_json", text);
                    editor.commit();
                } else {
//                    try {
//                        Snackbar.make(getCon, "ईन्टरनेट कनेक्सन छैन । ", Snackbar.LENGTH_LONG)
//                                .setAction("Retry", null).show();
//                    } catch (NullPointerException e) {
//                        e.printStackTrace();
//                    }
                }
            } else {
                text = sharedpreferences.getString("parties_chart_json", "");
            }

            try {
                JSONObject jsonObj = new JSONObject(text);
                data = jsonObj.getJSONArray("data");

                Log.e("DATA", "" + data.toString());

                for (int i = 0; i < data.length(); i++) {

                    JSONObject c = data.getJSONObject(i);

                    rawseats = c.getString("seats");
                    party_name_np = c.getString("sudur_political_party_name_np");

                    seat = Float.parseFloat(rawseats);

                  //  entries.add(new Entry(seat,i));
                  //  labels.add(party_name_np);

//                    PolticalParties_List_Model newData = new PolticalParties_List_Model();
//                    newData.poltical_party_name_en = c.getString("sudur_political_party_name_en");
//                    newData.poltical_party_name_np = c.getString("sudur_political_party_name_np");
//                    newData.mThumbnail = c.getString("photo");

                }
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }

            return text.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            if (result != null) {
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
}
