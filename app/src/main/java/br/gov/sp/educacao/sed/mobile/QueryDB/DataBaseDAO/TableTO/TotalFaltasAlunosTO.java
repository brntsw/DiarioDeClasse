package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AlexandreFQM on 21/03/16.
 */
public class TotalFaltasAlunosTO implements GenericsTable {
    public static final String nomeTabela = "TOTALFALTASALUNOS";

    private Integer id;
    private Integer codigoMatricula;
    private Integer faltasAnuais;
    private Integer faltasBimestre;
    private Integer faltasSequenciais;
    private Integer disciplica_id;

    private Integer aluno_id;

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

    public Integer getFaltasAnuais() {
        return faltasAnuais;
    }

    public void setFaltasAnuais(Integer faltasAnuais) {
        this.faltasAnuais = faltasAnuais;
    }

    public Integer getFaltasBimestre() {
        return faltasBimestre;
    }

    public void setFaltasBimestre(Integer faltasBimestre) {
        this.faltasBimestre = faltasBimestre;
    }

    public Integer getFaltasSequenciais() {
        return faltasSequenciais;
    }

    public void setFaltasSequenciais(Integer faltasSequenciais) {
        this.faltasSequenciais = faltasSequenciais;
    }

    public Integer getAluno_id() {
        return aluno_id;
    }

    public void setAluno_id(Integer aluno_id) {
        this.aluno_id = aluno_id;
    }

    public Integer getDisciplica_id() {
        return disciplica_id;
    }

    public void setDisciplica_id(Integer disciplica_id) {
        this.disciplica_id = disciplica_id;
    }


    public TotalFaltasAlunosTO(){
    }

    public TotalFaltasAlunosTO(JSONObject retorno, Integer disciplina_id, Integer aluno_id) throws JSONException {
        setCodigoMatricula(retorno.getInt("CodigoMatricula"));
        setFaltasAnuais(retorno.getInt("FaltasAnuais"));
        setFaltasBimestre(retorno.getInt("FaltasBimestre"));
        setFaltasSequenciais(retorno.getInt("FaltasSequenciais"));

        setDisciplica_id(disciplina_id);
        setAluno_id(aluno_id);
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("codigoMatricula", getCodigoMatricula());
        values.put("faltasAnuais", getFaltasAnuais());
        values.put("faltasBimestre", getFaltasBimestre());
        values.put("faltasSequenciais", getFaltasSequenciais());
        values.put("disciplina_id", getDisciplica_id());
        values.put("aluno_id", getAluno_id());

        return values;

    }

    @Override
    public String getCodigoUnico() {
        return "disciplina_id = " + getDisciplica_id() + " and aluno_id = " + getAluno_id();
    }
}


