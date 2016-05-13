package br.gov.sp.educacao.sed.mobile.Util;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by techresult on 12/05/2016.
 */
public class ProgressFactory {

    private Activity activity;
    private ProgressDialog progress;

    public ProgressFactory(Activity activity){
        this.activity = activity;
    }

    public void startProgress(final String msg) {

        progress = new ProgressDialog(activity);

        Thread thread = new Thread() {
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setTitle("Aguarde");
                        progress.setCanceledOnTouchOutside(false);

                        progress.setMessage(msg);
                        progress.show();
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        thread.run();

        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void endProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }

}
