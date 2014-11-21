package ua.ck.ostapiuk.ghostapiukrssreader.activity;

import android.app.Activity;
import android.os.AsyncTask;

import ua.ck.ostapiuk.ghostapiukrssreader.R;

public abstract class BaseActivity extends Activity
{
    public boolean isDualPane() {
        return getResources().getBoolean(R.bool.isDualPane);
    }
}
