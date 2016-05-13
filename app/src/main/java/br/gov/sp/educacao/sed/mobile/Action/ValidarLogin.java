package br.gov.sp.educacao.sed.mobile.Action;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import br.gov.sp.educacao.sed.mobile.Enuns.TipoServicoEnum;
import br.gov.sp.educacao.sed.mobile.Servidor.ConnectHandler;
import br.gov.sp.educacao.sed.mobile.Servidor.ParametroJson;
import br.gov.sp.educacao.sed.mobile.Servidor.RetornoJson;

/**
 * Created by afirmanet on 03/04/16.
 */
public class ValidarLogin {
    private Context context;
    private String user;
    private String password;

    public ValidarLogin(Context context, String user, String password) {
        this.context = context;
        this.user = user;
        this.password = password;
    }

    public Boolean executar() {
        Boolean returno = Boolean.FALSE;

        try {
            JSONObject jsonenvio = new JSONObject();
            jsonenvio.put("user", user).put("senha", password);

            ParametroJson parametroJson = new ParametroJson();
            parametroJson.setContext(context);
            parametroJson.setJsonObject(jsonenvio.toString());
            parametroJson.setTipoServicoEnum(TipoServicoEnum.LOGIN);

            ConnectHandler connectHandler = new ConnectHandler();
            connectHandler.execute(parametroJson);

            RetornoJson retorno = connectHandler.get();


            if (RetornoJson.RETORNO_COM_SUCESSO == retorno.getStatusRetorno()
                    || RetornoJson.RETORNO_COM_SUCESSO_SEM_RESPOSTA == retorno.getStatusRetorno()) {

                jsonenvio = retorno.getResultJson();
                jsonenvio.put("Usuario", user).put("Senha", password);

                AtualizarBancoOff.updateUsuario(context, jsonenvio);
                returno = Boolean.TRUE;
            }
            else{
                returno = Boolean.FALSE;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return returno;
    }
}