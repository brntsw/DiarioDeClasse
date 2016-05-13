package br.gov.sp.educacao.sed.mobile.Fragment.TreeView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;

import br.gov.sp.educacao.sed.mobile.R;

/**
 * Created by techresult on 04/04/2016.
 */
public class ConteudoHolder extends TreeNode.BaseNodeViewHolder<String> {

    private ImageView arrowView;

    public ConteudoHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode treeNode, String s) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_conteudo_registro_aula, null, false);
        TextView tvConteudo = (TextView) view.findViewById(R.id.tv_conteudo);
        tvConteudo.setText(s);

        arrowView = (ImageView) view.findViewById(R.id.arrow_icon);

        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setImageResource(active ? R.drawable.arrow_down : R.drawable.arrow_right);
    }

}
