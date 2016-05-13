package br.gov.sp.educacao.sed.mobile.Modelo;

/**
 * Created by techresult on 29/03/2016.
 */
public class Conteudo {

    private int id;
    private int codigoConteudo;
    private String descricaoConteudo;
    private int grupoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoConteudo() {
        return codigoConteudo;
    }

    public void setCodigoConteudo(int codigoConteudo) {
        this.codigoConteudo = codigoConteudo;
    }

    public String getDescricaoConteudo() {
        return descricaoConteudo;
    }

    public void setDescricaoConteudo(String descricaoConteudo) {
        this.descricaoConteudo = descricaoConteudo;
    }

    public int getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(int grupoId) {
        this.grupoId = grupoId;
    }
}
