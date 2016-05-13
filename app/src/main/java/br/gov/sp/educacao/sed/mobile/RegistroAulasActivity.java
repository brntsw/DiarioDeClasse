package br.gov.sp.educacao.sed.mobile;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;


public class RegistroAulasActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Bundle b;
    private UsuarioTO usuario;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_aulas);

        activity = this;

        Analytics.setTela(activity, activity.getClass().toString());

        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.getSerializable("usuario") != null) {
            usuario  = (UsuarioTO) extras.getSerializable("usuario");
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.registro_aulas);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.abc_ic_ab_back_mtrl_am_alpha, null));
        setSupportActionBar(toolbar);

        b = getIntent().getExtras();
        if(b != null && b.getSerializable("alunos") != null){
            //Fragment Turmas
            Fragment fragmentTurmas = Fragment.instantiate(getBaseContext(), "br.gov.sp.educacao.sed.mobile.Fragment.FragmentTurmasRegistroAula");

            Fragment fragment = Fragment.instantiate(getBaseContext(), "br.gov.sp.educacao.sed.mobile.Fragment.FragmentRegistroAula");
            fragment.setArguments(b);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.home, fragmentTurmas, "FragTurmasRegistro");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.home, fragment, "FragRegistro");
            fragmentTransaction.commit();
        }
        else{
            Fragment fragment = Fragment.instantiate(getBaseContext(), "br.gov.sp.educacao.sed.mobile.Fragment.FragmentTurmasRegistroAula");

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.home, fragment, "FragTurmasRegistro");
            //fragmentTransaction.addToBackStack("frequencia");
            fragmentTransaction.commit();
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void onBackPressed(){
        super.onBackPressed();

        if(b != null && b.getSerializable("alunos") != null){
            Fragment fragment = Fragment.instantiate(getBaseContext(), "br.gov.sp.educacao.sed.mobile.Fragment.FragmentTurmasRegistroAula");

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.home, fragment, "FragTurmasRegistro");
            //fragmentTransaction.addToBackStack("frequencia");
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro_aulas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
