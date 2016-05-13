package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AlexandreFQM on 21/03/16.
 */
public class HabilidadesAbordadasTO implements GenericsTable {
    public static final String nomeTabela = "HABILIDADESABORDADAS";


    private Integer id;
    private String dataCadastro;
    private String dataServidor;
    private String descricao;
    private Integer turma_id;
    private Integer habilidade_id;
    private Integer diasLetivos_id;
    private Integer usuario_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getTurma_id() {
        return turma_id;
    }

    public void setTurma_id(Integer turma_id) {
        this.turma_id = turma_id;
    }

    public Integer getHabilidade_id() {
        return habilidade_id;
    }

    public void setHabilidade_id(Integer habilidade_id) {
        this.habilidade_id = habilidade_id;
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

    public HabilidadesAbordadasTO(){}

    public HabilidadesAbordadasTO(JSONObject retorno, Integer turma_id, Integer habilidade_id, Integer diasLetivos_id, Integer usuario_id) throws JSONException {
        setDataCadastro(retorno.getString("DataCadastro").trim());
        setDataServidor(retorno.getString("DataServidor").trim());


        setTurma_id(turma_id);
        setHabilidade_id(habilidade_id);
        setDiasLetivos_id(diasLetivos_id);
        setUsuario_id(usuario_id);

    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("dataCadastro", getDataCadastro());
        values.put("dataServidor", getDataServidor());
        values.put("turma_id", getTurma_id());
        values.put("habilidade_id", getHabilidade_id());
        values.put("usuario_id", getUsuario_id());

        return values;

    }

    @Override
    public String getCodigoUnico() {
        return null;
    }
}


