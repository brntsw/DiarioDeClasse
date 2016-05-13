package br.gov.sp.educacao.sed.mobile.Modelo;


import java.io.Serializable;

public class TurmasFrequencia implements Serializable {

    private Integer id;
    private Integer codigoTurma;
    private Integer codigoDiretoria;
    private Integer codigoEscola;
    private Integer codigoTipoEnsino;
    private Integer aulasBimestre;
    private Integer aulasAno;
    private Integer turma_id;

    public TurmasFrequencia(){

    }

    public TurmasFrequencia(Integer id, Integer codigoTurma, Integer codigoDiretoria, Integer codigoEscola, Integer codigoTipoEnsino, Integer aulasBimestre, Integer aulasAno, Integer turma_id) {
        this.id = id;
        this.codigoTurma = codigoTurma;
        this.codigoDiretoria = codigoDiretoria;
        this.codigoEscola = codigoEscola;
        this.codigoTipoEnsino = codigoTipoEnsino;
        this.aulasBimestre = aulasBimestre;
        this.aulasAno = aulasAno;
        this.turma_id = turma_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(Integer codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public Integer getCodigoDiretoria() {
        return codigoDiretoria;
    }

    public void setCodigoDiretoria(Integer codigoDiretoria) {
        this.codigoDiretoria = codigoDiretoria;
    }

    public Integer getCodigoEscola() {
        return codigoEscola;
    }

    public void setCodigoEscola(Integer codigoEscola) {
        this.codigoEscola = codigoEscola;
    }

    public Integer getCodigoTipoEnsino() {
        return codigoTipoEnsino;
    }

    public void setCodigoTipoEnsino(Integer codigoTipoEnsino) {
        this.codigoTipoEnsino = codigoTipoEnsino;
    }

    public Integer getAulasBimestre() {
        return aulasBimestre;
    }

    public void setAulasBimestre(Integer aulasBimestre) {
        this.aulasBimestre = aulasBimestre;
    }

    public Integer getAulasAno() {
        return aulasAno;
    }

    public void setAulasAno(Integer aulasAno) {
        this.aulasAno = aulasAno;
    }

    public Integer getTurma_id() {
        return turma_id;
    }

    public void setTurma_id(Integer turma_id) {
        this.turma_id = turma_id;
    }
}
