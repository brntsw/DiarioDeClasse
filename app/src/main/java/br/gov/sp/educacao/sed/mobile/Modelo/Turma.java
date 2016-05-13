package br.gov.sp.educacao.sed.mobile.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by techresult on 24/06/2015.
 */
public class Turma implements Serializable {

    private int codigoDiretoria;
    private int codigoEscola;
    private String nomeEscola;
    private int codigoTurma;
    private String nomeTurma;
    private String nomeDiretoria;
    private int codigoDisciplina;
    private String nomeDisciplina;
    private int codigoTipoEnsino;
    private String nomeTipoEnsino;
    private int duracao;
    private int codigoSubturma;
    private String descricaoSubTurma;
    private ArrayList<Aluno> alunoArrayList = new ArrayList<Aluno>();
    private ArrayList<Calendario> calendarioArrayList = new ArrayList<Calendario>();
    private ArrayList<Aula> aulaArrayList = new ArrayList<Aula>();
    private ArrayList<Bimestre> bimestreArrayList = new ArrayList<Bimestre>();
    private ArrayList<TurmasFrequencia> turmasFrequenciaArrayList = new ArrayList<TurmasFrequencia>();
    private ArrayList<Disciplina> disciplinaArrayList = new ArrayList<Disciplina>();
    private int id;
    private int ano;
    private int bimestreAtual;
    private boolean header, footer;
    private int aulasAno;
    private int aulasBimestre;

    private String horarioAula;
    private String diaSemana;

    public int getAulasAno() {
        return aulasAno;
    }

    public void setAulasAno(int aulasAno) {
        this.aulasAno = aulasAno;
    }

    public int getBimestreAtual() {
        return bimestreAtual;
    }

