package br.gov.sp.educacao.sed.mobile;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.TimeUnit;

import br.gov.sp.educacao.sed.mobile.Action.UpdateDBService;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;
import br.gov.sp.educacao.sed.mobile.Util.Queries;


public class MainActivity extends Activity {

    private Activity activity;
    private Context context;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;
        this.activity = this;

        Analytics.setTela(activity, activity.getClass().toString());

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        final Intent intent;

        UsuarioTO usuario = Queries.getUsuarioAtivo(context);

        if(usuario != null) {
            intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("usuario", usuario);

        }else{
            intent = new Intent(MainActivity.this, LoginActivity.class);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    TimeUnit.SECONDS.sleep(2);

                    finish();
                    startActivity(intent);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
