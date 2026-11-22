package org.example.user;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Doacao {
    private Doador doador;
    private LocalDate dataAgendada;
    private SituacaoDoacao situacao;
    private ProfissionalDeSaude triador;
    private ProfissionalDeSaude responsavelPelaColeta;
    private LocalDateTime dataHoraConclusao;
    private String idBolsa;

    public Doacao (Doador doador, LocalDate dataAgendada){
        this.doador = doador;
        this.dataAgendada = dataAgendada;
        this.situacao = situacao;
    }

    public boolean triar(ProfissionalDeSaude profissional, boolean apto, String motivo) {
        if (situacao != SituacaoDoacao.AGENDADA) return false;
        this.triador = profissional;
        if (apto) {
            this.situacao = SituacaoDoacao.TRIADA;
        } else {
            this.situacao = SituacaoDoacao.INAPTA_TEMPORARIA;
        }
        return true;
    }
    public boolean concluir(ProfissionalDeSaude responsavel, LocalDateTime quando, String idBolsa) {
        if (situacao != SituacaoDoacao.TRIADA) return false;
        this.responsavelPelaColeta = responsavel;
        this.dataHoraConclusao = quando;
        this.idBolsa = idBolsa;
        this.situacao = SituacaoDoacao.CONCLUIDA;
        return true;
    }

    public boolean estaAberta() {
        return situacao == SituacaoDoacao.AGENDADA || situacao == SituacaoDoacao.TRIADA;
    }

    public boolean cancelar() {
        if (estaAberta()) {
            this.situacao = SituacaoDoacao.CANCELADA;
            return true;
        }
        return false;
    }

    public boolean estaConcluida() { return situacao == SituacaoDoacao.CONCLUIDA; }


    public Doador getDoador() {
        return doador;
    }

    public void setDoador(Doador doador) {
        this.doador = doador;
    }

    public LocalDate getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(LocalDate dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public SituacaoDoacao getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoDoacao situacao) {
        this.situacao = situacao;
    }

    public ProfissionalDeSaude getTriador() {
        return triador;
    }

    public void setTriador(ProfissionalDeSaude triador) {
        this.triador = triador;
    }

    public ProfissionalDeSaude getResponsavelPelaColeta() {
        return responsavelPelaColeta;
    }

    public void setResponsavelPelaColeta(ProfissionalDeSaude responsavelPelaColeta) {
        this.responsavelPelaColeta = responsavelPelaColeta;
    }

    public LocalDateTime getDataHoraConclusao() {
        return dataHoraConclusao;
    }

    public void setDataHoraConclusao(LocalDateTime dataHoraConclusao) {
        this.dataHoraConclusao = dataHoraConclusao;
    }

    public String getIdBolsa() {
        return idBolsa;
    }

    public void setIdBolsa(String idBolsa) {
        this.idBolsa = idBolsa;
    }

    public LocalDate getDataConclusao() {
        if (dataHoraConclusao == null) return null;
        return dataHoraConclusao.toLocalDate();
    }

    public String getResultado() {
        if (situacao == situacao.CONCLUIDA) {
            return "Coleta realizada (1 bolsa)";
        }
        if (situacao == situacao.INAPTA_TEMPORARIA) {
            return "Inapto: ";
        }
        if (situacao == situacao.TRIADA) {
            return "Apto, aguardando coleta";
        }
        if (situacao == situacao.CANCELADA) {
            return "Cancelada";
        }
        return "Aguardando triagem";
    }

    @Override
    public String toString() {
        String s = dataAgendada + " || " + situacao.getDescricao();
        if (estaConcluida()) {
            s += " || bolsa " + idBolsa + " || " + dataHoraConclusao + " || resp.: " + responsavelPelaColeta.getNome();
        }
        return s + " || " + getResultado();
    }
}
