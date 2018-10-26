package com.android.sebiya.firebase.event;


import com.android.sebiya.firebase.annotation.FirebaseEvent;
import com.android.sebiya.firebase.annotation.FirebaseParam;

@FirebaseEvent("click_event")
public class ClickEvent {

    @FirebaseParam("view")
    public String view;

    @FirebaseParam("extra")
    public String extra;

    public static ClickEvent create(String view, String extra) {
        ClickEvent event = new ClickEvent();
        event.view = view;
        event.extra = extra;
        return event;
    }
}
