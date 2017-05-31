package com.naxa.nepal.sudurpaschimanchal.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.naxa.nepal.sudurpaschimanchal.MainActivity;
import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.fragment.PoliticalPartiesFragment;
import com.naxa.nepal.sudurpaschimanchal.fragment.PolticianListFragment;
import com.naxa.nepal.sudurpaschimanchal.model.PolticalParties_List_Model;
import com.naxa.nepal.sudurpaschimanchal.model.Poltician_List_Model;
import com.naxa.nepal.sudurpaschimanchal.model.SosioEconomicStatModel;
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

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Susan on 5/31/2016.
 */
public class SplashScreenActivity extends Activity {

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    private ProgressBar downloadProgressBar;


    //    ===============================================================================================================//
//  ============================  About FWDC =================================== //
    public static final String FWDCDevPREFERENCES = "dev_activities";
    public static final String AboutFWDCPREFERENCES = "fwdc_json";
    SharedPreferences fwdcdevsharedpreferences;
    SharedPreferences aboutfwdcsharedpreferences;
    SharedPreferences.Editor fwdcdeveditor;
    SharedPreferences.Editor aboutfwdceditor;
    private boolean setData;
    String jsonToSend = null;
//===========================================  end of fwdc=====================================//

//    =================================== Introduction ============================================//
        public static final String IntroPREFERENCES = "intro_json";
            SharedPreferences introsharedpreferences;
            SharedPreferences.Editor introeditor;

//    ================================== end of introduction =======================================//

//================================================Socio Economic ===================================//
    public static final String SocioPREFERENCES = "socio_economic";
        SharedPreferences sociosharedpreferences;
        SharedPreferences.Editor socioeditor;
//    =================================== end of socio economic=====================================//

//    ================================== hamro sudur paschhim =========================================//

    public static final String SudurDevPREFERENCES = "hamro_sudurpaschhim";
    public static final String SudurAttractPREFERENCES = "sudur_attract";

    SharedPreferences sudurdevsharedpreferences, sudurattractsharedpreferences;
    SharedPreferences.Editor sudurdeveditor, sudurattracteditor;
//    ==================================== end of hamro sudur paschhim =================================//

//   ====================================== poltical parties and poltician =================================//
        public static final String PolticianPREFERENCES = "PolticianData";
            SharedPreferences polticiansharedpreferences;
            SharedPreferences.Editor polticianeditor;

        public static final String PartiesPREFERENCES = "poltical_parties_json";
            SharedPreferences partiessharedpreferences;
            SharedPreferences.Editor partieseditor;
//    ================================== end of poltical prties and poltician ================================//

//========================================================================================================================//

    TextView textView1, textView2, textView3, textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        //Check internet connection
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();


        textView3 = (TextView) findViewById(R.id.textView3);
        Typeface face3= Typeface.createFromAsset(getAssets(), "font/roboto_thin.ttf");
        textView3.setTypeface(face3);

        textView4 = (TextView) findViewById(R.id.textView4);
        Typeface face4= Typeface.createFromAsset(getAssets(), "font/roboto_thin.ttf");
        textView4.setTypeface(face4);

        downloadProgressBar = (ProgressBar) findViewById(R.id.splash_progress_bar);

//================================== shared preferences initialization ==============================================//

        //SharedPreferences-DEV_ACTIVITIES
        fwdcdevsharedpreferences = this.getSharedPreferences(FWDCDevPREFERENCES, Context.MODE_PRIVATE);
        fwdcdeveditor = fwdcdevsharedpreferences.edit();

        //SharedPreferences-About_FWDC
        aboutfwdcsharedpreferences = this.getSharedPreferences(AboutFWDCPREFERENCES, Context.MODE_PRIVATE);
        aboutfwdceditor = aboutfwdcsharedpreferences.edit();

        //introduction
        introsharedpreferences = getSharedPreferences(IntroPREFERENCES, Context.MODE_PRIVATE);
        introeditor = introsharedpreferences.edit();

        // socio economic status
        sociosharedpreferences = this.getSharedPreferences(SocioPREFERENCES, Context.MODE_PRIVATE);
        socioeditor = sociosharedpreferences.edit();

