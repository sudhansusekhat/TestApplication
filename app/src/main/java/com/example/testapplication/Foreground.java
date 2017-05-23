package com.fydo.Config;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


import com.fydo.Login.DomainActivity;
import com.fydo.Login.UserAuthActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Foreground implements Application.ActivityLifecycleCallbacks {



    public interface Listener {

        public void onBecameForeground();

        public void onBecameBackground();

    }
    public static final long CHECK_DELAY = 500;
    public static Foreground instance;
    public boolean wasBackground,checkBackground;

    private boolean foreground = false, paused = true;
    private Handler handler = new Handler();
    private List<Listener> listeners = new CopyOnWriteArrayList<Listener>();
    private Runnable check;
    public static Foreground init(Application application){
        if (instance == null) {
            instance = new Foreground();
            application.registerActivityLifecycleCallbacks(instance);
        }
        return instance;
    }

    public static Foreground get(Application application){
        if (instance == null) {
            init(application);
        }
        return instance;
    }

    public static Foreground get(Context ctx){
        if (instance == null) {
            Context appCtx = ctx.getApplicationContext();
            if (appCtx instanceof Application) {
                init((Application)appCtx);
            }
            throw new IllegalStateException("Foreground is not initialised and cannot obtain the Application object");
        }
        return instance;
    }

    public static Foreground get(){
        if (instance == null) {
            throw new IllegalStateException("Foreground is not initialised - invoke at least once with parameterised init/get");
        }
        return instance;
    }

    public boolean isForeground(){
        return foreground;
    }

    public boolean isBackground(){
        return !foreground;
    }

    public void addListener(Listener listener){
        listeners.add(listener);
    }

    public void removeListener(Listener listener){
        listeners.remove(listener);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        paused = false;
        wasBackground = !foreground;
        foreground = true;
        if (wasBackground) {
            if (!CommonUtilities.fromCamera &&!activity.getClass().getName().equals("com.fydo.Login.DomainActivity") && !activity.getClass().getName().equals("com.fydo.Login.ForgotPasswordActivity") && !activity.getClass().getName().equals("com.fydo.Login.LoginActivity") && !activity.getClass().getName().equals("com.fydo.Login.UserAuthActivity") && !activity.getClass().getName().equals("com.fydo.Login.VeficationCodeActivity") && !activity.getClass().getName().equals("com.fydo.Activity.Splashscreen")) {
                Intent i = new Intent(activity, UserAuthActivity.class);
                i.putExtra("fromBackground", "fromBackground");
                activity.startActivity(i);
            } else {

            }

        }
    }
    @Override
    public void onActivityPaused(Activity activity) {
        paused = true;
        checkBackground = false;
        handler.postDelayed(check = new Runnable(){
            @Override
            public void run() {
                if (foreground && paused) {
                    foreground = false;
                    for (Listener l : listeners) {
                        try {
                            l.onBecameBackground();
                        } catch (Exception exc) {
                        }
                    }
                } else {
                }
            }
        }, CHECK_DELAY);

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(Activity activity) {}

    @Override
    public void onActivityStopped(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

    @Override
    public void onActivityDestroyed(Activity activity) {}






}