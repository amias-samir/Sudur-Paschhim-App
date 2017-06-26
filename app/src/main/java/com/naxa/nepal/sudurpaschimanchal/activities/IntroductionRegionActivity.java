package com.naxa.nepal.sudurpaschimanchal.activities;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.adapter.IntroductionRegionAdapter;
import com.naxa.nepal.sudurpaschimanchal.model.IntroductionRegionModel;

import java.util.ArrayList;
import java.util.List;

public class IntroductionRegionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IntroductionRegionAdapter adapter;
    private List<IntroductionRegionModel> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_region);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("बिकाश क्षेत्र को परिचय");
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_back_icon);
        upArrow.setColorFilter(getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);



        recyclerView = (RecyclerView) findViewById(R.id.itemList);

        albumList = new ArrayList<>();
        adapter = new IntroductionRegionAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        prepareAlbums();



    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {

        IntroductionRegionModel a = new IntroductionRegionModel("True Romance", "");
        albumList.add(a);

        a = new IntroductionRegionModel("Xscpae", "");
        albumList.add(a);

        a = new IntroductionRegionModel("Xscpae", "");
        albumList.add(a);

        a = new IntroductionRegionModel("Xscpae", "");
        albumList.add(a);

        a = new IntroductionRegionModel("Xscpae", "");
        albumList.add(a);

        a = new IntroductionRegionModel("Xscpae", "");
        albumList.add(a);

        a = new IntroductionRegionModel("Xscpae", "");
        albumList.add(a);

        a = new IntroductionRegionModel("Xscpae", "");
        albumList.add(a);

        a = new IntroductionRegionModel("Xscpae", "");
        albumList.add(a);

        a = new IntroductionRegionModel("Xscpae", "");
        albumList.add(a);

        a = new IntroductionRegionModel("Xscpae", "");
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}



