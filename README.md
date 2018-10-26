# SimpleFirebaseAnalytics
Simplified send firebase event for Android.

[![](https://jitpack.io/v/kshdreams/SimpleFirebaseAnalytics.svg)](https://jitpack.io/#kshdreams/SimpleFirebaseAnalytics)

##### GETTING STARTED
SimpleFirebaseAnalytics releases are available via JitPack.
```ruby
// Project level build.gradle
// ...
repositories {
    maven { url 'https://jitpack.io' }
}
// ...

// Module level build.gradle
dependencies {
    // Replace version with release version, e.g. 1.0.0-alpha, -SNAPSHOT
    implementation "com.github.kshdreams:SimpleFirebaseAnalytics:[VERSION]"
}
```


###### USAGE
call SimpleFirebaseAnalytics.init() method on your Application.onCreate() method
```Java
public class YourApplication extends Application {

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
```

just define @FirebaseScreen annotation to Activity, Fragment class instead of call FirebaseAnalytics.setCurrentScreen()

```Java
@FirebaseScreen("main_screen")
public class MainActivity extends AppCompatActivity {
}

@FirebaseScreen("main_fragment")
public class MainFragment extends Fragmet {
}
```


just define @FirebaseEvent, @FirebaseParam on your data(pojo) class,
```Java
@FirebaseEvent("click_event")
public class ClickEvent {

    @FirebaseParam("view")
    public String view;

    @FirebaseParam("extra")
    public String extra;
```

and send firebase event using object.
```Java
    SimpleFirebaseAnalytics.sendEvent(ClickEvent.create("snackbar", "some extra here"));
```

