package br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO;

import android.content.Context;

public class PersistenceDAOFactory {

    public DAO create(Object objeto) {

        if (objeto instanceof AlunosTO) {
            return new PersistenceDAO<AlunosTO,Context>();
        }
        if (objeto instanceof AulasTO) {
            return new PersistenceDAO<AulasTO,Context>();
        }
        if (objeto instanceof AvaliacoesTO) {
            return new PersistenceDAO<AvaliacoesTO,Context>();
        }
        if (objeto instanceof BimestreTO) {
            return new PersistenceDAO<BimestreTO,Context>();
        }
        if (objeto instanceof ConteudoTO) {
            return new PersistenceDAO<ConteudoTO,Context>();
        }
        if (objeto instanceof DiasLetivosTO) {
            return new PersistenceDAO<DiasLetivosTO,Context>();
        }
        if (objeto instanceof DisciplinaTO) {
            return new PersistenceDAO<DisciplinaTO,Context>();
        }
        if (objeto instanceof FaltasAlunosTO) {
            return new PersistenceDAO<FaltasAlunosTO,Context>();
        }
        if (objeto instanceof GruposTO) {
            return new PersistenceDAO<GruposTO,Context>();
        }
        if (objeto instanceof HabilidadesAbordadasTO) {
            return new PersistenceDAO<HabilidadesAbordadasTO,Context>();
        }
        if (objeto instanceof HabilidadesTO) {
            return new PersistenceDAO<HabilidadesTO,Context>();
        }
        if (objeto instanceof NotasAlunoTO) {
            return new PersistenceDAO<NotasAlunoTO,Context>();
        }
        if (objeto instanceof TotalFaltasAlunosTO) {
            return new PersistenceDAO<TotalFaltasAlunosTO,Context>();
        }
        if (objeto instanceof TurmasFrequenciaTO) {
            return new PersistenceDAO<TurmasFrequenciaTO,Context>();
        }
        if (objeto instanceof TurmasTO) {
            return new PersistenceDAO<TurmasTO,Context>();
        }
        if (objeto instanceof UsuarioTO) {
            return new PersistenceDAO<UsuarioTO,Context>();
        }

        return null;
    }

}
