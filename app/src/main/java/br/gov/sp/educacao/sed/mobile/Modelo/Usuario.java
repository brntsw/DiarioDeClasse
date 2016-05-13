package br.gov.sp.educacao.sed.mobile.Modelo;

import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.ArrayList;


public class Usuario implements Serializable {

    private int id;
    private String user;
    private String name;
    private String senha;
    private String token;
    private String cpf;
    private String rg;
    private String digrg;
    //private ArrayList<TipoOcorrencia> arraytipoocorrencia = new ArrayList<TipoOcorrencia>();
    private ArrayList<Perfil> arrayperfil = new ArrayList<Perfil>();
    private Perfil perfilatual;
    private ArrayList<String> anoletivo = new ArrayList<String>();

    private static Usuario usuario = new Usuario();

    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    private boolean carregandoTurma;
    private boolean carregandoFrequencia;
    private int carregandoWS;

    public int getCarregandoWS() {
        return carregandoWS;
    }

    public void setCarregandoWS(int carregandoWS) {
        this.carregandoWS = carregandoWS;
    }

    public boolean isCarregandoFrequencia() {
        return carregandoFrequencia;
    }

    public void setCarregandoFrequencia(boolean carregandoFrequencia) {
        this.carregandoFrequencia = carregandoFrequencia;
    }

    public boolean isCarregandoTurma() {
        return carregandoTurma;
    }

    public void setCarregandoTurma(boolean carregandoTurma) {
        this.carregandoTurma = carregandoTurma;
    }

    public void setPrefs(SharedPreferences.Editor editor, SharedPreferences prefs) {
        this.editor = editor;
        this.prefs = prefs;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public String getDigrg() {
        return digrg;
    }

    public void setDigrg(String digrg) {
        this.digrg = digrg;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }


    public String getUser() {
        return user;
    }


    public void setUser(String user) {
        this.user = user;
    }


    public String getSenha() {
        return senha;
    }


    public void setSenha(String senha) {
        this.senha = senha;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public static Usuario getInstance() {
        return usuario;
    }


    public void addPerfil(Perfil perfil) {

        arrayperfil.add(perfil);

    }

    public Perfil getPerfilItem(int position) {
        return arrayperfil.get(position);
    }


    public Perfil getPerfilatual() {
        return perfilatual;
    }


    public void setPerfilatual(Perfil perfilatual) {
        this.perfilatual = perfilatual;
    }


    public int getArrayPerfilSize() {
        return arrayperfil.size();
    }

    public void clearArrayPerfil() {
        arrayperfil.clear();
    }

    public void adicionarAnoLetivo(String s) {
        anoletivo.add(s);
    }

    public String getAnoLetivo(int posicao) {
        return anoletivo.get(posicao);
    }

    public ArrayList<String> getArrayAnoLetivo() {
        return anoletivo;
    }

    public int getAnoLetivoSize() {
        return anoletivo.size();
    }

    public void clearArrayAnoLetivo() {
        anoletivo.clear();
    }
/*
    public void adicionarTipoOcorrencia(TipoOcorrencia tipoOcorrencia){
        arraytipoocorrencia.add(tipoOcorrencia);
    }

    public TipoOcorrencia getTipoOcorrencia(int position){
        return arraytipoocorrencia.get(position);
    }

    public int getArrayTipoOcorrenciaSize(){
        return arraytipoocorrencia.size();
    }

    public ArrayList<TipoOcorrencia> retornaTipoOcorrencia() {
        return arraytipoocorrencia;
    }
*/
}

