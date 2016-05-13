package br.gov.sp.educacao.sed.mobile.Modelo;

/**
 * Created by techresult on 29/03/2016.
 */
public class HabilidadesAbordadas {

    private int id;
    private int habilidadeId;
    private int diasLetivosId;
    private int turmaId;
    private int usuarioId;
    private String descricao;
    private String dataCadastro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getHabilidadeId() {
        return habilidadeId;
    }

    public void setHabilidadeId(int habilidadeId) {
        this.habilidadeId = habilidadeId;
    }

    public int getDiasLetivosId() {
        return diasLetivosId;
    }

    public void setDiasLetivosId(int diasLetivosId) {
        this.diasLetivosId = diasLetivosId;
    }

    public int getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(int turmaId) {
        this.turmaId = turmaId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
