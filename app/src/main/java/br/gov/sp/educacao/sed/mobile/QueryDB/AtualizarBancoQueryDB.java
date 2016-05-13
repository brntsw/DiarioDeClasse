package br.gov.sp.educacao.sed.mobile.QueryDB;

import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.gov.sp.educacao.sed.mobile.Modelo.Aula;
import br.gov.sp.educacao.sed.mobile.Modelo.Bimestre;
import br.gov.sp.educacao.sed.mobile.Modelo.DiasLetivos;
import br.gov.sp.educacao.sed.mobile.Modelo.Disciplina;
import br.gov.sp.educacao.sed.mobile.Modelo.Turma;
import br.gov.sp.educacao.sed.mobile.Modelo.TurmasFrequencia;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 15/03/2016.
 */
public class AtualizarBancoQueryDB extends Queries {

    public static ArrayList<TurmasFrequencia> getTurmasFrequencia(Context context, Turma turma) {

        TurmasFrequencia turmasFrequencia = new TurmasFrequencia();
        ArrayList<TurmasFrequencia> listaTurmasFrequencia = new ArrayList<>();
        final Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM TURMASFREQUENCIA WHERE turma_id = " + turma.getId(), null);

        while (cursor.moveToNext()) {
            turmasFrequencia.setId(cursor.getInt(cursor.getColumnIndex("id")));
            turmasFrequencia.setCodigoTurma(cursor.getInt(cursor.getColumnIndex("codigoTurma")));
            turmasFrequencia.setCodigoDiretoria(cursor.getInt(cursor.getColumnIndex("codigoDiretoria")));
            turmasFrequencia.setCodigoEscola(cursor.getInt(cursor.getColumnIndex("codigoEscola")));
            turmasFrequencia.setCodigoTipoEnsino(cursor.getInt(cursor.getColumnIndex("codigoTipoEnsino")));
            turmasFrequencia.setAulasBimestre(cursor.getInt(cursor.getColumnIndex("aulasBimestre")));
            turmasFrequencia.setAulasAno(cursor.getInt(cursor.getColumnIndex("aulasAno")));
            listaTurmasFrequencia.add(turmasFrequencia);
        }
        cursor.close();

        return listaTurmasFrequencia;
    }

    public static ArrayList<Disciplina> getDisciplina(Context context, ArrayList<TurmasFrequencia> listaTurmasFrequencia) {

        ArrayList<Disciplina> listaDisciplina = new ArrayList<>();

        for (TurmasFrequencia turmasFrequenciaFor : listaTurmasFrequencia) {

            final Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM DISCIPLINA WHERE turmasFrequencia_id = " + turmasFrequenciaFor.getId(), null);

            while (cursor.moveToNext()) {
                Disciplina disciplina = new Disciplina();

                disciplina.setId(cursor.getInt(cursor.getColumnIndex("id")));
                disciplina.setCodigoDisciplina(cursor.getInt(cursor.getColumnIndex("codigoDisciplina")));
                disciplina.setNomeDisciplina(cursor.getString(cursor.getColumnIndex("nomeDisciplina")));
                disciplina.setTurmasFrequencia_id(cursor.getInt(cursor.getColumnIndex("turmasFrequencia_id")));

                listaDisciplina.add(disciplina);
            }
            cursor.close();
        }

        return listaDisciplina;
    }


    public static Bimestre getBimestre(Context context, TurmasFrequencia turmasFrequencia) {
        Bimestre bimestre = new Bimestre();
        final Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM BIMESTRE WHERE turmasFrequencia_id = " + turmasFrequencia.getId(), null);

        if (cursor.moveToNext()) {
            bimestre.setId(cursor.getInt(cursor.getColumnIndex("id")));
            bimestre.setNumero(cursor.getInt(cursor.getColumnIndex("numero")));
            bimestre.setInicio(cursor.getString(cursor.getColumnIndex("inicioBimestre")));
            bimestre.setFim(cursor.getString(cursor.getColumnIndex("fimBimestre")));
        }
        cursor.close();

        return bimestre;
    }

    public static ArrayList<DiasLetivos> getDiasLetivos(Context context, Bimestre bimestre, List<Integer> listaDiaSemana) {
        ArrayList<DiasLetivos> listaDiasLetivos = new ArrayList<>();

        StringBuffer query = new StringBuffer();
        query.append(" SELECT * FROM DIASLETIVOS ");
        query.append(" WHERE    bimestre_id = " + bimestre.getId());

        final Cursor cursor = getBanco(context).get().rawQuery(query.toString(), null);

        while (cursor.moveToNext()) {
            try {
                String input_date = cursor.getString(cursor.getColumnIndex("dataAula"));
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                Date data = format1.parse(input_date);

                Calendar c = Calendar.getInstance();
                c.setTime(data);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;

                if (listaDiaSemana.contains(dayOfWeek)) {
                    final DiasLetivos diasLetivos = new DiasLetivos();
                    diasLetivos.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    diasLetivos.setDataAula(cursor.getString(cursor.getColumnIndex("dataAula")));
                    listaDiasLetivos.add(diasLetivos);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        cursor.close();

        return listaDiasLetivos;
    }

    public static Aula getAula(Context context, String inicioHora, String fimHora, int diaSemana, int idDisciplina){
        Aula aula = new Aula();

        final Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM AULAS WHERE inicioHora = '" + inicioHora + "' AND fimHora = '" + fimHora + "' AND diaSemana = " + diaSemana +
                                                                    " AND disciplina_id = " + idDisciplina, null);

        if(cursor.moveToNext()){
            aula.setId(cursor.getInt(cursor.getColumnIndex("id")));
            aula.setCodigoAula(cursor.getInt(cursor.getColumnIndex("codigoAula")));
        }

        cursor.close();

        return aula;
    }

}