        //SharedPreferences-DEV_ACTIVITIES
        sudurdevsharedpreferences = this.getSharedPreferences(SudurDevPREFERENCES, Context.MODE_PRIVATE);
        sudurdeveditor = sudurdevsharedpreferences.edit();

        //SharedPreferences-DEV_ACTIVITIES
        sudurattractsharedpreferences = this.getSharedPreferences(SudurAttractPREFERENCES, Context.MODE_PRIVATE);
        sudurattracteditor = sudurattractsharedpreferences.edit();

        // poltician list
        polticiansharedpreferences = this.getSharedPreferences(PolticianPREFERENCES, Context.MODE_PRIVATE);
        polticianeditor = polticiansharedpreferences.edit();

        // parties list
        partiessharedpreferences = this.getSharedPreferences(PartiesPREFERENCES, Context.MODE_PRIVATE);
        partieseditor = partiessharedpreferences.edit();



//======================================================== end of shared preferences initialization ========================//


        Thread pause = new Thread() {
            public void run() {
                try {

                    RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.splash_background);
                    Animation relativeAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                    relativeLayout.startAnimation(relativeAnim);

                    TextView textView3 = (TextView) findViewById(R.id.textView3);
                    Animation textViewAnimate3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_slow);
                    textView3.startAnimation(textViewAnimate3);


                    if (networkInfo != null && networkInfo.isConnected()) {

                        downloadProgressBar.setMax(8);
                        downloadProgressBar.setProgress(1);

                        //==============about fwdc dev call====================//
                        convertDataToJson();
                        FWDCDEVApiCall fwdcdevApiCall = new FWDCDEVApiCall();
                        fwdcdevApiCall.execute();
                        downloadProgressBar.setProgress(2);

                        //==============about fwdc call====================//
                        convertDataToJson();
                        AboutFWDCApiCall aboutFWDCApiCall = new AboutFWDCApiCall();
                        aboutFWDCApiCall.execute();
                        downloadProgressBar.setProgress(3);

                        //=========== introduction Api call ==============
                        convertDataToJson();
                        IntroApiCall introapiCall = new IntroApiCall();
                        introapiCall.execute();
                        downloadProgressBar.setProgress(4);

                        //socio economc status
                        convertDataToJson();
                        SocioEconomicService restApi = new SocioEconomicService();
                        restApi.execute();
                        downloadProgressBar.setProgress(5);

                        //============= Sudur DEVELOPEMENT ACTIVITIES APICALL==================//
                        convertDataToJson();
                        SudurDevApiCall sudurDevApiCall = new SudurDevApiCall();
                        sudurDevApiCall.execute();
                        downloadProgressBar.setProgress(6);

                        //=============local attraction PLACES APICALL==================//
                        convertDataToJson();
                        AttrractionApiCall attractionapiCall = new AttrractionApiCall();
                        attractionapiCall.execute();
                        downloadProgressBar.setProgress(7);


                        //==================Poltical parties ===================//
                        convertDataToJson();
                        PoticalPartiesListService poticalPartiesListService = new PoticalPartiesListService();
                        poticalPartiesListService.execute();
                        downloadProgressBar.setProgress(8);

                        //================= polticial list ========================//
                        convertDataToJson();
                        PoticianListService poticianListService = new PoticianListService();
                        poticianListService.execute();


//                        Intent stuff = new Intent(SplashScreenActivity.this, MainActivity.class);
//                        startActivity(stuff);
                 sleep(15000);
                    }


//                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

//                else {
                        Intent stuff = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(stuff);
//                    }

                }
//                finish();
            }
        };
        pause.start();



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

    //About fwdc dev
    public class FWDCDEVApiCall extends AsyncTask<String, Void, String> {
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
                        text = POST(UrlClass.URL_DEV_ACTIVITIES);
                        Log.e("FWDCDEV ACTIVITIES", "Splash" + text.toString());
                        fwdcdeveditor.putString("dev_activities", text);
                        fwdcdeveditor.commit();


            } catch (Exception e) {
                return e.getLocalizedMessage();
            }

            return text;
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

