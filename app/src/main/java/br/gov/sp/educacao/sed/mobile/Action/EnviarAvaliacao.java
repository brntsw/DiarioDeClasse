package br.gov.sp.educacao.sed.mobile.Action;

import android.content.Context;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.gov.sp.educacao.sed.mobile.Enuns.TipoServicoEnum;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.Servidor.ConnectHandler;
import br.gov.sp.educacao.sed.mobile.Servidor.ParametroJson;
import br.gov.sp.educacao.sed.mobile.Servidor.RetornoJson;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 01/12/2015.
 */
public class EnviarAvaliacao {

    private Context context;

    public EnviarAvaliacao(Context context) {
        this.context = context;
    }

    public void executar() {
        try {
            ParametroJson parametroJson = new ParametroJson();
            parametroJson.setContext(context);
            parametroJson.setJsonObject(montarJSOEnvio().toString());
            parametroJson.setTipoServicoEnum(TipoServicoEnum.AVALIACAO);

            if(!parametroJson.getJsonObject().equals("{}")) {
                ConnectHandler connectHandler = new ConnectHandler();
                connectHandler.execute(parametroJson);

                RetornoJson retorno = connectHandler.get();

                if (RetornoJson.RETORNO_COM_SUCESSO == retorno.getStatusRetorno()
                        || RetornoJson.RETORNO_COM_SUCESSO_SEM_RESPOSTA == retorno.getStatusRetorno()) {


                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                    AtualizarBancoOff.updateAvaliacao(context, dateFormat.format(new Date()).toString());

                } else if (RetornoJson.TOKEN_EXPIRADO == retorno.getStatusRetorno()) {
                    UsuarioTO usuario = Queries.getUsuarioAtivo(context);

                    ValidarLogin validarLogin = new ValidarLogin(context, usuario.getUsuario(), usuario.getSenha());
                    if (validarLogin.executar()) {
                        executar();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private JSONObject montarJSOEnvio() throws JSONException {
        StringBuffer query = new StringBuffer();
        query.append(" SELECT   av.codigoAvaliacao, av.codigoTurma, av.codigoDisciplina, av.codigoTipoAtividade, av.nome, av.data, av.bimestre, av.valeNota, av.mobileid, nt.nota, nt.codigoMatricula ");
        query.append(" FROM     AVALIACOES as av, ");
        query.append("          NOTASALUNO as nt ");
        query.append(" Where    av.id = nt.avaliacao_id ");
        query.append(" And      nt.dataServidor is null ");

        Cursor cursor = Queries.getSelect(context, query);

        JSONObject jsonEnvio = new JSONObject();
        if (cursor != null) {
            List<Integer> listAvaliacao = new ArrayList<>();

            JSONArray jsonArrayNotas  = new JSONArray();

            while (cursor.moveToNext()) {
                if (!listAvaliacao.contains(cursor.getInt(cursor.getColumnIndex("codigoAvaliacao")))) {
                    listAvaliacao.add(cursor.getInt(cursor.getColumnIndex("codigoAvaliacao")));

                    jsonEnvio.put("Codigo", cursor.getInt(cursor.getColumnIndex("codigoAvaliacao")));
                    jsonEnvio.put("CodigoTurma", cursor.getInt(cursor.getColumnIndex("codigoTurma")));
                    jsonEnvio.put("CodigoDisciplina", cursor.getInt(cursor.getColumnIndex("codigoDisciplina")));
                    jsonEnvio.put("CodigoTipoAtividade", cursor.getInt(cursor.getColumnIndex("codigoTipoAtividade")));
                    jsonEnvio.put("Nome", cursor.getString(cursor.getColumnIndex("nome")));
                    jsonEnvio.put("Data", cursor.getString(cursor.getColumnIndex("data")));
                    jsonEnvio.put("Bimestre", cursor.getInt(cursor.getColumnIndex("bimestre")));
                    jsonEnvio.put("ValeNota", cursor.getInt(cursor.getColumnIndex("valeNota")));
                    jsonEnvio.put("MobileId", cursor.getInt(cursor.getColumnIndex("mobileId")));

                    jsonArrayNotas = new JSONArray();
                    jsonEnvio.put("Notas", jsonArrayNotas);
                }

                if(cursor.getDouble(cursor.getColumnIndex("nota")) != 12){
                    JSONObject jsonObjectNota = new JSONObject();
                    jsonObjectNota.put("Nota", cursor.getDouble(cursor.getColumnIndex("nota")));
                    jsonObjectNota.put("CodigoMatriculaAluno", cursor.getInt(cursor.getColumnIndex("codigoMatricula")));

                    jsonArrayNotas.put(jsonObjectNota);
                }
            }
        }

        return jsonEnvio;
    }
}
