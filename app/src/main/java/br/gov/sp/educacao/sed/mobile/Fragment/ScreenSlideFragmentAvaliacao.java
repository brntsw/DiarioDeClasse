package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Modelo.Avaliacao;
import br.gov.sp.educacao.sed.mobile.Modelo.NotasAluno;
import br.gov.sp.educacao.sed.mobile.Modelo.Turma;
import br.gov.sp.educacao.sed.mobile.QueryDB.AvaliacaoQueryDB;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 26/08/2015.
 */
public class ScreenSlideFragmentAvaliacao extends Fragment {

    private Activity activity;
    private int position;
    private ArrayList<Aluno> alunos;
    private Turma turma;
    private Aluno aluno;
    private Avaliacao avaliacao;
    private TextView tvNome, tvStatus;
    private NumberPicker numberOfPlayersPicker, numberOfPlayersPickerDezena, numberOfPlayersPickerCentena;
    private Button btConfirma;
    private Menu menu;
    private MenuItem item;
    private UsuarioTO usuario;

    public FragmentLancamentoAvaliacaoPager fragmentLancamentoAvaliacaoPager;

    public ScreenSlideFragmentAvaliacao() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = getActivity();
        Analytics.setTela(activity, activity.getClass().toString());

        Bundle bundle = getArguments();
        position = bundle.getInt("posicao");
        usuario = Queries.getUsuarioAtivo(activity);
        alunos = (ArrayList<Aluno>) bundle.getSerializable("listaAlunos");
        turma = (Turma) bundle.getSerializable("turma");
        avaliacao = (Avaliacao) bundle.getSerializable("avaliacao");

