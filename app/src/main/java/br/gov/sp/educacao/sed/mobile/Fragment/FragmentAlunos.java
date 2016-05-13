package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.gov.sp.educacao.sed.mobile.Adapter.AlunoAdapter;
import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;

/**
 * Created by techresult on 26/06/2015.
 */
public class FragmentAlunos extends Fragment {
    private TextView tvUser;
    private ListView lvValor;
    private TextView tvTurma;
    private String nomeTurmaAlunoSelecionado;
    private AppCompatActivity appCompatActivity;
    private ArrayList<Aluno> alunoArrayList;
    private AlunoAdapter alunoAdapter;
    private Activity activity;
    private UsuarioTO usuario;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        appCompatActivity = (AppCompatActivity) getActivity();

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_alunos, container, false);
        activity = getActivity();
        Analytics.setTela(activity, activity.getClass().toString());
        tvUser = (TextView) layout.findViewById(R.id.tv_user);
        lvValor = (ListView) layout.findViewById(R.id.lv_valor);
        tvTurma = (TextView) layout.findViewById(R.id.tv_turma);

        Bundle bundle = getArguments();
        TurmaGrupo turmaGrupo = (TurmaGrupo) bundle.getSerializable(TurmaGrupo.BUNDLE_TURMA_GRUPO);
        usuario = (UsuarioTO) bundle.getSerializable("usuario");

        tvUser.setText(usuario.getNome());

        tvTurma.setText(turmaGrupo.getTurma().getNomeTurma());
        nomeTurmaAlunoSelecionado = turmaGrupo.getTurma().getNomeTurma();

        alunoArrayList = new ArrayList<>();
        for (int i = 0; i < turmaGrupo.getTurma().arrayAlunoSize(); i++) {
            Aluno aluno = turmaGrupo.getTurma().getAluno(i);
            alunoArrayList.add(aluno);
        }

        Collections.sort(alunoArrayList, new Comparator<Aluno>() {
            @Override
            public int compare(Aluno p1, Aluno p2) {
                return p1.getNumeroChamada() - p2.getNumeroChamada();
            }

        });

        alunoAdapter = new AlunoAdapter(activity, alunoArrayList);
        lvValor.setAdapter(alunoAdapter);

        lvValor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("Aluno", alunoArrayList.get(position));
                bundle.putSerializable("usuario", usuario);
                bundle.putString("nomeTurmaAlunoSelecionado", nomeTurmaAlunoSelecionado);

                Fragment fragment = Fragment.instantiate(activity, "br.gov.sp.educacao.sed.mobile.Fragment.FragmentAlunosDetalhe");
                fragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home, fragment, "FragAlunoDetalhe");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return layout;
    }
}
