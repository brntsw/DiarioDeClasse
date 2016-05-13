package br.gov.sp.educacao.sed.mobile.Modelo;

/**
 * Created by techresult on 29/03/2016.
 */
public class Habilidades {

    private int id;
    private int codigoHabilidade;
    private String descricaoHabilidade;
    private int conteudoId;

    public int getCodigoHabilidade() {
        return codigoHabilidade;
    }

    public void setCodigoHabilidade(int codigoHabilidade) {
        this.codigoHabilidade = codigoHabilidade;
    }

    public String getDescricaoHabilidade() {
        return descricaoHabilidade;
    }

    public void setDescricaoHabilidade(String descricaoHabilidade) {
        this.descricaoHabilidade = descricaoHabilidade;
    }

    public int getConteudoId() {
        return conteudoId;
    }

    public void setConteudoId(int conteudoId) {
        this.conteudoId = conteudoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
