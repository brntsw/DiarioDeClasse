package br.gov.sp.educacao.sed.mobile.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import br.gov.sp.educacao.sed.mobile.Fragment.FragmentLancamentoAvaliacao;
import br.gov.sp.educacao.sed.mobile.Modelo.Aula;
import br.gov.sp.educacao.sed.mobile.Modelo.Avaliacao;
import br.gov.sp.educacao.sed.mobile.Modelo.Bimestre;
import br.gov.sp.educacao.sed.mobile.Modelo.Turma;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.QueryDB.AtualizarBancoQueryDB;
import br.gov.sp.educacao.sed.mobile.QueryDB.AvaliacaoQueryDB;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.DateUtils;

/**
 * Created by techresult on 26/08/2015.
 */
public class AvaliacaoAdapter extends BaseAdapter {

    private Context context;
    private TurmaGrupo turmaGrupo;
    private Bimestre bimestre;
    private List<Integer> listaDiaSemana;
    private List<Aula> listaAula;
    private List<Avaliacao> avaliacaoArrayList;
    private List<String> listaDiasLetivosStr;
    public FragmentLancamentoAvaliacao fragmentLancamentoAvaliacao;
    //private EditText edData;
    private AlertDialog alert;
    private EditText viewError;
    private Fragment frag;
    private Turma turma;
    private TextView tvDiaAlert;
    private Bundle savedInstanceState;

    //Calendario
    private CaldroidFragment dialogCaldroidFragment;
    private CaldroidListener listener;
    private ArrayList<String> disableDateStrings;
    int mesSelecionado;
    private String data;
    private int diasMes;

    public AvaliacaoAdapter(Context context, List<Avaliacao> avaliacaoArrayList) {
        this.context = context;
        this.avaliacaoArrayList = avaliacaoArrayList;
    }

