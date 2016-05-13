package br.gov.sp.educacao.sed.mobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.R;

/**
 * Created by techresult on 26/06/2015.
 */
public class AlunoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Aluno> alunoArrayList;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public AlunoAdapter(Context context, ArrayList<Aluno> alunoArrayList) {
        super();
        this.context = context;
        this.alunoArrayList = alunoArrayList;
    }

    @Override
    public int getCount() {
        return alunoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return alunoArrayList.get(position).getCodigoAluno();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Aluno aluno = alunoArrayList.get(position);

        View row = convertView;
        ProdutoHolder holder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.layout_aluno, parent, false);

            holder = new ProdutoHolder();
            //
            holder.layAluno = (LinearLayout) row.findViewById(R.id.lay_aluno);
            holder.tvAluno = (TextView) row.findViewById(R.id.tv_aluno);
            //

            row.setTag(holder);


        } else {
            holder = (ProdutoHolder) row.getTag();
        }

        holder.tvAluno.setText(aluno.getNumeroChamada() + " - " + aluno.getNomeAluno());

        return row;
    }

    static class ProdutoHolder {
        LinearLayout layAluno;
        TextView tvAluno;

    }
}
