package br.gov.sp.educacao.sed.mobile.Enuns;

/**
 * Created by AlexandreFQM on 30/03/16.
 */
public enum TipoServicoEnum {
    LOGIN("/Login"),
    DADOS_OFF_LINE("/Offline2/Professor"),
    FREQUENCIA("/Frequencia"),
    REGISTRO_AULAS("/RegistroAula"),
    AVALIACAO("/AvaliacaoNova/Salvar");


    private static String urlServidor = "https://homologacaosed.educacao.sp.gov.br/SedApi/Api";

    //https://sed.educacao.sp.gov.br/SedApi/Api -> Prod
    //https://homologacaosed.educacao.sp.gov.br/SedApi/Api -> Homolog
    //https://sedhomolog.cloudapp.net/ApiMock/Api -> Mock data (Usuario: rg262645749sp senha 12345678)

    //https://secretariaescolardigitalapi.cloudapp.net/Api
    //https://sedhomolog.cloudapp.net/SedApi/Api
    //"https://sed.homologacao.sp.gov.br/webapi/Api"
    //"https://sed.educacao.sp.gov.br/webapi/Api"
    //"http://10.17.118.65/Api"
    //"http://10.17.118.114:5050/Api"
    //"https://10.200.240.19/webapi/Api"

    private String servico;

    TipoServicoEnum(String servico){
        this.servico = servico;
    }

    @Override
    public String toString() {
        return urlServidor + servico;
    }


}
