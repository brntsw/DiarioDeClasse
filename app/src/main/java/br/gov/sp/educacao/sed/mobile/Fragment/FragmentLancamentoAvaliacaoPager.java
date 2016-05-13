package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Modelo.Avaliacao;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.QueryDB.AvaliacaoQueryDB;
import br.gov.sp.educacao.sed.mobile.R;

/**
 * Created by techresult on 26/08/2015.
 */
public class FragmentLancamentoAvaliacaoPager extends Fragment {

    private LinearLayout layout, listaLancamento;
    private TextView tvAvaliacao, tvTurma;
    private Button btnAnterior, btnProximo;
    private ScreenSlideFragmentAvaliacao screenSlideFragmentAvaliacao;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private int NUM_PAGES;
    private ArrayList<Aluno> listaAlunos;
    private Bundle bundle;
    private TurmaGrupo turmaGrupo;
    private Avaliacao avaliacao;
    private Menu menu;
    private AlertDialog alert;
    private String data;
    private MenuItem item;
    private LinearLayout layoutPager;
    private boolean flagMenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        flagMenu = true;

        layout = (LinearLayout) inflater.inflate(R.layout.fragment_avaliacao_lancamento_pager, container, false);
        bundle = getArguments();
        turmaGrupo = (TurmaGrupo) bundle.getSerializable(TurmaGrupo.BUNDLE_TURMA_GRUPO);
        avaliacao = (Avaliacao) bundle.getSerializable(Avaliacao.BUNDLE_AVALIACAO);

        tvAvaliacao = (TextView) layout.findViewById(R.id.tv_avaliacao);
        tvTurma = (TextView) layout.findViewById(R.id.tv_turma);

        tvTurma.setText(turmaGrupo.getTurma().getNomeTurma() + " / " + turmaGrupo.getDisciplina().getNomeDisciplina());
        tvAvaliacao.setText(avaliacao.getNome() + " - " + avaliacao.getData());

        data = avaliacao.getData();

        listaAlunos = new ArrayList<>();
        for (int i = 0; i < turmaGrupo.getTurma().arrayAlunoSize(); i++) {
            Aluno aluno = turmaGrupo.getTurma().getAluno(i);
            listaAlunos.add(aluno);
        }
        Collections.sort(listaAlunos, new Comparator<Aluno>() {
            @Override
            public int compare(Aluno p1, Aluno p2) {
                return p1.getNumeroChamada() - p2.getNumeroChamada();
            }

        });

        NUM_PAGES = listaAlunos.size();

        listaLancamento = (LinearLayout) layout.findViewById(R.id.lista_lancamento);

        LayoutInflater inflaterPager = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutPager = (LinearLayout) inflaterPager.inflate(R.layout.layout_pager_alunos, container, false);
        listaLancamento.addView(layoutPager);

