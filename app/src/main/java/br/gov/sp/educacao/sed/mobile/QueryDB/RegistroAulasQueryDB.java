package br.gov.sp.educacao.sed.mobile.QueryDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.gov.sp.educacao.sed.mobile.Modelo.Conteudo;
import br.gov.sp.educacao.sed.mobile.Modelo.DiasLetivos;
import br.gov.sp.educacao.sed.mobile.Modelo.Disciplina;
import br.gov.sp.educacao.sed.mobile.Modelo.Grupo;
import br.gov.sp.educacao.sed.mobile.Modelo.Habilidades;
import br.gov.sp.educacao.sed.mobile.Modelo.HabilidadesAbordadas;
import br.gov.sp.educacao.sed.mobile.Modelo.Turma;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 15/03/2016.
 */
public class RegistroAulasQueryDB extends Queries {
    public static String HABILIDADES_ABORDADAS = "HABILIDADESABORDADAS";

    public static long setHabilidadesAbordadas(Context context, HabilidadesAbordadas habilidadesAbordadas){

        ContentValues insertValues = new ContentValues();
        insertValues.put("habilidade_id", habilidadesAbordadas.getHabilidadeId());
        insertValues.put("diasLetivos_id", habilidadesAbordadas.getDiasLetivosId());
        insertValues.put("turma_id", habilidadesAbordadas.getTurmaId());
        insertValues.put("usuario_id", habilidadesAbordadas.getUsuarioId());
        insertValues.put("descricao", habilidadesAbordadas.getDescricao());
        insertValues.put("dataCadastro", habilidadesAbordadas.getDataCadastro());

        String whereClause = "turma_id = ? AND usuario_id = ? AND diasLetivos_id = ? AND habilidade_id = ?";
        String[] whereArgs = new String[]{
                String.valueOf(habilidadesAbordadas.getTurmaId()),
                String.valueOf(habilidadesAbordadas.getUsuarioId()),
                String.valueOf(habilidadesAbordadas.getDiasLetivosId()),
                String.valueOf(habilidadesAbordadas.getHabilidadeId())
        };

        if(getBanco(context).get().update(HABILIDADES_ABORDADAS, insertValues, whereClause, whereArgs) == 0){
            return getBanco(context).get().insert("HABILIDADESABORDADAS", null, insertValues);
        }

        return 0;
    }

    public static boolean deletaHabilidadeAbordada(Context context, HabilidadesAbordadas habilidadesAbordadas){

        String whereClause = "turma_id = ? AND usuario_id = ? AND diasLetivos_id = ? AND habilidade_id = ?";
        String[] whereArgs = new String[]{
                String.valueOf(habilidadesAbordadas.getTurmaId()),
                String.valueOf(habilidadesAbordadas.getUsuarioId()),
                String.valueOf(habilidadesAbordadas.getDiasLetivosId()),
                String.valueOf(habilidadesAbordadas.getHabilidadeId())
        };

        return getBanco(context).get().delete(HABILIDADES_ABORDADAS, whereClause, whereArgs) > 0;

    }

    public static int setObservacoes(Context context, List<HabilidadesAbordadas> habilidadesAbordadas, String descricao){
        if(habilidadesAbordadas.size() > 0){
            int cont = 0;

            for(int i = 0; i < habilidadesAbordadas.size(); i++){
                ContentValues insertValues = new ContentValues();
                insertValues.put("descricao", descricao);

                String whereClause = "turma_id = ? AND usuario_id = ? AND diasLetivos_id = ? AND habilidade_id = ?";
                String[] whereArgs = new String[]{
                    String.valueOf(habilidadesAbordadas.get(i).getTurmaId()),
                    String.valueOf(habilidadesAbordadas.get(i).getUsuarioId()),
                    String.valueOf(habilidadesAbordadas.get(i).getDiasLetivosId()),
                    String.valueOf(habilidadesAbordadas.get(i).getHabilidadeId())
                };

                if(getBanco(context).get().update(HABILIDADES_ABORDADAS, insertValues, whereClause, whereArgs) > 0)
                    cont++;
            }


            return cont;
        }

        return 0;
    }