//        private void counter(){
//            cur++
//                    if (cur == total){
//                        //itent
//                    }
//        }

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
                        text1 = POST(UrlClass.URL_ABOUT_FWDC);
                        Log.e("ABOUT FWDC ", "Splash" + text1.toString());
                        aboutfwdceditor.putString("fwdc_json", text1);
                        aboutfwdceditor.commit();



            } catch (Exception e) {
                return e.getLocalizedMessage();
            }

            return text1;
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //Log.e("ONPOSTEXECUTE", "ONPOST");

            if (result != null) {
//                //Success
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

    //Intro Apicall
    public class IntroApiCall extends AsyncTask<String, Void, String> {
        JSONArray data = null;
        String intro_title_en, intro_title_np, intro_desc_en = null, intro_area_en = null, intro_boundary_en = null,
                intro_desc_np = null, intro_area_np = null, intro_boundary_np = null;

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

            String text = null;
//            introeditor.clear();
            try {

                        text = POST(UrlClass.URL_INTRODUCTION);
                        Log.e("INTRODUCTION FWDC :", "Splash" + text.toString());
                        introeditor.putString("intro_json", text);
                        introeditor.commit();


            } catch (Exception e) {
                return e.getLocalizedMessage();
            }

            return text;
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //Log.e("ONPOSTEXECUTE", "ONPOST");
            if (result != null) {

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

    //socio economic status
    private class SocioEconomicService extends AsyncTask<String, Void, String> {
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

            try {
                    text = POST(UrlClass.URL_SOCIO_ECONOMIC);
                Log.e("FWDC SOCIO ECONOMIC :  ", "Splash" + text.toString());
                socioeditor.putString("socio_economic", text);
                    socioeditor.commit();


        } catch (Exception e) {
            return e.getLocalizedMessage();
        }

            return text;
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //Log.e("ONPOSTEXECUTE", "ONPOST");

            if (result != null) {
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

    // sudur dev
    public class SudurDevApiCall extends AsyncTask<String, Void, String> {
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
                        text = POST(UrlClass.URL_SUDURPASCHHIM_DEV_ACT);

                        Log.e("SUDURPASCHHIM DEV : ", "Splash" + text.toString());

                        sudurdeveditor.putString("hamro_sudurpaschhim", text);
                        sudurdeveditor.commit();


            } catch (Exception e) {
                return e.getLocalizedMessage();
            }

            return text;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //Log.e("ONPOSTEXECUTE", "ONPOST");

            if (result != null) {
                //Success
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

    // sudur attract
    public class AttrractionApiCall extends AsyncTask<String, Void, String> {
        JSONArray data1 = null;
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
            String text1 = "";

            try {
                        text1 = POST(UrlClass.URL_SUDURPASCHHIM_LOCAL_ATTR);

                        Log.e("SUDUR LOCAL ATTRACTION", "Splash" + text1.toString());
                        sudurattracteditor.putString("sudur_attract", text1);
                        sudurattracteditor.commit();


            } catch (Exception e) {
                return e.getLocalizedMessage();
            }

            return text1;
        }

        @Override
        protected void onPostExecute(String result) {

            //Log.e("ONPOSTEXECUTE", "ONPOST");
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

    // poltical parties
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

            try {

                    text = POST(UrlClass.URL_PARTY_LIST);
                Log.e("POLTICAL PARTIES ", "Splash" + text.toString());
                partieseditor.putString("poltical_parties_json", text);
                    partieseditor.commit();



            } catch (Exception e) {
                return e.getLocalizedMessage();
            }

            return text;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            if (result != null) {

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

    // poltician list
    private class PoticianListService extends AsyncTask<String, Void, String> {
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

            try {
                    text = POST(UrlClass.URL_POLTICIAN_LIST);
                Log.e("POLTICIAN LIST :  ", "Splash" + text.toString());
                polticianeditor.putString("PolticianData", text);
                    polticianeditor.commit();




            } catch (Exception e) {
                return e.getLocalizedMessage();
            }

            return text;
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //Log.e("ONPOSTEXECUTE", "ONPOST");
            if (result != null) {

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
