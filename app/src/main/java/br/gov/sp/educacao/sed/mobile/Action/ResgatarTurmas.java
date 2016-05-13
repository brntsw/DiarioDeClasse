package br.gov.sp.educacao.sed.mobile.Action;

import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import br.gov.sp.educacao.sed.mobile.Enuns.TipoServicoEnum;
import br.gov.sp.educacao.sed.mobile.Servidor.ConnectHandler;
import br.gov.sp.educacao.sed.mobile.Servidor.ParametroJson;
import br.gov.sp.educacao.sed.mobile.Servidor.RetornoJson;

/**
 * Created by afirmanet on 03/04/16.
 */
public class ResgatarTurmas {
    private Context context;

    public ResgatarTurmas(Context context) {
        this.context = context;
    }

    public Boolean executar() {
        Boolean returno = Boolean.FALSE;

        try {

            new EnviarFrequencia(context).executar();
            new EnviarRegistroAula(context).executar();
            new EnviarAvaliacao(context).executar();

            ParametroJson parametroJson = new ParametroJson();
            parametroJson.setContext(context);
            parametroJson.setJsonObject(null);
            parametroJson.setTipoServicoEnum(TipoServicoEnum.DADOS_OFF_LINE);

            ConnectHandler connectHandler = new ConnectHandler();
            connectHandler.execute(parametroJson);

            RetornoJson retorno = connectHandler.get();


            if (RetornoJson.RETORNO_COM_SUCESSO == retorno.getStatusRetorno()
                    || RetornoJson.RETORNO_COM_SUCESSO_SEM_RESPOSTA == retorno.getStatusRetorno()) {
                AtualizarBancoOff.updateTurmas(context, retorno.getResultJson());

                returno = Boolean.TRUE;
            }

        } catch (JSONException | InterruptedException | ExecutionException | SQLException | IOException e) {
            e.printStackTrace();
        }

        return returno;
    }
}
