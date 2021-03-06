package com.naxa.nepal.sudurpaschimanchal.Tasks;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.naxa.nepal.sudurpaschimanchal.Interface.OnDistrictTaskCompleted;
import com.naxa.nepal.sudurpaschimanchal.activities.AskForHelpActivity;
import com.naxa.nepal.sudurpaschimanchal.model.UrlClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * gets the list of district from the server
 */

public class DistrictTask extends AsyncTask<String, Void, String> {

    String TAG = "DistrictTask";
    ProgressDialog districtProgress;
    private String jsonToSend;
    String response = null;
    private OnDistrictTaskCompleted listener;

    public DistrictTask(OnDistrictTaskCompleted listener){
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        convertDataToJson();
    }

    @Override
    protected String doInBackground(String... params) {

        response = POST(UrlClass.URL_PLACES_TYPE_LIST);
        Log.d(TAG, "RAW resposne"+response);
        return response.toString();
    }


    @Override
    protected void onPostExecute(String result) {

//        districtProgress.dismiss();

        JSONObject jsonObject = null;
        String dataSentStatus = "";

        try {
            jsonObject = new JSONObject(result);
            dataSentStatus = jsonObject.getString("code");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (dataSentStatus.equals("1")){
            //our task has been completed :)
        }

        listener.onTaskCompleted(result);
    }


    /**
     * Send post requests
     * @param urll
     * @return
     */
    public String POST(String urll) {
        String result = "";
        URL url;

        try {
            url = new URL(urll);
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
                    result += line;
                }
            } else {
                result = "";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    // data convert
    public void convertDataToJson() {
        try {
            JSONObject header = new JSONObject();
            header.put("token", "bf5d483811");
            jsonToSend = header.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
