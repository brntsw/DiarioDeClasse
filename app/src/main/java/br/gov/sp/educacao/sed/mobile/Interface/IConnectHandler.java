package br.gov.sp.educacao.sed.mobile.Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by techresult on 26/08/2014.
 */
public interface IConnectHandler {

    public void requisicaoConcluida(JSONObject retorno, boolean error, int tag) throws JSONException, SQLException, IOException;

    public void tokenExpirado(JSONObject retorno);

    public void finalRotina(JSONObject retorno, boolean error) throws IOException, SQLException, JSONException;
}
