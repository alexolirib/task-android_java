package com.alexolirib.tasks.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtils {

    public static Boolean isConnectionAvailable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetwork() != null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
