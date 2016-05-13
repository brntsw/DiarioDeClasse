package br.gov.sp.educacao.sed.mobile.Adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.gov.sp.educacao.sed.mobile.Fragment.FragmentLancamentoLista;
import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.R;

/**
 * Created by BRUNO on 04/07/2015.
 */
public class LancamentoAlunoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Aluno> alunoArrayList;
    private String data, hora;
    public FragmentLancamentoLista fragmentLancamentoLista;

    private final String siglaCompareceu;
    private final String siglaFaltou;
    private final String siglaNaoSeAplica;
    private final String siglaTransferido;
    private String descTransferido;

    public LancamentoAlunoAdapter(Context context, ArrayList<Aluno> alunoArrayList, String data, String hora){
        this.context = context;
        this.alunoArrayList = alunoArrayList;
        this.data = data;
        this.hora = hora;

        siglaCompareceu = context.getResources().getString(R.string.sigla_compareceu);
        siglaFaltou = context.getResources().getString(R.string.sigla_falta);
        siglaNaoSeAplica = context.getResources().getString(R.string.sigla_nao_se_aplica);
        siglaTransferido = context.getResources().getString(R.string.sigla_transferido);
        descTransferido = context.getResources().getString(R.string.desc_transferido);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Aluno aluno = alunoArrayList.get(position);

        final int posicaoReal = position + 1;

        View row = convertView;
        final FrequenciaHolder holder;

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.layout_frequencia_aluno, parent, false);

            row.setClickable(true);
            row.setFocusable(true);

            holder = new FrequenciaHolder();

            holder.layAluno = (LinearLayout) row.findViewById(R.id.lay_aluno);
            holder.tvAluno = (TextView) row.findViewById(R.id.tv_aluno);
            holder.tvAtivo = (TextView) row.findViewById(R.id.tv_ativo);
            holder.btFrequencia = (Button) row.findViewById(R.id.bt_frequencia);

            row.setTag(holder);
        }
        else{
            holder = (FrequenciaHolder) row.getTag();
        }

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof AppCompatActivity){
                    ((AppCompatActivity) context).onBackPressed();
                    fragmentLancamentoLista.fragmentLancamentoFrequenciaPager.goToAluno(aluno.getNumeroChamada() - 1);
                }
            }
        });

        //String textAluno = posicaoReal < 10 ? "0" + posicaoReal + " - " + aluno.getNomeAluno() : posicaoReal + " - " + aluno.getNomeAluno();
        String textAluno = aluno.getNumeroChamada() + " - " + aluno.getNomeAluno();

        //int img = position % 2 == 0 ? R.drawable.lancamento_nao : R.drawable.lancamento_sim;

        if (aluno.getAlunoAtivo() == 1){
            holder.tvAtivo.setVisibility(View.GONE);
            holder.btFrequencia.setVisibility(View.VISIBLE);
            holder.tvAluno.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            holder.btFrequencia.setVisibility(View.GONE);
            holder.tvAluno.setTextColor(context.getResources().getColor(R.color.cinza_titulo_indice));
            holder.tvAtivo.setVisibility(View.VISIBLE);
            holder.tvAtivo.setText(descTransferido);
        }

        holder.tvAluno.setText(textAluno);
        switch (aluno.getComparecimento()){
            case "Compareceu":
            case "C": {
                holder.btFrequencia.setText(siglaCompareceu);
                holder.btFrequencia.setBackgroundResource(R.drawable.button_presenca);
                break;
            }
            case "Faltou":
            case "F": {
                holder.btFrequencia.setText(siglaFaltou);
                holder.btFrequencia.setBackgroundResource(R.drawable.button_falta);
                break;
            }
            case "N": {
                holder.btFrequencia.setText(siglaNaoSeAplica);
                holder.btFrequencia.setBackgroundResource(R.drawable.button_nao_aplica);
                break;
            }
            default: {
                holder.btFrequencia.setBackgroundColor(context.getResources().getColor(R.color.transparente));
                break;
            }
        }

        return row;
    }

    static class FrequenciaHolder{
        LinearLayout layAluno;
        TextView tvAluno;
        Button btFrequencia;
        TextView tvAtivo;
    }
}
