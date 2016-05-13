package br.gov.sp.educacao.sed.mobile;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.concurrent.TimeUnit;

import br.gov.sp.educacao.sed.mobile.Action.AlarmReceiver;
import br.gov.sp.educacao.sed.mobile.Action.AtualizarBancoOff;
import br.gov.sp.educacao.sed.mobile.Action.ValidarLogin;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;
import br.gov.sp.educacao.sed.mobile.Util.NetworkUtils;
import br.gov.sp.educacao.sed.mobile.Util.ProgressFactory;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 19/06/2015.
 */
public class LoginActivity extends Activity {

    private EditText editusuario, editsenha;
    private Button btlogar;
    private Activity activity;
    private Context context;
    private BroadcastReceiver receiver;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        context = this;

        Analytics.setTela(activity, activity.getClass().toString());

        setContentView(R.layout.activity_login);

        editusuario = (EditText) findViewById(R.id.editusuario);
        editsenha = (EditText) findViewById(R.id.editsenha);
        btlogar = (Button) findViewById(R.id.btlogar);

        btlogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressFactory progressFactory = new ProgressFactory(activity);

                if (NetworkUtils.isWifi(LoginActivity.this)) {
                    progressFactory.startProgress(getResources().getString(R.string.verifica_usuario));

                    AtualizarBancoOff.limparTabelas(LoginActivity.this);

                    String strUsuario = editusuario.getText().toString();
                    String strSenha = editsenha.getText().toString();

                    if (!strUsuario.equals("") && !strSenha.equals("")) {
                        //Chamada de uma task separada somente para Login
                        ValidarLogin validarLogin = new ValidarLogin(context, editusuario.getText().toString().trim(), editsenha.getText().toString().trim());

                        if (validarLogin.executar()) {
                            progressFactory.endProgress();

                            progressFactory.startProgress(getResources().getString(R.string.carrega_info));

                            scheduleAlarm();

                            IntentFilter filter = new IntentFilter("br.gov.sp.educacao.sed.mobile.CARREGADADOS");
                            filter.addCategory(Intent.CATEGORY_DEFAULT);
                            receiver = new AlarmReceiver() {
                                @Override
                                public void onReceive(Context context, Intent intent) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                TimeUnit.SECONDS.sleep(20);

                                                progressFactory.endProgress();

                                                UsuarioTO usuario = Queries.getUsuarioAtivo(activity);
                                                Intent intentLogin = new Intent(LoginActivity.this, HomeActivity.class);
                                                intentLogin.putExtra("usuario", usuario);
                                                startActivity(intentLogin);

                                                finish();
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                }
                            };

                            registerReceiver(receiver, filter);
                        } else {
                            progressFactory.endProgress();
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.erro_servidor), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (strUsuario.equals("")) {
                            progressFactory.endProgress();
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.hintusuario), Toast.LENGTH_SHORT).show();
                        } else if (strSenha.equals("")) {
                            progressFactory.endProgress();
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.hintsenha), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.conecte_wifi), Toast.LENGTH_SHORT).show();
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    protected void onDestroy(){
        super.onDestroy();

        if(receiver != null){
            unregisterReceiver(receiver);
        }
    }

    public void scheduleAlarm() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        long periodicMillis = 60 * 60 * 1000; //1 hora
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                periodicMillis, pIntent); //AlarmManager.INTERVAL_HALF_HOUR
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://br.gov.sp.educacao.sed.mobile/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://br.gov.sp.educacao.sed.mobile/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}