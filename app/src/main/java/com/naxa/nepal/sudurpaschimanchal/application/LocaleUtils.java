package com.naxa.nepal.sudurpaschimanchal.application;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.view.ContextThemeWrapper;

import java.util.Locale;

/**
 * Created by Nishon Tandukar on 30 Jun 2017 .
 *
 * @email nishon.tan@gmail.com
 */

public class LocaleUtils {

    private static String TAG = "LocaleUtils";

    private static Locale currentLocale;

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        Log.i(TAG, " Locale is set to outer of loop " + locale.getDisplayLanguage());

        if (currentLocale != null) {
            Log.i(TAG, " Locale is set to " + locale.getDisplayCountry(locale));
            Locale.setDefault(currentLocale);
        }
    }

    public static void updateConfig(ContextThemeWrapper wrapper) {
        if (currentLocale != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Configuration configuration = new Configuration();
            configuration.setLocale(currentLocale);
            wrapper.applyOverrideConfiguration(configuration);
        }
    }

    public static void updateConfig(Application app, Configuration configuration) {
        if (currentLocale != null && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //Wrapping the configuration to avoid Activity endless loop
            Configuration config = new Configuration(configuration);
            // We must use the now-deprecated config.locale and res.updateConfiguration here,
            // because the replacements aren't available till API level 24 and 17 respectively.
            config.locale = currentLocale;
            Resources res = app.getBaseContext().getResources();
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
    }
}
