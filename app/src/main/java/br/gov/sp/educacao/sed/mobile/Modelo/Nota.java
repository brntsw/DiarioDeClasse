package br.gov.sp.educacao.sed.mobile.Modelo;

import java.io.Serializable;

/**
 * Created by techresult on 18/11/2015.
 */
public class Nota implements Serializable {

    String codMatricula;
    float nota;

    public String getCodMatricula() {
        return codMatricula;
    }

    public void setCodMatricula(String codMatricula) {
        this.codMatricula = codMatricula;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}
