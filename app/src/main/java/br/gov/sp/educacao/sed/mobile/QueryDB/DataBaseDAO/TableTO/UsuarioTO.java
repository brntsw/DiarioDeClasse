package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by afirmanet on 20/03/16.
 */
public class UsuarioTO implements GenericsTable, Serializable {
    public static final String nomeTabela = "USUARIO";

    private Integer id;
    private String usuario;
    private String senha;
    private String nome;
    private String cpf;
    private String rg;
    private String digitoRG;
    private String dataUltimoAcesso;
    private Integer ativo;
    private String token;

    private List<String> arrayAnoLetivo;

    public List<String> getArrayAnoLetivo() {
        return arrayAnoLetivo;
    }

    public void setArrayAnoLetivo(List<String> arrayAnoLetivo) {
        this.arrayAnoLetivo = arrayAnoLetivo;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getDigitoRG() {
        return digitoRG;
    }

    public void setDigitoRG(String digitoRG) {
        this.digitoRG = digitoRG;
    }

    public String getDataUltimoAcesso() {
        return dataUltimoAcesso;
    }

    public void setDataUltimoAcesso(String dataUltimoAcesso) {
        this.dataUltimoAcesso = dataUltimoAcesso;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UsuarioTO(Cursor cursor){
        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setNome(cursor.getString(cursor.getColumnIndex("nome")));
        setUsuario(cursor.getString(cursor.getColumnIndex("usuario")));
        setSenha(cursor.getString(cursor.getColumnIndex("senha")));
        setCpf(cursor.getString(cursor.getColumnIndex("cpf")));

        setRg(cursor.getString(cursor.getColumnIndex("rg")));
        setDigitoRG(cursor.getString(cursor.getColumnIndex("digitoRG")));
        setDataUltimoAcesso(cursor.getString(cursor.getColumnIndex("dataUltimoAcesso")));
        setAtivo(cursor.getInt(cursor.getColumnIndex("ativo")));

        setToken(cursor.getString(cursor.getColumnIndex("token")));

        Calendar anoAtual = Calendar.getInstance();
        List<String> anoLetivo = new ArrayList<String>();
        anoLetivo.add(String.valueOf(anoAtual.get(Calendar.YEAR)));
        setArrayAnoLetivo(anoLetivo);

    }

    public UsuarioTO(JSONObject retorno) throws JSONException {
        setUsuario(retorno.getString("Usuario").trim());
        setSenha(retorno.getString("Senha").trim());
        setNome(retorno.getString("Nome").trim());
        setCpf(retorno.getString("NumeroCpf").trim());
        setRg(retorno.getString("numeroRg").trim());
        setDigitoRG(retorno.getString("digitoRg").trim());
        setAtivo(1);
        setToken(retorno.getString("Token"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        setDataUltimoAcesso(dateFormat.format(new Date()).toString());
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put("usuario", getUsuario());
        values.put("senha", getSenha());
        values.put("nome", getNome());
        values.put("cpf", getCpf());
        values.put("rg", getRg());
        values.put("digitoRG", getDigitoRG());
        values.put("dataUltimoAcesso", getDataUltimoAcesso());
        values.put("ativo", getAtivo());
        values.put("token", getToken());

        return values;

    }

    @Override
    public String getCodigoUnico() {
        return "cpf = " + getCpf();
    }
}
