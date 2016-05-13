package br.gov.sp.educacao.sed.mobile.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 24/06/2015.
 */
public class FragmentHome extends Fragment {

    private static int NUM_PAGES;

    private AppCompatActivity activity;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private ImageView videoNovo;
    LayoutInflater layout;
    private ArrayList<ImageView> bullets; //marcadores de paginas da home
    private UsuarioTO usuario;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = inflater;

        activity = (AppCompatActivity) getActivity();

        usuario = Queries.getUsuarioAtivo(activity);

        //Obtem o perfil e verifica quantos slides terao
        int perfil = getArguments().getInt("perfil");

        switch (perfil) {
            case 4:
                NUM_PAGES = 1;
                break;
        }

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_home, container, false);

        TextView tvOla = (TextView) layout.findViewById(R.id.ola_home);
        tvOla.setText(usuario.getNome());

        /* TESTE DE DENSIDADE DA TELA */
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated

        Log.d("Width", String.valueOf(width));
        Log.d("Height", String.valueOf(height));

        //Determine density
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //densidade
        switch (metrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                Log.d("Densidade", "LOW");
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                Log.d("Densidade", "MEDIUM");
                break;
            case DisplayMetrics.DENSITY_HIGH:
                Log.d("Densidade", "HIGH");
                break;
        }

        //Se o numero de paginas eh diferente de zero, entao o PagerAdapter eh instanciado
        if (NUM_PAGES == 1) {
            //Instancia a ViewPager e a PagerAdapter
            mPager = (ViewPager) layout.findViewById(R.id.pager);
            mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
            mPager.setAdapter(mPagerAdapter);

            LinearLayout linearBullets = (LinearLayout) layout.findViewById(R.id.bullets);
            linearBullets.setVisibility(View.GONE);
        } else if (NUM_PAGES > 1) {
            //Instancia a ViewPager e a PagerAdapter
            mPager = (ViewPager) layout.findViewById(R.id.pager);
            mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
            mPager.setAdapter(mPagerAdapter);

            final ImageView img1, img2, img3, img4;
            img1 = (ImageView) layout.findViewById(R.id.img_page1);
            img2 = (ImageView) layout.findViewById(R.id.img_page2);
            img3 = (ImageView) layout.findViewById(R.id.img_page3);
            img4 = (ImageView) layout.findViewById(R.id.img_page4);

            mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    switch (i) {
                        case 0:
                            img1.setImageResource(R.drawable.selected);
                            img2.setImageResource(R.drawable.not_selected);
                            img3.setImageResource(R.drawable.not_selected);
                            img4.setImageResource(R.drawable.not_selected);
                            break;
                        case 1:
                            img1.setImageResource(R.drawable.not_selected);
                            img2.setImageResource(R.drawable.selected);
                            img3.setImageResource(R.drawable.not_selected);
                            img4.setImageResource(R.drawable.not_selected);
                            break;
                        case 2:
                            img1.setImageResource(R.drawable.not_selected);
                            img2.setImageResource(R.drawable.not_selected);
                            img3.setImageResource(R.drawable.selected);
                            img4.setImageResource(R.drawable.not_selected);
                            break;
                        case 3:
                            img1.setImageResource(R.drawable.not_selected);
                            img2.setImageResource(R.drawable.not_selected);
                            img3.setImageResource(R.drawable.not_selected);
                            img4.setImageResource(R.drawable.selected);
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        } else {
            LinearLayout linearBullets = (LinearLayout) layout.findViewById(R.id.bullets);
            linearBullets.setVisibility(View.GONE);
        }

        return layout;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("perfil", "professor");
            bundle.putSerializable("usuario", usuario);

            Fragment fragment = new ScreenSlideFragmentHome();
            fragment.setArguments(bundle);

            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
