package br.gov.sp.educacao.sed.mobile.Action;

import android.content.Context;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import br.gov.sp.educacao.sed.mobile.Enuns.TipoServicoEnum;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.Servidor.ConnectHandler;
import br.gov.sp.educacao.sed.mobile.Servidor.ParametroJson;
import br.gov.sp.educacao.sed.mobile.Servidor.RetornoJson;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 29/07/2015.
 */
public class EnviarFrequencia {
    private Context context;

    public EnviarFrequencia(Context context) {
        this.context = context;
    }

    public void executar() {
        try {
            ParametroJson parametroJson = new ParametroJson();
            parametroJson.setContext(context);
            parametroJson.setJsonObject(montarJSOEnvio().toString());
            parametroJson.setTipoServicoEnum(TipoServicoEnum.FREQUENCIA);

            if (!parametroJson.getJsonObject().equals("[]")) {
                ConnectHandler connectHandler = new ConnectHandler();
                connectHandler.execute(parametroJson);

                RetornoJson retorno = connectHandler.get();

                if (RetornoJson.RETORNO_COM_SUCESSO == retorno.getStatusRetorno()
                        || RetornoJson.RETORNO_COM_SUCESSO_SEM_RESPOSTA == retorno.getStatusRetorno()) {


                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                    AtualizarBancoOff.updateFrequencia(context, dateFormat.format(new Date()).toString());

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

    private JSONArray montarJSOEnvio() throws JSONException {
        StringBuffer query = new StringBuffer();
        query.append(" SELECT   al.codigoMatricula, au.codigoAula, fa.tipoFalta, di.dataAula, tf.codigoTurma  ");
        query.append(" FROM     FALTASALUNOS as fa, ");
        query.append("          ALUNOS as al, ");
        query.append("          DIASLETIVOS as di, ");
        query.append("          AULAS as au, ");
        query.append("          DISCIPLINA as dc, ");
        query.append("          TURMASFREQUENCIA as tf ");
        query.append(" Where    al.id = fa.aluno_id ");
        query.append(" And      di.id = fa.diasLetivos_id ");
        query.append(" And      au.id = fa.aula_id ");
        query.append(" And      dc.id = au.disciplina_id ");
        query.append(" And      tf.id = dc.turmasFrequencia_id ");
        query.append(" And      fa.dataServidor is null ");

        Cursor cursor = Queries.getSelect(context, query);

        final JSONArray jsonarrayenvio = new JSONArray();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                JSONObject jsonitem = new JSONObject();

                jsonitem.put("CodigoMatriculaAluno", cursor.getInt(cursor.getColumnIndex("codigoMatricula")));
                jsonitem.put("CodigoDaAula", cursor.getInt(cursor.getColumnIndex("codigoAula")));
                jsonitem.put("CodigoTipoFrequencia", cursor.getString(cursor.getColumnIndex("tipoFalta")));
                jsonitem.put("DataDaAula", cursor.getString(cursor.getColumnIndex("dataAula")));
                jsonitem.put("CodigoTurma", cursor.getInt(cursor.getColumnIndex("codigoTurma")));
                jsonitem.put("CodigoMotivoFrequencia", 0);
                jsonitem.put("Justificativa", "");

                jsonitem.put("Presenca", ("C".equals(cursor.getString(cursor.getColumnIndex("tipoFalta"))) ? true : false));

                jsonarrayenvio.put(jsonitem);
            }
        }

        return jsonarrayenvio;
    }
}
