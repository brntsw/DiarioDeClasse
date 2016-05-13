package br.gov.sp.educacao.sed.mobile.Util;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Gustavo on 23/07/2015.
 */
public class Analytics {

    private static GoogleAnalytics instance;
    private static Tracker tracker;

    public static void setTela(Context context, String nomeTela){
        instance = GoogleAnalytics.getInstance(context);
        tracker = instance.newTracker("UA-69688352-1");

        tracker.setScreenName(nomeTela);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    public static void setEvento(Context context, String categoria, String rotulo, String acao){
        instance = GoogleAnalytics.getInstance(context);
        tracker = instance.newTracker("UA-69688352-1");

        tracker.send(new HitBuilders.EventBuilder().setCategory(categoria)
                .setAction(acao)
                .setLabel(rotulo).build());

    }
}
