package br.gov.sp.educacao.sed.mobile.Util;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by techresult on 07/07/2015.
 */
public class DateUtils {

    public static int getCurrentDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int getCurrentYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int totalDiasMes(int mes, int ano){
        Calendar calDias = new GregorianCalendar(ano, mes, 1);
        return calDias.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //Retorna o padrao yyyy-MM-dd
    public static String parseDateNormal(int dia, int mes, int ano){
        return ano + "-" + (mes < 10 ? "0" + mes : mes) + "-" + (dia < 10 ? "0" + dia : dia);
    }

    //(str) dd/mm/yyyy para Calendar
    public static Calendar convertStringToDate(String data){
        Calendar dataConvertida = null;
        if(data != null) {
            String[] dataSeparada = data.split("/");
            String dia = dataSeparada[0];
            String mes = dataSeparada[1];
            String ano = dataSeparada[2];

            dataConvertida = Calendar.getInstance();

            dataConvertida.set(Calendar.DATE, Integer.parseInt(dia));
            dataConvertida.set(Calendar.MONTH, Integer.parseInt(mes) - 1);
            dataConvertida.set(Calendar.YEAR, Integer.parseInt(ano));
            dataConvertida.set(Calendar.HOUR,0);
            dataConvertida.set(Calendar.MINUTE,0);
            dataConvertida.set(Calendar.SECOND,0);
            dataConvertida.set(Calendar.MILLISECOND,0);

        }else{
            Log.e("Conversão da data","[DateUtils.convertStringToDate] Falha na conversão de data [null]");
        }
        return dataConvertida;
    }

    //dd-mm-yyyy para dd/mm/yyyy
    public static String convertTracoParaBarra(String data){
        return data.replace("-", "/");
    }

    public static String getCurrentDate(){
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();

        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");

        return date.format(currentDate);
    }

}
