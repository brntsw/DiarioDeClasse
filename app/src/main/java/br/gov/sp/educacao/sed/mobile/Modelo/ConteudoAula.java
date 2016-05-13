package br.gov.sp.educacao.sed.mobile.Modelo;

import java.util.ArrayList;

/**
 * Created by techresult on 24/08/2015.
 */
public class ConteudoAula {

    private int id;
    private long curriculoId;
    private String descricao;
    private int codigo;
    private int bimestre;
    private ArrayList<HabilidadeAula> habilidades;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setCurriculoId(long curriculoId){
        this.curriculoId = curriculoId;
    }

    public long getCurriculoId(){
        return curriculoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getBimestre() {
        return bimestre;
    }

    public void setBimestre(int bimestre) {
        this.bimestre = bimestre;
    }

    public ArrayList<HabilidadeAula> getHabilidades(){
        return habilidades;
    }

    public void setHabilidades(ArrayList<HabilidadeAula> habilidades){
        this.habilidades = habilidades;
    }
}
