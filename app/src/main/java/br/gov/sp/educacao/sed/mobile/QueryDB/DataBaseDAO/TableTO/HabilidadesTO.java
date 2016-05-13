package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AlexandreFQM on 21/03/16.
 */
public class HabilidadesTO implements GenericsTable {
    public static final String nomeTabela = "HABILIDADES";

    private Integer id;
    private Integer codigoHabilidade;
    private String descricaoHabilidade;
    private Integer conteudo_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigoHabilidade() {
        return codigoHabilidade;
    }

    public void setCodigoHabilidade(Integer codigoHabilidade) {
        this.codigoHabilidade = codigoHabilidade;
    }

    public String getDescricaoHabilidade() {
        return descricaoHabilidade;
    }

    public void setDescricaoHabilidade(String descricaoHabilidade) {
        this.descricaoHabilidade = descricaoHabilidade;
    }

    public Integer getConteudo_id() {
        return conteudo_id;
    }

    public void setConteudo_id(Integer conteudo_id) {
        this.conteudo_id = conteudo_id;
    }

    public HabilidadesTO(){}

    public HabilidadesTO(JSONObject retorno, Integer conteudo_id) throws JSONException {
        setCodigoHabilidade(retorno.getInt("Codigo"));
        setDescricaoHabilidade(retorno.getString("Descricao").trim());

        setConteudo_id(conteudo_id);

    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("codigoHabilidade", getCodigoHabilidade());
        values.put("descricaoHabilidade", getDescricaoHabilidade());
        values.put("conteudo_id", getConteudo_id());

        return values;

    }

    @Override
    public String getCodigoUnico() {
        return "codigoHabilidade = " + getCodigoHabilidade();
    }
}


