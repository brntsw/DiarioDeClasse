package br.gov.sp.educacao.sed.mobile.QueryDB;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Modelo.Aula;
import br.gov.sp.educacao.sed.mobile.Modelo.Disciplina;
import br.gov.sp.educacao.sed.mobile.Modelo.Turma;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmaGrupo;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmasFrequencia;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 15/03/2016.
 */
public class TurmaQueryDB extends Queries {

    public static ArrayList<TurmaGrupo> getTurmas(Context context, int ano) {
        ArrayList<TurmaGrupo> listaTurmaGrupo = new ArrayList<TurmaGrupo>();
        TurmaGrupo turmaGrupo = null;

        StringBuffer query = new StringBuffer();
        query.append(" SELECT   T.id AS idTurma, T.anoLetivo AS anoLetivoTurma, T.codigoTurma AS codigoTurma, T.nomeTurma AS nomeTurma, ");
        query.append("          T.codigoDiretoria AS codigoDiretoria, T.nomeDiretoria AS nomeDiretoria, T.codigoEscola AS codigoEscola, T.nomeEscola AS nomeEscola, ");
        query.append("          T.codigoTipoEnsino AS codigoTipoEnsino, T.nomeTipoEnsino AS nomeTipoEnsino,  ");
        query.append("          TF.id AS idTurmasFrequencia, TF.aulasBimestre AS aulasBimestre, TF.aulasAno AS aulasAno,  ");
        query.append("          D.id AS idDisciplina, D.codigoDisciplina AS codigoDisciplina, D.nomeDisciplina AS nomeDisciplina  ");
        query.append(" FROM     TURMAS AS T, ");
        query.append("          TURMASFREQUENCIA AS TF, ");
        query.append("          DISCIPLINA AS D ");
        query.append(" WHERE    TF.turma_id = T.id ");
        query.append(" AND      D.turmasFrequencia_id = TF.id ");

        final Cursor cursor = getBanco(context).get().rawQuery(query.toString(), null);

        while (cursor.moveToNext()) {

            turmaGrupo = new TurmaGrupo();

            //TURMA
            Turma turma = new Turma();
            turma.setId(cursor.getInt(cursor.getColumnIndex("idTurma")));
            turma.setAno(cursor.getInt(cursor.getColumnIndex("anoLetivoTurma")));
            turma.setCodigoTurma(cursor.getInt(cursor.getColumnIndex("codigoTurma")));
            turma.setNomeTurma(cursor.getString(cursor.getColumnIndex("nomeTurma")));
            turma.setCodigoDiretoria(cursor.getInt(cursor.getColumnIndex("codigoDiretoria")));
            turma.setCodigoEscola(cursor.getInt(cursor.getColumnIndex("codigoEscola")));
            turma.setNomeEscola(cursor.getString(cursor.getColumnIndex("nomeEscola")));
            turma.setNomeDiretoria(cursor.getString(cursor.getColumnIndex("nomeDiretoria")));
            turma.setCodigoTipoEnsino(cursor.getInt(cursor.getColumnIndex("codigoTipoEnsino")));
            turma.setNomeTipoEnsino(cursor.getString(cursor.getColumnIndex("nomeTipoEnsino")));

            //TURMASFREQUENCIAS
            TurmasFrequencia turmasFrequencia = new TurmasFrequencia();
            turmasFrequencia.setId(cursor.getInt(cursor.getColumnIndex("idTurmasFrequencia")));
            turmasFrequencia.setTurma_id(cursor.getInt(cursor.getColumnIndex("idTurma")));
            turmasFrequencia.setCodigoTurma(cursor.getInt(cursor.getColumnIndex("codigoTurma")));
            turmasFrequencia.setCodigoDiretoria(cursor.getInt(cursor.getColumnIndex("codigoDiretoria")));
            turmasFrequencia.setCodigoEscola(cursor.getInt(cursor.getColumnIndex("codigoEscola")));
            turmasFrequencia.setCodigoTipoEnsino(cursor.getInt(cursor.getColumnIndex("codigoTipoEnsino")));
            turmasFrequencia.setAulasBimestre(cursor.getInt(cursor.getColumnIndex("aulasBimestre")));
            turmasFrequencia.setAulasAno(cursor.getInt(cursor.getColumnIndex("aulasAno")));

            //DISCIPLINA
            Disciplina disciplina = new Disciplina();
            disciplina.setId(cursor.getInt(cursor.getColumnIndex("idDisciplina")));
            disciplina.setCodigoDisciplina(cursor.getInt(cursor.getColumnIndex("codigoDisciplina")));
            disciplina.setNomeDisciplina(cursor.getString(cursor.getColumnIndex("nomeDisciplina")));


            //MONTA OBJ TURMAGRUPO COM OBJETOS ASSOCIADOS
            turmaGrupo.setTurma(turma);
            turmaGrupo.setTurmasFrequencia(turmasFrequencia);
            turmaGrupo.setDisciplina(disciplina);

            listaTurmaGrupo.add(turmaGrupo);


            //BUSCA DADOS DE ALUNOS POR TURMA
            final Cursor cursorAluno = getBanco(context).get().rawQuery("SELECT * FROM ALUNOS WHERE turma_id = " + turma.getId(), null);

            while (cursorAluno.moveToNext()) {

                Aluno aluno = new Aluno();

                aluno.setId(cursorAluno.getInt(cursorAluno.getColumnIndex("id")));
                aluno.setCodigoAluno(cursorAluno.getInt(cursorAluno.getColumnIndex("codigoAluno")));
                aluno.setCodigoMatricula(cursorAluno.getInt(cursorAluno.getColumnIndex("codigoMatricula")));
                aluno.setAlunoAtivo(cursorAluno.getInt(cursorAluno.getColumnIndex("alunoAtivo")));
                aluno.setNumeroChamada(cursorAluno.getInt(cursorAluno.getColumnIndex("numeroChamada")));
                aluno.setNomeAluno(cursorAluno.getString(cursorAluno.getColumnIndex("nomeAluno")));
                aluno.setNumeroRa(cursorAluno.getString(cursorAluno.getColumnIndex("numeroRa")));
                aluno.setDigitoRa(cursorAluno.getString(cursorAluno.getColumnIndex("digitoRa")));
                aluno.setUfRa(cursorAluno.getString(cursorAluno.getColumnIndex("ufRa")));
                aluno.setNomePai(cursorAluno.getString(cursorAluno.getColumnIndex("pai")));
                aluno.setNomeMae(cursorAluno.getString(cursorAluno.getColumnIndex("mae")));
                aluno.setCodigoTurma(cursorAluno.getInt(cursorAluno.getColumnIndex("turma_id")));

                turma.addAluno(aluno);
            }
            cursorAluno.close();

        }
        cursor.close();

        return listaTurmaGrupo;
    }

}
