package br.gov.sp.educacao.sed.mobile.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.gov.sp.educacao.sed.mobile.Fragment.TreeView.ConteudoHolder;
import br.gov.sp.educacao.sed.mobile.Fragment.TreeView.HabilidadeHolder;
import br.gov.sp.educacao.sed.mobile.Modelo.Aula;
import br.gov.sp.educacao.sed.mobile.Modelo.Bimestre;
import br.gov.sp.educacao.sed.mobile.Modelo.Conteudo;
import br.gov.sp.educacao.sed.mobile.Modelo.DiasLetivos;
import br.gov.sp.educacao.sed.mobile.Modelo.Grupo;
import br.gov.sp.educacao.sed.mobile.Modelo.Habilidades;
import br.gov.sp.educacao.sed.mobile.Modelo.HabilidadesAbordadas;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.QueryDB.AtualizarBancoQueryDB;
import br.gov.sp.educacao.sed.mobile.QueryDB.AvaliacaoQueryDB;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.FrequenciaQueryDB;
import br.gov.sp.educacao.sed.mobile.QueryDB.RegistroAulasQueryDB;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.DateUtils;

/**
 * Created by techresult on 11/08/2015.
 */
public class FragmentRegistroAula extends Fragment{

    private AppCompatActivity appCompatActivity;
    private TurmaGrupo turmaGrupo;
    private View rootView;
    private TextView tvDia;

    private Grupo grupo;
    private Bimestre bimestre;
    private List<String> listaDiasLetivosStr;
    private List<Integer> listaDiaSemana;

    private List<DiasLetivos> listaDiasLetivos;
    //Calendario
    private CaldroidFragment dialogCaldroidFragment;
    private CaldroidListener listener;
    private ArrayList<String> disableDateStrings;
    int mesSelecionado;
    private String data;
    private int diasMes;

