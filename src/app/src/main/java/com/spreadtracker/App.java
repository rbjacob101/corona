package com.spreadtracker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.spreadtracker.contactstracing.Calculator;
import com.spreadtracker.contactstracing.ContactTracer;
import com.spreadtracker.contactstracing.Database;
import com.spreadtracker.contactstracing.Person;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * The application class for the spread tracker app.
 */
public class App extends Application {
    /**
     * A globally accessible instance of the {@link App} class.
     */
    private static WeakReference<App> instance;
    public static App getInstance() {
        return instance.get();
    }
    public static Context getContext () {
        return instance.get();
    }
    private ContactTracer mTracer;

    public ContactTracer getContactTracer () { return mTracer; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = new WeakReference<>(this);
        mTracer = new ContactTracer(this); // Create new ContactTracer object to wrap model calculations
    }

    // Taken from:
    // https://stackoverflow.com/a/45364096/10149816
    public static Activity getActivity(Context context) {
        if (context == null) return null;
        if (context instanceof Activity) return (Activity) context;
        if (context instanceof ContextWrapper) return getActivity(((ContextWrapper)context).getBaseContext());
        return null;
    }
}
