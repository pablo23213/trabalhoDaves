package org.example.user;
import java.time.LocalDate;

public class DoadorMulher extends Doador{

    public DoadorMulher (String nome, String cpf, int telefone, String email,
                        LocalDate dataNascimento, double peso,
                        TipoSanguineo tipoSanguineo, FatorRh fatorRh) {
        super(nome, cpf, email, telefone, dataNascimento, peso, tipoSanguineo, fatorRh);
    }

    @Override
    public char getSexo() {
        return 'F';
    }
    @Override
    public int getIntervaloDias(){
        return 90;
    }
    @Override
    public int getLimiteAnual(){
        return 3;
    }

}