    public AvaliacaoAdapter(Context context, TurmaGrupo turmaGrupo, List<Avaliacao> avaliacaoArrayList, List<String> listaDiasLetivosStr, Fragment frag, Bundle savedInstanceState) {
        this.context = context;
        this.turmaGrupo = turmaGrupo;
        this.avaliacaoArrayList = avaliacaoArrayList;
        this.listaDiasLetivosStr = listaDiasLetivosStr;
        this.frag = frag;
        this.savedInstanceState = savedInstanceState;

        bimestre = AtualizarBancoQueryDB.getBimestre(context, turmaGrupo.getTurmasFrequencia());

        try {
            listaAula = AvaliacaoQueryDB.getAula(context, turmaGrupo.getDisciplina());
            listaDiaSemana = new ArrayList<>();
            for (Aula aulaFor : listaAula) {
                if (!listaDiaSemana.contains(aulaFor.getDiaSemana())){
                    listaDiaSemana.add(aulaFor.getDiaSemana());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getCount() {
        return avaliacaoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        final Avaliacao avaliacao = avaliacaoArrayList.get(position);

        View row = convertView;
        final ProdutoHolder holder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.layout_avaliacao, parent, false);

            holder = new ProdutoHolder();
            //
            holder.layAvaliacao = (LinearLayout) row.findViewById(R.id.lay_avaliacao);
            holder.tvNome = (TextView) row.findViewById(R.id.tv_nome);
            holder.tvData = (TextView) row.findViewById(R.id.tv_data);
            holder.tvBimestre = (TextView) row.findViewById(R.id.tv_bimestre);
            holder.tvEdit = (TextView) row.findViewById(R.id.tv_edit);
            //

            row.setTag(holder);


        } else {
            holder = (ProdutoHolder) row.getTag();
        }

        holder.tvNome.setText(avaliacao.getNome().toUpperCase());
        holder.tvData.setText(avaliacao.getData());
        holder.tvBimestre.setText(avaliacao.getBimestre() + "º bimestre");
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"icomoon.ttf");
        holder.tvEdit.setTypeface(typeface);
        holder.tvEdit.setText(String.valueOf((char) 0xe600));

        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertbuilder = new AlertDialog.Builder(context);
                alertbuilder.setTitle(context.getResources().getString(R.string.edit_ava));

                alertbuilder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                final LinearLayout.LayoutParams paramslayoutmain = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                paramslayoutmain.setMargins(20, 20, 20, 20);

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View view = inflater.inflate(R.layout.alert_avaliacao, null);
                alertbuilder.setView(view);

                final EditText edNome = (EditText) view.findViewById(R.id.ed_nome);
                edNome.setText(avaliacao.getNome());

                tvDiaAlert = (TextView) view.findViewById(R.id.tv_dia_alert);
                tvDiaAlert.setText(avaliacao.getData());

                tvDiaAlert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogCaldroidFragment = new CaldroidFragment();
                        getCaldroid(savedInstanceState);
                        dialogCaldroidFragment.setCaldroidListener(getListenerCaldroid());
                    }
                });

                TextView tvBimestreAtual = (TextView) view.findViewById(R.id.tv_bimestre_atual);
                tvBimestreAtual.setText(avaliacao.getBimestre() + "º");

                final Spinner spTipo = (Spinner) view.findViewById(R.id.spTipo);

                String tipoAtividade;
                switch (avaliacao.getTipoAtividade()) {
                    case 11:
                        tipoAtividade = "Avaliacao";
                        break;
                    case 12:
                        tipoAtividade = "Atividade";
                        break;
                    case 13:
                        tipoAtividade = "Trabalho";
                        break;
                    case 14:
                        tipoAtividade = "Outros";
                        break;
                    default:
                        tipoAtividade = "";
                }
                spTipo.setSelection(getIndex(spTipo, tipoAtividade));

                final CheckBox ckVale = (CheckBox) view.findViewById(R.id.ckVale);
                ckVale.setChecked(avaliacao.isValeNota());

                alert = alertbuilder.create();
                alert.show();
                alert.setCanceledOnTouchOutside(false);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (edNome.getText().toString().length() <= 0) {
                            Toast.makeText(context, context.getResources().getString(R.string.complete_campos), Toast.LENGTH_SHORT).show();
                            viewError = edNome;
                            viewError.setError(context.getResources().getString(R.string.obrigatorio));
                        }
                        else if(tvDiaAlert.getText().toString().equals("DD/MM/AAAA")){
                            Toast.makeText(context, context.getResources().getString(R.string.hintdata), Toast.LENGTH_SHORT).show();
                        }
                        else if(spTipo.getSelectedItem().toString().equals("Selecione")){
                            Toast.makeText(context, context.getResources().getString(R.string.selecione_tipo), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            avaliacao.setNome(edNome.getText().toString());
                            avaliacao.setData(tvDiaAlert.getText().toString());

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

                            AvaliacaoQueryDB.editarAvaliacao(context, avaliacao);
                            Toast.makeText(context, context.getResources().getString(R.string.avaliacao_atualizada), Toast.LENGTH_SHORT).show();
                            alert.dismiss();
                        }
                    }
                });
            }
        });

        return row;
    }

    private int getIndex(Spinner spinner, String tipo){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            String item = spinner.getItemAtPosition(i).toString();
            if (stripAccents(item).equals(tipo)){
                index = i;
            }
        }
        return index;
    }

    public void getCaldroid(Bundle savedInstanceState){
        final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
        if (savedInstanceState != null) {
            dialogCaldroidFragment.restoreDialogStatesFromKey(
                    ((AppCompatActivity)context).getSupportFragmentManager(), savedInstanceState,
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

        dialogCaldroidFragment.show(((AppCompatActivity)context).getSupportFragmentManager(),
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
                    if(tvDiaAlert != null){
                        tvDiaAlert.setText(data);
                    }

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

    String stripAccents(String string) {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return string;
    }

    static class ProdutoHolder {
        LinearLayout layAvaliacao;
        TextView tvNome, tvData, tvBimestre, tvEdit;

    }
}
