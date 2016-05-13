package br.gov.sp.educacao.sed.mobile.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by techresult on 20/07/2015.
 */
public class Calendario implements Serializable {

    int mes;
    ArrayList<Integer> diaArrayList = new ArrayList<>();

    public Calendario() {
    }

    public Calendario(int mes) {
        this.mes = mes;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void addDia(int dia){
        diaArrayList.add(dia);
    }

    public int arrayDiaSize() {
        return diaArrayList.size();
    }

    public int getDia(int position) {
        return diaArrayList.get(position);
    }

}
