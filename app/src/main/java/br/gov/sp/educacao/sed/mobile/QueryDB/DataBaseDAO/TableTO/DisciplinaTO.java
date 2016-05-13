package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AlexandreFQM on 21/03/16.
 */
public class DisciplinaTO  implements GenericsTable {
    public static final String nomeTabela = "DISCIPLINA";

    private Integer id;
    private Integer codigoDisciplina;
    private String nomeDisciplina;
    private Integer turmasFrequencia_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(Integer codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public Integer getTurmasFrequencia_id() {
        return turmasFrequencia_id;
    }

    public void setTurmasFrequencia_id(Integer turma_id) {
        this.turmasFrequencia_id = turma_id;
    }


    public DisciplinaTO(){}

    public DisciplinaTO(JSONObject retorno, Integer turmasFrequencia_id) throws JSONException {
        setCodigoDisciplina(retorno.getInt("CodigoDisciplina"));
        setNomeDisciplina(retorno.getString("NomeDisciplina").trim());

        setTurmasFrequencia_id(turmasFrequencia_id);
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("codigoDisciplina", getCodigoDisciplina());
        values.put("nomeDisciplina", getNomeDisciplina());
        values.put("turmasFrequencia_id", getTurmasFrequencia_id());

        return values;

    }

    @Override
    public String getCodigoUnico() {
        return "codigoDisciplina = " + getCodigoDisciplina() + " and turmasFrequencia_id = " + getTurmasFrequencia_id();
    }
}


