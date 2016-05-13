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
 * Created by techresult on 03/07/2015.
 */
public class FrequenciaActivity extends AppCompatActivity {
    Toolbar toolbar;
    private Activity activity;
    private UsuarioTO usuario;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequencia);

        activity = this;

        Analytics.setTela(activity, activity.getClass().toString());

        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.getSerializable("usuario") != null) {
            usuario  = (UsuarioTO) extras.getSerializable("usuario");
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.frequencia);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.abc_ic_ab_back_mtrl_am_alpha, null));
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        
        //Par
        // �metro est�tico do perfil -> PRECISA SER MUDADO PARA DIN�MICO
        Bundle b = new Bundle();
        b.putInt("perfil", 4);

        Fragment fragment = Fragment.instantiate(getBaseContext(), "br.gov.sp.educacao.sed.mobile.Fragment.FragmentFrequencia");
        fragment.setArguments(b);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home, fragment, "FragFrequencia");
        //fragmentTransaction.addToBackStack("frequencia");
        fragmentTransaction.commit();


    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