        mPager = (ViewPager) layoutPager.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        //Botoes Anterior e Proximo
        btnAnterior = (Button) layoutPager.findViewById(R.id.btnAnterior);
        btnProximo = (Button) layoutPager.findViewById(R.id.btnProximo);

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPager.getCurrentItem() + 1 == listaAlunos.size()){
                    //Toast.makeText(getActivity(), "ultimo", Toast.LENGTH_LONG).show();

                    Bundle bundle = new Bundle();

                    bundle.putSerializable("listaAlunos", listaAlunos);
                    bundle.putSerializable(Avaliacao.BUNDLE_AVALIACAO, avaliacao);
                    bundle.putBoolean("flag", false);

                    FragmentAvaliacaoLista fragmentAvaliacaoLista = new FragmentAvaliacaoLista();
                    fragmentAvaliacaoLista.fragmentLancamentoAvaliacaoPager = FragmentLancamentoAvaliacaoPager.this;
                    fragmentAvaliacaoLista.setArguments(bundle);

                    listaLancamento.removeAllViews();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.lista_lancamento, fragmentAvaliacaoLista, "FragAvaliacaoLista");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    flagMenu = false;

                } else {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                }

            }
        });

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    btnAnterior.setEnabled(false);
                    btnAnterior.setClickable(false);
                } else {
                    btnAnterior.setEnabled(true);
                    btnAnterior.setClickable(true);
                }

                if (position + 1 == NUM_PAGES) {
                    btnProximo.setText("LISTAR");
                    btnProximo.setBackgroundResource(R.drawable.btn_listar);
                    //AvaliacaoQueryDB.listarAvaliaco(getActivity(), avaliacao);
                    setItem2();
                } else {
                    btnProximo.setText(R.string.proximo);
                    btnProximo.setBackgroundResource(R.drawable.btn_screen_slide_lancamento);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return layout;
    }

    public void refreshLista(){

        listaLancamento.removeAllViews();
        listaLancamento.addView(layoutPager);

        mPager = (ViewPager) layoutPager.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());

        //mPager.setAdapter(mPagerAdapter);

        //goToAluno(NUM_PAGES-1);
        goToAluno(0);

        //

        //Botoes Anterior e Proximo
        btnAnterior = (Button) layoutPager.findViewById(R.id.btnAnterior);
        btnProximo = (Button) layoutPager.findViewById(R.id.btnProximo);
    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.menu_lancamento_frequencia, menu);

        this.menu = menu;
        //setItem();
        if (flagMenu){
            setItem2();
        } else {
            item = menu.findItem(R.id.action_listar);
            item.setEnabled(false);
            item.setVisible(false);
            flagMenu = true;
        }
    }

    public boolean onOptionsItemSelected( MenuItem item ) {
        switch (item.getItemId()) {
            /*case R.id.action_confirmar:
                alertConfirma();
                return true;*/
            case R.id.action_listar:

                Bundle bundle = new Bundle();

                bundle.putSerializable("listaAlunos", listaAlunos);
                bundle.putSerializable("avaliacao", avaliacao);
                bundle.putBoolean("flag", true);

                FragmentAvaliacaoLista fragmentAvaliacaoLista = new FragmentAvaliacaoLista();
                fragmentAvaliacaoLista.fragmentLancamentoAvaliacaoPager = FragmentLancamentoAvaliacaoPager.this;
                fragmentAvaliacaoLista.setArguments(bundle);

                listaLancamento.removeAllViews();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.lista_lancamento, fragmentAvaliacaoLista, "FragAvaliacaoLista");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                flagMenu = false;

                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

    public void setItem(){
        //item = menu.findItem(R.id.action_confirmar);
        item.setEnabled(AvaliacaoQueryDB.getAvaliacaoCompleta(getActivity(), listaAlunos, avaliacao));
    }

    public void setItem2(){
        item = menu.findItem(R.id.action_listar);
        /*if (AvaliacaoQueryDB.getAvaliacaoListada(getActivity(), avaliacao)){
            item.setEnabled(true);
            item.setVisible(true);
        } else {
            item.setEnabled(false);
            item.setVisible(false);
        }*/
        item.setEnabled(true);
        item.setVisible(true);

        //item.setEnabled(false);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Bundle bundle = new Bundle();
            bundle.putInt("posicao", position);

            bundle.putSerializable("avaliacao", avaliacao);
            bundle.putSerializable("listaAlunos", listaAlunos);
            bundle.putSerializable("turma", turmaGrupo.getTurma());

            screenSlideFragmentAvaliacao = new ScreenSlideFragmentAvaliacao();
            screenSlideFragmentAvaliacao.fragmentLancamentoAvaliacaoPager = FragmentLancamentoAvaliacaoPager.this;
            screenSlideFragmentAvaliacao.setArguments(bundle);

            return screenSlideFragmentAvaliacao;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void nextAluno(){
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    public void goToAluno(int posicao){
        mPager.setCurrentItem(posicao, true);
    }

    public void goToFinish(){
        mPager.setCurrentItem(NUM_PAGES-1, true);
    }

}
