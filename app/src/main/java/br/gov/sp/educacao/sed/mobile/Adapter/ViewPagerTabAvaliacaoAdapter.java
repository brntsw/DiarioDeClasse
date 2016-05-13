package br.gov.sp.educacao.sed.mobile.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.gov.sp.educacao.sed.mobile.Fragment.FragmentTabAvaliacaoConsulta;
import br.gov.sp.educacao.sed.mobile.Fragment.FragmentTabAvaliacaoLancamento;

/**
 * Created by techresult on 24/08/2015.
 */
public class ViewPagerTabAvaliacaoAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[];
    int numOfTabs;

    public ViewPagerTabAvaliacaoAdapter(FragmentManager fm, CharSequence titles[], int numOfTabs) {
        super(fm);

        this.titles = titles;
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new FragmentTabAvaliacaoLancamento();
        }
        else{
            return new FragmentTabAvaliacaoConsulta();
        }
    }

    public CharSequence getPageTitle(int position){
        return titles[position];
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
