package br.gov.sp.educacao.sed.mobile.Modelo;

/**
 * Created by techresult on 28/03/2016.
 */
public class NotasAluno {

    private Integer id;
    private Integer codigoMatricula;
    private String nota;
    private String dataCadastro;
    private String dataServidor;
    private Integer aluno_id;
    private Integer usuario_id;
    private Integer avaliacao_id;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(Integer codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getDataServidor() {
        return dataServidor;
    }

    public void setDataServidor(String dataServidor) {
        this.dataServidor = dataServidor;
    }

    public Integer getAluno_id() {
        return aluno_id;
    }

    public void setAluno_id(Integer aluno_id) {
        this.aluno_id = aluno_id;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Integer getAvaliacao_id() {
        return avaliacao_id;
    }

    public void setAvaliacao_id(Integer avaliacao_id) {
        this.avaliacao_id = avaliacao_id;
    }
}
