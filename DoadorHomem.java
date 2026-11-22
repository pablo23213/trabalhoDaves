package org.example.user;
import java.time.LocalDate;

public class DoadorHomem extends Doador{

    public DoadorHomem (String nome, String cpf, int telefone, String email,
    LocalDate dataNascimento, double peso,
    TipoSanguineo tipoSanguineo, FatorRh fatorRh) {
        super(nome, cpf, email, telefone, dataNascimento, peso, tipoSanguineo, fatorRh);
    }

    @Override
    public char getSexo() {
        return 'M';
    }
        @Override
        public int getIntervaloDias(){
            return 60;
    }
        @Override
        public int getLimiteAnual(){
            return 4;
    }
    }


