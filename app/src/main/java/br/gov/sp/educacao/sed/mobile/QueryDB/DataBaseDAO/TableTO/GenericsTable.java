package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by afirmanet on 20/03/16.
 */
public interface GenericsTable {

    public ContentValues getContentValues();
    public String getCodigoUnico();
}
