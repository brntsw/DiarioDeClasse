package br.gov.sp.educacao.sed.mobile.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import br.gov.sp.educacao.sed.mobile.AvaliacaoActivity;
import br.gov.sp.educacao.sed.mobile.FrequenciaActivity;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.RegistroAulasActivity;
import br.gov.sp.educacao.sed.mobile.Util.CustomGrid;

/**
 * Created by techresult on 22/06/2015.
 */
public class ScreenSlideFragmentHome extends Fragment {
    private Context context;

    private GridView gridView;
    private CustomGrid adapter;
    private String[] descricaoItem;
    Drawable[] imagens;
    private UsuarioTO usuario;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        descricaoItem = new String[]{
                getResources().getString(R.string.turmas),
                getResources().getString(R.string.registro_aulas),
                getResources().getString(R.string.frequencia),
                getResources().getString(R.string.avaliacao)
        };

        imagens = new Drawable[]{
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_turmas, null),
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_planejamento, null),
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_frequencia, null),
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_avaliacao, null)
        };
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();

        String perfil = getArguments().getString("perfil");
        usuario = (UsuarioTO) getArguments().getSerializable("usuario");

        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);

        if (perfil.equals("professor")) {
            adapter = new CustomGrid(context, descricaoItem, imagens);
            gridView = (GridView) relativeLayout.findViewById(R.id.grid);

            gridView.setAdapter(adapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(context, "You clicked at: " + descricaoItem[position], Toast.LENGTH_LONG).show();

                    Intent intent;

                    switch (position) {
                        case 0://Turmas
                            Fragment fragment = Fragment.instantiate(getActivity(), "br.gov.sp.educacao.sed.mobile.Fragment.FragmentTurmas");

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("usuario", usuario);
                            fragment.setArguments(bundle);

                            FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
                            tx.replace(R.id.home, fragment, "FragTurmas");
                            tx.addToBackStack(null);
                            tx.commit();
                            break;

                        case 1: //Registro de aulas
                            intent = new Intent(getActivity(), RegistroAulasActivity.class);
                            intent.putExtra("usuario", usuario);
                            startActivity(intent);
                            break;

                        case 2://Frequencia
                            //Inicializa a Activity FrequenciaActivity
                            intent = new Intent(getActivity(), FrequenciaActivity.class);
                            intent.putExtra("usuario", usuario);
                            startActivity(intent);
                            break;

                        case 3://Avaliacao
                            //Inicializa a Activity AvaliacaoActivity
                            intent = new Intent(getActivity(), AvaliacaoActivity.class);
                            intent.putExtra("usuario", usuario);
                            startActivity(intent);
                            break;

                    }
                }
            });
        }

        return relativeLayout;
    }
}
