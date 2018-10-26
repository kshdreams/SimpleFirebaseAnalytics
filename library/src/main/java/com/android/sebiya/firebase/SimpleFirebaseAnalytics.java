package com.android.sebiya.firebase;


import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.FragmentManager.FragmentLifecycleCallbacks;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.sebiya.firebase.annotation.FirebaseAnnotationParser;


public class SimpleFirebaseAnalytics {

    private static final String LOG_TAG = "SimpleFirebaseAnalytics";

    private static final boolean ENABLE_DEBUG_LOG = BuildConfig.DEBUG;

    private static Analytics sAnalytics;

    public static void init(@NonNull Application application, @NonNull Analytics analytics) {
        if (sAnalytics == null) {
            sAnalytics = analytics;
            application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                private FragmentLifecycleCallbacks mFragmentLifecycleCallbacks;
                private FragmentManager.FragmentLifecycleCallbacks mSupportFragmentLifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
                    public void onFragmentResumed(FragmentManager fragmentManager, Fragment fragment) {
                        super.onFragmentResumed(fragmentManager, fragment);
                        SimpleFirebaseAnalytics.setCurrentScreen(fragment.getActivity(), fragment);
                    }
                };

                public void onActivityPaused(Activity activity) {
                }

                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                public void onActivityStarted(Activity activity) {
                }

                public void onActivityStopped(Activity activity) {
                }

                public void onActivityCreated(Activity activity, Bundle bundle) {
                    if (activity instanceof AppCompatActivity) {
                        ((AppCompatActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(this.mSupportFragmentLifecycleCallbacks, true);
                    } else if (VERSION.SDK_INT >= 26) {
                        this.mFragmentLifecycleCallbacks = new FragmentLifecycleCallbacks() {
                            public void onFragmentResumed(android.app.FragmentManager fragmentManager, android.app.Fragment fragment) {
                                super.onFragmentResumed(fragmentManager, fragment);
                                SimpleFirebaseAnalytics.setCurrentScreen(fragment.getActivity(), fragment);
                            }
                        };
                        activity.getFragmentManager().registerFragmentLifecycleCallbacks(this.mFragmentLifecycleCallbacks, true);
                    }
                }

                public void onActivityResumed(Activity activity) {
                    SimpleFirebaseAnalytics.setCurrentScreen(activity, activity);
                }

                public void onActivityDestroyed(Activity activity) {
                    if (activity instanceof AppCompatActivity) {
                        ((AppCompatActivity) activity).getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(this.mSupportFragmentLifecycleCallbacks);
                    } else if (VERSION.SDK_INT >= 26) {
                        activity.getFragmentManager().unregisterFragmentLifecycleCallbacks(this.mFragmentLifecycleCallbacks);
                    }
                }
            });
        } else {
            Log.e(LOG_TAG, "init. already init called. so ignore this init method");
        }
    }

    public static void setCurrentScreen(Activity activity, Object obj) {
        if (!sAnalytics.isUserAgreed()) {
            return;
        }
        String screenId = FirebaseAnnotationParser.getScreenId(obj);
        if (screenId != null) {
            sAnalytics.setCurrentScreen(activity, screenId, obj.getClass().getSimpleName());
            if (ENABLE_DEBUG_LOG) {
                String str = LOG_TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setCurrentScreen. screenId - ");
                stringBuilder.append(screenId);
                stringBuilder.append(", obj - ");
                stringBuilder.append(obj);
                Log.d(str, stringBuilder.toString());
            }

        }
    }

    public static void sendEvent(Object obj) {
        if (!sAnalytics.isUserAgreed()) {
            return;
        }
        String event = FirebaseAnnotationParser.getEvent(obj);
        if (event != null) {
            Bundle param = FirebaseAnnotationParser.getParam(obj);
            if (param != null) {
                sAnalytics.logEvent(event, param);
                if (ENABLE_DEBUG_LOG) {
                    String str = LOG_TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("sendEvent. event - ");
                    stringBuilder.append(event);
                    stringBuilder.append(", param - ");
                    stringBuilder.append(param);
                    Log.d(str, stringBuilder.toString());
                }
            }
        }
    }

    public static void sendEvent(String event, Object paramObj) {
        if (!sAnalytics.isUserAgreed()) {
            return;
        }
        Bundle param = FirebaseAnnotationParser.getParam(paramObj);
        if (param != null) {
            sAnalytics.logEvent(event, param);
            if (ENABLE_DEBUG_LOG) {
                String str = LOG_TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("sendEvent. event - ");
                stringBuilder.append(event);
                stringBuilder.append(", param - ");
                stringBuilder.append(param);
                Log.d(str, stringBuilder.toString());
            }
        }
    }
}