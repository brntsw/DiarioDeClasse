package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AlexandreFQM on 21/03/16.
 */
public class ConteudoTO implements GenericsTable {
    public static final String nomeTabela = "CONTEUDO";

    private Integer id;
    private Integer codigoConteudo;
    private String descricaoConteudo;
    private Integer grupo_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigoConteudo() {
        return codigoConteudo;
    }

    public void setCodigoConteudo(Integer codigoConteudo) {
        this.codigoConteudo = codigoConteudo;
    }

    public String getDescricaoConteudo() {
        return descricaoConteudo;
    }

    public void setDescricaoConteudo(String descricaoConteudo) {
        this.descricaoConteudo = descricaoConteudo;
    }

    public Integer getGrupo_id() {
        return grupo_id;
    }

    public void setGrupo_id(Integer grupo_id) {
        this.grupo_id = grupo_id;
    }

    public ConteudoTO(){}

    public ConteudoTO(JSONObject retorno, Integer grupo_id) throws JSONException {
        setCodigoConteudo(retorno.getInt("Codigo"));
        setDescricaoConteudo(retorno.getString("Descricao").trim());

        setGrupo_id(grupo_id);

    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("codigoConteudo", getCodigoConteudo());
        values.put("descricaoConteudo", getDescricaoConteudo());
        values.put("grupo_id", getGrupo_id());

        return values;

    }

    @Override
    public String getCodigoUnico() {
        return "codigoConteudo = " + getCodigoConteudo() + " and grupo_id = " + getGrupo_id();
    }
}


