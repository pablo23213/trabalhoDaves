package org.example.user;

public class EstoqueDeSangue {

    private int aPositivo = 0;
    private int aNegativo = 0;
    private int bPositivo = 0;
    private int bNegativo = 0;
    private int abPositivo = 0;
    private int abNegativo = 0;
    private int oPositivo = 0;
    private int oNegativo = 0;

    public void adicionar(TipoSanguineo tipo, FatorRh rh, int quantidade) {
        if (tipo == TipoSanguineo.A && rh == FatorRh.POSITIVO) {
            aPositivo = aPositivo + quantidade;
        } else if (tipo == TipoSanguineo.A && rh == FatorRh.NEGATIVO) {
            aNegativo = aNegativo + quantidade;
        } else if (tipo == TipoSanguineo.B && rh == FatorRh.POSITIVO) {
            bPositivo = bPositivo + quantidade;
        } else if (tipo == TipoSanguineo.B && rh == FatorRh.NEGATIVO) {
            bNegativo = bNegativo + quantidade;
        } else if (tipo == TipoSanguineo.AB && rh == FatorRh.POSITIVO) {
            abPositivo = abPositivo + quantidade;
        } else if (tipo == TipoSanguineo.AB && rh == FatorRh.NEGATIVO) {
            abNegativo = abNegativo + quantidade;
        } else if (tipo == TipoSanguineo.O && rh == FatorRh.POSITIVO) {
            oPositivo = oPositivo + quantidade;
        } else {
            oNegativo = oNegativo + quantidade;
        }
    }

    public int getUnidades(TipoSanguineo tipo, FatorRh rh) {
        if (tipo == TipoSanguineo.A && rh == FatorRh.POSITIVO) {
            return aPositivo;
        } else if (tipo == TipoSanguineo.A && rh == FatorRh.NEGATIVO) {
            return aNegativo;
        } else if (tipo == TipoSanguineo.B && rh == FatorRh.POSITIVO) {
            return bPositivo;
        } else if (tipo == TipoSanguineo.B && rh == FatorRh.NEGATIVO) {
            return bNegativo;
        } else if (tipo == TipoSanguineo.AB && rh == FatorRh.POSITIVO) {
            return abPositivo;
        } else if (tipo == TipoSanguineo.AB && rh == FatorRh.NEGATIVO) {
            return abNegativo;
        } else if (tipo == TipoSanguineo.O && rh == FatorRh.POSITIVO) {
            return oPositivo;
        } else {
            return oNegativo;
        }
    }

}
