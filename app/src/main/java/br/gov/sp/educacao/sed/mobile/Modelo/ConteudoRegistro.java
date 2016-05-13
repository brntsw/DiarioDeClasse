package br.gov.sp.educacao.sed.mobile.Modelo;

/**
 * Created by techresult on 04/09/2015.
 */
public class ConteudoRegistro {

    private int registroId;
    private int conteudo;
    private String dataRegistro;


    public int getRegistroId() {
        return registroId;
    }

    public void setRegistroId(int registroId) {
        this.registroId = registroId;
    }

    public int getConteudo() {
        return conteudo;
    }

    public void setConteudo(int conteudo) {
        this.conteudo = conteudo;
    }

    public String getDataRegistro(){
        return dataRegistro;
    }
    
    public void setDataRegistro(String dataRegistro){
        this.dataRegistro = dataRegistro;
    }
}
