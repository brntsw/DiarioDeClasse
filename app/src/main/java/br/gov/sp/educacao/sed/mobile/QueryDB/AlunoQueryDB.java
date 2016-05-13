package br.gov.sp.educacao.sed.mobile.QueryDB;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 15/03/2016.
 */
public class AlunoQueryDB extends Queries {

    public static ArrayList<Integer> getIdAlunos(Context context, int usuarioId, int codigoTurma, int codigoDisciplina, int ano){

        ArrayList<Integer> arrayListId = new ArrayList<>();

        Cursor cursor = getBanco(context).get().rawQuery("SELECT id FROM ALUNOS as a WHERE a.UsuarioId = " + usuarioId + " AND CodigoTurma=" + codigoTurma + " AND CodigoDisciplina=" + codigoDisciplina + " AND ano=" + ano, null);

        while (cursor.moveToNext()){
            arrayListId.add(cursor.getInt(cursor.getColumnIndex("id")));
        }
        cursor.close();

        return arrayListId;
    }

    public static List<Aluno> getAlunosAtivos(Context context, List<Aluno> alunos){
        List<Aluno> alunosAtivos = new ArrayList<>();

        for(Aluno aluno : alunos){
            Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM ALUNOS WHERE id = " + aluno.getId(), null);

            if(cursor.getCount() > 0){
                alunosAtivos.add(aluno);
            }

            cursor.close();
        }

        return alunosAtivos;
    }
}
