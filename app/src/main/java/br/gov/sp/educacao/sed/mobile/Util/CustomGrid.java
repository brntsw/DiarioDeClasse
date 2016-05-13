package br.gov.sp.educacao.sed.mobile.Util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import br.gov.sp.educacao.sed.mobile.R;

/**
 * Created by BRUNO on 21/06/2015.
 */
public class CustomGrid extends BaseAdapter {
    private Context mContext;
    private String[] descricaoItem;
    private Drawable[] imagens;

    public CustomGrid(Context context, String[] descricaoItem, Drawable[] imagens) {
        this.mContext = context;
        this.descricaoItem = descricaoItem;
        this.imagens = imagens;
    }

    @Override
    public int getCount() {
        return descricaoItem.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            //grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, parent, false);
            TextView textView = (TextView) grid.findViewById(R.id.tvGrid);
            textView.setText(descricaoItem[position]);
            textView.setCompoundDrawablesWithIntrinsicBounds(null, imagens[position], null, null);
        } else {
            grid = convertView;
        }

        return grid;
    }
}
