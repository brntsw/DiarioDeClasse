package br.gov.sp.educacao.sed.mobile.Modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by techresult on 21/07/2015.
 */
public class Aula implements Serializable {

    private int id;
    private String inicio, fim;
    private int diaSemana, codigoAula;
    private List<Integer> listaDiasSemana;

    public Aula(String inicio, String fim, int diaSemana, int codigoAula) {
        this.inicio = inicio;
        this.fim = fim;
        this.diaSemana = diaSemana;
        this.codigoAula = codigoAula;
    }

    public Aula() {
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

    public int getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    public int getCodigoAula() {
        return codigoAula;
    }

    public void setCodigoAula(int codigoAula) {
        this.codigoAula = codigoAula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getListaDiasSemana() {
        return listaDiasSemana;
    }

    public void setListaDiasSemana(List<Integer> listaDiasSemana) {
        this.listaDiasSemana = listaDiasSemana;
    }
}
