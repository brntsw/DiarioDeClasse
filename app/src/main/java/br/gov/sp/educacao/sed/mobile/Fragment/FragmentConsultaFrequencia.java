package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.gov.sp.educacao.sed.mobile.Adapter.ConsultaAlunoAdapter;
import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.R;

/**
 * Created by techresult on 07/08/2015.
 */
public class FragmentConsultaFrequencia extends Fragment {

    private RelativeLayout layout;
    private TextView tvHorario;
    private LinearLayout linearHorario;
    private ListView lvConsulta;
    private Bundle bundle;
    private TurmaGrupo turmaGrupo;
    private ConsultaAlunoAdapter adapter;
    private ArrayList<Aluno> alunoArrayList;
    private CharSequence[] sequence = {"Anual","Bimestre","Sequênciais"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_consulta_frequencia, container, false);

        bundle = getArguments();
        tvHorario = (TextView) layout.findViewById(R.id.tv_horario);
        linearHorario = (LinearLayout) layout.findViewById(R.id.linearHorario);
        lvConsulta = (ListView) layout.findViewById(R.id.lv_consulta);

        linearHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getResources().getString(R.string.periodo));
                builder.setItems(sequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        CharSequence selectedValue = sequence[which];
                        tvHorario.setText(selectedValue);

                        populaLista(which);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        tvHorario.setText(sequence[0]);
        turmaGrupo = (TurmaGrupo) bundle.getSerializable("turmaGrupo");
        TextView tvTurma = (TextView) layout.findViewById(R.id.tv_turma);
        tvTurma.setText(turmaGrupo.getTurma().getNomeTurma() + " / " + turmaGrupo.getTurma().getNomeTipoEnsino());

        alunoArrayList = turmaGrupo.getTurma().getAlunos();

        Collections.sort(alunoArrayList, new Comparator<Aluno>() {
            @Override
            public int compare(Aluno p1, Aluno p2) {
                return p1.getNumeroChamada() - p2.getNumeroChamada();
            }

        });

        populaLista(0);

        return layout;
    }

    public void populaLista(int periodo){

        adapter = new ConsultaAlunoAdapter(getActivity(),alunoArrayList, periodo, turmaGrupo);
        lvConsulta.setAdapter(adapter);

    }
}