    public static Grupo getGrupo(Context context, Turma turma, Disciplina disciplina){
        Grupo grupo = new Grupo();
        final Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM GRUPOS WHERE anoLetivo = " + turma.getAno() + " AND codigoTipoEnsino = " + turma.getCodigoTipoEnsino() + " AND codigoDisciplina = " + disciplina.getCodigoDisciplina(), null);

        cursor.moveToNext();

        if (cursor.getCount() > 0) {
            grupo.setId(cursor.getInt(cursor.getColumnIndex("id")));
            grupo.setCodigo(cursor.getInt(cursor.getColumnIndex("codigoGrupo")));
            grupo.setAnoLetivo(cursor.getInt(cursor.getColumnIndex("anoLetivo")));
            grupo.setCodigoTipoEnsino(cursor.getInt(cursor.getColumnIndex("codigoTipoEnsino")));
            grupo.setSerie(cursor.getInt(cursor.getColumnIndex("serie")));
            grupo.setCodigoDisciplina(cursor.getInt(cursor.getColumnIndex("codigoDisciplina")));
            grupo.setDisciplinaId(cursor.getInt(cursor.getColumnIndex("disciplina_id")));
        }

        cursor.close();

        return grupo;
    }

    public static List<Conteudo> getConteudos(Context context, Grupo grupo){
        List<Conteudo> conteudos = new ArrayList<>();

        final Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM CONTEUDO WHERE grupo_id = " + grupo.getId(), null);

        while (cursor.moveToNext()){
            final Conteudo conteudo = new Conteudo();
            conteudo.setId(cursor.getInt(cursor.getColumnIndex("id")));
            conteudo.setCodigoConteudo(cursor.getInt(cursor.getColumnIndex("codigoConteudo")));
            conteudo.setDescricaoConteudo(cursor.getString(cursor.getColumnIndex("descricaoConteudo")));

            conteudos.add(conteudo);
        }

        cursor.close();

        return conteudos;
    }

    public static List<Habilidades> getHabilidades(Context context, Conteudo conteudo){
        List<Habilidades> habilidades = new ArrayList<>();

        final Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM HABILIDADES WHERE conteudo_id = " + conteudo.getId(), null);

        while (cursor.moveToNext()){
            final Habilidades habilidade = new Habilidades();
            habilidade.setId(cursor.getInt(cursor.getColumnIndex("id")));
            habilidade.setCodigoHabilidade(cursor.getInt(cursor.getColumnIndex("codigoHabilidade")));
            habilidade.setDescricaoHabilidade(cursor.getString(cursor.getColumnIndex("descricaoHabilidade")));

            habilidades.add(habilidade);
        }

        cursor.close();

        return habilidades;
    }

    public static List<HabilidadesAbordadas> getHabilidadesAbordadas(Context context, Turma turma, UsuarioTO usuario, DiasLetivos diasLetivos){
        List<HabilidadesAbordadas> listaHabilidadesAbordadas = new ArrayList<>();

        final Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM " + HABILIDADES_ABORDADAS + " WHERE turma_id = " + turma.getId() + " AND usuario_id = " + usuario.getId() +
                                                                " AND diasLetivos_id = " + diasLetivos.getId(), null);

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                final HabilidadesAbordadas habilidadeAbordada = new HabilidadesAbordadas();
                habilidadeAbordada.setId(cursor.getInt(cursor.getColumnIndex("id")));
                habilidadeAbordada.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                habilidadeAbordada.setTurmaId(cursor.getInt(cursor.getColumnIndex("turma_id")));
                habilidadeAbordada.setDiasLetivosId(cursor.getInt(cursor.getColumnIndex("diasLetivos_id")));
                habilidadeAbordada.setUsuarioId(cursor.getInt(cursor.getColumnIndex("usuario_id")));
                habilidadeAbordada.setHabilidadeId(cursor.getInt(cursor.getColumnIndex("habilidade_id")));

                listaHabilidadesAbordadas.add(habilidadeAbordada);
            }
        }

        cursor.close();

        return listaHabilidadesAbordadas;
    }

    public static Boolean registroAulaEnviada(Context context, DiasLetivos diasLetivos){
        Cursor cursor = getBanco(context).get().rawQuery("SELECT * FROM " + HABILIDADES_ABORDADAS + " WHERE diasLetivos_id = " + diasLetivos.getId(), null);
        int totalRegistros = cursor.getCount();
        cursor.close();

        if(totalRegistros > 0) {
            cursor = getBanco(context).get().rawQuery("SELECT * FROM " + HABILIDADES_ABORDADAS + " WHERE diasLetivos_id = " + diasLetivos.getId()
                    + " AND dataServidor is not null", null);
            int registrosEnviados = cursor.getCount();
            cursor.close();

            if (registrosEnviados == totalRegistros)
                return Boolean.TRUE;
            else
                return Boolean.FALSE;
        }
        else{
            return Boolean.FALSE;
        }
    }

}
