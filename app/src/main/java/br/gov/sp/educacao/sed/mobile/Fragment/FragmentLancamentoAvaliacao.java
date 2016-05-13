package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.gov.sp.educacao.sed.mobile.Adapter.AvaliacaoAdapter;
import br.gov.sp.educacao.sed.mobile.Modelo.Aula;
import br.gov.sp.educacao.sed.mobile.Modelo.Avaliacao;
import br.gov.sp.educacao.sed.mobile.Modelo.Bimestre;
import br.gov.sp.educacao.sed.mobile.Modelo.DiasLetivos;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmasFrequencia;
import br.gov.sp.educacao.sed.mobile.QueryDB.AtualizarBancoQueryDB;
import br.gov.sp.educacao.sed.mobile.QueryDB.AvaliacaoQueryDB;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;
import br.gov.sp.educacao.sed.mobile.Util.DateUtils;

/**
 * Created by techresult on 24/08/2015.
 */
public class FragmentLancamentoAvaliacao extends Fragment {

    private LinearLayout layout;
    private Bundle bundle;
    private TurmaGrupo turmaGrupo;
    private Button btNova;

    private AlertDialog alert;
    private Activity activity;
    private AppCompatActivity appCompatActivity;
    private TextView tvDia;
    private TextView tvDiaAlert;
    private EditText viewError;
    private ArrayList<Avaliacao> avaliacaoArrayList;
    private ArrayList<Avaliacao> avaliacaoBimestreArrayList;
    private ListView lvAvaliacoes;
    private AvaliacaoAdapter avaliacaoAdapter;
    private List<DiasLetivos> listaDiasLetivos;
    private List<Integer> listaDiaSemana;
    private List<Aula> listaAula;
    private List<TurmasFrequencia> listaTurmasFrequencia;
    private Bimestre bimestre;
    private List<String> listaDiasLetivosStr;
    private Bundle savedInstance;

    //Calendario
    private CaldroidFragment dialogCaldroidFragment;
    private CaldroidListener listener;
    private ArrayList<String> disableDateStrings;
    int mesSelecionado;
    private String data;
    private int diasMes;

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        bundle = getArguments();
        turmaGrupo = (TurmaGrupo) bundle.getSerializable(TurmaGrupo.BUNDLE_TURMA_GRUPO);

        listaTurmasFrequencia = AtualizarBancoQueryDB.getTurmasFrequencia(getActivity(), turmaGrupo.getTurma());
        bimestre = AtualizarBancoQueryDB.getBimestre(getActivity(), turmaGrupo.getTurmasFrequencia());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        savedInstance = savedInstanceState;

        activity = getActivity();
        Analytics.setTela(activity, activity.getClass().toString());

        appCompatActivity = (AppCompatActivity) getActivity();

        layout = (LinearLayout) inflater.inflate(R.layout.fragment_lancamento_avaliacao, container, false);

        bundle = getArguments();
        turmaGrupo = (TurmaGrupo) bundle.getSerializable(TurmaGrupo.BUNDLE_TURMA_GRUPO);

        listaTurmasFrequencia = AtualizarBancoQueryDB.getTurmasFrequencia(getActivity(), turmaGrupo.getTurma());

        bimestre = AtualizarBancoQueryDB.getBimestre(getActivity(), turmaGrupo.getTurmasFrequencia());