    public void setBimestreAtual(int bimestreAtual) {
        this.bimestreAtual = bimestreAtual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public boolean isFooter() {
        return footer;
    }

    public void setFooter(boolean footer) {
        this.footer = footer;
    }

    public ArrayList<Aluno> getAlunos(){
        return alunoArrayList;
    }

    public Aluno getAluno(int position) {
        return alunoArrayList.get(position);
    }

    public int arrayAlunoSize() {
        return alunoArrayList.size();
    }

    public void addAluno(Aluno aluno) {
        alunoArrayList.add(aluno);
    }

    public void addBimestre(Bimestre bimestre) {
        bimestreArrayList.add(bimestre);
    }

    public int arrayBimestreSize() {
        return bimestreArrayList.size();
    }

    public Bimestre getBimestre(int position) {
        return bimestreArrayList.get(position);
    }

    public TurmasFrequencia getTurmasFrequencia(int position) {
        return turmasFrequenciaArrayList.get(position);
    }

    public int arrayTurmasFrequenciaSize() {
        return turmasFrequenciaArrayList.size();
    }

    public void addTurmasFrequencia(TurmasFrequencia turmasFrequencia) {
        turmasFrequenciaArrayList.add(turmasFrequencia);
    }

    public Disciplina getDisciplina(int position) {
        return disciplinaArrayList.get(position);
    }

    public int arrayDisciplinaSize() {
        return disciplinaArrayList.size();
    }

    public void addDisciplina(Disciplina disciplina) {
        disciplinaArrayList.add(disciplina);
    }

    public Calendario getCalendario(int position) {
        return calendarioArrayList.get(position);
    }

    public int arrayCalendarioSize() {
        return calendarioArrayList.size();
    }

    public void addCalendario(Calendario calendario) {
        calendarioArrayList.add(calendario);
    }

    public Aula getAula(int position) {
        return aulaArrayList.get(position);
    }

    public int arrayAulaSize() {
        return aulaArrayList.size();
    }

    public void addAula(Aula aula) {
        aulaArrayList.add(aula);
    }

    public Turma() {
    }

    public Turma(int codigoDiretoria, int codigoEscola, String nomeEscola, int codigoTurma, String nomeTurma, String nomeDiretoria, int codigoDisciplina, String nomeDisciplina, int codigoTipoEnsino, String nomeTipoEnsino, int ano){
        this.codigoDiretoria = codigoDiretoria;
        this.codigoEscola = codigoEscola;
        this.nomeEscola = nomeEscola;
        this.codigoTurma = codigoTurma;
        this.nomeTurma = nomeTurma;
        this.nomeDiretoria = nomeDiretoria;
        this.codigoDisciplina = codigoDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.codigoTipoEnsino = codigoTipoEnsino;
        this.nomeTipoEnsino = nomeTipoEnsino;
        this.ano = ano;
    }

    public Turma(int codigoDiretoria, int codigoEscola, String nomeEscola, int codigoTurma, String nomeTurma, String nomeDiretoria, int codigoDisciplina, String nomeDisciplina, int codigoTipoEnsino, String nomeTipoEnsino, int duracao, int codigoSubturma, String descricaoSubTurma, int ano) {
        this.codigoDiretoria = codigoDiretoria;
        this.codigoEscola = codigoEscola;
        this.nomeEscola = nomeEscola;
        this.codigoTurma = codigoTurma;
        this.nomeTurma = nomeTurma;
        this.nomeDiretoria = nomeDiretoria;
        this.codigoDisciplina = codigoDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.codigoTipoEnsino = codigoTipoEnsino;
        this.nomeTipoEnsino = nomeTipoEnsino;
        this.duracao = duracao;
        this.codigoSubturma = codigoSubturma;
        this.descricaoSubTurma = descricaoSubTurma;
        this.id = id;
        this.ano = ano;
    }

    public Turma(int codigoDiretoria, int codigoEscola, String nomeEscola, int codigoTurma, String nomeTurma, String nomeDiretoria, int codigoDisciplina, String nomeDisciplina, int codigoTipoEnsino, String nomeTipoEnsino, int duracao, int codigoSubturma, String descricaoSubTurma, int ano, int bimestreAtual) {
        this.codigoDiretoria = codigoDiretoria;
        this.codigoEscola = codigoEscola;
        this.nomeEscola = nomeEscola;
        this.codigoTurma = codigoTurma;
        this.nomeTurma = nomeTurma;
        this.nomeDiretoria = nomeDiretoria;
        this.codigoDisciplina = codigoDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.codigoTipoEnsino = codigoTipoEnsino;
        this.nomeTipoEnsino = nomeTipoEnsino;
        this.duracao = duracao;
        this.codigoSubturma = codigoSubturma;
        this.descricaoSubTurma = descricaoSubTurma;
        this.id = id;
        this.ano = ano;
        this.bimestreAtual = bimestreAtual;
    }

    public int getCodigoDiretoria() {
        return codigoDiretoria;
    }

    public void setCodigoDiretoria(int codigoDiretoria) {
        this.codigoDiretoria = codigoDiretoria;
    }

    public int getCodigoEscola() {
        return codigoEscola;
    }

    public void setCodigoEscola(int codigoEscola) {
        this.codigoEscola = codigoEscola;
    }

    public String getNomeEscola() {
        return nomeEscola;
    }

    public void setNomeEscola(String nomeEscola) {
        this.nomeEscola = nomeEscola;
    }

    public int getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(int codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public String getNomeDiretoria() {
        return nomeDiretoria;
    }

    public void setNomeDiretoria(String nomeDiretoria) {
        this.nomeDiretoria = nomeDiretoria;
    }

    public int getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(int codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public int getCodigoTipoEnsino() {
        return codigoTipoEnsino;
    }

    public void setCodigoTipoEnsino(int codigoTipoEnsino) {
        this.codigoTipoEnsino = codigoTipoEnsino;
    }

    public String getNomeTipoEnsino() {
        return nomeTipoEnsino;
    }

    public void setNomeTipoEnsino(String nomeTipoEnsino) {
        this.nomeTipoEnsino = nomeTipoEnsino;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getCodigoSubturma() {
        return codigoSubturma;
    }

    public void setCodigoSubturma(int codigoSubturma) {
        this.codigoSubturma = codigoSubturma;
    }

    public String getDescricaoSubTurma() {
        return descricaoSubTurma;
    }

    public void setDescricaoSubTurma(String descricaoSubTurma) {
        this.descricaoSubTurma = descricaoSubTurma;
    }

    public int getAulasBimestre() {
        return aulasBimestre;
    }

    public void setAulasBimestre(int aulasBimestre) {
        this.aulasBimestre = aulasBimestre;
    }

    public ArrayList<TurmasFrequencia> getTurmasFrequenciaArrayList() {
        return turmasFrequenciaArrayList;
    }

    public void setTurmasFrequenciaArrayList(ArrayList<TurmasFrequencia> turmasFrequenciaArrayList) {
        this.turmasFrequenciaArrayList = turmasFrequenciaArrayList;
    }

    public String getHorarioAula() {
        return horarioAula;
    }

    public void setHorarioAula(String horarioAula) {
        this.horarioAula = horarioAula;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
}
