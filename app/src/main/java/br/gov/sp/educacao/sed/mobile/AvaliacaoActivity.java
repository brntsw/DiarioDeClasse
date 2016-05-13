package br.gov.sp.educacao.sed.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;

/**
 * Created by techresult on 24/08/2015.
 */
public class AvaliacaoActivity extends AppCompatActivity {

    Toolbar toolbar;
    Bundle b;
    private Activity activity;
    private UsuarioTO usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);

        activity = this;

        Analytics.setTela(activity, activity.getClass().toString());

        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.getSerializable("usuario") != null) {
            usuario  = (UsuarioTO) extras.getSerializable("usuario");
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.avaliacao);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.abc_ic_ab_back_mtrl_am_alpha, null));
        setSupportActionBar(toolbar);

        Fragment fragment = Fragment.instantiate(getBaseContext(), "br.gov.sp.educacao.sed.mobile.Fragment.FragmentTurmasAvaliacao");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home, fragment, "FragTurmasAvaliacao");
        fragmentTransaction.commit();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void onBackPressed(){
        super.onBackPressed();

    }
}
