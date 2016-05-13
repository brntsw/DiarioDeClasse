package br.gov.sp.educacao.sed.mobile.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.gov.sp.educacao.sed.mobile.Fragment.FragmentTabFrequenciaConsulta;
import br.gov.sp.educacao.sed.mobile.Fragment.FragmentTabFrequenciaLancamento;

/**
 * Created by techresult on 03/07/2015.
 */
public class ViewPagerTabFrequenciaAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[];
    int numOfTabs;

    public ViewPagerTabFrequenciaAdapter(FragmentManager fm, CharSequence titles[], int numOfTabs) {
        super(fm);

        this.titles = titles;
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new FragmentTabFrequenciaLancamento();
        }
        else{
            return new FragmentTabFrequenciaConsulta();
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
