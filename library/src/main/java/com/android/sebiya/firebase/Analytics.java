package com.android.sebiya.firebase;

import android.app.Activity;
import android.os.Bundle;

public interface Analytics {
    boolean isUserAgreed();
    void setCurrentScreen(Activity activity, String a, String b);
    void logEvent(String event, Bundle param);
}
