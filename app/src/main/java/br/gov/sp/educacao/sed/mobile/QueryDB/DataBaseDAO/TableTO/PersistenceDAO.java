package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;


import android.content.Context;
import android.database.Cursor;

import br.gov.sp.educacao.sed.mobile.Util.Queries;

public class PersistenceDAO<E,C> extends Queries implements DAO<E,C>  {

    private DAOGenerics daoGenerics;

    @Override
    public long insert(E object, C context) throws Exception {
        this.daoGenerics = (DAOGenerics) object;
        return getBanco((Context) context).get().insert(this.daoGenerics.getNomeTabela(), null, this.daoGenerics.getArgsInsert());
    }

    @Override
    public Cursor select(E object, C context) throws Exception {

        this.daoGenerics = (DAOGenerics) object;

        StringBuilder stringSelect = new StringBuilder();
        stringSelect.append("SELECT * FROM ");
        stringSelect.append(this.daoGenerics.getNomeTabela());
        if(this.daoGenerics.getArgsSelectWhere() != null && !this.daoGenerics.getArgsSelectWhere().equals("")) {
            stringSelect.append(" WHERE ");
            stringSelect.append(this.daoGenerics.getArgsSelectWhere());
        }
        return getBanco((Context) context).get().rawQuery(stringSelect.toString(), null);
    }

    @Override
    public int update(E object, C context) throws Exception {

        this.daoGenerics = (DAOGenerics) object;
        return getBanco((Context) context).get().update(this.daoGenerics.getNomeTabela(), this.daoGenerics.getArgsUpdateValues(), this.daoGenerics.getArgsUpdateWhere(), null);
    }

    @Override
    public int delete(E object, C context) throws Exception {
        this.daoGenerics = (DAOGenerics) object;
        return getBanco((Context) context).get().delete(this.daoGenerics.getNomeTabela(),this.daoGenerics.getArgsDelete(), null);
    }

}
