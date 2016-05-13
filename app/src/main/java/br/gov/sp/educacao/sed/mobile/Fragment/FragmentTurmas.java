package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import br.gov.sp.educacao.sed.mobile.Adapter.TurmaAdapter;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.TurmaQueryDB;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;
import br.gov.sp.educacao.sed.mobile.Util.DateUtils;

/**
 * Created by techresult on 24/06/2015.
 */
public class FragmentTurmas extends Fragment {

    private Activity activity;
    private AppCompatActivity appCompatActivity;
    private ArrayList<TurmaGrupo> turmaArrayList;
    private ListView lvValor;
    private TurmaAdapter turmaAdapter;
    private LinearLayout layTurma;
    private TextView tvTurma;
    private TextView tvUser;
    private UsuarioTO usuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        Analytics.setTela(activity, activity.getClass().toString());

        usuario = (UsuarioTO) getArguments().getSerializable("usuario");

        appCompatActivity = (AppCompatActivity) getActivity();
        Toolbar toolbar = (Toolbar) appCompatActivity.findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setLogo(null);
            toolbar.setTitle(R.string.turmas);
            toolbar.getMenu().clear();
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    appCompatActivity.onBackPressed();
                }
            });
        }

        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_turmas, container, false);

        tvUser = (TextView) linearLayout.findViewById(R.id.tv_user);
        lvValor = (ListView) linearLayout.findViewById(R.id.lv_valor);
        layTurma = (LinearLayout) linearLayout.findViewById(R.id.lay_turma);
        tvTurma = (TextView) linearLayout.findViewById(R.id.tv_turma);

        tvUser.setText(usuario.getNome());

        Date date = new Date();
        int ano = DateUtils.getCurrentYear(date);

        turmaArrayList = TurmaQueryDB.getTurmas(activity, ano);
        Collections.sort(turmaArrayList, new Comparator<TurmaGrupo>() {
            @Override
            public int compare(TurmaGrupo p1, TurmaGrupo p2) {
                return p1.getTurma().getNomeEscola().compareToIgnoreCase(p2.getTurma().getNomeEscola());
            }

        });

        getfathergroup();

        turmaAdapter = new TurmaAdapter(activity, turmaArrayList);
        lvValor.setAdapter(turmaAdapter);

        lvValor.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (turmaArrayList.size() > 0) {

                    if (turmaArrayList.get(firstVisibleItem).getTurma().isHeader()) {
                        setHeader(firstVisibleItem);
                    } else if (turmaArrayList.get(firstVisibleItem).getTurma().isFooter()) {
                        setHeader(firstVisibleItem);
                    }
                    layTurma.setVisibility(View.VISIBLE);
                } else {
                    layTurma.setVisibility(View.GONE);
                }
            }
        });

        lvValor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle b = new Bundle();
                b.putSerializable(TurmaGrupo.BUNDLE_TURMA_GRUPO, turmaArrayList.get(position));
                b.putSerializable("usuario", usuario);

                Fragment fragment = Fragment.instantiate(activity, "br.gov.sp.educacao.sed.mobile.Fragment.FragmentAlunos");
                fragment.setArguments(b);

                FragmentTransaction fragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home, fragment, "FragAluno");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return linearLayout;
    }

    private void setHeader(int position) {

        final TurmaGrupo turma = turmaArrayList.get(position);
        tvTurma.setText(turma.getTurma().getNomeDiretoria() + " / " + turma.getTurma().getNomeEscola());
    }



    private void getfathergroup() {

        String nomeDiretoriaEscola = "";
        for(TurmaGrupo turmaGrupoFor : turmaArrayList){

            String diretoriaEscola = turmaGrupoFor.getTurma().getNomeDiretoria() + " / " + turmaGrupoFor.getTurma().getNomeEscola();
            turmaGrupoFor.getTurma().setHeader(false);
            turmaGrupoFor.getTurma().setFooter(false);

            if (!nomeDiretoriaEscola.equals(diretoriaEscola)) {
                turmaGrupoFor.getTurma().setHeader(true);
                nomeDiretoriaEscola = diretoriaEscola;
                if (turmaArrayList.size() > 0) {
                    turmaGrupoFor.getTurma().setFooter(true);
                }
            }
        }

    }
}
