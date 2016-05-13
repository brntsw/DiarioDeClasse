package br.gov.sp.educacao.sed.mobile.Util;

/**
 * Created by techresult on 02/03/2016.
 */
public class Utils {

    public static String trataNota(String nota){
        String retorno;

        if(nota.length() == 1 || nota.length() == 2){
            retorno = nota + ",00";
        }
        else if(nota.length() == 3){
            retorno = nota.replace(".", ",") + "0";
        }
        else if(nota.contains("10")){
            retorno = nota.replace(".", ",") + "0";
        }
        else{
            retorno = nota.replace(".", ",");
        }

        return retorno;
    }

    public static String convertStatus(int status){
        if(status == 1){
            return "Ativo";
        }else{
            return "Transferido";
        }
    }

}
