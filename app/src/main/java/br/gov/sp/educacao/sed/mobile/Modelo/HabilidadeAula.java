package br.gov.sp.educacao.sed.mobile.Modelo;

/**
 * Created by techresult on 24/08/2015.
 */
public class HabilidadeAula {
    private long conteudoId;
    private int codigo_conteudo;
    private int selecionado;
    private int codigo;
    private String descricao;

    public long getConteudoId(){
        return conteudoId;
    }

    public void setConteudoId(long conteudoId){
        this.conteudoId = conteudoId;
    }

    public int isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(int selecionado) {
        this.selecionado = selecionado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCodigoConteudo() {
        return codigo_conteudo;
    }

    public void setCodigoConteudo(int codigo_conteudo) {
        this.codigo_conteudo = codigo_conteudo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HabilidadeAula that = (HabilidadeAula) o;

        return codigo == that.codigo;
    }

    @Override
    public int hashCode() {
        return codigo;
    }

}
