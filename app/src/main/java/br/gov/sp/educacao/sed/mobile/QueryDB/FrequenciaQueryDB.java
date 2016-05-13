package br.gov.sp.educacao.sed.mobile.QueryDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Modelo.Aula;
import br.gov.sp.educacao.sed.mobile.Modelo.DiasLetivos;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.Util.DateUtils;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

public class FrequenciaQueryDB extends Queries {

    public static Boolean setComparecimento(Context context, UsuarioTO usuario, DiasLetivos diaLetivo, Aula aula, int idAluno, String comparecimento, String data, String hora) {

        String dataAtual = DateUtils.getCurrentDate();

        ContentValues insertValues = new ContentValues();
        insertValues.put("aluno_id", idAluno);
        insertValues.put("tipoFalta", comparecimento);
        insertValues.put("dataCadastro", dataAtual);
        insertValues.put("usuario_id", usuario.getId());
        insertValues.put("aula_id", aula.getId());

        final Cursor cursor = getBanco(context).get().rawQuery("SELECT id FROM DIASLETIVOS WHERE dataAula='"+data+"'", null);
        cursor.moveToNext();
        if (cursor.getCount() == 0){
            Log.e("ERRO","Falha ao buscar DiasLetivoId");
        } else {
            insertValues.put("diasLetivos_id", cursor.getString(cursor.getColumnIndex("id")));
        }
        cursor.close();
        //insertValues.put("hora", hora);

        if (getBanco(context).get().update("FALTASALUNOS", insertValues, "aluno_id=" + idAluno + " AND diasLetivos_id=" + diaLetivo.getId() + " AND aula_id=" + aula.getId(), null) == 0){
            getBanco(context).get().insert("FALTASALUNOS", null, insertValues);
        }
        return true;

    }

    public static String getComparecimento(Context context, int idAluno, Aula aula, String data){
        final Cursor cursor = getBanco(context).get().rawQuery("SELECT F.tipoFalta AS tipoFalta FROM FALTASALUNOS AS F, DIASLETIVOS D WHERE F.diasLetivos_id = D.id AND F.aluno_id=" + idAluno + " AND D.dataAula='" + data + "' AND F.aula_id=" + aula.getId(), null);

        cursor.moveToNext();
        String retorno = "";

        if (cursor.getCount() > 0){
            switch (cursor.getString(cursor.getColumnIndex("tipoFalta"))){
                case "C":
                    retorno = "Compareceu";
                    break;
                case "F":
                    retorno = "Faltou";
                    break;
                case "N":
                    retorno = "Não se aplica";
                    break;
                case "T":
                    retorno = "Não se aplica";
                    break;
            }
        }
        return retorno;

    }

    public static String getSiglaComparecimento(Context context, int idAluno, Aula aula, String data){
        final Cursor cursor = getBanco(context).get().rawQuery("SELECT F.tipoFalta AS tipoFalta FROM FALTASALUNOS AS F, DIASLETIVOS D WHERE F.diasLetivos_id = D.id AND F.aluno_id=" + idAluno + " AND D.dataAula='" + data + "' AND F.aula_id=" + aula.getId(), null);

        if(cursor.getCount() > 0) {
            cursor.moveToNext();
            String retorno = cursor.getString(cursor.getColumnIndex("tipoFalta"));
            return retorno;
        }else{
            return "";
        }

    }


    public static Aluno getTotalFaltas(Context context, Aluno aluno, int idDisciplina){
        final Cursor cursor = getBanco(context).get().rawQuery("SELECT faltasAnuais, faltasBimestre, faltasSequenciais FROM TOTALFALTASALUNOS WHERE aluno_id=" + aluno.getId(), null);
        if (cursor.getCount() > 0){
            cursor.moveToNext();
            aluno.setFaltasAnuais(cursor.getInt(cursor.getColumnIndex("faltasAnuais")));
            aluno.setFaltasBimestre(cursor.getInt(cursor.getColumnIndex("faltasBimestre")));
            aluno.setFaltasSequenciais(cursor.getInt(cursor.getColumnIndex("faltasSequenciais")));
        }
        cursor.close();
        return aluno;

    }

    public static DiasLetivos getDiaLetivo(Context context, String data){
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        Date date = null;
        try {
            date = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strDate = sdf.format(date);

        final Cursor cursor = getBanco(context).get().rawQuery("SELECT id FROM DIASLETIVOS WHERE dataAula = '" + strDate + "'", null);

        DiasLetivos diasLetivos = null;

        if(cursor.getCount() > 0){
            diasLetivos = new DiasLetivos();
            cursor.moveToNext();

            diasLetivos.setId(cursor.getInt(cursor.getColumnIndex("id")));
        }

        cursor.close();

        return diasLetivos;
    }

    public static int getFaltasEnviadas(Context context, DiasLetivos diasLetivos, Aula aula){
        final Cursor cursor = getBanco(context).get().rawQuery("SELECT id FROM FALTASALUNOS WHERE diasLetivos_id = " + diasLetivos.getId() + " AND aula_id = " + aula.getId() +
                " AND dataServidor IS NOT NULL", null);

        if(cursor.getCount() > 0){
            int count = 0;

            while(cursor.moveToNext()){
                count++;
            }
            cursor.close();

            return count;
        }
        else{
            cursor.close();

            return 0;
        }
    }

    public static int getFaltas(Context context, DiasLetivos diasLetivos, Aula aula){
        final Cursor cursor = getBanco(context).get().rawQuery("SELECT id FROM FALTASALUNOS WHERE diasLetivos_id = " + diasLetivos.getId() + " AND aula_id = " + aula.getId(), null);

        if(cursor.getCount() > 0){
            int count = 0;

            while(cursor.moveToNext()){
                count++;
            }
            cursor.close();

            return count;
        }
        else{
            cursor.close();

            return 0;
        }
    }


}
