package br.gov.sp.educacao.sed.mobile;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import br.gov.sp.educacao.sed.mobile.Action.AlarmReceiver;
import br.gov.sp.educacao.sed.mobile.Action.AtualizarBancoOff;
import br.gov.sp.educacao.sed.mobile.Action.ResgatarTurmas;
import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Modelo.Turma;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.Util.AlertFactory;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;
import br.gov.sp.educacao.sed.mobile.Util.DialogRegistrarAula;
import br.gov.sp.educacao.sed.mobile.Util.NetworkUtils;
import br.gov.sp.educacao.sed.mobile.Util.ProgressFactory;

/**
 * Created by techresult on 22/06/2015.
 */
public class HomeActivity extends AppCompatActivity{

    Toolbar toolbar;

    private Activity activity;
    private Context context;
    DialogRegistrarAula dialogRegistrarAula;
    private UsuarioTO usuario;
    private Bundle extras;
    private ProgressDialog progress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        activity = this;
        context = this;
        extras = getIntent().getExtras();


        if(extras != null && extras.getSerializable("usuario") != null) {
            usuario  = (UsuarioTO) extras.getSerializable("usuario");
            Analytics.setTela(activity, "API " + Build.VERSION.SDK_INT + " User " + usuario.getNome());
        }else{
            Analytics.setTela(activity, "API " + Build.VERSION.SDK_INT + " User Anonimo");
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    finish();
                } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                    toolbar.setNavigationIcon(null);
                    //toolbar.setLogo(R.drawable.icon);
                    toolbar.setTitle(R.string.app_name);
                    toolbar.inflateMenu(R.menu.menu_main);
                }
            }
        });

        //Parâmetro estático do perfil -> PRECISA SER MUDADO PARA DIN�MICO
        Bundle b = new Bundle();
        b.putInt("perfil", 4);
        b.putSerializable("usuario", usuario);

        Fragment fragment = Fragment.instantiate(activity, "br.gov.sp.educacao.sed.mobile.Fragment.FragmentHome");
        fragment.setArguments(b);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home, fragment, "FragHome");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        if(extras != null && extras.getSerializable("alunos") != null) {
            dialogRegistrarAula = new DialogRegistrarAula();
            dialogRegistrarAula.setCancelable(false);
            dialogRegistrarAula.show(fm, "Registrar aula");
        }
    }


    public void setButtons(Button btnSim, Button btnNao){
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogRegistrarAula != null){
                    Bundle extras = getIntent().getExtras();
                    ArrayList<Aluno> alunos = (ArrayList<Aluno>) extras.getSerializable("alunos");
                    Turma turma = (Turma) extras.getSerializable("turma");
                    String data = extras.getString("data");

                    Intent intent = new Intent(HomeActivity.this, RegistroAulasActivity.class);
                    intent.putExtra("alunos", alunos);
                    intent.putExtra("turma", turma);
                    intent.putExtra("data", data);
                    startActivity(intent);
                    dialogRegistrarAula.dismiss();
                }
            }
        });

        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRegistrarAula.dismiss();
            }
        });
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
        switch (id) {
            case R.id.action_share:
                return true;
            case R.id.action_evaluate:
                return true;
            case R.id.action_sinc:

                final AlertDialog.Builder alertbuilderSinc = new AlertDialog.Builder(activity);
                alertbuilderSinc.setTitle(getResources().getString(R.string.confirma_sinc));

                alertbuilderSinc.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressFactory progressFactory = new ProgressFactory(activity);
                        progressFactory.startProgress(getResources().getString(R.string.inicia_sinc));

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    TimeUnit.SECONDS.sleep(2);

                                    ResgatarTurmas resgatarTurmas = new ResgatarTurmas(context);
                                    if (!resgatarTurmas.executar()) {
                                        progressFactory.endProgress();
                                        activity.runOnUiThread(new Runnable() {
                                            public void run() {
                                                AlertFactory.constroeAlert("Ops!", getResources().getString(R.string.erro_turmas), activity).show();
                                            }
                                        });
                                    }
                                    else{
                                        progressFactory.endProgress();
                                        activity.runOnUiThread(new Runnable() {
                                            public void run() {
                                                AlertFactory.constroeAlert("Sucesso", getResources().getString(R.string.sincronizacao_realizada), activity).show();
                                            }
                                        });
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }


                            }
                        }).start();
                    }
                });
                alertbuilderSinc.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                if(NetworkUtils.isWifi(this)){
                    alertbuilderSinc.create().show();
                }
                else{
                    Toast.makeText(HomeActivity.this, getResources().getString(R.string.conecte_wifi), Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.action_about:

                final AlertDialog.Builder alertbuilder = new AlertDialog.Builder(activity);
                alertbuilder.setTitle("Sobre");

                PackageInfo pInfo = null;
                try {
                    pInfo = getPackageManager().getPackageInfo(activity.getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                if(pInfo != null) {
                    alertbuilder.setMessage("Di@rio de Classe versão " + pInfo.versionName);

                    alertbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertbuilder.create().show();
                }

                return true;
            case R.id.action_logoff:
                finish();
                AtualizarBancoOff.limparTabelas(HomeActivity.this);
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);

                cancelAlarm();

                return true;
            case R.id.action_notification:
                return true;
            /*case R.id.action_tabelas:
                Intent dbmanager = new Intent(HomeActivity.this, AndroidDatabaseManager.class);
                startActivity(dbmanager);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed(){
        Bundle extras = getIntent().getExtras();

        if( extras != null && extras.getInt("registro") == 1){
            HomeActivity.this.finish();
        }
        else{
            int count = getFragmentManager().getBackStackEntryCount();

            if(count == 0){
                super.onBackPressed();
            }
            else{
                getFragmentManager().popBackStack();
            }
        }
    }

    public void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }
}
