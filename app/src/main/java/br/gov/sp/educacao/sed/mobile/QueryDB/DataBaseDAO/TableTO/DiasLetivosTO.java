package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONException;

import java.io.Serializable;

/**
 * Created by AlexandreFQM on 21/03/16.
 */
public class DiasLetivosTO implements GenericsTable, Serializable {
    public static final String nomeTabela = "DIASLETIVOS";

    private Integer id;
    private String dataAula;
    private Integer bimestre_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataAula() {
        return dataAula;
    }

    public void setDataAula(String dataAula) {
        this.dataAula = dataAula;
    }

    public Integer getBimestre_id() {
        return bimestre_id;
    }

    public void setBimestre_id(Integer bimestre_id) {
        this.bimestre_id = bimestre_id;
    }

    public DiasLetivosTO(){}

    public DiasLetivosTO(Integer bimestre_id, Integer dia, Integer mes, Integer anoletivo) throws JSONException {
        setDataAula(dia + "/" + mes + "/" + anoletivo);

        setBimestre_id(bimestre_id);
    }


    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("dataAula", getDataAula());
        values.put("bimestre_id", getBimestre_id());

        return values;

    }

    @Override
    public String getCodigoUnico() {
        return "dataAula = '" + getDataAula() + "' and bimestre_id = " + getBimestre_id();
    }

}


