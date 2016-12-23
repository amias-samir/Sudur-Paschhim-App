package com.naxa.nepal.sudurpaschimanchal;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.naxa.nepal.sudurpaschimanchal.activities.AboutFWDCActivity;
import com.naxa.nepal.sudurpaschimanchal.fragment.HomeFragment;
import com.naxa.nepal.sudurpaschimanchal.fragment.MapFragment;
import com.naxa.nepal.sudurpaschimanchal.fragment.NewsAndEventsFragment;
import com.naxa.nepal.sudurpaschimanchal.fragment.Video_Fragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;

    android.app.FragmentTransaction fragmentTransaction;
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.video_list,
            R.drawable.newsfeed,
            R.drawable.ic_map,
    };
    ViewPagerAdapter adapter;

//    DrawerLayout drawerLayout;
//    ActionBarDrawerToggle drawerToggle;
//    NavigationView navigation;

    private String[] mFragmentTitleList = {
            "गृह पृष्ठ"
            , "भिडियो"
            , "समाचार तथा कार्यक्रमहरू"
            , "नक्सा"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();

        //to change page title on viewpager scroll
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                toolbar.setTitle(mFragmentTitleList[position]);
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //-------------back button navigation--------------//
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
        });


    }


    //initiliaze the required UI elements
    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("गृह पृष्ठ");
        toolbar.canShowOverflowMenu();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
//        setupNavDrawer();
        changeTabColor();


    }

//    private void setupNavDrawer(){
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_list_activity);
//        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_homepage_name, R.string.app_homepage_name);
//        drawerLayout.addDrawerListener(drawerToggle);
//
//
//        navigation = (NavigationView) findViewById(R.id.navigation_view);
//        View header =se navigation.getHeaderView(0);
//        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                // int id = menuItem.getItemId();
//
//                selectDrawerItem(menuItem);
//
//                return false;
//            }
//        });
//    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_item_home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_item_message:
                fragmentClass = AboutFWDCActivity.class;
                break;
            case R.id.nav_item_settings:
                fragmentClass = AboutFWDCActivity.class;
                break;
            case R.id.nav_item_about_us:
                fragmentClass = AboutFWDCActivity.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.containerView, fragment).commit();
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
//        drawerLayout.closeDrawers();
    }


    private void changeTabColor() {


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                tab.getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.accent), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tabIconColor), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //binds the fragment and the icons together
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
//        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
//        tabLayout.getTabAt(5).setIcon(tabIcons[5]);
//        tabLayout.getTabAt(6).setIcon(tabIcons[6]);
//        tabLayout.getTabAt(7).setIcon(tabIcons[7]);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new Video_Fragment(), "Videos");
        adapter.addFragment(new NewsAndEventsFragment(), "News and Events");
        adapter.addFragment(new MapFragment(), "Map");
//        adapter.addFragment(new SociaEconomicFragment(), "Socio Economic Status");
//        adapter.addFragment(new MapFragment(), "Map");
//        adapter.addFragment(new FragmentNewsEvents(), "News and Events");
//        adapter.addFragment(new AddOfficeFragment(), "Add Office");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        //contains the fragments objects
        private final List<Fragment> mFragmentList = new ArrayList<>();
        //contains the list of title of the fragments
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

            return null;
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_report_problem_24dp)
                .setTitle("Exit From App")
                .setMessage("Are you sure you want to Exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.onBackPressed();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            finishAffinity();
                        } else {
                            finish();
                        }
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }

}
