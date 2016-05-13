package br.gov.sp.educacao.sed.mobile.Util;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import br.gov.sp.educacao.sed.mobile.HomeActivity;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.RegistroAulasActivity;

/**
 * Created by techresult on 31/07/2015.
 */
public class DialogRegistrarAula extends DialogFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View rootView = inflater.inflate(R.layout.layout_dialog_registrar_aula, container, false);
        Button btnSim = (Button) rootView.findViewById(R.id.btnSim);
        Button btnNao = (Button) rootView.findViewById(R.id.btnNao);

        HomeActivity homeActivity = (HomeActivity) getActivity();
        homeActivity.setButtons(btnSim, btnNao);

        return rootView;
    }

}
