package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.Date;

import br.gov.sp.educacao.sed.mobile.Adapter.TurmaAdapter;
import br.gov.sp.educacao.sed.mobile.FrequenciaLancamentoActivity;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.TurmaQueryDB;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;
import br.gov.sp.educacao.sed.mobile.Util.DateUtils;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 03/07/2015.
 */
public class FragmentTabFrequenciaLancamento extends Fragment {

    private Activity activity;
    private AppCompatActivity appCompatActivity;
    private ListView lvValor;
    private LinearLayout layTurma;
    private TextView tvTurma;
    private TextView tvUser;
    private ArrayList<TurmaGrupo> turmaArrayList;
    private int ano;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        activity = getActivity();
        Analytics.setTela(activity, activity.getClass().toString());

        appCompatActivity = (AppCompatActivity) getActivity();

        Toolbar toolbar = (Toolbar) appCompatActivity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setLogo(null);
            toolbar.setTitle(R.string.frequencia);
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

        Date date = new Date();
        ano = DateUtils.getCurrentYear(date);

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

        final Bundle state = savedInstanceState;

        lvValor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), FrequenciaLancamentoActivity.class);
                intent.putExtra("turmaGrupo", turmaArrayList.get(i));
                intent.putExtra("ano", ano);
                startActivity(intent);
            }
        });

        return layout;
    }

    private void listarTurmaOrg() {
        turmaArrayList = TurmaQueryDB.getTurmas(activity, ano);

        getfathergroup();

        TurmaAdapter turmaAdapter = new TurmaAdapter(activity, turmaArrayList);
        lvValor.setAdapter(turmaAdapter);
    }

    private void setHeader(int position) {

        final TurmaGrupo turmaGrupo = turmaArrayList.get(position);
        tvTurma.setText(turmaGrupo.getTurma().getNomeDiretoria() + " / " + turmaGrupo.getTurma().getNomeEscola());
    }

    private void getfathergroup() {

        String nome = "";
        for (int i = 0; i < turmaArrayList.size(); i++) {
            final TurmaGrupo turmaGrupo = turmaArrayList.get(i);
            String diretoriaEscola = turmaGrupo.getTurma().getNomeDiretoria() + " / " + turmaGrupo.getTurma().getNomeEscola();
            turmaGrupo.getTurma().setHeader(false);
            turmaGrupo.getTurma().setFooter(false);

            if (!nome.equals(diretoriaEscola)) {
                turmaGrupo.getTurma().setHeader(true);
                nome = diretoriaEscola;
                if (i > 0) {
                    turmaArrayList.get(i - 1).getTurma().setFooter(true);
                }
            }
        }

    }
}
