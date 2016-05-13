package br.gov.sp.educacao.sed.mobile.Action;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.AlunosTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.AulasTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.AvaliacoesTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.BimestreTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.ConteudoTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.DiasLetivosTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.DisciplinaTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.GruposTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.HabilidadesTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.NotasAlunoTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.TotalFaltasAlunosTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.TurmasFrequenciaTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.TurmasTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 11/08/2015.
 */
public class AtualizarBancoOff {

    public static void updateFrequencia(Context context, String Data) {
        ContentValues values = new ContentValues();
        values.put("dataServidor", Data);

        int rowsUpdate = Queries.getBanco(context).get().update("FALTASALUNOS", values, "dataServidor is null", null);

    }

    public static void updateRegistro(Context context, String Data) {
        ContentValues values = new ContentValues();
        values.put("dataServidor", Data);

        int rowsUpdate = Queries.getBanco(context).get().update("HABILIDADESABORDADAS", values, "dataServidor is null", null);

    }

    public static void updateAvaliacao(Context context, String Data) {
        ContentValues values = new ContentValues();
        values.put("dataServidor", Data);

        int rowsUpdate = Queries.getBanco(context).get().update("NOTASALUNO", values, "dataServidor is null", null);

    }

    public static void updateUsuario(Context context, JSONObject retorno) {
        try {
            UsuarioTO usuarioTO = null;
            usuarioTO = new UsuarioTO(retorno);
            Integer usuario_id = Queries.setDadosDataBase(context, UsuarioTO.nomeTabela, usuarioTO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void updateTurmas(Context context, JSONObject retorno) throws JSONException, SQLException, IOException {

        int ano = retorno.getInt("Ano");
        Log.d("Import Inicio ", String.valueOf(System.currentTimeMillis()));

        convertJSONArrayForTurmas(context, ano, retorno.getJSONArray("TurmasTurmas"));
        Log.d("Import TurmasTurmas", String.valueOf(System.currentTimeMillis()));

        convertJSONArrayForTurmasFrequencia(context, ano, retorno.getJSONArray("TurmasFrequencia"));
        Log.d("Import TurmasFrequencia", String.valueOf(System.currentTimeMillis()));

        convertJSONArrayForTurmasCurriculos(context, retorno.getJSONArray("Curriculos"));
        Log.d("Import Curriculos", String.valueOf(System.currentTimeMillis()));

        Log.d("Import Fim ", String.valueOf(System.currentTimeMillis()));
    }

    public static void limparTabelas(Context context) {
        Queries.deleteTable(context, "HABILIDADESABORDADAS", null, null);
        Queries.deleteTable(context, "HABILIDADES", null, null);
        Queries.deleteTable(context, "CONTEUDO", null, null);
        Queries.deleteTable(context, "GRUPOS", null, null);
        Queries.deleteTable(context, "FALTASALUNOS", null, null);
        Queries.deleteTable(context, "DIASLETIVOS", null, null);
        Queries.deleteTable(context, "BIMESTRE", null, null);
        Queries.deleteTable(context, "NOTASALUNO", null, null);
        Queries.deleteTable(context, "AVALIACOES", null, null);
        Queries.deleteTable(context, "TOTALFALTASALUNOS", null, null);
        Queries.deleteTable(context, "AULAS", null, null);
        Queries.deleteTable(context, "DISCIPLINA", null, null);
        Queries.deleteTable(context, "TURMASFREQUENCIA", null, null);
        Queries.deleteTable(context, "ALUNOS", null, null);
        Queries.deleteTable(context, "TURMAS", null, null);
        Queries.deleteTable(context, "USUARIO", null, null);
    }

    private static void convertJSONArrayForTurmasAvaliacoes(Context context, JSONArray jsonArrayAvaliacoes) throws JSONException {

        for (int i = 0; i < jsonArrayAvaliacoes.length(); i++) {
            JSONObject jsonObjectAvaliacoes = jsonArrayAvaliacoes.getJSONObject(i);

            StringBuffer queryTurma = new StringBuffer();
            queryTurma.append(" SELECT id FROM TURMAS  as t ");
            queryTurma.append(" WHERE t.codigoTurma      = " + jsonObjectAvaliacoes.getInt("CodigoTurma"));

            Cursor cursorTurma = Queries.getSelect(context, queryTurma);
            Integer turma_id = -1;
            if (cursorTurma != null) {
                turma_id = cursorTurma.getInt(cursorTurma.getColumnIndex("id"));
                cursorTurma.close();
            }

            StringBuffer queryDisciplina = new StringBuffer();
            queryDisciplina.append(" SELECT id FROM DISCIPLINA  as t ");
            queryDisciplina.append(" WHERE t.codigoDisciplina      = ").append(jsonObjectAvaliacoes.getInt("CodigoDisciplina"));

            Cursor cursorDisciplina = Queries.getSelect(context, queryDisciplina);
            Integer disciplina_id = -1;
            if (cursorDisciplina != null)
                disciplina_id = cursorDisciplina.getInt(cursorDisciplina.getColumnIndex("id"));


            AvaliacoesTO avaliacoesTO = new AvaliacoesTO(jsonObjectAvaliacoes, turma_id, disciplina_id);
            Integer avaliacao_id = Queries.setDadosDataBase(context, AvaliacoesTO.nomeTabela, avaliacoesTO);

            if(jsonObjectAvaliacoes.has("Notas")) {
                JSONArray jsonArrayNotas = jsonObjectAvaliacoes.getJSONArray("Notas");
                for (int l = 0; l < jsonArrayNotas.length(); l++) {
                    JSONObject jsonObjNota = jsonArrayNotas.getJSONObject(l);


                    StringBuffer queryAlunos = new StringBuffer();
                    queryAlunos.append(" SELECT id FROM ALUNOS as t ");
                    queryAlunos.append(" WHERE t.codigoMatricula      = ").append(jsonObjNota.getInt("CodigoMatriculaAluno"));

                    Cursor cursorAlunos = Queries.getSelect(context, queryAlunos);
                    Integer aluno_id = -1;
                    if (cursorAlunos != null && cursorDisciplina != null) {
                        aluno_id = cursorAlunos.getInt(cursorDisciplina.getColumnIndex("id"));
                        cursorDisciplina.close();
                    }


                    NotasAlunoTO notasAlunoTO = new NotasAlunoTO(jsonObjNota, aluno_id, null, avaliacao_id);
                    Integer notaAluno_id = Queries.setDadosDataBase(context, NotasAlunoTO.nomeTabela, notasAlunoTO);

                }
            }
        }
    }

    private static void convertJSONArrayForTurmasFrequencia(Context context, Integer anoLetivo, JSONArray jsonArrayFrequencia) throws JSONException {
        for (int i = 0; i < jsonArrayFrequencia.length(); i++) {
            JSONObject jsonObjectFrequencia = jsonArrayFrequencia.getJSONObject(i);


            StringBuffer query = new StringBuffer();
            query.append(" SELECT id FROM TURMAS ");
            query.append(" WHERE codigoTurma = ").append(jsonObjectFrequencia.getInt("CodigoTurma"));

            Cursor cursor = Queries.getSelect(context, query);
            Integer turma_id = -1;
            if (cursor != null) {
                turma_id = cursor.getInt(cursor.getColumnIndex("id"));
                cursor.close();
            }

            TurmasFrequenciaTO turmasFrequenciaTO = new TurmasFrequenciaTO(jsonObjectFrequencia, turma_id);
            Integer turmasFrequencia_id = Queries.setDadosDataBase(context, TurmasFrequenciaTO.nomeTabela, turmasFrequenciaTO);

            convertJSONArrayForTurmasDisciplina(context, jsonObjectFrequencia.getJSONObject("Disciplina"), turmasFrequencia_id);
            Integer bimestre_id = convertJSONArrayForTurmasBimestres(context, jsonObjectFrequencia.getJSONObject("BimestreAtual"), turmasFrequencia_id);
            convertJSONArrayForTurmasCalendario(context, jsonObjectFrequencia.getJSONArray("CalendarioBimestreAtual"), bimestre_id, anoLetivo);
            convertJSONArrayForTurmasAvaliacoes(context, jsonObjectFrequencia.getJSONArray("Avaliacoes"));

        }
    }

    private static void convertJSONArrayForTurmasCurriculos(Context context, JSONArray jsonArrayCurriculos) throws JSONException {

        for (int i = 0; i < jsonArrayCurriculos.length(); i++) {
            JSONObject jsonObjCurriculos = jsonArrayCurriculos.getJSONObject(i);

            JSONObject jsonObjGrupo = jsonObjCurriculos.getJSONObject("Grupo");

            StringBuffer query = new StringBuffer();
            query.append(" SELECT id FROM DISCIPLINA  as t ");
            query.append(" WHERE t.codigoDisciplina      = ").append(jsonObjGrupo.getInt("CodigoDisciplina"));

            Cursor cursor = Queries.getSelect(context, query);
            Integer disciplina_id = -1;
            if (cursor != null) {
                disciplina_id = cursor.getInt(cursor.getColumnIndex("id"));
                cursor.close();
            }


            GruposTO gruposTO = new GruposTO(jsonObjGrupo, disciplina_id);
            Integer grupo_id = Queries.setDadosDataBase(context, GruposTO.nomeTabela, gruposTO);

            JSONArray jsonArrayCurriculo = jsonObjGrupo.getJSONArray("Curriculos");
            for (int k = 0; k < jsonArrayCurriculo.length(); k++) {
                JSONObject jsonObject = jsonArrayCurriculo.getJSONObject(k);

                JSONArray jsonConteudos = jsonObject.getJSONArray("Conteudos");
                for (int x = 0; x < jsonConteudos.length(); x++) {
                    JSONObject jsonObjectConteudos = jsonConteudos.getJSONObject(x);

                    ConteudoTO conteudoTO = new ConteudoTO(jsonObjectConteudos, grupo_id);
                    Integer conteudo_id = Queries.setDadosDataBase(context, ConteudoTO.nomeTabela, conteudoTO);

                    JSONArray jsonHabilidades = jsonObjectConteudos.getJSONArray("Habilidades");
                    for (int y = 0; y < jsonHabilidades.length(); y++) {
                        JSONObject jsonObjectHabilidades = jsonHabilidades.getJSONObject(y);

                        HabilidadesTO habilidadesTO = new HabilidadesTO(jsonObjectHabilidades, conteudo_id);
                        Integer habilidade_id = Queries.setDadosDataBase(context, HabilidadesTO.nomeTabela, habilidadesTO);
                    }
                }
            }
        }
    }

    private static void convertJSONArrayForTurmasDisciplina(Context context, JSONObject jsonObjectDisciplina, Integer turmasFrequencia_id) throws JSONException {
        DisciplinaTO disciplinaTO = new DisciplinaTO(jsonObjectDisciplina, turmasFrequencia_id);
        Integer disciplina_id = Queries.setDadosDataBase(context, DisciplinaTO.nomeTabela, disciplinaTO);

        JSONArray jsonarrayAula = jsonObjectDisciplina.getJSONArray("Aulas");
        for (int k = 0; k < jsonarrayAula.length(); k++) {
            JSONObject jsonObjectAulas = jsonarrayAula.getJSONObject(k);

            AulasTO aulasTO = new AulasTO(jsonObjectAulas, disciplina_id);
            Integer aulas_id = Queries.setDadosDataBase(context, AulasTO.nomeTabela, aulasTO);
        }


        JSONArray jsonarrayFaltasAlunos = jsonObjectDisciplina.getJSONArray("FaltasAlunos");
        for (int k = 0; k < jsonarrayFaltasAlunos.length(); k++) {
            JSONObject jsonObjectFaltasAlunos = jsonarrayFaltasAlunos.getJSONObject(k);

            StringBuffer query = new StringBuffer();
            query.append(" SELECT id FROM ALUNOS  as t ");
            query.append(" WHERE t.codigoMatricula      = ").append(jsonObjectFaltasAlunos.getInt("CodigoMatricula"));

            Cursor cursor = Queries.getSelect(context, query);
            Integer aluno_id = -1;
            if (cursor != null) {
                aluno_id = cursor.getInt(cursor.getColumnIndex("id"));
                cursor.close();
            }

            TotalFaltasAlunosTO totalFaltasAlunosTO = new TotalFaltasAlunosTO(jsonObjectFaltasAlunos, disciplina_id, aluno_id);
            Integer totalFaltasAlunos_id = Queries.setDadosDataBase(context, TotalFaltasAlunosTO.nomeTabela, totalFaltasAlunosTO);
        }
    }

    private static Integer convertJSONArrayForTurmasBimestres(Context context, JSONObject jsonarrayBimestre, Integer turmasFrequencia_id) throws JSONException {
        BimestreTO bimestreTO = new BimestreTO(jsonarrayBimestre, turmasFrequencia_id);

        return Queries.setDadosDataBase(context, BimestreTO.nomeTabela, bimestreTO);
    }

    private static void convertJSONArrayForTurmasCalendario(Context context, JSONArray jsonarrayCalendario, Integer bimestre_id, Integer anoLetivo) throws JSONException {
        for (int i = 0; i < jsonarrayCalendario.length(); i++) {
            JSONObject jsonObjectCalendario = jsonarrayCalendario.getJSONObject(i);

            JSONArray jsonArrayDias = jsonObjectCalendario.getJSONArray("DiasLetivos");
            for (int l = 0; l < jsonArrayDias.length(); l++) {
                DiasLetivosTO diasLetivosTO = new DiasLetivosTO(bimestre_id, (Integer) jsonArrayDias.get(l), jsonObjectCalendario.getInt("Mes"), anoLetivo);
                Integer diasLetivo_id = Queries.setDadosDataBase(context, DiasLetivosTO.nomeTabela, diasLetivosTO);
            }
        }
    }

    private static void convertJSONArrayForTurmas(Context context, int ano, JSONArray jsonArrayTurmas) throws JSONException {
        for (int i = 0; i < jsonArrayTurmas.length(); i++) {
            JSONObject jsonTurma = jsonArrayTurmas.getJSONObject(i);

            TurmasTO turmasTO = new TurmasTO(jsonTurma, ano);
            Integer turma_id = Queries.setDadosDataBase(context, TurmasTO.nomeTabela, turmasTO);

            JSONArray jsonArrayAlunos = jsonTurma.getJSONArray("Alunos");
            for (int j = 0; j < jsonArrayAlunos.length(); j++) {
                JSONObject jsonAluno = jsonArrayAlunos.getJSONObject(j);

                AlunosTO alunosTO = new AlunosTO(jsonAluno, turma_id);
                Integer aluno_id = Queries.setDadosDataBase(context, AlunosTO.nomeTabela, alunosTO);
            }
        }
    }
}
