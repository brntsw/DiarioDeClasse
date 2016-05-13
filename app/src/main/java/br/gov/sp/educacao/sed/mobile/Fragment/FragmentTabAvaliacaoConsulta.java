package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
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
import br.gov.sp.educacao.sed.mobile.AvaliacaoConsultaActivity;
import br.gov.sp.educacao.sed.mobile.Modelo.Turma;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.TurmaQueryDB;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;
import br.gov.sp.educacao.sed.mobile.Util.DateUtils;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 24/08/2015.
 */
public class FragmentTabAvaliacaoConsulta extends Fragment {

    private Activity activity;
    private AppCompatActivity appCompatActivity;
    private ListView lvValor;
    private LinearLayout layTurma;
    private TextView tvTurma;
    private TextView tvUser;
    private ArrayList<TurmaGrupo> turmaArrayList;
    private int ano;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        Analytics.setTela(activity, activity.getClass().toString());

        appCompatActivity = (AppCompatActivity) getActivity();
        //dialogCaldroidFragment = new CaldroidFragment();

        Toolbar toolbar = (Toolbar) appCompatActivity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setLogo(null);
            toolbar.setTitle(R.string.avaliacao);
            toolbar.getMenu().clear();
            toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.abc_ic_ab_back_mtrl_am_alpha, null));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    appCompatActivity.onBackPressed();
                }
            });
        }

        UsuarioTO usuario = Queries.getUsuarioAtivo(getActivity());

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_turmas, container, false);

        lvValor = (ListView) layout.findViewById(R.id.lv_valor);
        layTurma = (LinearLayout) layout.findViewById(R.id.lay_turma);
        tvTurma = (TextView) layout.findViewById(R.id.tv_turma);
        tvUser = (TextView) layout.findViewById(R.id.tv_user);

        tvUser.setText(usuario.getNome());

        ano = DateUtils.getCurrentYear(new Date());

        listarTurmaOrg();

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
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), AvaliacaoConsultaActivity.class);
                intent.putExtra(TurmaGrupo.BUNDLE_TURMA_GRUPO, turmaArrayList.get(i));
                startActivity(intent);

            }
        });

        return layout;
    }

    private void listarTurmaOrg() {
        turmaArrayList = TurmaQueryDB.getTurmas(activity, ano);
        Collections.sort(turmaArrayList, new Comparator<TurmaGrupo>() {
            @Override
            public int compare(TurmaGrupo p1, TurmaGrupo p2) {
                return p1.getTurma().getNomeEscola().compareToIgnoreCase(p2.getTurma().getNomeEscola());
            }

        });

        getfathergroup();

        TurmaAdapter turmaAdapter = new TurmaAdapter(activity, turmaArrayList);
        lvValor.setAdapter(turmaAdapter);
    }

    private void setHeader(int position) {

        final Turma turma = turmaArrayList.get(position).getTurma();
        tvTurma.setText(turma.getNomeDiretoria() + " / " + turma.getNomeEscola());
    }

    private void getfathergroup() {

        String nome = "";
        for (int i = 0; i < turmaArrayList.size(); i++) {
            final Turma turma = turmaArrayList.get(i).getTurma();
            String diretoriaEscola = turma.getNomeDiretoria() + " / " + turma.getNomeEscola();
            turmaArrayList.get(i).getTurma().setHeader(false);
            turmaArrayList.get(i).getTurma().setFooter(false);

            if (!nome.equals(diretoriaEscola)) {
                turmaArrayList.get(i).getTurma().setHeader(true);
                nome = diretoriaEscola;
                if (i > 0) {
                    turmaArrayList.get(i - 1).getTurma().setFooter(true);
                }
            }
        }

    }
}
