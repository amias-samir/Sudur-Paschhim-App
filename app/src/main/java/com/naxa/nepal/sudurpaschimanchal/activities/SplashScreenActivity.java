package com.naxa.nepal.sudurpaschimanchal.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.naxa.nepal.sudurpaschimanchal.MainActivity;
import com.naxa.nepal.sudurpaschimanchal.R;

/**
 * Created by Susan on 5/31/2016.
 */
public class SplashScreenActivity extends Activity {

    TextView textView1, textView2, textView3, textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        textView1 = (TextView) findViewById(R.id.textView);
//        Typeface face1= Typeface.createFromAsset(getAssets(), "font/roboto_thin.ttf");
//        textView1.setTypeface(face1);
//
//        textView2 = (TextView) findViewById(R.id.textView2);
//        Typeface face2= Typeface.createFromAsset(getAssets(), "font/roboto_thin.ttf");
//        textView2.setTypeface(face2);

        textView3 = (TextView) findViewById(R.id.textView3);
        Typeface face3= Typeface.createFromAsset(getAssets(), "font/roboto_thin.ttf");
        textView3.setTypeface(face3);

        textView4 = (TextView) findViewById(R.id.textView4);
        Typeface face4= Typeface.createFromAsset(getAssets(), "font/roboto_thin.ttf");
        textView4.setTypeface(face4);

        Thread pause = new Thread() {
            public void run() {
                try {

                    RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.splash_background);
                    Animation relativeAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                    relativeLayout.startAnimation(relativeAnim);

//                    TextView textView = (TextView) findViewById(R.id.textView);
//                    Animation textViewAnimate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_slow);
//                    textView.startAnimation(textViewAnimate);

//                    TextView textView2 = (TextView) findViewById(R.id.textView2);
//                    Animation textViewAnimate2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_slow);
//                    textView2.startAnimation(textViewAnimate2);

                    TextView textView3 = (TextView) findViewById(R.id.textView3);
                    Animation textViewAnimate3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_slow);
                    textView3.startAnimation(textViewAnimate3);

                    sleep(2000);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent stuff = new Intent(SplashScreenActivity.this,MainActivity.class);
                    startActivity(stuff);
                }
                finish();
            }
        };
        pause.start();

    }

}
