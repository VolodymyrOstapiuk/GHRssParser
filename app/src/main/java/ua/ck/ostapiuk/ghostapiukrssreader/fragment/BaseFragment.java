package ua.ck.ostapiuk.ghostapiukrssreader.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import ua.ck.ostapiuk.ghostapiukrssreader.R;


/**
 * Created by Vova on 07.11.2014.
 */
public abstract class BaseFragment extends Fragment {
     public abstract void refresh(Bundle args);
 }
