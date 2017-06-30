package com.naxa.nepal.sudurpaschimanchal.application;

import android.app.Application;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by Nishon Tandukar on 30 Jun 2017 .
 *
 * @email nishon.tan@gmail.com
 */

public class HamroSudurpaschim extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setNepaliLocale();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.updateConfig(this, newConfig);
    }


    private void setNepaliLocale() {

        LocaleUtils.setLocale(new Locale("ne"));
        LocaleUtils.updateConfig(this, getBaseContext().getResources().getConfiguration());

    }
}
