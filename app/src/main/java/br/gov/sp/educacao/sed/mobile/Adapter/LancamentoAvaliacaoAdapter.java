package br.gov.sp.educacao.sed.mobile.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.gov.sp.educacao.sed.mobile.Fragment.FragmentLancamentoAvaliacaoPager;
import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Modelo.Avaliacao;
import br.gov.sp.educacao.sed.mobile.Modelo.NotasAluno;
import br.gov.sp.educacao.sed.mobile.QueryDB.AvaliacaoQueryDB;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.Queries;
import br.gov.sp.educacao.sed.mobile.Util.Utils;

/**
 * Created by techresult on 01/09/2015.
 */
public class LancamentoAvaliacaoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Aluno> alunoArrayList;
    private Avaliacao avaliacao;
    private AlertDialog alert;
    static String LABEL_MATRICULA;
    static String LABEL_STATUS;
    private UsuarioTO usuario;

    public FragmentLancamentoAvaliacaoPager fragmentLancamentoAvaliacaoPager;

    public LancamentoAvaliacaoAdapter(Context context, ArrayList<Aluno> alunoArrayList, Avaliacao avaliacao, FragmentLancamentoAvaliacaoPager fragmentLancamentoAvaliacaoPager){
        this.context = context;
        this.alunoArrayList = alunoArrayList;
        this.avaliacao = avaliacao;
        this.fragmentLancamentoAvaliacaoPager = fragmentLancamentoAvaliacaoPager;
        this.usuario = Queries.getUsuarioAtivo(context);
    }

    @Override
    public int getCount() {
        return alunoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return alunoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunoArrayList.get(position).getCodigoAluno();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Aluno aluno = alunoArrayList.get(position);

        NotasAluno notasAluno = new NotasAluno();
        notasAluno.setAluno_id(aluno.getId());
        notasAluno.setAvaliacao_id(avaliacao.getId());

        final String nota = AvaliacaoQueryDB.getNotasAluno(context, notasAluno);

        View row = convertView;
        final AvaliacaoHolder holder;

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.layout_avaliacao_aluno, parent, false);

            holder = new AvaliacaoHolder();

            holder.layAluno = (LinearLayout) row.findViewById(R.id.lay_aluno);
            holder.tvAluno = (TextView) row.findViewById(R.id.tv_aluno);
            holder.tvStatusAluno = (TextView) row.findViewById(R.id.tv_status_aluno);
            holder.tvMatriculaAluno = (TextView) row.findViewById(R.id.tv_matricula_aluno);
            holder.tvAtivo = (TextView) row.findViewById(R.id.tv_ativo);
            holder.tvNota = (TextView) row.findViewById(R.id.tv_nota);

            row.setTag(holder);
        }
        else{
            holder = (AvaliacaoHolder) row.getTag();
        }

        LABEL_MATRICULA = context.getResources().getString(R.string.desc_matricula);
        LABEL_STATUS = context.getResources().getString(R.string.desc_status);

        String textAluno = aluno.getNumeroChamada() + " - " + aluno.getNomeAluno();
        String statusAluno = LABEL_STATUS.toUpperCase()+": "+  Utils.convertStatus(aluno.getAlunoAtivo());
        String matriculaAluno;
        if(aluno.getDigitoRa() == null) {
            //RA SEM DIGITO
            matriculaAluno =  LABEL_MATRICULA.toUpperCase()+": " + aluno.getNumeroRa();
        }else {
            //RA COM DIGITO
            matriculaAluno =  LABEL_MATRICULA.toUpperCase()+": " + aluno.getNumeroRa() + "-" + aluno.getDigitoRa();
        }

        if (aluno.getAlunoAtivo() == 1){
            holder.tvAtivo.setVisibility(View.GONE);
            holder.tvNota.setVisibility(View.VISIBLE);
            holder.tvNota.setPaintFlags(holder.tvNota.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            if (!nota.equals("12")){
                if (nota.equals("11")){
                    holder.tvNota.setText("S/N");
                } else {
                    holder.tvNota.setText(String.valueOf(nota));
                }
            } else {
                Typeface typeface = Typeface.createFromAsset(context.getAssets(),"icomoon.ttf");
                holder.tvNota.setTypeface(typeface);
                holder.tvNota.setText(String.valueOf((char) 0xe600));
            }
            holder.tvAluno.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            holder.tvNota.setVisibility(View.GONE);
            holder.tvAluno.setTextColor(context.getResources().getColor(R.color.cinza_titulo_indice));
            holder.tvAtivo.setVisibility(View.VISIBLE);
            holder.tvAtivo.setText("T");
        }

        holder.tvAluno.setText(textAluno);
        holder.tvStatusAluno.setText(statusAluno);
        holder.tvMatriculaAluno.setText(matriculaAluno);

        holder.tvNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertbuilder = new AlertDialog.Builder(context);
                alertbuilder.setTitle(context.getResources().getString(R.string.nota));

                alertbuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                final LinearLayout.LayoutParams paramslayoutmain = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                paramslayoutmain.setMargins(20, 20, 20, 20);

                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.alert_nota, null);
                alertbuilder.setView(view);

                final TextView tvNome = (TextView) view.findViewById(R.id.tv_nome);
                String dadosAluno = aluno.getNumeroChamada() + " - " + aluno.getNomeAluno();
                tvNome.setText(dadosAluno);

                final TextView tvNota = (TextView) view.findViewById(R.id.tv_nota);
                final NumberPicker numberOfPlayersPicker = (NumberPicker) view.findViewById(R.id.numberOfPlayersPicker);
                numberOfPlayersPicker.setMaxValue(11);
                numberOfPlayersPicker.setMinValue(0);
                numberOfPlayersPicker.setDisplayedValues(new String[]{"S/N", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});

                final NumberPicker numberOfPlayersPickerDezena = (NumberPicker) view.findViewById(R.id.numberOfPlayersPickerDezena);
                numberOfPlayersPickerDezena.setMaxValue(9);
                numberOfPlayersPickerDezena.setMinValue(0);

                final NumberPicker numberOfPlayersPickerCentena = (NumberPicker) view.findViewById(R.id.numberOfPlayersPickerCentena);
                numberOfPlayersPickerCentena.setMaxValue(9);
                numberOfPlayersPickerCentena.setMinValue(0);

                if (!nota.equals("12")){
                    if (nota.equals("11")){
                        tvNota.setText("Sem nota");
                    } else {
                        tvNota.setText("Nota " + nota);
                        numberOfPlayersPicker.setValue(Integer.parseInt(nota.replace(".", ",").split(",")[0]) + 1);
                        numberOfPlayersPickerDezena.setValue(Integer.parseInt(nota.replace(".", ",").split(",")[1].substring(0, 1)));
                        if(nota.length() > 3){
                            numberOfPlayersPickerCentena.setValue(Integer.parseInt(nota.replace(".", ",").split(",")[1].substring(1,2)));
                        }
                        else{
                            numberOfPlayersPickerCentena.setValue(Integer.parseInt("0"));
                        }
                    }
                }

                numberOfPlayersPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                        if (newVal == 11 | newVal == 0) {
                            numberOfPlayersPickerDezena.setValue(0);
                            numberOfPlayersPickerCentena.setValue(0);
                        }

                        if (newVal == 0) {
                            tvNota.setText("Sem nota");
                            holder.tvNota.setText("S/N");
                        } else {
                            tvNota.setText("Nota " + String.valueOf(newVal - 1) + "," + numberOfPlayersPickerDezena.getValue() + numberOfPlayersPickerCentena.getValue());
                        }
                    }
                });

                numberOfPlayersPickerDezena.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                        if (numberOfPlayersPicker.getValue() == 0 | numberOfPlayersPicker.getValue() == 11) {
                            numberOfPlayersPickerDezena.setValue(0);
                            numberOfPlayersPickerCentena.setValue(0);
                        }

                        if (numberOfPlayersPicker.getValue() == 0) {
                            tvNota.setText("Sem nota");
                            holder.tvNota.setText("S/N");
                        } else {
                            tvNota.setText("Nota " + (numberOfPlayersPicker.getValue() - 1) + "," + numberOfPlayersPickerDezena.getValue() + numberOfPlayersPickerCentena.getValue());
                        }

                    }
                });


                numberOfPlayersPickerCentena.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                        if (numberOfPlayersPicker.getValue() == 0 | numberOfPlayersPicker.getValue() == 11) {
                            numberOfPlayersPickerDezena.setValue(0);
                            numberOfPlayersPickerCentena.setValue(0);
                        }

                        if (numberOfPlayersPicker.getValue() == 0) {
                            tvNota.setText("Sem nota");
                            holder.tvNota.setText("S/N");
                        } else {
                            tvNota.setText("Nota " + (numberOfPlayersPicker.getValue() - 1) + "," + numberOfPlayersPickerDezena.getValue() + numberOfPlayersPickerCentena.getValue());
                        }
                    }
                });

                alert = alertbuilder.create();
                alert.show();

                alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (avaliacao.isValeNota()) {
                            NotasAluno notasAluno = new NotasAluno();
                            notasAluno.setAluno_id(aluno.getId());
                            notasAluno.setCodigoMatricula(aluno.getCodigoMatricula());
                            notasAluno.setAvaliacao_id(avaliacao.getId());
                            notasAluno.setUsuario_id(usuario.getId());
                            notasAluno.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date()));

                            final int nota = numberOfPlayersPicker.getValue();
                            if (nota == 0) {
                                notasAluno.setNota("");
                                AvaliacaoQueryDB.setNotasAluno(context, notasAluno);
                                holder.tvNota.setText("S/N");
                            } else {
                                notasAluno.setNota((numberOfPlayersPicker.getValue() - 1) + "." + numberOfPlayersPickerDezena.getValue() + numberOfPlayersPickerCentena.getValue());
                                holder.tvNota.setText(String.valueOf(nota - 1) + "," + numberOfPlayersPickerDezena.getValue() + numberOfPlayersPickerCentena.getValue());
                                AvaliacaoQueryDB.setNotasAluno(context, notasAluno);
                            }
                        }

                        alert.dismiss();
                    }
                });

                if(!avaliacao.isValeNota()){
                    numberOfPlayersPicker.setEnabled(false);
                    numberOfPlayersPickerDezena.setEnabled(false);
                    numberOfPlayersPickerCentena.setEnabled(false);
                }
            }
        });

        return row;
    }

    static class AvaliacaoHolder{
        LinearLayout layAluno;
        TextView tvAluno;
        TextView tvStatusAluno;
        TextView tvMatriculaAluno;
        TextView tvNota;
        TextView tvAtivo;
    }
}
