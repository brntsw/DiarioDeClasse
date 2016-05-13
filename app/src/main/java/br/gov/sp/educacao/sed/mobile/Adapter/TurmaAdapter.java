package br.gov.sp.educacao.sed.mobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.R;

/**
 * Created by techresult on 25/06/2015.
 */
public class TurmaAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TurmaGrupo> turmaArrayList;

    public TurmaAdapter(Context context, ArrayList<TurmaGrupo> turmaArrayList) {
        super();
        this.context = context;
        this.turmaArrayList = turmaArrayList;
    }

    @Override
    public int getCount() {
        return turmaArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return turmaArrayList.get(position).getTurma().getCodigoTurma();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final TurmaGrupo turmaGrupo = turmaArrayList.get(position);

        View row = convertView;
        ProdutoHolder holder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = new ProdutoHolder();

            row = inflater.inflate(R.layout.layout_turma_frequencia, parent, false);
            holder.tvDisciplina = (TextView) row.findViewById(R.id.tv_disciplina);

            holder.layDiretoria = (LinearLayout) row.findViewById(R.id.lay_diretoria);
            holder.tvDiretoria = (TextView) row.findViewById(R.id.tv_diretoria);
            holder.tvTurma = (TextView) row.findViewById(R.id.tv_turma);
            holder.tvTipo = (TextView) row.findViewById(R.id.tv_tipo);

            row.setTag(holder);


        } else {
            holder = (ProdutoHolder) row.getTag();
        }

        holder.tvTurma.setText(turmaGrupo.getTurma().getNomeTurma());
        holder.tvTipo.setText(turmaGrupo.getTurma().getNomeTipoEnsino());
        holder.tvDiretoria.setText(turmaGrupo.getTurma().getNomeDiretoria() + " / " + turmaGrupo.getTurma().getNomeEscola());

        if (turmaGrupo.getTurma().isHeader()) {
            holder.layDiretoria.setVisibility(View.VISIBLE);

        } else {
            holder.layDiretoria.setVisibility(View.GONE);

        }

        //if (tipo == 2) {
        holder.tvDisciplina.setText(turmaGrupo.getDisciplina().getNomeDisciplina());
        holder.tvDiretoria.setText(turmaGrupo.getTurma().getNomeDiretoria() + " / " + turmaGrupo.getTurma().getNomeEscola());

        return row;
    }

    static class ProdutoHolder {
        LinearLayout layDiretoria;
        TextView tvDiretoria;
        TextView tvTurma;
        TextView tvTipo;
        TextView tvDisciplina;
        LinearLayout layTurma;

    }
}
