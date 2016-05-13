package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AlexandreFQM on 21/03/16.
 */
public class FaltasAlunosTO implements GenericsTable {
    public static final String nomeTabela = "FALTASALUNOS";


    private Integer id;
    private String tipoFalta;
    private String dataCadastro;
    private String dataServidor;
    private Integer aluno_id;
    private Integer usuario_id;
    private Integer diasLetivos_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoFalta() {
        return tipoFalta;
    }

    public void setTipoFalta(String tipoFalta) {
        this.tipoFalta = tipoFalta;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getDataServidor() {
        return dataServidor;
    }

    public void setDataServidor(String dataServidor) {
        this.dataServidor = dataServidor;
    }

    public Integer getAluno_id() {
        return aluno_id;
    }

    public void setAluno_id(Integer aluno_id) {
        this.aluno_id = aluno_id;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Integer getDiasLetivos_id() {
        return diasLetivos_id;
    }

    public void setDiasLetivos_id(Integer diasLetivos_id) {
        this.diasLetivos_id = diasLetivos_id;
    }



    public FaltasAlunosTO(){
    }

    public FaltasAlunosTO(JSONObject retorno, Integer aluno_id, Integer usuario_id, Integer diasLetivos_id) throws JSONException {
        setTipoFalta(retorno.getString("TipoFalta"));
        setDataCadastro(retorno.getString("DataCadastro").trim());
        setDataServidor(retorno.getString("DataServidor").trim());

        setAluno_id(aluno_id);
        setUsuario_id(usuario_id);
        setDiasLetivos_id(diasLetivos_id);

    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("tipoFalta", getTipoFalta());
        values.put("dataCadastro", getDataCadastro());
        values.put("dataServidor", getDataServidor());
        values.put("aluno_id", getAluno_id());
        values.put("usuario_id", getUsuario_id());
        values.put("diasLetivos_id", getDiasLetivos_id());

        return values;
    }

    @Override
    public String getCodigoUnico() {
        return "diasLetivos_id = " + getDiasLetivos_id() + " and aluno_id = " + getAluno_id();
    }
}


