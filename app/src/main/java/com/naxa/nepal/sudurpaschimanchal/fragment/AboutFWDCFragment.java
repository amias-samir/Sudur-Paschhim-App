package com.naxa.nepal.sudurpaschimanchal.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.activities.GathanAadeshPdfActivity;
import com.naxa.nepal.sudurpaschimanchal.adapter.ExpandableListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;

/**
 * Created by Nishon Tandukar on 27 Jun 2017 .
 *
 * @email nishon.tan@gmail.com
 */

public class AboutFWDCFragment extends Fragment {


    private static final String TAG = "AboutFWDCFragment";
    private ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> mission = new ArrayList<String>();
    List<String> vision = new ArrayList<String>();
    List<String> objective = new ArrayList<String>();
    List<String> main_works = new ArrayList<String>();
    List<String> gathan_aadesh = new ArrayList<String>();
    ExpandableListAdapter listAdapter;


    TextView tvTitle;
    ImageView ivImageView;

    ExpandableTextView tvDesc;
    private Button buttonToggle;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fwdc_about, container, false);

        initUI(rootView);
        prepareListData();
        try {
            setFWDCDesc();
        } catch (JSONException e) {

            Log.e("shit", " " + e.toString());
        }
        setExpListView();

        return rootView;
    }

    private void initUI(View rootView) {

        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

        tvTitle = (TextView) rootView.findViewById(R.id.textView_label_about_fwdc);
        tvDesc = (ExpandableTextView) rootView.findViewById(R.id.textView_about_fwdc);
        ivImageView = (ImageView) rootView.findViewById(R.id.backdrop);
        buttonToggle = (Button) rootView.findViewById(R.id.button_toggle);

    }


    private void setExpListView() {

        tvDesc.setAnimationDuration(750L);

        // set interpolators for both expanding and collapsing animations
        tvDesc.setInterpolator(new OvershootInterpolator());

        // or set them separately
        tvDesc.setExpandInterpolator(new OvershootInterpolator());
        tvDesc.setCollapseInterpolator(new OvershootInterpolator());

        // toggle the ExpandableTextView
        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(final View v) {
                tvDesc.toggle();
                buttonToggle.setText(tvDesc.isExpanded() ? R.string.collapse : R.string.expand);
            }
        });

        // but, you can also do the checks yourself
        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (tvDesc.isExpanded()) {
                    tvDesc.collapse();
                    buttonToggle.setText(R.string.expand);
                } else {
                    tvDesc.expand();
                    buttonToggle.setText(R.string.collapse);
                }
            }
        });

        // listen for expand / collapse events
        tvDesc.setOnExpandListener(new ExpandableTextView.OnExpandListener() {
            @Override
            public void onExpand(final ExpandableTextView view) {
                Log.d(TAG, "ExpandableTextView expanded");
            }

            @Override
            public void onCollapse(final ExpandableTextView view) {
                Log.d(TAG, "ExpandableTextView collapsed");
            }
        });


        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                if (groupPosition == 4) {
                    switch (childPosition) {
                        case 0:
                            Toast.makeText(getActivity().getApplicationContext(), groupPosition + "," + childPosition + ":" + "गठन आदेश", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), GathanAadeshPdfActivity.class);
                            startActivity(intent);
                            break;
                    }

                }

                return false;
            }
        });

    }


    private void setFWDCDesc() throws JSONException {
        SharedPreferences sharedpreferences;
        sharedpreferences = getActivity().getSharedPreferences("fwdc_json", Context.MODE_PRIVATE);
        String text = sharedpreferences.getString("fwdc_json", "");
        JSONObject fwdcDescJSON = new JSONObject(text);
        JSONArray jsonArray = fwdcDescJSON.getJSONArray("data");
        JSONObject jsonObject = jsonArray.getJSONObject(0);



        String fwdcDescTitle = jsonObject.getString("title_np");
        String fwdcDescDetail = jsonObject.getString("desc_np");

        tvDesc.setText(fwdcDescDetail);
        tvTitle.setText(fwdcDescTitle);
    }


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
