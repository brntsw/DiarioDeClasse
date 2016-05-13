package br.gov.sp.educacao.sed.mobile.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by techresult on 25/08/2015.
 */
public class Avaliacao implements Serializable {

    public static final String BUNDLE_AVALIACAO = "avaliacao";

    String nome, data, dataCadastro;
    int codigo, bimestre, codTurma, codDisciplina, id, tipoAtividade, mobileId;
    boolean valeNota;
    private int turmaId;
    private int disciplinaId;
    ArrayList<Nota> arrayListNota = new ArrayList<>();

    public int getCodigo(){
        return this.codigo;
    }

    public void setCodigo(int codigo){
        this.codigo = codigo;
    }

    public int getMobileId() {
        return mobileId;
    }

    public void setMobileId(int mobileId) {
        this.mobileId = mobileId;
    }

    public int getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(int tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    public boolean isValeNota() {
        return valeNota;
    }

    public void setValeNota(boolean valeNota) {
        this.valeNota = valeNota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataCadastro(){
        return this.dataCadastro;
    }

    public void setDataCadastro(String dataCadastro){
        this.dataCadastro = dataCadastro;
    }

    public int getBimestre() {
        return bimestre;
    }

    public void setBimestre(int bimestre) {
        this.bimestre = bimestre;
    }

    public int getCodTurma() {
        return codTurma;
    }

    public void setCodTurma(int codTurma) {
        this.codTurma = codTurma;
    }

    public int getCodDisciplina() {
        return codDisciplina;
    }

    public void setCodDisciplina(int codDisciplina) {
        this.codDisciplina = codDisciplina;
    }

    public int getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(int turmaId) {
        this.turmaId = turmaId;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }
}
