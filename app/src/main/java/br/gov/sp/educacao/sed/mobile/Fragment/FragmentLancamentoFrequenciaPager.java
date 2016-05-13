package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Modelo.Aula;
import br.gov.sp.educacao.sed.mobile.Modelo.Bimestre;
import br.gov.sp.educacao.sed.mobile.Modelo.DiasLetivos;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.QueryDB.AlunoQueryDB;
import br.gov.sp.educacao.sed.mobile.QueryDB.AtualizarBancoQueryDB;
import br.gov.sp.educacao.sed.mobile.QueryDB.AvaliacaoQueryDB;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.FrequenciaQueryDB;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.DateUtils;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

public class FragmentLancamentoFrequenciaPager extends Fragment {
    private Activity activity;
    private RelativeLayout layout;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private int NUM_PAGES;
    private ArrayList<Aluno> listaAlunos;
    private ArrayList<Aula> listaAula;
    private ArrayList<Aula> listaAulaSelecionada;
    private ArrayList<DiasLetivos> listaDiasLetivos;
    private ArrayList<String> listaDiasLetivosStr;
    private Bimestre bimestre;
    private Aula aulaEspecifica;

    private ArrayList<Integer> listaDiaSemana;
    private ScreenSlideFragmentLancamento fragmentSlideLancamento;

    private int anoAtual;
    private TextView tvDia;
    private TextView tvHorario;
    private LinearLayout linearHorario, listaLancamento, layoutPager;
    private LayoutInflater inflaterPager;
    private Button btnAnterior, btnProximo;
    private Bundle bundle;
    private Date dataAtual;
    private TurmaGrupo turmaGrupo;
    public boolean ultimo;

    //Calendario
    private CaldroidFragment dialogCaldroidFragment;
    private CaldroidListener listener;
    private ArrayList<String> disableDateStrings;
    int mesSelecionado;
    private String data;
    private int diasMes;

