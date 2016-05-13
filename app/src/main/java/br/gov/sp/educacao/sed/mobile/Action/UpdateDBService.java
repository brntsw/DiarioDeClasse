package br.gov.sp.educacao.sed.mobile.Action;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by afirmanet on 17/04/16.
 */
public class UpdateDBService extends IntentService {

    public UpdateDBService() {
        super("UpdateDBService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final Context context = this;

        new Thread(){
            @Override
            public void run(){
                ResgatarTurmas resgatarTurmas = new ResgatarTurmas(context);
                resgatarTurmas.executar();
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        Log.d("UpdateDBService", "service done");
        Intent i = new Intent("br.gov.sp.educacao.sed.mobile.CARREGADADOS");
        sendBroadcast(i);
    }
}

