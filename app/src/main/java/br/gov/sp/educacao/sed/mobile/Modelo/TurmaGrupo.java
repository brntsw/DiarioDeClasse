package br.gov.sp.educacao.sed.mobile.Modelo;

import java.io.Serializable;
import java.util.List;

public class TurmaGrupo implements Serializable {

    public static final String BUNDLE_TURMA_GRUPO = "turma_grupo";

    private Turma turma;
    private TurmasFrequencia turmasFrequencia;
    private Disciplina disciplina;

    public TurmaGrupo() {
    }

    public TurmaGrupo(Turma turma, TurmasFrequencia turmasFrequencia, Disciplina disciplina) {
        this.turma = turma;
        this.turmasFrequencia = turmasFrequencia;
        this.disciplina = disciplina;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public TurmasFrequencia getTurmasFrequencia() {
        return turmasFrequencia;
    }

    public void setTurmasFrequencia(TurmasFrequencia turmasFrequencia) {
        this.turmasFrequencia = turmasFrequencia;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
}
