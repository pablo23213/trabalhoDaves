package org.example.user;
import java.time.LocalDate;

    public interface RequisitosDoacao {
        String verificacao(Doador doador, LocalDate data);
    }

