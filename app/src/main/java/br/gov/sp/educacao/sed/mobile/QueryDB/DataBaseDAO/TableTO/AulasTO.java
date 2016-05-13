package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AlexandreFQM on 21/03/16.
 */
public class AulasTO implements GenericsTable {
    public static final String nomeTabela = "AULAS";

    private Integer id;
    private String inicioHora;
    private String fimHora;
    private Integer diaSemana;
    private Integer codigoAula;
    private Integer disciplina_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInicioHora() {
        return inicioHora;
    }

    public void setInicioHora(String inicioHora) {
        this.inicioHora = inicioHora;
    }

    public String getFimHora() {
        return fimHora;
    }

    public void setFimHora(String fimHora) {
        this.fimHora = fimHora;
    }

    public Integer getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Integer getCodigoAula() {
        return codigoAula;
    }

    public void setCodigoAula(Integer codigoAula) {
        this.codigoAula = codigoAula;
    }

    public Integer getDisciplina_id() {
        return disciplina_id;
    }

    public void setDisciplina_id(Integer disciplina_id) {
        this.disciplina_id = disciplina_id;
    }

    public AulasTO(){}

    public AulasTO(JSONObject retorno, Integer disciplina_id) throws JSONException {
        setInicioHora(retorno.getString("Inicio").trim());
        setFimHora(retorno.getString("Fim").trim());
        setDiaSemana(retorno.getInt("DiaSemana"));
        setCodigoAula(retorno.getInt("CodigoAula"));

        setDisciplina_id(disciplina_id);
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("inicioHora", getInicioHora());
        values.put("fimHora", getFimHora());
        values.put("diaSemana", getDiaSemana());
        values.put("codigoAula", getCodigoAula());
        values.put("disciplina_id", getDisciplina_id());

        return values;

    }

    @Override
    public String getCodigoUnico() {
        return "inicioHora = '" + getInicioHora() + "' and fimHora = '" + getFimHora() + "' and diaSemana = " + getDiaSemana() + " and disciplina_id = " + getDisciplina_id();
    }

}


