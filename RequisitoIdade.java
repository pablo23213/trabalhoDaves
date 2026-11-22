package org.example.user;
import java.time.LocalDate;

public class RequisitoIdade implements RequisitosDoacao{

    @Override
        public String verificacao(Doador doador, LocalDate data) {
        int idade = doador.getIdade(data);
            if(idade < 16 || idade > 69){
                return "idade" + idade + "idade nao permitida para doar asngue";
        }
            return null;
    }
}