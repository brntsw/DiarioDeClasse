package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AlexandreFQM on 21/03/16.
 */
public class GruposTO implements GenericsTable {
    public static final String nomeTabela = "GRUPOS";

    private Integer id;
    private Integer codigoGrupo;
    private Integer anoLetivo;
    private Integer codigoTipoEnsino;
    private Integer serie;
    private Integer codigoDisciplina;
    private Integer disciplina_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigoGrupo() {
        return codigoGrupo;
    }

    public void setCodigoGrupo(Integer codigoGrupo) {
        this.codigoGrupo = codigoGrupo;
    }

    public Integer getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(Integer anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public Integer getCodigoTipoEnsino() {
        return codigoTipoEnsino;
    }

    public void setCodigoTipoEnsino(Integer codigoTipoEnsino) {
        this.codigoTipoEnsino = codigoTipoEnsino;
    }

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
    }

    public Integer getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(Integer codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public Integer getDisciplina_id() {
        return disciplina_id;
    }

    public void setDisciplina_id(Integer disciplina_id) {
        this.disciplina_id = disciplina_id;
    }

    public GruposTO(){}

    public GruposTO(JSONObject retorno, Integer disciplina_id) throws JSONException {
        setCodigoGrupo(retorno.getInt("Codigo"));
        setAnoLetivo(retorno.getInt("AnoLetivo"));
        setCodigoTipoEnsino(retorno.getInt("CodigoTipoEnsino"));
        setSerie(retorno.getInt("Serie"));
        setCodigoDisciplina(retorno.getInt("CodigoDisciplina"));


        setDisciplina_id(disciplina_id);

    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("codigoGrupo", getCodigoGrupo());
        values.put("anoLetivo", getAnoLetivo());
        values.put("codigoTipoEnsino", getCodigoTipoEnsino());
        values.put("serie", getSerie());
        values.put("codigoDisciplina", getCodigoDisciplina());
        values.put("disciplina_id", getDisciplina_id());

        return values;

    }

    @Override
    public String getCodigoUnico() {
        return "codigoTipoEnsino = " + getCodigoTipoEnsino() + " and disciplina_id = " + getDisciplina_id() + " and serie = " + getSerie();
    }
}