    private UsuarioTO usuario;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        activity = getActivity();
        dataAtual = new Date();

        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_lancamento_pager, container, false);

        bundle = getArguments();
        tvHorario = (TextView) layout.findViewById(R.id.tv_horario);
        linearHorario = (LinearLayout) layout.findViewById(R.id.linearHorario);
        tvDia = (TextView) layout.findViewById(R.id.tv_dia);

        usuario = Queries.getUsuarioAtivo(activity);

        turmaGrupo = (TurmaGrupo) bundle.getSerializable("turmaGrupo");
        TextView tvTurma = (TextView) layout.findViewById(R.id.tv_turma);
        tvTurma.setText(turmaGrupo.getTurma().getNomeTurma() + " / " + turmaGrupo.getTurma().getNomeTipoEnsino());

        anoAtual = bundle.getInt("ano");

        listaAlunos = new ArrayList<>();
        for (int i = 0; i < turmaGrupo.getTurma().arrayAlunoSize(); i++) {
            Aluno aluno = turmaGrupo.getTurma().getAluno(i);
            listaAlunos.add(aluno);
        }

        turmaGrupo.getTurmasFrequencia();

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

        listaDiasLetivos = AtualizarBancoQueryDB.getDiasLetivos(getActivity(), bimestre, listaDiaSemana);

        listaDiasLetivosStr = new ArrayList<>();

        for(DiasLetivos diasLetivosFor : listaDiasLetivos){
            listaDiasLetivosStr.add(diasLetivosFor.getDataAula());
        }

        Collections.sort(listaAlunos, new Comparator<Aluno>() {
            @Override
            public int compare(Aluno p1, Aluno p2) {
                return p1.getNumeroChamada() - p2.getNumeroChamada();
            }

        });

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

        NUM_PAGES = listaAlunos.size();

        listaLancamento = (LinearLayout) layout.findViewById(R.id.lista_lancamento);

        inflaterPager = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutPager = (LinearLayout) inflaterPager.inflate(R.layout.layout_pager_alunos, container, false);
        listaLancamento.addView(layoutPager);

        mPager = (ViewPager) layoutPager.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        //mPager.setAdapter(mPagerAdapter);

        //Botoes Anterior e Proximo
        btnAnterior = (Button) layoutPager.findViewById(R.id.btnAnterior);
        btnProximo = (Button) layoutPager.findViewById(R.id.btnProximo);
        //layButtons = (LinearLayout) layoutPager.findViewById(R.id.lay_buttons);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    btnAnterior.setEnabled(false);
                    btnAnterior.setClickable(false);
                }
                else{
                    btnAnterior.setEnabled(true);
                    btnAnterior.setClickable(true);
                }

                if (position + 1 == NUM_PAGES) {
                    btnProximo.setText("LISTAR");
                    btnProximo.setBackgroundResource(R.drawable.btn_listar);
                }
                else{
                    btnProximo.setText(R.string.proximo);
                    btnProximo.setBackgroundResource(R.drawable.btn_screen_slide_lancamento);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //A�oes Botoes
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPager.getCurrentItem() + 1 == listaAlunos.size()){
                    //Toast.makeText(getActivity(), "ultimo", Toast.LENGTH_LONG).show();
                    ultimo = true;
                    listar();

                } else {
                    ultimo = false;
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                }

            }
        });

        return layout;
    }

    public void refreshLista(){
        listaLancamento.removeAllViews();
        listaLancamento.addView(layoutPager);

        goToAluno(0);
        //
        ultimo = false;
        //Botoes Anterior e Proximo
        btnAnterior = (Button) layoutPager.findViewById(R.id.btnAnterior);
        btnProximo = (Button) layoutPager.findViewById(R.id.btnProximo);
    }

    public void onResume(){
        super.onResume();

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {

            Bundle bundle = new Bundle();
            bundle.putInt("posicao", position);
            bundle.putString("data", tvDia.getText().toString());
            bundle.putString("hora", tvHorario.getText().toString());

            bundle.putSerializable("listaAlunos", listaAlunos);
            bundle.putSerializable("listaAulaSelecionada", listaAulaSelecionada);
            bundle.putSerializable("aula", aulaEspecifica);
            bundle.putSerializable("turmaGrupo", turmaGrupo);
            bundle.putSerializable("usuario", usuario);

            fragmentSlideLancamento = new ScreenSlideFragmentLancamento();
            fragmentSlideLancamento.fragmentLancamentoFrequenciaPager = FragmentLancamentoFrequenciaPager.this;
            fragmentSlideLancamento.setArguments(bundle);

            return fragmentSlideLancamento;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void nextAluno(){
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    public void goToAluno(int posicao){
        mPager.setCurrentItem(posicao, true);
    }

    public void getComparecimento(){
        for(Aluno alunoFor : listaAlunos){
            alunoFor.setComparecimento(FrequenciaQueryDB.getSiglaComparecimento(getActivity(), alunoFor.getId(), aulaEspecifica, tvDia.getText().toString()));
        }
    }

    public void onDateSelected(Date date) {

        final Date dataSelecionada = date;

        int mes = DateUtils.getCurrentMonth(date);

        listaAulaSelecionada = new ArrayList<>();

        int dia = DateUtils.getCurrentDay(date);
        int ano = DateUtils.getCurrentYear(date);

        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, mes - 1, dia);
        int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
        final ArrayList<String> listahorario = new ArrayList<>();
        for (int i = 0; i < listaAula.size(); i++) {
            if (listaAula.get(i).getDiaSemana() + 1 == diaSemana){
                Aula aula = listaAula.get(i);
                listahorario.add(aula.getInicio() + "/" + aula.getFim());
                listaAulaSelecionada.add(aula);
            }
        }

        if(listahorario.size() > 1) {
            tvHorario.setBackgroundColor(getResources().getColor(R.color.transparente));
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getActivity().getResources().getString(R.string.horario));
            final CharSequence[] cs = listahorario.toArray(new CharSequence[listahorario.size()]);
            builder.setItems(cs, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    CharSequence selectedValue = cs[which];
                    tvHorario.setText(selectedValue);

                    aulaEspecifica = getAulaEspecifica(dataSelecionada);

                    if (getFragmentManager().findFragmentByTag("FragLancamentoLista") != null) {
                        getFragmentManager().findFragmentByTag("FragLancamentoLista").onDestroy();
                    }
                    getComparecimento();
                    mPager.setAdapter(mPagerAdapter);
                    btnAnterior.setEnabled(false);
                    btnAnterior.setClickable(false);
                    btnProximo.setText(R.string.proximo);
                    btnProximo.setBackgroundResource(R.drawable.btn_screen_slide_lancamento);

                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


        /*if (listahorario.size() > 2){
            tvHorario.setText("Ciclo I");
            linearHorario.setEnabled(false);
        }*/
        else if (listahorario.size() > 0){
            tvHorario.setText(listahorario.get(0));
            linearHorario.setClickable(true);
        }

        if(listahorario.size() == 1){
            tvHorario.setBackgroundColor(getResources().getColor(R.color.caldroid_lighter_gray));
        }

        linearHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listahorario.size() > 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getActivity().getResources().getString(R.string.horario));
                    final CharSequence[] cs = listahorario.toArray(new CharSequence[listahorario.size()]);
                    builder.setItems(cs, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            CharSequence selectedValue = cs[which];
                            tvHorario.setText(selectedValue);

                            aulaEspecifica = getAulaEspecifica(dataSelecionada);

                            if (getFragmentManager().findFragmentByTag("FragLancamentoLista") != null){
                                getFragmentManager().findFragmentByTag("FragLancamentoLista").onDestroy();
                            }
                            getComparecimento();
                            mPager.setAdapter(mPagerAdapter);
                            btnAnterior.setEnabled(false);
                            btnAnterior.setClickable(false);
                            btnProximo.setText(R.string.proximo);
                            btnProximo.setBackgroundResource(R.drawable.btn_screen_slide_lancamento);

                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        });

        aulaEspecifica = getAulaEspecifica(date);

        if (getFragmentManager().findFragmentByTag("FragLancamentoLista") != null){
            getFragmentManager().findFragmentByTag("FragLancamentoLista").onDestroy();
        }

        anoAtual = ano;

        data = dia + "/" + mes + "/" + anoAtual;

        mPager.setAdapter(mPagerAdapter);

        btnAnterior.setEnabled(false);
        btnAnterior.setClickable(false);

        btnProximo.setText(R.string.proximo);
        btnProximo.setBackgroundResource(R.drawable.btn_screen_slide_lancamento);

        getComparecimento();

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

                                    String strDataAtual = sdf.format(new Date());
                                    Date dataAtual = sdf.parse(strDataAtual);

                                    if((dataLetivo.before(dataAtual) || dataLetivo.equals(dataAtual)) &&
                                            (dataLetivo.before(fimBimestre) || dataLetivo.equals(fimBimestre))){
                                        if(dataLetivo.equals(dataAtual)){
                                            dialogCaldroidFragment.setBackgroundResourceForDate(R.color.amarelo_dia_letivo, dataLetivo);
                                            dialogCaldroidFragment.setTextColorForDate(R.color.amarelo_texto_dia_letivo, dataLetivo);
                                        }
                                        else{
                                            dialogCaldroidFragment.setBackgroundResourceForDate(R.color.azul_dia_letivo, dataLetivo);
                                            dialogCaldroidFragment.setTextColorForDate(R.color.azul_texto_dia_letivo, dataLetivo);
                                        }

                                        //Fazer a query para verificar se todos os registros de FALTASALUNOS da data e seus horários estão com
                                        //dataServidor preenchidos
                                        listaAulaSelecionada = new ArrayList<>();
                                        final ArrayList<String> listahorario = new ArrayList<>();
                                        for (int x = 0; x < listaAula.size(); x++) {
                                            if (listaAula.get(x).getDiaSemana() + 1 == dayOfWeek){
                                                Aula aula = listaAula.get(x);
                                                listahorario.add(aula.getInicio() + "/" + aula.getFim());
                                                listaAulaSelecionada.add(aula);
                                            }
                                        }

                                        DiasLetivos diaLetivo = FrequenciaQueryDB.getDiaLetivo(getActivity(), strDataLetivo);

                                        if(listahorario.size() > 1){
                                            Boolean chamadaCompleta = Boolean.TRUE;

                                            for(String horario : listahorario){
                                                Aula aula = AtualizarBancoQueryDB.getAula(getActivity(), horario.split("/")[0].trim(), horario.split("/")[1].trim(),
                                                        dayOfWeek - 1, turmaGrupo.getDisciplina().getId());

                                                int faltasEnviadas = FrequenciaQueryDB.getFaltasEnviadas(getActivity(), diaLetivo, aula);

                                                List<Aluno> alunosAtivos = AlunoQueryDB.getAlunosAtivos(getActivity(), listaAlunos);

                                                if(faltasEnviadas != alunosAtivos.size()){
                                                    chamadaCompleta = Boolean.FALSE;
                                                }
                                            }

                                            if(chamadaCompleta){
                                                dialogCaldroidFragment.setBackgroundResourceForDate(R.color.verde_dia_letivo, dataLetivo);
                                                dialogCaldroidFragment.setTextColorForDate(R.color.verde_texto_dia_letivo, dataLetivo);
                                            }
                                        }
                                        else if(listahorario.size() == 1){

                                            Aula aula = AtualizarBancoQueryDB.getAula(getActivity(), listahorario.get(0).split("/")[0].trim(), listahorario.get(0).split("/")[1].trim(),
                                                    dayOfWeek - 1, turmaGrupo.getDisciplina().getId());

                                            int faltasEnviadas = FrequenciaQueryDB.getFaltasEnviadas(getActivity(), diaLetivo, aula);

                                            List<Aluno> alunosAtivos = AlunoQueryDB.getAlunosAtivos(getActivity(), listaAlunos);

                                            if(faltasEnviadas == alunosAtivos.size()){
                                                dialogCaldroidFragment.setBackgroundResourceForDate(R.color.verde_dia_letivo, dataLetivo);
                                                dialogCaldroidFragment.setTextColorForDate(R.color.verde_texto_dia_letivo, dataLetivo);
                                            }
                                        }

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

    public void listar(){

        Bundle bundle = new Bundle();
        bundle.putString("data", tvDia.getText().toString());
        bundle.putString("hora", tvHorario.getText().toString());

        bundle.putSerializable("listaAlunos", listaAlunos);
        bundle.putSerializable("listaAulaSelecionada", listaAulaSelecionada);
        bundle.putSerializable("aula", aulaEspecifica);

        FragmentLancamentoLista fragmentLancamentoLista = new FragmentLancamentoLista();
        fragmentLancamentoLista.fragmentLancamentoFrequenciaPager = FragmentLancamentoFrequenciaPager.this;
        fragmentLancamentoLista.setArguments(bundle);

        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("FragLancamentoLista");

        if(fragment == null) {
            listaLancamento.removeAllViews();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.lista_lancamento, fragmentLancamentoLista, "FragLancamentoLista");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

    private Aula getAulaEspecifica(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int diaSemana = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        String horario = tvHorario.getText().toString();
        String inicioHora = horario.split("/")[0].trim();
        String fimHora = horario.split("/")[1].trim();

        return AtualizarBancoQueryDB.getAula(getActivity(), inicioHora, fimHora, diaSemana, turmaGrupo.getDisciplina().getId());
    }

}
