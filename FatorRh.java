package org.example.user;

public enum FatorRh {
    POSITIVO('+'), NEGATIVO('-');

    private char simbolo;

    FatorRh(char simbolo) {
        this.simbolo = simbolo;
    }

    public char getSimbolo() { return simbolo; }
}
