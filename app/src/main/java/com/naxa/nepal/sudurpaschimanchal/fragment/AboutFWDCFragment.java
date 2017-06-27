package com.naxa.nepal.sudurpaschimanchal.fragment;

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

import com.naxa.nepal.sudurpaschimanchal.R;

import at.blogc.android.views.ExpandableTextView;

/**
 * Created by Nishon Tandukar on 27 Jun 2017 .
 *
 * @email nishon.tan@gmail.com
 */

public class AboutFWDCFragment extends Fragment {


    private static final String TAG = "AboutFWDCFragment";
    private ExpandableListView expListView;

    TextView tvTitle;
    ImageView ivImageView;

    ExpandableTextView tvDesc ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fwdc_about, container, false);

        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

        tvTitle = (TextView) rootView.findViewById(R.id.textView_label_about_fwdc);
        tvDesc = (ExpandableTextView) rootView.findViewById(R.id.textView_about_fwdc);
        ivImageView = (ImageView) rootView.findViewById(R.id.backdrop);
        final Button buttonToggle = (Button) rootView.findViewById(R.id.button_toggle);
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



        return rootView;
    }
}
