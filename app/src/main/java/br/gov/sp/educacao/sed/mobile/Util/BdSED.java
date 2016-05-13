package br.gov.sp.educacao.sed.mobile.Util;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.gov.sp.educacao.sed.mobile.R;

/**
 * Created by Gustavo on 05/12/2014.
 */

public class BdSED extends BdManager {

    //nome do banco de dados e vers�o
    public static final String NAME = "SEDDB";
    public static final String TAG_LOG = "SEDDB";
    public static final int VERSAO = 3;

    public BdSED(Context context) {
        //defino pelo contrutor do BdManager a versão e o nome do banco
        super(context, NAME, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        criaTabelas(bd);
    }

    /**
     * Este método é chamado automaticamente quando a versão é alterada.
     */
    @Override
    public void onUpgrade(SQLiteDatabase bd, int versaoAtual, int versaoNova) {
        //realizaa tratamento de upgrade, caso tenha
        //alteração em tabelas por exemplo.
        Log.e(TAG_LOG, "V.atual: " + versaoAtual);
        Log.e(TAG_LOG, "Nova V.: " + versaoNova);
        //Aqui voc� deve fazer o tratamento do update do banco.

        try {

            // Script de alteração da versão 2
            if (versaoNova == 2) {

                bd.execSQL("CREATE TABLE \"BIMESTRE\" (\n" +
                        "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "    \"numero\" int,\n" +
                        "    \"inicio\" VARCHAR,\n" +
                        "    \"fim\" VARCHAR,\n" +
                        "    \"CodigoTurma\" int,\n" +
                        "    \"CodigoDisciplina\" int,\n" +
                        "    \"tipo\" int,\n" +
                        "    \"ano\" int,\n" +
                        "    \"UsuarioId\" int\n" +
                        ");");
                bd.execSQL("ALTER TABLE \"CURRICULO\"" +
                        "ADD COLUMN codigo_curriculo INT");

            }

            if(versaoNova == 3){
                bd.execSQL("ALTER TABLE \"AVALIACAO\"" +
                        "ADD COLUMN tipoAtividade INT DEFAULT NULL");

                bd.execSQL("ALTER TABLE \"AVALIACAO\"" +
                        "ADD COLUMN valeNota INT DEFAULT NULL");
            }

            if(versaoNova == 4){
                bd.execSQL("ALTER TABLE \"AVALIACAO\"" +
                        "ADD COLUMN codigo INT DEFAULT NULL");
            }
/*
            if(versaoNova == 3){

                Log.d("VERSAO", "4");

                bd.execSQL("ALTER TABLE \"CURRICULO\"" +
                            "ADD COLUMN codigo_curriculo INT");

            }
*/
        } catch (Exception e) {
            Log.e(TAG_LOG, "onUpgrade", e);
        }
        //criaTabelas(bd);
    }

    private void criaTabelas(SQLiteDatabase bd) {
        try {
            byFile(R.raw.script, bd);
        } catch (Exception e) {
            Log.e(TAG_LOG, "criaTabelas", e);
        }
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1, Cursor2);
            return alc;
        }


    }
}