package br.gov.sp.educacao.sed.mobile.Servidor;

import android.content.Context;

import br.gov.sp.educacao.sed.mobile.Enuns.TipoServicoEnum;

/**
 * Created by afirmanet on 03/04/16.
 */
public class ParametroJson {
    private TipoServicoEnum tipoServicoEnum;
    private String jsonObject;
    private Context context;
    private String user;
    private String password;
    private Boolean isTokenExpirado;

    public TipoServicoEnum getTipoServicoEnum() {
        return tipoServicoEnum;
    }

    public void setTipoServicoEnum(TipoServicoEnum tipoServicoEnum) {
        this.tipoServicoEnum = tipoServicoEnum;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isTokenExpirado() {
        return isTokenExpirado;
    }

    public void setIsTokenExpirado(Boolean isTokenExpirado) {
        this.isTokenExpirado = isTokenExpirado;
    }
}