    private List<Aula> listaAula;
    private List<Conteudo> listaConteudos;
    private List<Habilidades> listaHabilidades;
    private UsuarioTO usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_registrar_aulas, container, false);

        Bundle args = getArguments();
        final Bundle savedInstance = savedInstanceState;
        turmaGrupo = (TurmaGrupo) args.getSerializable(TurmaGrupo.BUNDLE_TURMA_GRUPO);
        usuario = (UsuarioTO) args.getSerializable("usuario");

        Button btnRegistrar = (Button) rootView.findViewById(R.id.btnRegistrar);
        tvDia = (TextView) rootView.findViewById(R.id.tv_dia);

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
                getCaldroid(savedInstance);
                dialogCaldroidFragment.setCaldroidListener(getListenerCaldroid());
            }
        });

        listaDiaSemana = new ArrayList<>();
        for(Aula aula : listaAula){
            if (!listaDiaSemana.contains(aula.getDiaSemana())){
                listaDiaSemana.add(aula.getDiaSemana());
            }
        }

        appCompatActivity = (AppCompatActivity) getActivity();
        Toolbar toolbar = (Toolbar) appCompatActivity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.registro_aulas);
        }

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strData = tvDia.getText().toString();
                DiasLetivos diaLetivo = FrequenciaQueryDB.getDiaLetivo(getActivity(), strData);

                EditText observacoes = (EditText) rootView.findViewById(R.id.editobservacoes);

                //Consulta a tabela HabilidadesAbordadas
                List<HabilidadesAbordadas> listaHabilidadesAbordadas = RegistroAulasQueryDB.getHabilidadesAbordadas(getActivity(), turmaGrupo.getTurma(), usuario, diaLetivo);

                int observacoesInseridas = RegistroAulasQueryDB.setObservacoes(getActivity(), listaHabilidadesAbordadas, observacoes.getText().toString());
                if(observacoesInseridas > 0){
                    Toast.makeText(getActivity(), getResources().getString(R.string.registro_realizado), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private void onDateSelected(Date date){
        String strData = new SimpleDateFormat("dd/MM/yyyy").format(date);

        DiasLetivos diaLetivo = FrequenciaQueryDB.getDiaLetivo(getActivity(), strData);

        grupo = RegistroAulasQueryDB.getGrupo(getActivity(), turmaGrupo.getTurma(), turmaGrupo.getDisciplina());
        listaConteudos = RegistroAulasQueryDB.getConteudos(getActivity(), grupo);

        //Consulta a tabela HabilidadesAbordadas
        List<HabilidadesAbordadas> listaHabilidadesAbordadas = RegistroAulasQueryDB.getHabilidadesAbordadas(getActivity(), turmaGrupo.getTurma(), usuario, diaLetivo);

        LinearLayout linearConteudos = (LinearLayout) rootView.findViewById(R.id.linearConteudos);
        linearConteudos.removeAllViews();

        TreeNode root = TreeNode.root();

        EditText observacoes = (EditText) rootView.findViewById(R.id.editobservacoes);
        observacoes.setText("");

        for (int i = 0; i < listaConteudos.size(); i++) {
            TreeNode conteudo = new TreeNode(listaConteudos.get(i).getDescricaoConteudo()).setViewHolder(new ConteudoHolder(getActivity()));

            listaHabilidades = RegistroAulasQueryDB.getHabilidades(getActivity(), listaConteudos.get(i));

            if (listaHabilidades.size() > 0) {
                for (int j = 0; j < listaHabilidades.size(); j++) {
                    boolean isChecked = false;

                    if(listaHabilidadesAbordadas.size() > 0){
                        for(int x = 0; x < listaHabilidadesAbordadas.size(); x++){
                            if(listaHabilidades.get(j).getId() == listaHabilidadesAbordadas.get(x).getHabilidadeId())
                                isChecked = true;

                            observacoes.setText(listaHabilidadesAbordadas.get(x).getDescricao());
                        }
                    }

                    TreeNode habilidade = new TreeNode(new HabilidadeHolder.HabilidadeView(isChecked, turmaGrupo.getTurma(), listaHabilidades.get(j), diaLetivo)).setViewHolder(new HabilidadeHolder(getActivity()));
                    conteudo.addChild(habilidade);
                }
            }

            root.addChild(conteudo);
        }

        AndroidTreeView tView = new AndroidTreeView(getActivity(), root);
        linearConteudos.addView(tView.getView());

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

                                        String strInicioBimestre = sdf.format(inicioBimestre);
                                        Date dataInicioBimestre = sdf.parse(strInicioBimestre);

                                        String strFimBimestre = sdf.format(fimBimestre);
                                        Date dataFimBimestre = sdf.parse(strFimBimestre);

                                        if((dataLetivo.after(dataInicioBimestre) || dataLetivo.equals(dataInicioBimestre)) &&
                                                (dataLetivo.before(dataFimBimestre) || dataLetivo.equals(dataFimBimestre))){
                                            if(dataLetivo.equals(dataAtual)){
                                                dialogCaldroidFragment.setBackgroundResourceForDate(R.color.amarelo_dia_letivo, dataLetivo);
                                                dialogCaldroidFragment.setTextColorForDate(R.color.amarelo_texto_dia_letivo, dataLetivo);
                                            }
                                            else{
                                                dialogCaldroidFragment.setBackgroundResourceForDate(R.color.azul_dia_letivo, dataLetivo);
                                                dialogCaldroidFragment.setTextColorForDate(R.color.azul_texto_dia_letivo, dataLetivo);
                                            }

                                            DiasLetivos diaLetivo = FrequenciaQueryDB.getDiaLetivo(getActivity(), strDataLetivo);

                                            if(RegistroAulasQueryDB.registroAulaEnviada(getActivity(), diaLetivo)){
                                                dialogCaldroidFragment.setBackgroundResourceForDate(R.color.verde_dia_letivo, dataLetivo);
                                                dialogCaldroidFragment.setTextColorForDate(R.color.verde_texto_dia_letivo, dataLetivo);
                                            }

                                            diasLetivosConfirmados.add(diaLetivoMes);
                                        }
                                    }
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
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
}
