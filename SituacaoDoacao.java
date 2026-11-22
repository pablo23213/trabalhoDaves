package org.example.user;

public enum SituacaoDoacao {
    AGENDADA("Agendada"),
    TRIADA("Triada (apta)"),
    INAPTA_TEMPORARIA("Inapta temporária"),
    CONCLUIDA("Concluída"),
    CANCELADA("Cancelada");

    private String descricao;

    SituacaoDoacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
