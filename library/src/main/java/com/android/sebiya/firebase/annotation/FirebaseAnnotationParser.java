package com.android.sebiya.firebase.annotation;

import android.os.Bundle;

import java.lang.reflect.Field;

public class FirebaseAnnotationParser {
    public static String getScreenId(Object obj) {
        FirebaseScreen firebaseScreen =  obj.getClass().getAnnotation(FirebaseScreen.class);
        if (firebaseScreen != null) {
            return firebaseScreen.value();
        }
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        if (declaredFields == null) {
            return null;
        }
        int length = declaredFields.length;
        int i = 0;
        while (i < length) {
            Field field = declaredFields[i];
            if ((field.getAnnotation(FirebaseScreen.class)) != null) {
                try {
                    return (String) field.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                i++;
            }
        }
        return null;
    }

    public static String getEvent(Object obj) {
        FirebaseEvent firebaseEvent = obj.getClass().getAnnotation(FirebaseEvent.class);
        return firebaseEvent != null ? firebaseEvent.value() : null;
    }

    public static Bundle getParam(Object obj) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        if (declaredFields == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        for (Field field : declaredFields) {
            FirebaseParam firebaseParam =  field.getAnnotation(FirebaseParam.class);
            if (firebaseParam != null) {
                try {
                    bundle.putString(firebaseParam.value(), (String) field.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bundle;
    }
}