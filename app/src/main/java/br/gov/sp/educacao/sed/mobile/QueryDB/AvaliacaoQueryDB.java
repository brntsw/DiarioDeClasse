package br.gov.sp.educacao.sed.mobile.QueryDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Modelo.Aula;
import br.gov.sp.educacao.sed.mobile.Modelo.Avaliacao;
import br.gov.sp.educacao.sed.mobile.Modelo.Disciplina;
import br.gov.sp.educacao.sed.mobile.Modelo.NotasAluno;
import br.gov.sp.educacao.sed.mobile.Modelo.Turma;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 15/03/2016.
 */
public class AvaliacaoQueryDB extends Queries {
    public static void editarAvaliacao(Context context, Avaliacao avaliacao) {
        ContentValues insertValues = new ContentValues();
        insertValues.put("nome", avaliacao.getNome());
        insertValues.put("data", avaliacao.getData());
        insertValues.put("codigoTipoAtividade", avaliacao.getTipoAtividade());
        insertValues.put("valeNota", avaliacao.isValeNota() ? 1 : 0);

        getBanco(context).get().update("AVALIACOES", insertValues, "id=" + avaliacao.getId(), null);

    }

    public static int insertAvaliacao(Context context, Avaliacao avaliacao, Turma turma) {
        ContentValues insertValues = new ContentValues();
        insertValues.put("codigoAvaliacao", avaliacao.getCodigo());
        insertValues.put("codigoTurma", turma.getCodigoTurma());
        insertValues.put("codigoDisciplina", avaliacao.getCodDisciplina());
        insertValues.put("codigoTipoAtividade", avaliacao.getTipoAtividade());
        insertValues.put("nome", avaliacao.getNome());
        insertValues.put("data", avaliacao.getData());
        insertValues.put("dataCadastro", avaliacao.getDataCadastro());
        insertValues.put("bimestre", avaliacao.getBimestre());
        insertValues.put("valeNota", avaliacao.isValeNota() ? 1 : 0);
        insertValues.put("mobileId", avaliacao.getMobileId());
        insertValues.put("turma_id", avaliacao.getTurmaId());
        insertValues.put("disciplina_id", avaliacao.getDisciplinaId());

        int rowsUpdate = getBanco(context).get().update("AVALIACOES", insertValues, "codigoAvaliacao = " + avaliacao.getCodigo() + " AND codigoTurma = " + avaliacao.getCodTurma() + " AND codigoDisciplina = " + avaliacao.getCodDisciplina(), null);
        if (rowsUpdate == 0) {
            return (int) getBanco(context).get().insert("AVALIACOES", null, insertValues);
        } else {
            return rowsUpdate;
        }

    }

    public static ArrayList<Avaliacao> getListaAvaliacao(Context context, Turma turma, Disciplina disciplina, String data) {
        ArrayList<Avaliacao> arrayListAvaliacao = new ArrayList<>();

        final Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM AVALIACOES WHERE codigoTurma = " + turma.getCodigoTurma() + " AND codigoDisciplina = " + disciplina.getCodigoDisciplina() + " AND data = '" + data + "'", null);

        while (cursor.moveToNext()) {
            final Avaliacao avaliacao = new Avaliacao();
            avaliacao.setId(cursor.getInt(cursor.getColumnIndex("id")));
            avaliacao.setCodigo(cursor.getInt(cursor.getColumnIndex("codigoAvaliacao")));
            avaliacao.setCodTurma(cursor.getInt(cursor.getColumnIndex("codigoTurma")));
            avaliacao.setCodDisciplina(cursor.getInt(cursor.getColumnIndex("codigoDisciplina")));
            avaliacao.setTipoAtividade(cursor.getInt(cursor.getColumnIndex("codigoTipoAtividade")));
            avaliacao.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            avaliacao.setData(cursor.getString(cursor.getColumnIndex("data")));
            avaliacao.setBimestre(cursor.getInt(cursor.getColumnIndex("bimestre")));
            avaliacao.setValeNota(cursor.getInt(cursor.getColumnIndex("valeNota")) == 1);
            avaliacao.setMobileId(cursor.getInt(cursor.getColumnIndex("mobileId")));
            avaliacao.setTurmaId(cursor.getInt(cursor.getColumnIndex("turma_id")));
            avaliacao.setDisciplinaId(cursor.getInt(cursor.getColumnIndex("disciplina_id")));

            arrayListAvaliacao.add(avaliacao);
        }
        cursor.close();

        return arrayListAvaliacao;
    }

    public static int getUltimoCodigoAvaliacao(Context context, Avaliacao avaliacao) {
        final Cursor cursor = getBanco(context).get().rawQuery("SELECT codigoAvaliacao FROM AVALIACOES WHERE codigoTurma = " + avaliacao.getCodTurma() + " AND codigoDisciplina = " + avaliacao.getCodDisciplina() + " ORDER BY codigoAvaliacao DESC LIMIT 1", null);

        if (cursor.moveToNext()) {
            int ultimoCodigo = cursor.getInt(cursor.getColumnIndex("codigoAvaliacao"));
            cursor.close();
            return ultimoCodigo + 1;
        } else {
            cursor.close();
            return 0;
        }
    }

