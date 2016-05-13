package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AlexandreFQM on 21/03/16.
 */
public class NotasAlunoTO implements GenericsTable {
    public static final String nomeTabela = "NOTASALUNO";


    private Integer id;
    private Integer codigoMatricula;
    private String nota;
    private String dataCadastro;
    private String dataServidor;
    private Integer aluno_id;
    private Integer usuario_id;
    private Integer avaliacao_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(Integer codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
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

    public Integer getAvaliacao_id() {
        return avaliacao_id;
    }

    public void setAvaliacao_id(Integer avaliacao_id) {
        this.avaliacao_id = avaliacao_id;
    }

    public NotasAlunoTO(){}

    public NotasAlunoTO(JSONObject retorno, Integer aluno_id, Integer usuario_id, Integer avaliacao_id) throws JSONException {
        setCodigoMatricula(retorno.getInt("CodigoMatriculaAluno"));
        setNota(retorno.getString("Nota").trim());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String dataImportacao = dateFormat.format(new Date()).toString();

        setDataCadastro(dataImportacao);
        setDataServidor(dataImportacao);

        setAluno_id(aluno_id);
        setUsuario_id(usuario_id);
        setAvaliacao_id(avaliacao_id);
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("codigoMatricula", getCodigoMatricula());
        values.put("nota", getNota());
        values.put("dataCadastro", getDataCadastro());
        values.put("dataServidor", getDataServidor());
        values.put("aluno_id", getAluno_id());
        values.put("usuario_id", getUsuario_id());
        values.put("avaliacao_id", getAvaliacao_id());

        return values;

    }

    @Override
    public String getCodigoUnico() {
        return "aluno_id = " + getAluno_id() + " and avaliacao_id = " + getAvaliacao_id();
    }
}


