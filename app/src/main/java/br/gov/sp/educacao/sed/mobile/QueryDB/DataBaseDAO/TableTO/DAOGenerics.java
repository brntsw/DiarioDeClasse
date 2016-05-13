package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.ContentValues;

import org.json.JSONObject;

import br.gov.sp.educacao.sed.mobile.Modelo.Usuario;

public class DAOGenerics{

    public Object objeto;
    public String acao;
    public String nomeTabela;
    public String argsDelete;
    public ContentValues argsInsert;
    public ContentValues argsUpdateValues;
    public String argsUpdateWhere;
    public String argsSelectWhere;

    public static final String ACAO_INSERT = "insert";
    public static final String ACAO_UPDATE = "update";
    public static final String ACAO_DELETE = "delete";
    public static final String ACAO_SELECT = "select";

    public DAOGenerics(){
    }

    public DAOGenerics(JSONObject retorno){
    }

    public DAOGenerics(Object objeto, String acao){

        //SET DE ACAO QUE SERA EXECUTADA
        this.acao = acao;

        if(acao.equals(ACAO_DELETE)) {
            //SET DE ARGS DELETE GENERICO
            this.setArgsDelete("UsuarioId=" + Usuario.getInstance().getId());
        }

        if (objeto instanceof AlunosTO) {
            AlunosTO aluno = (AlunosTO) objeto;
            this.setNomeTabela("ALUNOS");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(aluno.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(aluno.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + aluno.getId());
                    break;
            }
        }
        if (objeto instanceof AulasTO) {
            AulasTO aula = (AulasTO) objeto;
            this.setNomeTabela("AULAS");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(aula.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(aula.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + aula.getId());
                    break;
            }
        }
        if (objeto instanceof AvaliacoesTO) {
            AvaliacoesTO avaliacao = (AvaliacoesTO) objeto;
            this.setNomeTabela("AVALIACOES");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(avaliacao.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(avaliacao.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + avaliacao.getId());
                    break;
            }
        }
        if (objeto instanceof BimestreTO) {
            BimestreTO bimestre = (BimestreTO) objeto;
            this.setNomeTabela("BIMESTRE");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(bimestre.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(bimestre.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + bimestre.getId());
                    break;
            }
        }
        if (objeto instanceof ConteudoTO) {
            ConteudoTO conteudo = (ConteudoTO) objeto;
            this.setNomeTabela("CONTEUDO");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(conteudo.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(conteudo.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + conteudo.getId());
                    break;
            }
        }
        if (objeto instanceof DiasLetivosTO) {
            DiasLetivosTO diasLetivos = (DiasLetivosTO) objeto;
            this.setNomeTabela("DIASLETIVOS");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(diasLetivos.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(diasLetivos.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + diasLetivos.getId());
                    break;
            }
        }
        if (objeto instanceof DisciplinaTO) {
            DisciplinaTO discplina = (DisciplinaTO) objeto;
            this.setNomeTabela("DISCIPLINA");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(discplina.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(discplina.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + discplina.getId());
                    break;
            }
        }
        if (objeto instanceof FaltasAlunosTO) {
            FaltasAlunosTO faltasAlunos = (FaltasAlunosTO) objeto;
            this.setNomeTabela("FALTASALUNOS");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(faltasAlunos.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(faltasAlunos.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + faltasAlunos.getId());
                    break;
            }
        }
        if (objeto instanceof GruposTO) {
            GruposTO grupo = (GruposTO) objeto;
            this.setNomeTabela("GRUPOS");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(grupo.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(grupo.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + grupo.getId());
                    break;
            }
        }
        if (objeto instanceof HabilidadesAbordadasTO) {
            HabilidadesAbordadasTO habilidadesAbordadas = (HabilidadesAbordadasTO) objeto;
            this.setNomeTabela("HABILIDADESABORDADAS");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(habilidadesAbordadas.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(habilidadesAbordadas.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + habilidadesAbordadas.getId());
                    break;
            }
        }
        if (objeto instanceof HabilidadesTO) {
            HabilidadesTO habilidades = (HabilidadesTO) objeto;
            this.setNomeTabela("HABILIDADES");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(habilidades.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(habilidades.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + habilidades.getId());
                    break;
            }
        }
        if (objeto instanceof NotasAlunoTO) {
            NotasAlunoTO notasAluno = (NotasAlunoTO) objeto;
            this.setNomeTabela("NOTASALUNO");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(notasAluno.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(notasAluno.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + notasAluno.getId());
                    break;
            }
        }
        if (objeto instanceof TotalFaltasAlunosTO) {
            TotalFaltasAlunosTO totalFaltasAlunosTO = (TotalFaltasAlunosTO) objeto;
            this.setNomeTabela("TOTALFALTASALUNOS");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(totalFaltasAlunosTO.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(totalFaltasAlunosTO.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + totalFaltasAlunosTO.getId());
                    break;
            }
        }
        if (objeto instanceof TurmasFrequenciaTO) {
            TurmasFrequenciaTO turmasFrequencia = (TurmasFrequenciaTO) objeto;
            this.setNomeTabela("TURMASFREQUENCIA");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(turmasFrequencia.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(turmasFrequencia.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + turmasFrequencia.getId());
                    break;
            }
        }
        if (objeto instanceof TurmasTO) {
            TurmasTO turma = (TurmasTO) objeto;
            this.setNomeTabela("TURMAS");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(turma.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(turma.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + turma.getId());
                    break;
            }
        }
        if (objeto instanceof UsuarioTO) {
            UsuarioTO usuario = (UsuarioTO) objeto;
            this.setNomeTabela("USUARIO");
            switch (acao){
                case ACAO_INSERT:
                    this.setArgsInsert(usuario.getContentValues());
                    break;
                case ACAO_SELECT:
                    this.setArgsSelectWhere("");
                    break;
                case ACAO_UPDATE:
                    this.setArgsUpdateValues(usuario.getContentValues());
                    break;
                case ACAO_DELETE:
                    this.setArgsDelete(" AND id=" + usuario.getId());
                    break;
            }
        }
    }

    public String getNomeTabela() {
        return nomeTabela;
    }

    public void setNomeTabela(String nomeTabela) {
        this.nomeTabela = nomeTabela;
    }

    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }



    public ContentValues getArgsInsert() {
        return argsInsert;
    }

    public void setArgsInsert(ContentValues argsInsert) {
        this.argsInsert = argsInsert;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }


    public String getArgsUpdateWhere() {
        return argsUpdateWhere;
    }

    public void setArgsUpdateWhere(String argsUpdateWhere) {
        this.argsUpdateWhere = argsUpdateWhere;
    }

    public ContentValues getArgsUpdateValues() {
        return argsUpdateValues;
    }

    public void setArgsUpdateValues(ContentValues argsUpdateValues) {
        this.argsUpdateValues = argsUpdateValues;
    }

    public String getArgsSelectWhere() {
        return argsSelectWhere;
    }

    public void setArgsSelectWhere(String argsSelectWhere) {
        this.argsSelectWhere = argsSelectWhere;
    }

    public String getArgsDelete() {
        return argsDelete;
    }

    public void setArgsDelete(String argsDelete) {
        this.argsDelete = argsDelete;
    }
}
