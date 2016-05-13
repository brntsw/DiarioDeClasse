package br.gov.sp.educacao.sed.mobile.Util;

import android.content.Context;
import android.database.Cursor;

import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.GenericsTable;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;

/**
 * Created by Gustavo on 08/06/2015.
 */
public class Queries {

    private static Banco banco;

    public static Banco getBanco(Context context) {

        if (banco == null){
            banco = new Banco(new BdSED(context));
            banco.open();
        } else {
            if (!banco.get().isOpen()){
                banco.open();
            }
        }

        return banco;
    }

    public static int getVersao(Context context) {

        getBanco(context);
        Cursor cursor = banco.get().rawQuery("SELECT numero FROM VERSAO ORDER BY id LIMIT 1 ", null);

        cursor.moveToNext();
        cursor.close();

        //banco.close();

        return cursor.getInt(cursor.getColumnIndex("numero"));

    }

    public static Integer setDadosDataBase(Context context, String nameTable, GenericsTable genericsTable){
        long id = -1;

        getBanco(context).get().beginTransaction();
        try {
            if (genericsTable.getCodigoUnico() != null) {
                int rowsUpdate = getBanco(context).get().update(nameTable, genericsTable.getContentValues(), genericsTable.getCodigoUnico(), null);

                if (rowsUpdate == 0) {
                    id = getBanco(context).get().insert(nameTable, null, genericsTable.getContentValues());
                }else{
                    StringBuffer query = new StringBuffer();
                    query.append(" SELECT id FROM ").append(nameTable);
                    query.append(" WHERE ").append(genericsTable.getCodigoUnico());

                    final Cursor cursor = Queries.getSelect(context, query);
                    id = cursor.getLong(cursor.getColumnIndex("id"));
                    cursor.close();
                }
            }else{
                id = getBanco(context).get().insert(nameTable, null, genericsTable.getContentValues());
            }
            getBanco(context).get().setTransactionSuccessful();
        } finally {
            getBanco(context).get().endTransaction();
        }

        return Integer.valueOf(String.valueOf(id));
    }


    public static Integer deleteTable(Context context, String nameTable, String whereClause, String[] whereArgs){
        long id = -1;

        getBanco(context).get().beginTransaction();
        try {
            getBanco(context).get().delete(nameTable, whereClause, whereArgs);
            getBanco(context).get().setTransactionSuccessful();
        } finally {
            getBanco(context).get().endTransaction();
        }

        return Integer.valueOf(String.valueOf(id));
    }

    public static Cursor getSelect(Context context, StringBuffer query){
        Cursor cursor = Queries.getBanco(context).get().rawQuery(query.toString(), null);
        if(!cursor.moveToFirst()){
            cursor = null;
        }
        return cursor;
    }


    public static UsuarioTO getUsuarioAtivo(Context context){
        UsuarioTO usuario = null;

        StringBuffer query = new StringBuffer();
        query.append(" SELECT   us.id, us.usuario, us.senha, us.nome, us.cpf, us.rg, us.digitoRG, us.dataUltimoAcesso, us.ativo, us.token  ");
        query.append(" FROM     USUARIO as us ");
        query.append(" Where    us.ativo = 1 ");

        Cursor cursor = Queries.getBanco(context).get().rawQuery(query.toString(), null);
        if(cursor.moveToFirst()){
            usuario = new UsuarioTO(cursor);
        }

        cursor.close();

        return usuario;
    }
}