    public static Boolean setNotasAluno(Context context, NotasAluno notasAluno) {

        ContentValues insertValues = new ContentValues();
        insertValues.put("codigoMatricula", notasAluno.getCodigoMatricula());
        insertValues.put("nota", notasAluno.getNota());
        insertValues.put("dataCadastro", notasAluno.getDataCadastro());
        insertValues.put("dataServidor", notasAluno.getDataServidor());
        insertValues.put("aluno_id", notasAluno.getAluno_id());
        insertValues.put("usuario_id", notasAluno.getUsuario_id());
        insertValues.put("avaliacao_id", notasAluno.getAvaliacao_id());

        if (getBanco(context).get().update("NOTASALUNO", insertValues, "aluno_id=" + notasAluno.getAluno_id() + " AND avaliacao_id=" + notasAluno.getAvaliacao_id(), null) == 0) {
            getBanco(context).get().insert("NOTASALUNO", null, insertValues);
        }
        return true;
    }

    public static Boolean getAvaliacaoCompleta(Context context, ArrayList<Aluno> alunos, Avaliacao avaliacao) {
        for (int i = 0; i < alunos.size(); i++) {
            final Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM NOTASALUNO WHERE avaliacao_id=" + avaliacao.getId() + " AND aluno_id=" + alunos.get(i).getId(), null);
            cursor.moveToNext();

            if (cursor.getCount() == 0) {
                return false;
            }
            cursor.close();
        }

        return true;

    }

    public static String getNotasAluno(Context context, NotasAluno notasAluno) {
        final Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM NOTASALUNO WHERE avaliacao_id=" + notasAluno.getAvaliacao_id() + " AND aluno_id=" + notasAluno.getAluno_id(), null);

        cursor.moveToNext();

        String retorno;

        if (cursor.getCount() == 0) {
            retorno = "12";
        } else {
            retorno = cursor.getString(cursor.getColumnIndex("nota"));
        }

        cursor.close();

        return retorno;

    }

    public static ArrayList<Aula> getAula(Context context, Disciplina disciplina) {
        ArrayList<Aula> listaAula = new ArrayList<>();

        final Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM AULAS WHERE disciplina_id = " + disciplina.getId(), null);

        while (cursor.moveToNext()) {
            final Aula aula = new Aula();
            aula.setInicio(cursor.getString(cursor.getColumnIndex("inicioHora")));
            aula.setFim(cursor.getString(cursor.getColumnIndex("fimHora")));
            aula.setDiaSemana(cursor.getInt(cursor.getColumnIndex("diaSemana")));
            aula.setCodigoAula(cursor.getInt(cursor.getColumnIndex("codigoAula")));

            listaAula.add(aula);
        }

        cursor.close();

        return listaAula;
    }

    public static List<Avaliacao> getAvaliacoesByAvaliacao(Context context, Avaliacao avaliacao){
        List<Avaliacao> avaliacoes = new ArrayList<>();

        final Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM AVALIACOES WHERE bimestre = " + avaliacao.getBimestre() +
                " AND codigoTipoAtividade = " + avaliacao.getTipoAtividade() + " AND turma_id = " + avaliacao.getTurmaId() +
                " AND disciplina_id = " + avaliacao.getDisciplinaId(), null);

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()) {
                Avaliacao avaliacaoObj = new Avaliacao();
                avaliacaoObj.setId(cursor.getInt(cursor.getColumnIndex("id")));
                avaliacaoObj.setCodigo(cursor.getInt(cursor.getColumnIndex("codigoAvaliacao")));
                avaliacaoObj.setCodTurma(cursor.getInt(cursor.getColumnIndex("codigoTurma")));
                avaliacaoObj.setCodDisciplina(cursor.getInt(cursor.getColumnIndex("codigoDisciplina")));
                avaliacaoObj.setTipoAtividade(cursor.getInt(cursor.getColumnIndex("codigoTipoAtividade")));
                avaliacaoObj.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                avaliacaoObj.setData(cursor.getString(cursor.getColumnIndex("data")));
                avaliacaoObj.setBimestre(cursor.getInt(cursor.getColumnIndex("bimestre")));
                avaliacaoObj.setValeNota(cursor.getInt(cursor.getColumnIndex("valeNota")) == 1);
                avaliacaoObj.setMobileId(cursor.getInt(cursor.getColumnIndex("mobileId")));
                avaliacaoObj.setTurmaId(cursor.getInt(cursor.getColumnIndex("turma_id")));
                avaliacaoObj.setDisciplinaId(cursor.getInt(cursor.getColumnIndex("disciplina_id")));

                avaliacoes.add(avaliacaoObj);
            }

            cursor.close();
        }

        return avaliacoes;
    }

    public static int getTotalNotasAluno(Context context, Avaliacao avaliacao){
        final Cursor cursor = getBanco(context).get().rawQuery("SELECT id FROM NOTASALUNO WHERE avaliacao_id = " + avaliacao.getId(), null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }
}
