package br.gov.sp.educacao.sed.mobile.Modelo;

import java.io.Serializable;

/**
 * Created by techresult on 23/03/2016.
 */
public class DiasLetivos implements Serializable {

    private Integer id;
    private String dataAula;
    private Integer bimestre_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataAula() {
        return dataAula;
    }

    public void setDataAula(String dataAula) {
        this.dataAula = dataAula;
    }

    public Integer getBimestre_id() {
        return bimestre_id;
    }

    public void setBimestre_id(Integer bimestre_id) {
        this.bimestre_id = bimestre_id;
    }

}
