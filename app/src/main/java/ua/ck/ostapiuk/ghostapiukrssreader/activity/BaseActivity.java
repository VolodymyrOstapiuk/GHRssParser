package ua.ck.ostapiuk.ghostapiukrssreader.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import ua.ck.ostapiuk.ghostapiukrssreader.R;

public abstract class BaseActivity extends FragmentActivity {
    public boolean isDualPane() {
        return getResources().getBoolean(R.bool.isDualPane);
    }
}
