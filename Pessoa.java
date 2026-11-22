package org.example.user;

public abstract class Pessoa {
    private String nome, cpf;
    private String email;
    private int telefone;

    public Pessoa(String nome, String cpf, String email,int telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }

    public Pessoa(String nome, String cpf, int telefone, String email) {

    }

    public abstract String getPapel();

    @Override
    public String toString() {
        return getPapel() + ": " + nome + " (CPF " + cpf + ")";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

}
