package br.gov.sp.educacao.sed.mobile.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by techresult on 17/07/2015.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    public static String REDE_MODIFICADA = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = NetworkUtils.getConnectivityStatus(context);

        if(status == NetworkUtils.TYPE_WIFI){
            Log.d("NetworkChange", "WiFi");
        }
        else if(status == NetworkUtils.TYPE_2G){
            Log.d("NetworkChange", "2G");
        }
        else if(status == NetworkUtils.TYPE_3G){
            Log.d("NetworkChange", "3G");
        }
        else if(status == NetworkUtils.TYPE_4G) {
            Log.d("NetworkChange", "4G");
        }
        else{
            Log.d("NetworkChange", "Nao conectado");
        }
    }
}
