package org.example.user;

public class Medico extends ProfissionalDeSaude{

    public Medico(String nome, String cpf, int telefone, String email,
                  String matricula, String crm) {
        super(nome, cpf, telefone, email, matricula, crm);
    }

    @Override
    public String getPapel() { return "Médico(a)"; }
}
