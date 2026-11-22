package org.example.user;
import java.time.LocalDate;

public abstract class RequisitoPeso implements RequisitosDoacao {

    public String verificacao(Doador doador, LocalDate data) {
        if (doador.getPeso() < 50) {
            return "peso" + doador.getPeso() + "peso abaixo do minimo";
        }
        return "";
    }
}
