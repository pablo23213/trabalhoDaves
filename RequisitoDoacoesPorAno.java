package org.example.user;

import java.time.LocalDate;

public class RequisitoDoacoesPorAno implements RequisitosDoacao{

    @Override
    public String verificacao(Doador doador, LocalDate data) {
        LocalDate liberacao = doador.getDataLiberacao();
        if (liberacao != null && data.isBefore(liberacao)) {
            return "intervalo de " + doador.getIntervaloDias()
                    + " dias não cumprido (liberado em " + liberacao + ")";
        }
        return null;
    }
}