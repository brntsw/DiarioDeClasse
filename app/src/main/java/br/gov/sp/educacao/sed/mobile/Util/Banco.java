package br.gov.sp.educacao.sed.mobile.Util;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Gustavo on 05/12/2014.
 */

public class Banco {

    private BdManager bancoManager;
    private SQLiteDatabase sqld;

    public Banco(BdManager bancoManager) {

        this.bancoManager = bancoManager;
    }

    public void open() {

        sqld = bancoManager.getWritableDatabase();
    }

    public SQLiteDatabase get() {

        if (sqld != null && sqld.isOpen()) {
            return sqld;
        }
        return null;
    }

    public void close() {
        bancoManager.close();
    }
}