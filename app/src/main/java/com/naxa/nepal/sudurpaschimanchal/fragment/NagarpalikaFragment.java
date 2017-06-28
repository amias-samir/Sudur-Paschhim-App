package com.naxa.nepal.sudurpaschimanchal.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.activities.HamroSudurPaschimActivity;
import com.naxa.nepal.sudurpaschimanchal.activities.NagarBudgetActivity;
import com.naxa.nepal.sudurpaschimanchal.adapter.NagarpalikaBudget_Adapter;
import com.naxa.nepal.sudurpaschimanchal.model.District;
import com.naxa.nepal.sudurpaschimanchal.model.NagarpalikaBudget_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NagarpalikaFragment extends Fragment {

    public static final String MyPREFERENCES = "nagar_budget";
    SharedPreferences sharedpreferences;
    private boolean setData;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ImageView imageView;

    String district_id_fromactivity = "0";

    NagarpalikaBudget_Adapter ca;
    public static List<NagarpalikaBudget_Model> resultCur = new ArrayList<>();
    public static List<NagarpalikaBudget_Model> filteredList = new ArrayList<>();

    String text = null;
    JSONArray data = null;
    String districtToFilter = "all is well";
    private List<District> FilteredBudgetWithCurrentDistrict;


    public NagarpalikaFragment() {
        // Required empty public constructor

    }


    public void setDistrictToFilter(String districtToFilter) {

        this.districtToFilter = districtToFilter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_nagarpalika, container, false);


//        district_id_fromactivity = "2";

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.nagarList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        //   createList();


        FilteredBudgetWithCurrentDistrict = new ArrayList<>();

        try {
            populateBudgetAsync(districtToFilter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ((NagarBudgetActivity) getActivity()).setFragmentRefreshListener(new HamroSudurPaschimActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {

                Log.e("NagarFragment", "onCreateView: districtID ::: " + district_id_fromactivity);
                district_id_fromactivity = HamroSudurPaschimActivity.dist_spinner_id;
                // Refresh Your Fragment
                createList();


            }
        });

        return rootView;
    }

    public void createList() {
        resultCur.clear();
        jsonParse();
        fillTable();
    }


    private void populateBudgetAsync(final String distictNameEng) throws JSONException {
        String text = sharedpreferences.getString("nagar_budget", "");
        JSONObject nagarBugdgetJSON = new JSONObject(text);

        final JSONArray data = nagarBugdgetJSON.getJSONArray("data");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < data.length(); i++) {
                    try {
                        JSONObject row = data.getJSONObject(i);
                        String EngDistrictName = row.getString("district_name_en");
                        String NepDistrictName = row.getString("district_name_np");

                        Boolean shouldThisDistBeAdded = EngDistrictName.equalsIgnoreCase(distictNameEng);

                        if (shouldThisDistBeAdded) {
                            District district = new District(String.valueOf(i), EngDistrictName, NepDistrictName);
                            FilteredBudgetWithCurrentDistrict.add(district);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                Log.d("Aasish", FilteredBudgetWithCurrentDistrict.size() + " is the size");


                //todo call recycle view here


            }
        };
        new Thread(runnable).start();
    }


    public void jsonParse() {

        String district_id = null, dev_title_en = null, dev_title_np = null,
                dev_desc_en = null, dev_desc_np = null, dev_contractor_en = null,
                dev_contractor_np = null, dev_budget = null, district_name_en = null,
                district_name_np = null;

        JSONObject jsonObj = null;
        try {

            text = sharedpreferences.getString("nagar_budget", "");
            if (text != null) {
                Log.e("NagarBudget_JSON", "" + text.toString());
                jsonObj = new JSONObject(text);

                data = jsonObj.getJSONArray("data");

                Log.e("DATA", "" + data.toString());
                for (int i = 0; i < data.length(); i++) {
                    JSONObject c = data.getJSONObject(i);
                    district_id = c.getString("district_id");
                    Log.e("Nagar List", "" + district_id.toString());

                    if (district_id_fromactivity.equals("0")) {
                        NagarpalikaBudget_Model newData = new NagarpalikaBudget_Model();
//                newData.set(c.getString("dev_status_id"));
                        newData.setDistrict_id(c.getString("district_id"));
                        newData.setNagar_title_en(c.getString("nagar_gau_palika"));
                        newData.setNagar_title_np(c.getString("nagar_gau_palika_np"));
                        newData.setNagar_budget_amount_en(c.getString("budget"));
                        newData.setNagar_budget_amount_np(c.getString("budget_np"));

                        resultCur.add(newData);
                        Log.e("Nagarpalika Budget Model :", "" + newData.toString());

                    } else if (district_id.equals(district_id_fromactivity)) {
                        NagarpalikaBudget_Model newData = new NagarpalikaBudget_Model();
//                newData.set(c.getString("dev_status_id"));
                        newData.setDistrict_id(c.getString("district_id"));
                        newData.setNagar_title_en(c.getString("nagar_gau_palika"));
                        newData.setNagar_title_np(c.getString("nagar_gau_palika_np"));
                        newData.setNagar_budget_amount_en(c.getString("budget"));
                        newData.setNagar_budget_amount_np(c.getString("budget_np"));

                        resultCur.add(newData);
                        Log.e("Nagarpalika Budget Model :", "" + newData.toString());

                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void fillTable() {

        filteredList = resultCur;
        ca = new NagarpalikaBudget_Adapter(getActivity(), filteredList);
        recyclerView.setAdapter(ca);

    }

}
