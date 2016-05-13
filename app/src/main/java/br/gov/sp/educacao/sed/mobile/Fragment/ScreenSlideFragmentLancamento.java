package br.gov.sp.educacao.sed.mobile.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Modelo.Aula;
import br.gov.sp.educacao.sed.mobile.Modelo.DiasLetivos;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.FrequenciaQueryDB;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScreenSlideFragmentLancamento extends Fragment {

    private int position;
    private ArrayList<Aluno> alunos;
    private ArrayList<Aula> aulas;
    private Aula aula;
    private Aluno aluno;
    private TurmaGrupo turmaGrupo;
    private Button btFalta, btCompareceu, btNaoAplica;
    private TextView tvStatus;
    private String data, hora;
    public FragmentLancamentoFrequenciaPager fragmentLancamentoFrequenciaPager;
    private Activity activity;
    private Menu menu;
    private MenuItem item;
    private AlertDialog alert;
    private TextView tvFaltasAno;
    private TextView tvFaltasBimestre;
    private TextView tvFaltasSequencia;
    private int bimestreAtual;
    private boolean flagMenu = true;

    private String siglaCompareceu;
    private String siglaFaltou;
    private String siglaNaoSeAplica;
    private String siglaTransferido;
    private String descCompareceu;
    private String descFaltou;
    private String descNaoSeAplica;
    private String descTransferido;
    private UsuarioTO usuario;
    private DiasLetivos diaLetivo;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public ScreenSlideFragmentLancamento(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        activity = getActivity();
        Analytics.setTela(activity, activity.getClass().toString());

        siglaCompareceu = getResources().getString(R.string.sigla_compareceu);
        siglaFaltou = getResources().getString(R.string.sigla_falta);
        siglaNaoSeAplica = getResources().getString(R.string.sigla_nao_se_aplica);
        siglaTransferido = getResources().getString(R.string.sigla_transferido);
        descCompareceu = getResources().getString(R.string.desc_compareceu);
        descFaltou = getResources().getString(R.string.desc_falta);
        descNaoSeAplica = getResources().getString(R.string.desc_nao_se_aplica);
        descTransferido = getResources().getString(R.string.desc_transferido);

        Bundle bundle = getArguments();
        position = bundle.getInt("posicao");
        alunos = (ArrayList<Aluno>) bundle.getSerializable("listaAlunos");
        aulas = (ArrayList<Aula>) bundle.getSerializable("listaAulaSelecionada");
        aula = (Aula) bundle.getSerializable("aula");

        turmaGrupo = (TurmaGrupo) bundle.getSerializable("turmaGrupo");
        bimestreAtual = turmaGrupo.getTurma().getBimestreAtual();

        usuario = (UsuarioTO) bundle.getSerializable("usuario");
        data = bundle.getString("data");
        hora = bundle.getString("hora");

        diaLetivo = FrequenciaQueryDB.getDiaLetivo(activity, data);

        aluno = alunos.get(position);
        aluno.setComparecimento(FrequenciaQueryDB.getComparecimento(getActivity(), aluno.getId(), aula, data));

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_screen_slide_lancamento_frequencia, container, false);
        TextView tvNome = (TextView) layout.findViewById(R.id.tvNome);
        btNaoAplica = (Button) layout.findViewById(R.id.bt_nao_aplica);
        btFalta = (Button) layout.findViewById(R.id.bt_falta);
        btCompareceu = (Button) layout.findViewById(R.id.bt_compareceu);
        tvStatus = (TextView) layout.findViewById(R.id.tv_status);
        tvFaltasAno = (TextView) layout.findViewById(R.id.tvFaltasAno);
        tvFaltasBimestre = (TextView) layout.findViewById(R.id.tvFaltasBimestre);
        tvFaltasSequencia = (TextView) layout.findViewById(R.id.tvFaltasSequencia);
        TextView tvTituloAnual = (TextView) layout.findViewById(R.id.tv_titulo_anual);
        TextView tvTituloBimestral = (TextView) layout.findViewById(R.id.tv_titulo_bimestral);

        if (aluno.getAlunoAtivo() == 1) {
            btNaoAplica.setEnabled(true);
            btCompareceu.setEnabled(true);
            btFalta.setEnabled(true);

            if (!alunos.get(position).getComparecimento().equals(descTransferido)) {
                tvStatus.setVisibility(View.VISIBLE);
                if (alunos.get(position).getComparecimento().equals(descFaltou)) {
                    tvStatus.setText(descFaltou);
                    tvStatus.setTextColor(getResources().getColor(R.color.red_sangue));
                }else if (alunos.get(position).getComparecimento().equals(descCompareceu)) {
                    tvStatus.setText(descCompareceu);
                    tvStatus.setTextColor(getResources().getColor(R.color.blue_marinho));
                } else if (alunos.get(position).getComparecimento().equals(descNaoSeAplica)) {
                    tvStatus.setText(descNaoSeAplica);
                    tvStatus.setTextColor(getResources().getColor(R.color.amarelo_texto_dia_letivo));
                }
            }
        } else {
            tvStatus.setVisibility(View.VISIBLE);
            tvStatus.setText(descTransferido);
            tvStatus.setTextColor(getResources().getColor(R.color.amarelo_texto_dia_letivo));
            btNaoAplica.setEnabled(false);
            btCompareceu.setEnabled(false);
            btFalta.setEnabled(false);
            aluno.setComparecimento(siglaTransferido);
            alunos.get(position).setComparecimento(siglaTransferido);
            FrequenciaQueryDB.setComparecimento(getActivity(), usuario, diaLetivo, aula, aluno.getId(), siglaTransferido, data, hora);
        }

        int aulasAno = turmaGrupo.getTurma().getAulasAno();

        aluno = FrequenciaQueryDB.getTotalFaltas(getActivity(), aluno, turmaGrupo.getDisciplina().getId());

        //exibição de faltas e porcentagem
        if (aluno.getFaltasAnuais() == 0){
            tvFaltasAno.setText(String.valueOf(aluno.getFaltasAnuais()));
        } else {
            Double cCom=((double)aluno.getFaltasAnuais()/turmaGrupo.getTurmasFrequencia().getAulasAno())*100;
            tvFaltasAno.setText(aluno.getFaltasAnuais() + " ("+ String.format("%.2f", cCom) +"%)");
            if (cCom >= 20){
                tvFaltasAno.setTypeface(null, Typeface.BOLD);
                tvTituloAnual.setTypeface(null, Typeface.BOLD);
            }
        }

        Double cCom = null;
        Log.i("Bimestre atual: ", String.valueOf(bimestreAtual));

        int aulasBimestre = turmaGrupo.getTurma().getAulasBimestre();
        if (aluno.getFaltasBimestre() == 0){
            tvFaltasBimestre.setText(String.valueOf(aluno.getFaltasBimestre()));
        } else {
            cCom=((double)aluno.getFaltasBimestre()/turmaGrupo.getTurmasFrequencia().getAulasBimestre())*100;
            tvFaltasBimestre.setText(aluno.getFaltasBimestre() + " ("+ String.format("%.2f", cCom) + "%)");
        }

        if (cCom != null && cCom >= 20){
            tvFaltasBimestre.setTypeface(null, Typeface.BOLD);
            tvTituloBimestral.setTypeface(null, Typeface.BOLD);
        }

        tvFaltasSequencia.setText(String.valueOf(aluno.getFaltasSequenciais()));

        tvNome.setText(aluno.getNumeroChamada() + "/" + turmaGrupo.getTurma().getAlunos().size() + " - " + aluno.getNomeAluno());
        btNaoAplica.setOnClickListener(onClickListenerImgButtons);
        btFalta.setOnClickListener(onClickListenerImgButtons);
        btCompareceu.setOnClickListener(onClickListenerImgButtons);

        return layout;
    }

    public View.OnClickListener onClickListenerImgButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(buttonClick);
            tvStatus.setVisibility(View.VISIBLE);
            if (v == btFalta){
                tvStatus.setText(descFaltou);
                tvStatus.setTextColor(getResources().getColor(R.color.red_sangue));
                aluno.setComparecimento(siglaFaltou);
                alunos.get(position).setComparecimento(siglaFaltou);
                FrequenciaQueryDB.setComparecimento(getActivity(), usuario, diaLetivo, aula, aluno.getId(), siglaFaltou, data, hora);
            } else if(v == btCompareceu) {
                tvStatus.setText(descCompareceu);
                tvStatus.setTextColor(getResources().getColor(R.color.blue_marinho));
                aluno.setComparecimento(siglaCompareceu);
                alunos.get(position).setComparecimento(siglaCompareceu);
                FrequenciaQueryDB.setComparecimento(getActivity(), usuario, diaLetivo, aula, aluno.getId(), siglaCompareceu, data, hora);
            }
            else if(v == btNaoAplica){
                tvStatus.setText(descNaoSeAplica);
                tvStatus.setTextColor(getResources().getColor(R.color.amarelo_texto_dia_letivo));
                aluno.setComparecimento(siglaNaoSeAplica);
                alunos.get(position).setComparecimento(siglaNaoSeAplica);
                FrequenciaQueryDB.setComparecimento(getActivity(), usuario, diaLetivo, aula, aluno.getId(), siglaNaoSeAplica, data, hora);
            }
            Log.d("POSITION", String.valueOf(position));
            Log.d("ALUNOS", String.valueOf(alunos.size()));

            /*//Assim que for feito o último lançamento, o botão de confirmação no Toolbar é ativado
            if (position + 1 == alunos.size()){
                setItem(verificaChamadaCompleta());
            }*/

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {

                        TimeUnit.MILLISECONDS.sleep(500);

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fragmentLancamentoFrequenciaPager.nextAluno(); //Chamada do método nextAluno à partir da variável de instância do FragmentLancamentoFrequenciaPager
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }).start();
        }
    };

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_lancamento_frequencia, menu);

        this.menu = menu;
        //setItem(verificaChamadaCompleta());

    }

    public boolean onOptionsItemSelected( MenuItem item ) {
        switch (item.getItemId()) {
            /*case R.id.action_confirmar:
                verificaAulas(aulas);
                return true;*/
            case R.id.action_listar:
                fragmentLancamentoFrequenciaPager.listar();
                flagMenu = true; //Alterado para 'true' para aparecer a lista em qualquer ocasião
                return true;
            /*case R.id.action_pesquisa:
                SearchView sv = new SearchView(getActivity());
                int searchButtonId = sv.getContext().getResources().getIdentifier("android:id/search_button", null, null);

                //Botao pesquisa
                ImageView searchButton = (ImageView) sv.findViewById(searchButtonId);
                searchButton.performClick();
                return true;*/
            default:
                return super.onOptionsItemSelected( item );
        }
    }

}