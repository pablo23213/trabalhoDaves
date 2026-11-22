package org.example.user;

public class Atendente extends Funcionario{

    private String atuacao;

    public Atendente(String nome, String cpf, int telefone, String email,
                     String formulario, String atuacao) {
        super(nome, cpf, email, telefone, formulario);
        this.atuacao = atuacao;
    }

    public String getAtuacao() {
        return atuacao;
    }

    public void setAtuacao(String atuacao) {
        this.atuacao = atuacao;
    }

    @Override
    public String getPapel(){
        return "Atendenete";
    }
}
