package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.List;

import br.gov.sp.educacao.sed.mobile.Adapter.AvaliacaoAdapter;
import br.gov.sp.educacao.sed.mobile.Modelo.Avaliacao;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.QueryDB.AvaliacaoQueryDB;
import br.gov.sp.educacao.sed.mobile.R;

/**
 * Created by techresult on 24/08/2015.
 */
public class FragmentConsultaAvaliacao extends Fragment {

    private RelativeLayout layout;
    private TextView tvPeriodo;
    private LinearLayout linearPeriodo;
    private ListView lvConsulta;
    private Spinner spTipo;
    private Bundle bundle;
    private TurmaGrupo turmaGrupo;
    private CharSequence[] sequence = {"1 bimestre","2 bimestre","3 bimestre","4 bimestre"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_consulta_avaliacao, container, false);

        bundle = getArguments();
        tvPeriodo = (TextView) layout.findViewById(R.id.tv_periodo);
        spTipo = (Spinner) layout.findViewById(R.id.sp_tipo);
        linearPeriodo = (LinearLayout) layout.findViewById(R.id.linearPeriodo);
        lvConsulta = (ListView) layout.findViewById(R.id.lv_consulta);

        turmaGrupo = (TurmaGrupo) bundle.getSerializable(TurmaGrupo.BUNDLE_TURMA_GRUPO);

        linearPeriodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getResources().getString(R.string.periodo));
                builder.setItems(sequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        CharSequence selectedValue = sequence[which];
                        tvPeriodo.setText(selectedValue);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        tvPeriodo.setText(sequence[0]);
        TextView tvTurma = (TextView) layout.findViewById(R.id.tv_turma);
        tvTurma.setText(turmaGrupo.getTurma().getNomeTurma() + " / " + turmaGrupo.getDisciplina().getNomeDisciplina());

        spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setTurmaId(turmaGrupo.getTurma().getId());
                avaliacao.setDisciplinaId(turmaGrupo.getDisciplina().getId());

                int bimestre = Integer.parseInt(tvPeriodo.getText().toString().substring(0, 1));

                avaliacao.setBimestre(bimestre);

                int tipoAtividade = 0;
                String strSelectedItem = stripAccents(spTipo.getSelectedItem().toString());

                //Atividades
                switch (strSelectedItem) {
                    case "Avaliacao":
                        tipoAtividade = 11;
                        break;
                    case "Atividade":
                        tipoAtividade = 12;
                        break;
                    case "Trabalho":
                        tipoAtividade = 13;
                        break;
                    case "Outros":
                        tipoAtividade = 14;
                        break;
                }

                avaliacao.setTipoAtividade(tipoAtividade);

                final List<Avaliacao> avaliacoes = AvaliacaoQueryDB.getAvaliacoesByAvaliacao(getActivity(), avaliacao);
                if(avaliacoes.size() > 0){
                    AvaliacaoAdapter adapter = new AvaliacaoAdapter(getActivity(), avaliacoes);
                    lvConsulta.setAdapter(adapter);

                    lvConsulta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Avaliacao avaliacao = avaliacoes.get(position);
                            bundle.putSerializable(TurmaGrupo.BUNDLE_TURMA_GRUPO, turmaGrupo);
                            bundle.putSerializable(Avaliacao.BUNDLE_AVALIACAO, avaliacao);

                            /*Fragment fragment = Fragment.instantiate(activity, "br.gov.sp.educacao.sed.mobile.Fragment.FragmentLancamentoAvaliacaoPager");
                            fragment.setArguments(bundle);

                            FragmentTransaction fragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frequencia_lancamento, fragment, "FragAvaliacaoLancamentoPager");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();*/
                        }
                    });
                }
                else{
                    lvConsulta.setAdapter(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return layout;

    }

    String stripAccents(String string) {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return string;
    }
}
