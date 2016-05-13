package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by afirmanet on 20/03/16.
 */
public class AlunosTO implements GenericsTable {
    public static final String nomeTabela = "ALUNOS";

    private Integer id;
    private Integer codigoMatricula;
    private Integer codigoAluno;
    private Integer alunoAtivo;
    private Integer numeroChamada;
    private String nomeAluno;
    private Integer numeroRa;
    private String digitoRa;
    private String ufRa;
    private String pai;
    private String mae;
    private Integer turma_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(Integer codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public Integer getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(Integer codigoAluno) {
        this.codigoAluno = codigoAluno;
    }

    public Integer getAlunoAtivo() {
        return alunoAtivo;
    }

    public void setAlunoAtivo(Integer alunoAtivo) {
        this.alunoAtivo = alunoAtivo;
    }

    public Integer getNumeroChamada() {
        return numeroChamada;
    }

    public void setNumeroChamada(Integer numeroChamada) {
        this.numeroChamada = numeroChamada;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public Integer getNumeroRa() {
        return numeroRa;
    }

    public void setNumeroRa(Integer numeroRa) {
        this.numeroRa = numeroRa;
    }

    public String getDigitoRa() {
        return digitoRa;
    }

    public void setDigitoRa(String digitoRa) {
        this.digitoRa = digitoRa;
    }

    public String getUfRa() {
        return ufRa;
    }

    public void setUfRa(String ufRa) {
        this.ufRa = ufRa;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public Integer getTurma_id() {
        return turma_id;
    }

    public void setTurma_id(Integer turma_id) {
        this.turma_id = turma_id;
    }

    public AlunosTO(){}

    public AlunosTO(JSONObject retorno, Integer turma_id) throws JSONException {
        setCodigoMatricula(retorno.getInt("CodigoMatricula"));
        setCodigoAluno(retorno.getInt("CodigoAluno"));

        setAlunoAtivo("Ativo".equals(retorno.getString("AlunoAtivo").trim()) ? 1 : 0);

        setNumeroChamada(retorno.getInt("NumeroChamada"));
        setNomeAluno(retorno.getString("NomeAluno").trim());
        setNumeroRa(retorno.getInt("NumeroRa"));
        setDigitoRa(retorno.getString("DigitoRa"));
        setUfRa(retorno.getString("UfRa"));

        JSONObject pais = retorno.getJSONObject("Pais");
        setPai(pais.getString("Pai").trim());
        setMae(pais.getString("Mae").trim());

        setTurma_id(turma_id);
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("codigoMatricula", getCodigoMatricula());
        values.put("codigoAluno", getCodigoAluno());
        values.put("alunoAtivo", getAlunoAtivo());
        values.put("numeroChamada", getNumeroChamada());
        values.put("nomeAluno", getNomeAluno());
        values.put("numeroRa", getNumeroRa());
        values.put("digitoRa", getDigitoRa());
        values.put("ufRa", getUfRa());
        values.put("pai", getPai());
        values.put("mae", getMae());
        values.put("turma_id", getTurma_id());

        return values;

    }

    @Override
    public String getCodigoUnico() {
        return "codigoMatricula = " + getCodigoMatricula();
    }

}
