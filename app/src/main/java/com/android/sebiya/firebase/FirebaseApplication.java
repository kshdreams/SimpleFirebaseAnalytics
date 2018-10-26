package com.android.sebiya.firebase;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SimpleFirebaseAnalytics.init(this, new Analytics() {
            @Override
            public boolean isUserAgreed() {
                // TODO : better to show dialog to agree to send event log
                return true;
            }

            @Override
            public void setCurrentScreen(Activity activity, String var1, String var2) {
                FirebaseAnalytics.getInstance(activity).setCurrentScreen(activity, var1, var2);
            }

            @Override
            public void logEvent(String event, Bundle param) {
                FirebaseAnalytics.getInstance(FirebaseApplication.this).logEvent(event, param);
            }
        });
    }
}
