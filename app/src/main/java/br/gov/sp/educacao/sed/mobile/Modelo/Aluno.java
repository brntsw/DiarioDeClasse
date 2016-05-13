package br.gov.sp.educacao.sed.mobile.Modelo;

import java.io.Serializable;

/**
 * Created by techresult on 24/06/2015.
 */
public class Aluno implements Serializable {

    private int codigoAluno;
    private int alunoAtivo;
    private int numeroChamada;
    private String nomeAluno;
    private String numeroRa;
    private String digitoRa;
    private String ufRa;
    private int codigoTurma;
    private int codigoDisciplina;
    private int id;
    private String comparecimento;
    private int codigoMatricula;
    private int faltasAnuais;
    private int faltasBimestre;
    private int faltasSequenciais;
    private String nomePai;
    private String nomeMae;

    public Aluno() {
    }

    public Aluno(int codigoMatricula, int codigoAluno, int alunoAtivo, int numeroChamada, String nomeAluno, String numeroRa, String digitoRa, String ufRa, int codigoTurma, int codigoDisciplina,  int faltasAnuais, int faltasBimestre, int faltasSequenciais) {
        this.codigoMatricula = codigoMatricula;
        this.codigoAluno = codigoAluno;
        this.alunoAtivo = alunoAtivo;
        this.numeroChamada = numeroChamada;
        this.nomeAluno = nomeAluno;
        this.numeroRa = numeroRa;
        this.setDigitoRa(digitoRa);
        this.ufRa = ufRa;
        this.codigoTurma = codigoTurma;
        this.codigoDisciplina = codigoDisciplina;
        this.faltasAnuais = faltasAnuais;
        this.faltasBimestre = faltasBimestre;
        this.faltasSequenciais = faltasSequenciais;
    }

    public int getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(int codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public int getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(int codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(int codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public int getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(int codigoAluno) {
        this.codigoAluno = codigoAluno;
    }


    public int getNumeroChamada() {
        return numeroChamada;
    }

    public void setNumeroChamada(int numeroChamada) {
        this.numeroChamada = numeroChamada;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getNumeroRa() {
        return numeroRa;
    }

    public void setNumeroRa(String numeroRa) {
        this.numeroRa = numeroRa;
    }


    public String getUfRa() {
        return ufRa;
    }

    public void setUfRa(String ufRa) {
        this.ufRa = ufRa;
    }

    public int getFaltasAnuais() {
        return faltasAnuais;
    }

    public void setFaltasAnuais(int faltasAnuais) {
        this.faltasAnuais = faltasAnuais;
    }

    public int getFaltasBimestre() {
        return faltasBimestre;
    }

    public void setFaltasBimestre(int faltasBimestre) {
        this.faltasBimestre = faltasBimestre;
    }

    public int getFaltasSequenciais() {
        return faltasSequenciais;
    }

    public void setFaltasSequenciais(int faltasSequenciais) {
        this.faltasSequenciais = faltasSequenciais;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getDigitoRa() {
        return digitoRa;
    }

    public void setDigitoRa(String digitoRa) {
        this.digitoRa = digitoRa;
    }

    public int getAlunoAtivo() {
        return alunoAtivo;
    }

    public void setAlunoAtivo(int alunoAtivo) {
        this.alunoAtivo = alunoAtivo;
    }

    public String getComparecimento() {
        return comparecimento;
    }

    public void setComparecimento(String comparecimento) {
        this.comparecimento = comparecimento;
    }
}