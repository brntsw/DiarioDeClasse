package br.gov.sp.educacao.sed.mobile.Modelo;

import java.io.Serializable;

/**
 * Created by techresult on 10/11/2015.
 */
public class Bimestre implements Serializable {
    private int id;
    private int numero;
    private String inicio, fim;

    public Bimestre() {
    }

    public Bimestre(int numero, String inicio, String fim) {
        this.numero = numero;
        this.inicio = inicio;
        this.fim = fim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }
}
