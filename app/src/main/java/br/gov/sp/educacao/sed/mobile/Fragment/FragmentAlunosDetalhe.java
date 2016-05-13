package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;
import br.gov.sp.educacao.sed.mobile.Util.Utils;

public class FragmentAlunosDetalhe extends Fragment {

    private TextView tvUser;
    private TextView tvAluno;
    private TextView tvTurma;
    private TextView tvMatricula;
    private TextView tvStatus;
    private TextView tvNomePai;
    private TextView tvNomeMae;
    private Activity activity;
    static String LABEL_MATRICULA;
    static String LABEL_STATUS;
    static String LABEL_TURMA;
    static String LABEL_NOME_PAI;
    static String LABEL_NOME_MAE;
    static String IDX_SEPARATOR = ": ";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_alunos_detalhe, container, false);
        activity = getActivity();
        Analytics.setTela(activity, activity.getClass().toString());
        tvUser = (TextView) layout.findViewById(R.id.tv_user);
        tvAluno = (TextView) layout.findViewById(R.id.tv_aluno);
        tvTurma = (TextView) layout.findViewById(R.id.tv_turma);
        tvMatricula = (TextView) layout.findViewById(R.id.tv_matricula);
        tvStatus = (TextView) layout.findViewById(R.id.tv_status);
        tvNomePai = (TextView) layout.findViewById(R.id.tv_nome_pai);
        tvNomeMae = (TextView) layout.findViewById(R.id.tv_nome_mae);

        //SETA LABEL'S DO ARQUIVO STRING (XML)
        LABEL_TURMA = getResources().getString(R.string.desc_turma);
        LABEL_MATRICULA = getResources().getString(R.string.desc_matricula);
        LABEL_STATUS = getResources().getString(R.string.desc_status);
        LABEL_NOME_PAI = getResources().getString(R.string.desc_nome_pai);
        LABEL_NOME_MAE = getResources().getString(R.string.desc_nome_mae);

        Bundle bundle = getArguments();

        Aluno aluno = (Aluno) bundle.getSerializable("Aluno");
        String nomeTurmaAlunoSelecionado = getArguments().getString("nomeTurmaAlunoSelecionado");

        UsuarioTO usuario = (UsuarioTO) bundle.getSerializable("usuario");

        tvUser.setText(usuario.getNome());
        tvAluno.setText(aluno.getNomeAluno());
        tvTurma.setText(LABEL_TURMA+IDX_SEPARATOR+nomeTurmaAlunoSelecionado);
        if(aluno.getDigitoRa() == null) {
            //RA SEM DIGITO
            tvMatricula.setText(LABEL_MATRICULA + IDX_SEPARATOR + aluno.getNumeroRa());
        }else {
            //RA COM DIGITO
            tvMatricula.setText(LABEL_MATRICULA + IDX_SEPARATOR + aluno.getNumeroRa() + "-" + aluno.getDigitoRa());
        }
        tvStatus.setText(LABEL_STATUS + IDX_SEPARATOR + Utils.convertStatus(aluno.getAlunoAtivo()));


        String nomePaiFormatado = aluno.getNomePai();
        if(nomePaiFormatado == null){
            nomePaiFormatado = getResources().getString(R.string.desc_info_indisp);
        }

        String nomeMaeFormatado = aluno.getNomeMae();
        if(nomeMaeFormatado == null){
            nomeMaeFormatado = getResources().getString(R.string.desc_info_indisp);
        }
        tvNomePai.setText(LABEL_NOME_PAI+IDX_SEPARATOR+nomePaiFormatado);
        tvNomeMae.setText(LABEL_NOME_MAE+IDX_SEPARATOR+nomeMaeFormatado);

        return layout;
    }
}
