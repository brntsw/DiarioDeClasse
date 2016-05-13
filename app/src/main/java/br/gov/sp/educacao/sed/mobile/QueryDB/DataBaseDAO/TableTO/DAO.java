package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;


import android.database.Cursor;

public interface DAO<E,C> {
    public long insert(E object, C context) throws Exception;
    public Cursor select(E objeto, C context) throws Exception;
    public int update(E object, C context) throws Exception;
    public int delete(E object, C context) throws Exception;
}
