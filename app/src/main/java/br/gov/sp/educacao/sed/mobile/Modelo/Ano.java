package br.gov.sp.educacao.sed.mobile.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by techresult on 29/06/2015.
 */
public class Ano implements Serializable {

    String ano;
    ArrayList<Turma> turmaArrayList = new ArrayList<>();

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void addTurma(Turma turma) {
        turmaArrayList.add(turma);
    }
}
