package br.gov.sp.educacao.sed.mobile.Modelo;

import java.util.ArrayList;

/**
 * Created by techresult on 24/08/2015.
 */
public class Curriculo {

    private int id;
    private long grupoId;
    private int codigo;
    private int codigoTurma;
    private int bimestre;
    private ArrayList<ConteudoAula> conteudos;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public ArrayList<ConteudoAula> getConteudos() {
        return conteudos;
    }

    public void setConteudos(ArrayList<ConteudoAula> conteudos) {
        this.conteudos = conteudos;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(long grupoId) {
        this.grupoId = grupoId;
    }

    public int getBimestre() {
        return bimestre;
    }

    public void setBimestre(int bimestre) {
        this.bimestre = bimestre;
    }

    public int getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(int codigoTurma) {
        this.codigoTurma = codigoTurma;
    }
}
