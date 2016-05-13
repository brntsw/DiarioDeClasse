package br.gov.sp.educacao.sed.mobile.Modelo;

import java.io.Serializable;

public class Disciplina implements Serializable{

    private Integer id;
    private Integer codigoDisciplina;
    private String nomeDisciplina;
    private Integer turmasFrequencia_id;

    public Disciplina() {
    }

    public Disciplina(Integer id, Integer codigoDisciplina, String nomeDisciplina, Integer turmasFrequencia_id) {
        this.id = id;
        this.codigoDisciplina = codigoDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.turmasFrequencia_id = turmasFrequencia_id;
    }

    public Integer getId() {
        return id;

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(Integer codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public Integer getTurmasFrequencia_id() {
        return turmasFrequencia_id;
    }

    public void setTurmasFrequencia_id(Integer turmasFrequencia_id) {
        this.turmasFrequencia_id = turmasFrequencia_id;
    }
}
