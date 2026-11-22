package org.example.user;

public class PerfilEstadoCritico {
    private TipoSanguineo tipo;
    private FatorRh rh;
    private int unidades;

    public PerfilEstadoCritico(TipoSanguineo tipo, FatorRh rh, int unidades) {
        this.tipo = tipo;
        this.rh = rh;
        this.unidades = unidades;
    }

    public TipoSanguineo getTipo() { return tipo; }
    public FatorRh getRh() { return rh; }
    public int getUnidades() { return unidades; }

    public String getNome() {
        return tipo.name() + rh.getSimbolo();
    }
}