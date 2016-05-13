package br.gov.sp.educacao.sed.mobile.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.QueryDB.FrequenciaQueryDB;
import br.gov.sp.educacao.sed.mobile.R;

/**
 * Created by techresult on 10/08/2015.
 */
public class ConsultaAlunoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Aluno> alunoArrayList;
    private TurmaGrupo turmaGrupo;
    private int periodo;

    public ConsultaAlunoAdapter(Context context, ArrayList<Aluno> alunoArrayList, int periodo, TurmaGrupo turmaGrupo){
        this.context = context;
        this.alunoArrayList = alunoArrayList;
        this.periodo = periodo;
        this.turmaGrupo = turmaGrupo;
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

        Aluno aluno = alunoArrayList.get(position);

        View row = convertView;
        final FrequenciaHolder holder;

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.layout_consulta_frequencia, parent, false);

            holder = new FrequenciaHolder();

            holder.layAluno = (LinearLayout) row.findViewById(R.id.lay_aluno);
            holder.tvAluno = (TextView) row.findViewById(R.id.tv_aluno);
            //holder.switchPresenca = (SwitchCompat) row.findViewById(R.id.switch_presenca);
            holder.tvFalta = (TextView) row.findViewById(R.id.tv_falta);

            row.setTag(holder);
        }
        else{
            holder = (FrequenciaHolder) row.getTag();
        }

        String textAluno = aluno.getNumeroChamada() + " - " + aluno.getNomeAluno();
        holder.tvAluno.setText(textAluno);

        Double cCom;

        aluno = FrequenciaQueryDB.getTotalFaltas(context, aluno, turmaGrupo.getDisciplina().getId());

        switch (periodo){
            case 0:
                int aulasAno = turmaGrupo.getTurma().getAulasAno();
                if(aluno.getFaltasAnuais() > 0 && aulasAno > 0) {
                    cCom = ((double) aluno.getFaltasAnuais() / aulasAno) * 100;
                    holder.tvFalta.setText(aluno.getFaltasAnuais() + " ("+ String.format("%.2f", cCom) +"%)");
                    if (cCom >= 20){
                        holder.tvFalta.setTypeface(null, Typeface.BOLD);
                    } else {
                        holder.tvFalta.setTypeface(null, Typeface.NORMAL);
                    }
                }else {
                    holder.tvFalta.setText(String.valueOf(aluno.getFaltasAnuais()));
                }
                break;
            case 2:{
                if(aluno.getFaltasSequenciais() > 0) {
                    holder.tvFalta.setText(String.valueOf(aluno.getFaltasSequenciais()));
                }
                break;
            }
            default:{
                int aulasBimestre = turmaGrupo.getTurma().getAulasBimestre();
                if(aluno.getFaltasAnuais() > 0 && aulasBimestre > 0) {
                    cCom = ((double)aluno.getFaltasBimestre()/aulasBimestre)*100;
                    holder.tvFalta.setText(aluno.getFaltasBimestre() + " (" + String.format("%.2f", cCom) + "%)");
                    if (cCom >= 20){
                        holder.tvFalta.setTypeface(null, Typeface.BOLD);
                    }else {
                        holder.tvFalta.setTypeface(null, Typeface.NORMAL);
                    }
                }else {
                    holder.tvFalta.setText(String.valueOf(aluno.getFaltasBimestre()));
                }

                break;
            }

        }

        return row;
    }

    static class FrequenciaHolder{
        LinearLayout layAluno;
        TextView tvAluno;
        TextView tvFalta;
    }
}
