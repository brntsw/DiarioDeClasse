package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;


import android.content.Context;

import br.gov.sp.educacao.sed.mobile.Util.Queries;

public class PersistenceController extends Queries {

    private Object objeto;
    private Context context;
    private DAO persistenceDAO;
    private PersistenceDAOFactory persistenceDAOFactory = new PersistenceDAOFactory();

    public PersistenceController(Object objeto, Context context){
        persistenceDAO = persistenceDAOFactory.create(objeto);
        this.objeto = objeto;
        this.context = context;
    }

    public Object insert() throws Exception {
        return persistenceDAO.insert(objeto,context);
    }

    public Object select() throws Exception {
        return persistenceDAO.select(objeto,context);
    }

    public Object update() throws Exception {
        return persistenceDAO.update(objeto,context);
    }

    public Object delete() throws Exception {
        return persistenceDAO.delete(objeto,context);
    }


}
