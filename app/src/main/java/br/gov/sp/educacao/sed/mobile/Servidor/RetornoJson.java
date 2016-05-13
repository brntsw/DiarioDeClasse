package br.gov.sp.educacao.sed.mobile.Servidor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by AlexandreFQM on 31/03/16.
 */
public class RetornoJson {
    public static final int TOKEN_EXPIRADO = 400;
    public static final int RETORNO_COM_SUCESSO = 200;
    public static final int RETORNO_COM_SUCESSO_SEM_RESPOSTA = 204;
    public static final int ERROR = 500;
    public static final int NAO_ENCONTRADO = 404;

    private int statusRetorno;
    private String resultString;

    public RetornoJson(int statusRetorno, String resultString) {
        this.statusRetorno = statusRetorno;
        this.resultString = resultString;

    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public int getStatusRetorno() {
        return statusRetorno;
    }

    public void setStatusRetorno(int statusRetorno) {
        this.statusRetorno = statusRetorno;
    }

    public JSONObject getResultJson() {
        JSONObject jsonObject = new JSONObject();
        try{
            if (getResultString() != null && !getResultString().equals("") && !getResultString().equals(null)) {
                Object json = null;

                json = new JSONTokener(getResultString()).nextValue();


                if (!(json instanceof JSONObject)) {

                    jsonObject = new JSONObject();

                    jsonObject.put("Lista", new JSONArray(getResultString()));
                } else {

                    jsonObject = new JSONObject(getResultString());
                }
            }
        }catch(JSONException e ){
            jsonObject = new JSONObject();

            try {
                jsonObject.put("Message", "Erro de servidor, tente novamente mais tarde!");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        return jsonObject;
    }
}
