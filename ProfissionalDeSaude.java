package org.example.user;

public abstract class ProfissionalDeSaude extends Funcionario{
    private String registroProfissional;

    public ProfissionalDeSaude(String nome, String cpf, int telefone, String email,
                             String matricula, String registroProfissional) {
        super(nome, cpf, email,telefone,  matricula);
        this.registroProfissional = registroProfissional;
    }

    public String getRegistroProfissional() { return registroProfissional; }
}
