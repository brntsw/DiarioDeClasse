package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by afirmanet on 20/03/16.
 */
public class TurmasTO implements GenericsTable {
    public static final String nomeTabela = "TURMAS";

    private Integer id;
    private Integer codigoTurma;
    private String nomeTurma;
    private Integer codigoDiretoria;
    private String nomeDiretoria;
    private Integer codigoEscola;
    private String nomeEscola;
    private Integer codigoTipoEnsino;
    private String nomeTipoEnsino;
    private Integer anoLetivo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(Integer codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public Integer getCodigoDiretoria() {
        return codigoDiretoria;
    }

    public void setCodigoDiretoria(Integer codigoDiretoria) {
        this.codigoDiretoria = codigoDiretoria;
    }

    public String getNomeDiretoria() {
        return nomeDiretoria;
    }

    public void setNomeDiretoria(String nomeDiretoria) {
        this.nomeDiretoria = nomeDiretoria;
    }

    public Integer getCodigoEscola() {
        return codigoEscola;
    }

    public void setCodigoEscola(Integer codigoEscola) {
        this.codigoEscola = codigoEscola;
    }

    public String getNomeEscola() {
        return nomeEscola;
    }

    public void setNomeEscola(String nomeEscola) {
        this.nomeEscola = nomeEscola;
    }

    public Integer getCodigoTipoEnsino() {
        return codigoTipoEnsino;
    }

    public void setCodigoTipoEnsino(Integer codigoTipoEnsino) {
        this.codigoTipoEnsino = codigoTipoEnsino;
    }

    public String getNomeTipoEnsino() {
        return nomeTipoEnsino;
    }

    public void setNomeTipoEnsino(String nomeTipoEnsino) {
        this.nomeTipoEnsino = nomeTipoEnsino;
    }

    public Integer getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(Integer anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public TurmasTO(){}

    public TurmasTO(JSONObject retorno, Integer anoLetivo) throws JSONException {
        setCodigoTurma(retorno.getInt("CodigoTurma"));
        setNomeTurma(retorno.getString("NomeTurma").trim());
        setCodigoDiretoria(retorno.getInt("CodigoDiretoria"));
        setNomeDiretoria(retorno.getString("NomeDiretoria").trim());
        setCodigoEscola(retorno.getInt("CodigoEscola"));
        setNomeEscola(retorno.getString("NomeEscola").trim());
        setCodigoTipoEnsino(retorno.getInt("CodigoTipoEnsino"));
        setNomeTipoEnsino(retorno.getString("NomeTipoEnsino").trim());
        setAnoLetivo(anoLetivo);

    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("codigoTurma", getCodigoTurma());
        values.put("nomeTurma", getNomeTurma());
        values.put("codigoDiretoria", getCodigoDiretoria());
        values.put("nomeDiretoria", getNomeDiretoria());
        values.put("codigoEscola", getCodigoEscola());
        values.put("nomeEscola", getNomeEscola());
        values.put("codigoTipoEnsino", getCodigoTipoEnsino());
        values.put("nomeTipoEnsino", getNomeTipoEnsino());
        values.put("anoLetivo", getAnoLetivo());

        return values;

    }

    @Override
    public String getCodigoUnico() {
        return "codigoTurma = " + getCodigoTurma();
    }
}
