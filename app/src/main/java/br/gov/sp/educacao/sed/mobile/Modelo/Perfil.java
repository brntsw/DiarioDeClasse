package br.gov.sp.educacao.sed.mobile.Modelo;

import java.io.Serializable;

public class Perfil implements Serializable {

    private int comportamento;
    private int codigo;
    private int nivel;
    private String nome;

    public Perfil(int codigo, int nivel, String nome, int comportamento) {
        this.comportamento = comportamento;
        this.nivel = nivel;
        this.nome = nome;
        this.codigo = codigo;
    }

    public Perfil(int comportamento, String nome) {
        this.comportamento = comportamento;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getComportamento() {
        return comportamento;
    }

    public void setComportamento(int comportamento) {
        this.comportamento = comportamento;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}
