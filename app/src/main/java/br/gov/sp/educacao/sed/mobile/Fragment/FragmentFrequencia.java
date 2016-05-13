package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import br.gov.sp.educacao.sed.mobile.Adapter.ViewPagerTabFrequenciaAdapter;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;
import br.gov.sp.educacao.sed.mobile.Util.SlidingTabLayout;

/**
 * Created by techresult on 29/06/2015.
 */
public class FragmentFrequencia extends Fragment {

    private Activity activity;
    private AppCompatActivity appCompatActivity;
    ViewPager viewPager;
    ViewPagerTabFrequenciaAdapter adapter;
    SlidingTabLayout tabs;
    int numOfTabs = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        Analytics.setTela(activity, activity.getClass().toString());

        appCompatActivity = (AppCompatActivity) getActivity();

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_frequencia, container, false);
        CharSequence[] titles = {getResources().getString(R.string.tab_lancamento), getResources().getString(R.string.tab_consulta)};

        //Cria o ViewPager
        adapter = new ViewPagerTabFrequenciaAdapter(appCompatActivity.getSupportFragmentManager(), titles, numOfTabs);

        //ViewPager
        viewPager = (ViewPager) layout.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        //Sliding Tab Layout view
        tabs = (SlidingTabLayout) layout.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); //Isso faz com que as tabs fiquem com espacamento igual

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.amarelo_selecao_aba);
            }
        });

        tabs.setViewPager(viewPager);

        return layout;
    }

}
