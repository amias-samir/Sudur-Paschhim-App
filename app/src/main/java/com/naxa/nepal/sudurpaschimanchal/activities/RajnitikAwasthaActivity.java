package com.naxa.nepal.sudurpaschimanchal.activities;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.Utils.ChartColor;
import com.naxa.nepal.sudurpaschimanchal.fragment.PoliticalPartiesFragment;
import com.naxa.nepal.sudurpaschimanchal.fragment.PolticianListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Susan on 10/27/2016.
 */
public class RajnitikAwasthaActivity  extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private PieChart mChart;

    PieChart pieChart;

    private static String stat_name = "राजनीतिक अवस्था";

    public static final String EXTRA_NAME = "cheese_name";

    //=====================================Half pie chart ====================================//
    protected String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    protected Typeface mTfRegular;
    protected Typeface mTfLight;

    //=================================end of half pie chart ==================================//


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


        ScrollView scroll = (ScrollView) findViewById(R.id.scrollView);
        scroll.setFocusableInTouchMode(true);
        scroll.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        //=======================================================Half pie chart=========================================//

//        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
//        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");



        //====================================================end of half pie chart oncreate=============================//



//=================================Pie Chart Draw==============================//

        pieChart = (PieChart) findViewById(R.id.chart1);

        pieChart.setCenterText(stat_name);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(75f, 0));
        entries.add(new Entry(50f, 1));
        entries.add(new Entry(55f, 2));
        entries.add(new Entry(53f, 3));
        entries.add(new Entry(25f, 4));
        entries.add(new Entry(15f, 5));
        entries.add(new Entry(25f, 6));
        entries.add(new Entry(30f, 7));
        entries.add(new Entry(25f, 8));

        PieDataSet dataset = new PieDataSet(entries, stat_name);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("एमाले");
        labels.add("काँग्रेस");
        labels.add("माओवादी");
        labels.add("फोरम");
        labels.add("जनमोर्चा");
        labels.add("माले");
        labels.add("चुरे भावोर");
        labels.add("राप्रपा");
        labels.add("नेमकिपा");

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ChartColor.COLORFUL_COLORS); //
        pieChart.setDescription("विवरण");
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
        rajnitik_bibaran.setText("सुदूरपश्चिमका ९ जिल्लाको एकै प्रदेश हुनुपर्ने यदी त्यसो नभए आफ्नो पार्टीभित्र पनि संर्घष गरेर अघि बढ्ने पूर्वमन्त्री एवम् एकीकृत माओवादीका पोलिटव्युरो सदस्य लेखराज भटट्ले बताएका छन् ।");


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        final SharedPreferences wmbPreference = PreferenceManager
//                .getDefaultSharedPreferences(getActivity());

        adapter.addFragment(new PolticianListFragment(), "राजनीतिक \n व्यक्तिहरू");
        adapter.addFragment(new PoliticalPartiesFragment(),"राजनीतिक \n पार्टीहरु");
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


    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }
}
