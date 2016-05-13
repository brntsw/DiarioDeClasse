package br.gov.sp.educacao.sed.mobile.Action;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by techresult on 10/05/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentUpdate = new Intent(context, UpdateDBService.class);
        context.startService(intentUpdate);
    }
}
