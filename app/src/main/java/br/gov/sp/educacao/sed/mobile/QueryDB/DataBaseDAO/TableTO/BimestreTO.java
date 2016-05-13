package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AlexandreFQM on 21/03/16.
 */
public class BimestreTO implements GenericsTable {
    public static final String nomeTabela = "BIMESTRE";

    private Integer id;
    private Integer numero;
    private String inicioBimestre;
    private String fimBimestre;
    private Integer turmasFrequencia_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getInicioBimestre() {
        return inicioBimestre;
    }

    public void setInicioBimestre(String inicioBimestre) {
        this.inicioBimestre = inicioBimestre;
    }

    public String getFimBimestre() {
        return fimBimestre;
    }

    public void setFimBimestre(String fimBimestre) {
        this.fimBimestre = fimBimestre;
    }

    public Integer getTurmasFrequencia_id() {
        return turmasFrequencia_id;
    }

    public void setTurmasFrequencia_id(Integer turmasFrequencia_id) {
        this.turmasFrequencia_id = turmasFrequencia_id;
    }

    public BimestreTO(){}

    public BimestreTO(JSONObject retorno, Integer turmasFrequencia_id) throws JSONException {
        setNumero(retorno.getInt("Numero"));
        setInicioBimestre(retorno.getString("Inicio").trim());
        setFimBimestre(retorno.getString("Fim").trim());

        setTurmasFrequencia_id(turmasFrequencia_id);
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("numero", getNumero());
        values.put("inicioBimestre", getInicioBimestre());
        values.put("fimBimestre", getFimBimestre());
        values.put("turmasFrequencia_id", getTurmasFrequencia_id());

        return values;

    }

    @Override
    public String getCodigoUnico() {
        return "numero = " + getNumero() + " and turmasFrequencia_id = " + getTurmasFrequencia_id();
    }
}


