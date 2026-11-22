package org.example.user;
import java.time.LocalDateTime;

public abstract class Funcionario extends Pessoa {

    private String formulario;

    public Funcionario(String nome, String cpf, String email,int telefone, String formulario) {
        super(nome, cpf, email, telefone);
        this.formulario = formulario;
    }

    public String getFormulario() {
        return formulario;
    }

    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }
}