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
 * Created by techresult on 25/09/2015.
 */
public class EnviarRegistroAula {
    private Context context;

    public EnviarRegistroAula(Context context) {
        this.context = context;
    }

    public void executar() {
        try {
            ParametroJson parametroJson = new ParametroJson();
            parametroJson.setContext(context);
            parametroJson.setJsonObject(montarJSOEnvio().toString());
            parametroJson.setTipoServicoEnum(TipoServicoEnum.REGISTRO_AULAS);

            if (!parametroJson.getJsonObject().equals("{}")) {
                ConnectHandler connectHandler = new ConnectHandler();
                connectHandler.execute(parametroJson);


                RetornoJson retorno = connectHandler.get();


                if (RetornoJson.RETORNO_COM_SUCESSO == retorno.getStatusRetorno()
                        || RetornoJson.RETORNO_COM_SUCESSO_SEM_RESPOSTA == retorno.getStatusRetorno()) {


                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                    AtualizarBancoOff.updateRegistro(context, dateFormat.format(new Date()).toString());
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
        JSONObject jsonEnvio = new JSONObject();

        StringBuffer query = new StringBuffer();
        query.append(" SELECT   tf.codigoTurma, g.codigoGrupo, ha.descricao, di.dataAula, c.codigoConteudo, h.codigoHabilidade  ");
        query.append(" FROM     HABILIDADESABORDADAS as ha, ");
        query.append("          DIASLETIVOS as di, ");
        query.append("          HABILIDADES as h, ");
        query.append("          CONTEUDO as c, ");
        query.append("          GRUPOS as g, ");
        query.append("          DISCIPLINA as dc, ");
        query.append("          TURMASFREQUENCIA as tf ");
        query.append(" Where    di.id = ha.diasLetivos_id ");
        query.append(" AND      h.id = ha.habilidade_id ");
        query.append(" AND      c.id = h.conteudo_id ");
        query.append(" AND      g.id = c.grupo_id ");
        query.append(" AND      dc.id = g.disciplina_id ");
        query.append(" AND      tf.id = dc.turmasFrequencia_id ");
        query.append(" AND      ha.dataServidor is null ");

        Cursor cursor = Queries.getSelect(context, query);

        if (cursor != null) {
            JSONArray jsonArrayConteudos = new JSONArray();
            JSONArray jsonArrayHabilidades = new JSONArray();

            JSONObject jsonObjConteudo;

            List<Integer> listGrupo = new ArrayList<>();
            List<Integer> listConteudo = new ArrayList<>();

            while (cursor.moveToNext()) {

                if (!listGrupo.contains(cursor.getInt(cursor.getColumnIndex("codigoTurma")))) {
                    listGrupo.add(cursor.getInt(cursor.getColumnIndex("codigoTurma")));

                    jsonEnvio.put("CodigoTurma", cursor.getInt(cursor.getColumnIndex("codigoTurma")));
                    jsonEnvio.put("CodigoGrupoCurriculo", cursor.getInt(cursor.getColumnIndex("codigoGrupo")));
                    jsonEnvio.put("Observacoes", cursor.getString(cursor.getColumnIndex("descricao")));
                    jsonEnvio.put("Ocorrencias", "");
                    jsonEnvio.put("DataCriacao", cursor.getString(cursor.getColumnIndex("dataAula")));

                    jsonArrayConteudos = new JSONArray();

                    jsonEnvio.put("Conteudos", jsonArrayConteudos);

                    listConteudo = new ArrayList<>();
                }

                if (!listConteudo.contains(cursor.getInt(cursor.getColumnIndex("codigoConteudo")))) {
                    listConteudo.add(cursor.getInt(cursor.getColumnIndex("codigoConteudo")));

                    jsonObjConteudo = new JSONObject();

                    jsonObjConteudo.put("CodigoConteudo", cursor.getInt(cursor.getColumnIndex("codigoConteudo")));
                    jsonArrayConteudos.put(jsonObjConteudo);

                    jsonArrayHabilidades = new JSONArray();
                    jsonObjConteudo.put("Habilidades", jsonArrayHabilidades);
                }

                jsonArrayHabilidades.put(cursor.getInt(cursor.getColumnIndex("codigoHabilidade")));
            }
        }

        return jsonEnvio;

    }
}