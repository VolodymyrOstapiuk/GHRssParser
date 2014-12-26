package ua.ck.ostapiuk.ghostapiukrssreader.util.connection;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectionChecker {
    public boolean isConnectionAvailable(Fragment fragment) {
        ConnectivityManager manager =
                (ConnectivityManager) fragment.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
