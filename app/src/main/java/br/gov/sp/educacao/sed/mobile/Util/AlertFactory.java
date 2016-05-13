package br.gov.sp.educacao.sed.mobile.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertFactory {

    private static AlertDialog alerta;

    public static AlertDialog constroeAlert(String titulo, String corpo, Context activity) {


        AlertDialog.Builder alertaerro = new AlertDialog.Builder(activity);
        alertaerro.setTitle(titulo);
        alertaerro.setMessage(corpo);


        alertaerro.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                alerta.cancel();

            }
        });

        alerta = alertaerro.create();
        alerta.setCancelable(false);
        alerta.setCanceledOnTouchOutside(false);

        return alerta;
    }
}
