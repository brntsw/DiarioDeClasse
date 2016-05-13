package br.gov.sp.educacao.sed.mobile.Fragment;


import android.os.Bundle;
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
import br.gov.sp.educacao.sed.mobile.Modelo.Turma;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.TurmaQueryDB;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.DateUtils;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTurmasRegistroAula extends Fragment {

    private AppCompatActivity appCompatActivity;
    private ArrayList<TurmaGrupo> turmaArrayList;
    private ListView lvValor;
    private LinearLayout layTurma;
    private TextView tvTurma, tvUser;
    private TurmaAdapter turmaAdapter;
    private int ano;
    private UsuarioTO usuario;



    public FragmentTurmasRegistroAula() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        appCompatActivity = (AppCompatActivity) getActivity();
        Toolbar toolbar = (Toolbar) appCompatActivity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.registro_aulas);
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_turmas, container, false);

        lvValor = (ListView) rootView.findViewById(R.id.lv_valor);
        layTurma = (LinearLayout) rootView.findViewById(R.id.lay_turma);
        tvTurma = (TextView) rootView.findViewById(R.id.tv_turma);
        tvUser = (TextView) rootView.findViewById(R.id.tv_user);

        Date date = new Date();
        ano = DateUtils.getCurrentYear(date);
        usuario = Queries.getUsuarioAtivo(getActivity());

        tvUser.setText(usuario.getNome());

        turmaArrayList = TurmaQueryDB.getTurmas(getActivity(), ano);
        Collections.sort(turmaArrayList, new Comparator<TurmaGrupo>() {
            @Override
            public int compare(TurmaGrupo p1, TurmaGrupo p2) {
                return p1.getTurma().getNomeEscola().compareToIgnoreCase(p2.getTurma().getNomeEscola());
            }

        });

        getfathergroup();

        turmaAdapter = new TurmaAdapter(getActivity(), turmaArrayList);
        lvValor.setAdapter(turmaAdapter);

        getfathergroup();

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
                b.putSerializable("ano", ano);
                b.putSerializable("usuario", usuario);

                Fragment fragment = Fragment.instantiate(getActivity(), "br.gov.sp.educacao.sed.mobile.Fragment.FragmentRegistroAula");
                fragment.setArguments(b);

                FragmentTransaction fragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home, fragment, "FragRegistro");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        });

        return rootView;

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
            turma.setHeader(false);
            turma.setFooter(false);

            if (!nome.equals(diretoriaEscola)) {
                turma.setHeader(true);
                nome = diretoriaEscola;
                if (i > 0) {
                    turma.setFooter(true);
                }
            }
        }

    }


}
