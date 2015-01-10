package ua.ck.ostapiuk.ghostapiukrssreader.util.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;


public class ConnectionChecker {
    public boolean isConnectionAvailable(Fragment fragment) {
        ConnectivityManager manager =
                (ConnectivityManager) fragment.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