        aluno = alunos.get(position);

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_screen_slide_lancamento_avaliacao, container, false);

        tvStatus = (TextView) layout.findViewById(R.id.tv_status);

        tvNome = (TextView) layout.findViewById(R.id.tvNome);
        tvNome.setText(aluno.getNumeroChamada() + " - " + aluno.getNomeAluno());

        btConfirma = (Button) layout.findViewById(R.id.bt_confirma);
        numberOfPlayersPicker = (NumberPicker) layout.findViewById(R.id.numberOfPlayersPicker);
        numberOfPlayersPicker.setMaxValue(11);
        numberOfPlayersPicker.setMinValue(0);
        numberOfPlayersPicker.setDisplayedValues(new String[]{"S/N", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});

        numberOfPlayersPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 0 || newVal == 11) {
                    numberOfPlayersPickerDezena.setValue(0);
                    numberOfPlayersPickerCentena.setValue(0);
                }
            }
        });

        numberOfPlayersPickerDezena = (NumberPicker) layout.findViewById(R.id.numberOfPlayersPickerDezena);
        numberOfPlayersPickerDezena.setMaxValue(9);
        numberOfPlayersPickerDezena.setMinValue(0);

        numberOfPlayersPickerDezena.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (numberOfPlayersPicker.getValue() == 0 || numberOfPlayersPicker.getValue() == 11) {
                    numberOfPlayersPickerDezena.setValue(0);
                }
            }
        });

        numberOfPlayersPickerCentena = (NumberPicker) layout.findViewById(R.id.numberOfPlayersPickerCentena);
        numberOfPlayersPickerCentena.setMaxValue(9);
        numberOfPlayersPickerCentena.setMinValue(0);

        numberOfPlayersPickerCentena.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (numberOfPlayersPicker.getValue() == 0 || numberOfPlayersPicker.getValue() == 11) {
                    numberOfPlayersPickerCentena.setValue(0);
                }
            }
        });

        NotasAluno notasAluno = new NotasAluno();
        notasAluno.setAluno_id(aluno.getId());
        notasAluno.setAvaliacao_id(avaliacao.getId());

        String nota = AvaliacaoQueryDB.getNotasAluno(activity, notasAluno);

        if (aluno.getAlunoAtivo() == 1){
            numberOfPlayersPicker.setEnabled(true);
            numberOfPlayersPickerDezena.setEnabled(true);
            numberOfPlayersPickerCentena.setEnabled(true);
            btConfirma.setEnabled(true);
            if (!nota.equals("12")){
                if (nota.equals("11")){
                    tvStatus.setText("Sem nota");
                } else {
                    if(!nota.equals("")) {
                        tvStatus.setText("Nota " + (nota.length() == 3 ? nota.replace(".", ",") + "0" : nota.replace(".", ",")));
                        numberOfPlayersPicker.setValue(Integer.parseInt(nota.replace(".", ",").split(",")[0]) + 1);
                        numberOfPlayersPickerDezena.setValue(Integer.parseInt(nota.replace(".", ",").split(",")[1].substring(0, 1)));
                        try{
                            numberOfPlayersPickerCentena.setValue(Integer.parseInt(nota.replace(".", ",").split(",")[1].substring(1, 2)));
                        }
                        catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e){
                            numberOfPlayersPickerCentena.setValue(Integer.parseInt("0"));
                        }
                        //numberOfPlayersPicker.setValue(nota + 1);
                    }
                }
            }
        }
        else {
            tvStatus.setVisibility(View.VISIBLE);
            tvStatus.setText("Transferido");
            tvStatus.setTextColor(getResources().getColor(R.color.amarelo_texto_dia_letivo));
            numberOfPlayersPicker.setEnabled(false);
            numberOfPlayersPickerDezena.setEnabled(false);
            numberOfPlayersPickerCentena.setEnabled(false);
            btConfirma.setEnabled(false);

            notasAluno = new NotasAluno();
            notasAluno.setAluno_id(aluno.getId());
            notasAluno.setCodigoMatricula(aluno.getCodigoMatricula());
            notasAluno.setAvaliacao_id(avaliacao.getId());
            notasAluno.setUsuario_id(usuario.getId());
            notasAluno.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date()));
            notasAluno.setNota("12");

            AvaliacaoQueryDB.setNotasAluno(activity, notasAluno);
        }

        if(!avaliacao.isValeNota()){
            numberOfPlayersPicker.setEnabled(false);
            numberOfPlayersPickerDezena.setEnabled(false);
            numberOfPlayersPickerCentena.setEnabled(false);
            btConfirma.setEnabled(false);
        }

        btConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotasAluno notasAluno = new NotasAluno();
                notasAluno.setAluno_id(aluno.getId());
                notasAluno.setCodigoMatricula(aluno.getCodigoMatricula());
                notasAluno.setAvaliacao_id(avaliacao.getId());
                notasAluno.setUsuario_id(usuario.getId());
                notasAluno.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date()));

                if (numberOfPlayersPicker.getValue() == 0) {
                    tvStatus.setText("Sem nota");
                    notasAluno.setNota("11");
                    AvaliacaoQueryDB.setNotasAluno(activity, notasAluno);
                } else {
                    tvStatus.setText("Nota " + (numberOfPlayersPicker.getValue() - 1) + "," + numberOfPlayersPickerDezena.getValue() + numberOfPlayersPickerCentena.getValue());
                    notasAluno.setNota((numberOfPlayersPicker.getValue() - 1) + "." + numberOfPlayersPickerDezena.getValue() + "" + numberOfPlayersPickerCentena.getValue());
                    AvaliacaoQueryDB.setNotasAluno(activity, notasAluno);
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            TimeUnit.MILLISECONDS.sleep(500);

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //setItem(verificaAvaliacaoCompleta());
                                    fragmentLancamentoAvaliacaoPager.nextAluno(); //Chamada do metodo nextAluno a partir da variavel de instancia do FragmentLancamentoFrequenciaPager
                                    //fragmentLancamentoAvaliacaoPager.setItem();
                                }
                            });

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                }).start();
            }
        });

        return layout;

    }

}