        try {
            listaAula = AvaliacaoQueryDB.getAula(getActivity(), turmaGrupo.getDisciplina());
            listaDiaSemana = new ArrayList<>();
            for (Aula aulaFor : listaAula) {
                if (!listaDiaSemana.contains(aulaFor.getDiaSemana())){
                    listaDiaSemana.add(aulaFor.getDiaSemana());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvDia = (TextView) layout.findViewById(R.id.tv_dia);
        listaDiasLetivos = AtualizarBancoQueryDB.getDiasLetivos(getActivity(), bimestre, listaDiaSemana);

        listaDiasLetivosStr = new ArrayList<>();

        for(DiasLetivos diasLetivosFor : listaDiasLetivos){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date = sdf.parse(diasLetivosFor.getDataAula());
                listaDiasLetivosStr.add(sdf.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //Caldroid
        dialogCaldroidFragment = new CaldroidFragment();
        getCaldroid(savedInstanceState);

        //Setup do Listener do Caldroid
        listener = getListenerCaldroid();

        //Seta o Listener ao dialogCaldroid
        dialogCaldroidFragment.setCaldroidListener(listener);

        tvDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCaldroidFragment = new CaldroidFragment();
                getCaldroid(savedInstanceState);
                dialogCaldroidFragment.setCaldroidListener(getListenerCaldroid());
            }
        });

        btNova = (Button) layout.findViewById(R.id.bt_nova);

        /** Cadastro de uma nova avaliaçao **/
        btNova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertbuilder = new AlertDialog.Builder(activity);
                alertbuilder.setTitle(activity.getResources().getString(R.string.nova_ava));

                alertbuilder.setPositiveButton(getResources().getString(R.string.cadastrar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                final LinearLayout.LayoutParams paramslayoutmain = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                paramslayoutmain.setMargins(20, 20, 20, 20);

                View view = activity.getLayoutInflater().inflate(R.layout.alert_avaliacao, null);
                alertbuilder.setView(view);

                final EditText edNome = (EditText) view.findViewById(R.id.ed_nome);
                tvDiaAlert = (TextView) view.findViewById(R.id.tv_dia_alert);
                final Spinner spTipo = (Spinner) view.findViewById(R.id.spTipo);
                final CheckBox ckVale = (CheckBox) view.findViewById(R.id.ckVale);

                tvDiaAlert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogCaldroidFragment = new CaldroidFragment();
                        getCaldroid(savedInstanceState);
                        dialogCaldroidFragment.setCaldroidListener(getListenerCaldroid());
                    }
                });

                TextView tvBimestreAtual = (TextView) view.findViewById(R.id.tv_bimestre_atual);
                tvBimestreAtual.setText(bimestre.getNumero() + "º");

                alert = alertbuilder.create();
                alert.show();
                alert.setCanceledOnTouchOutside(false);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (edNome.getText().toString().length() > 0) {

                            if (spTipo.getSelectedItemPosition() == 0) {
                                Toast.makeText(activity, getResources().getString(R.string.selecione_tipo), Toast.LENGTH_LONG).show();
                            }
                            else if(tvDiaAlert.getText().toString().equals("DD/MM/AAAA")){
                                Toast.makeText(activity, getResources().getString(R.string.hintdata), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                final Avaliacao avaliacao = new Avaliacao();

                                avaliacao.setCodTurma(turmaGrupo.getTurma().getCodigoTurma());
                                avaliacao.setCodDisciplina(turmaGrupo.getDisciplina().getCodigoDisciplina());

                                avaliacao.setNome(edNome.getText().toString());
                                avaliacao.setData(tvDiaAlert.getText().toString());
                                avaliacao.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date()));
                                avaliacao.setBimestre(bimestre.getNumero());

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
                                avaliacao.setValeNota(ckVale.isChecked());
                                avaliacao.setTurmaId(turmaGrupo.getTurma().getId());
                                avaliacao.setDisciplinaId(turmaGrupo.getDisciplina().getId());

                                avaliacao.setCodigo(AvaliacaoQueryDB.getUltimoCodigoAvaliacao(activity, avaliacao));

                                AvaliacaoQueryDB.insertAvaliacao(activity, avaliacao, turmaGrupo.getTurma());
                                avaliacaoArrayList.add(avaliacao);
                                refreshLista();
                                alert.dismiss();
                            }

                        } else {
                            Toast.makeText(activity, getResources().getString(R.string.complete_campos), Toast.LENGTH_SHORT).show();
                            viewError = edNome;
                            viewError.setError(getResources().getString(R.string.obrigatorio));
                        }
                    }
                });
            }
        });

        TextView tvTurma = (TextView) layout.findViewById(R.id.tv_turma);
        tvTurma.setText(turmaGrupo.getTurma().getNomeTurma() + " / " + turmaGrupo.getDisciplina().getNomeDisciplina());

        lvAvaliacoes = (ListView) layout.findViewById(R.id.lv_avaliacoes);

        lvAvaliacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Avaliacao avaliacao = avaliacaoBimestreArrayList.get(position);
                bundle.putSerializable(TurmaGrupo.BUNDLE_TURMA_GRUPO, turmaGrupo);
                bundle.putSerializable(Avaliacao.BUNDLE_AVALIACAO, avaliacao);

                Fragment fragment = Fragment.instantiate(activity, "br.gov.sp.educacao.sed.mobile.Fragment.FragmentLancamentoAvaliacaoPager");
                fragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frequencia_lancamento, fragment, "FragAvaliacaoLancamentoPager");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return layout;
    }

    String stripAccents(String string) {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return string;
    }

    public void getCaldroid(Bundle savedInstanceState){
        final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
        if (savedInstanceState != null) {
            dialogCaldroidFragment.restoreDialogStatesFromKey(
                    getFragmentManager(), savedInstanceState,
                    "DIALOG_CALDROID_SAVED_STATE", dialogTag);
            Bundle args = dialogCaldroidFragment.getArguments();
            if (args == null) {
                args = new Bundle();
                args.putInt(CaldroidFragment.THEME_RESOURCE, R.style.CalDroidTheme);
                dialogCaldroidFragment.setArguments(args);
            }
        } else {
            // Setup arguments
            Bundle bundle = new Bundle();
            bundle.putInt(CaldroidFragment.THEME_RESOURCE, R.style.CalDroidTheme);
            // Setup dialogTitle
            dialogCaldroidFragment.setArguments(bundle);
        }

        dialogCaldroidFragment.show(getFragmentManager(),
                dialogTag);
    }

    public CaldroidListener getListenerCaldroid(){

        listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                int dia = DateUtils.getCurrentDay(date);
                int mes = DateUtils.getCurrentMonth(date);
                int ano = DateUtils.getCurrentYear(date);

                if(mes == mesSelecionado) {
                    data = dia + "/" + mes + "/" + ano;
                    tvDia.setText(data);
                    if(tvDiaAlert != null){
                        tvDiaAlert.setText(data);
                    }

                    onDateSelected(date);

                    dialogCaldroidFragment.dismiss();
                }
            }

            public void onChangeMonth(int month, int year){
                //limpa dias do mes anterior
                int diasMesAnterior = DateUtils.totalDiasMes(month - 2, year);
                for (int i = 0; i < diasMesAnterior; i++) {
                    Calendar calendarAnterior = Calendar.getInstance();
                    calendarAnterior.set(year, month - 2, i+1);
                    Date dataLetivoNext = calendarAnterior.getTime();
                    dialogCaldroidFragment.clearBackgroundResourceForDate(dataLetivoNext);
                    dialogCaldroidFragment.clearTextColorForDate(dataLetivoNext);
                }

                //limpa dias do mes seguinte
                int diasMesNext = DateUtils.totalDiasMes(month, year);
                for (int i = 0; i < diasMesNext; i++) {
                    Calendar calendarNext = Calendar.getInstance();
                    calendarNext.set(year, month, i+1);
                    Date dataLetivoNext = calendarNext.getTime();
                    dialogCaldroidFragment.clearBackgroundResourceForDate(dataLetivoNext);
                    dialogCaldroidFragment.clearTextColorForDate(dataLetivoNext);
                }

                //Obt?m o total de dias no m?s
                diasMes = DateUtils.totalDiasMes(month - 1, year);

                ArrayList<Integer> totalDiasMes = new ArrayList<>();

                for(int i = 0; i < diasMes; i++){
                    totalDiasMes.add(i + 1);
                }

                ArrayList<Integer> diasLetivosConfirmados = new ArrayList<>();

                Calendar calendar = Calendar.getInstance();
                int anoAtual = calendar.get(Calendar.YEAR);

                SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
                Date date = null;
                //Estiliza o background dos dias letivos
                for(int i = 0; i < listaDiasLetivosStr.size(); i++){
                    try {
                        date = sdf.parse(listaDiasLetivosStr.get(i));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);

                    int diaLetivoMes = cal.get(Calendar.DAY_OF_MONTH);

                    //calendar.set(Calendar.DAY_OF_MONTH, diasLetivos.get(i));
                    if(month - 1 == cal.get(Calendar.MONTH)){
                        calendar.set(year, month - 1, diaLetivoMes);

                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                        try {
                            Date inicioBimestre = sdf.parse(bimestre.getInicio());
                            Date fimBimestre = sdf.parse(bimestre.getFim());

                            for(int j = 0; j < listaDiaSemana.size(); j++){
                                if(listaDiaSemana.get(j) + 1 == dayOfWeek){
                                    String strDataLetivo = sdf.format(calendar.getTime());
                                    Date dataLetivo = sdf.parse(strDataLetivo);

                                    String strInicioBimestre = sdf.format(inicioBimestre);
                                    Date dataInicioBimestre = sdf.parse(strInicioBimestre);

                                    String strFimBimestre = sdf.format(fimBimestre);
                                    Date dataFimBimestre = sdf.parse(strFimBimestre);

                                    if((dataLetivo.after(dataInicioBimestre) || dataLetivo.equals(dataInicioBimestre)) &&
                                            (dataLetivo.before(dataFimBimestre) || dataLetivo.equals(dataFimBimestre))){
                                        dialogCaldroidFragment.setBackgroundResourceForDate(R.color.azul_dia_letivo, dataLetivo);
                                        dialogCaldroidFragment.setTextColorForDate(R.color.azul_texto_dia_letivo, dataLetivo);

                                        diasLetivosConfirmados.add(diaLetivoMes);
                                    }
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }

                //Remove os dias letivos da lista totalDiasMes
                for(int i = 0; i < totalDiasMes.size(); i++){
                    for(int j = 0; j < diasLetivosConfirmados.size(); j++){
                        if(totalDiasMes.get(i) == diasLetivosConfirmados.get(j)){
                            totalDiasMes.remove(Integer.valueOf(diasLetivosConfirmados.get(j)));
                        }
                    }
                }



                //Cria o array de dias formatado
                disableDateStrings = new ArrayList<>();

                for(int i = 0; i < totalDiasMes.size(); i++){
                    disableDateStrings.add(DateUtils.parseDateNormal(totalDiasMes.get(i), month, year));
                    Log.d("DISABLED", String.valueOf(disableDateStrings.get(i)));
                }

                //Desabilita os dias n?o letivos
                dialogCaldroidFragment.setDisableDatesFromString(disableDateStrings);

                mesSelecionado = month;

                if(month == 1 && year > anoAtual){
                    dialogCaldroidFragment.prevMonth();
                }
                if(month == 12 && year < anoAtual){
                    dialogCaldroidFragment.nextMonth();
                }
            }

            public void onCaldroidViewCreated(){

            }
        };
        return listener;
    }

    public void onDateSelected(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        String strData = sdf.format(date);

        avaliacaoArrayList = AvaliacaoQueryDB.getListaAvaliacao(activity, turmaGrupo.getTurma(), turmaGrupo.getDisciplina(), strData);
        avaliacaoBimestreArrayList = new ArrayList<>();
        for (int i = 0; i < avaliacaoArrayList.size(); i++) {
            Avaliacao avaliacao = avaliacaoArrayList.get(i);
            if (avaliacao.getBimestre() == bimestre.getNumero()){
                avaliacaoBimestreArrayList.add(avaliacao);
            }
            avaliacaoAdapter = new AvaliacaoAdapter(activity, turmaGrupo, avaliacaoBimestreArrayList, listaDiasLetivosStr, FragmentLancamentoAvaliacao.this, savedInstance);
            avaliacaoAdapter.fragmentLancamentoAvaliacao = FragmentLancamentoAvaliacao.this;
            lvAvaliacoes.setAdapter(avaliacaoAdapter);
        }

        if(avaliacaoBimestreArrayList.size() == 0){
            lvAvaliacoes.setAdapter(null);
        }
    }

    public void refreshLista(){

        avaliacaoArrayList = AvaliacaoQueryDB.getListaAvaliacao(activity, turmaGrupo.getTurma(), turmaGrupo.getDisciplina(), tvDia.getText().toString());
        avaliacaoBimestreArrayList = new ArrayList<>();
        for (int i = 0; i < avaliacaoArrayList.size(); i++) {
            Avaliacao avaliacao = avaliacaoArrayList.get(i);
            if(avaliacao.getBimestre() == bimestre.getNumero()){
                avaliacaoBimestreArrayList.add(avaliacao);
            }
            avaliacaoAdapter = new AvaliacaoAdapter(activity,turmaGrupo, avaliacaoBimestreArrayList, listaDiasLetivosStr, FragmentLancamentoAvaliacao.this, savedInstance);
            avaliacaoAdapter.fragmentLancamentoAvaliacao = FragmentLancamentoAvaliacao.this;
            lvAvaliacoes.setAdapter(avaliacaoAdapter);
        }
    }
}