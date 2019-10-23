package com.elias.martins.model;
//classe adicionada por elias não faz parte do projeto inicial 10/07/2019
public class Endereco {
    private String bairro;
    private String cidade;
    private String logradouro;


    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Endereco(String bairro, String cidade, String logradouro) {

        this.bairro = bairro;
        this.cidade = cidade;
        this.logradouro = logradouro;

    }

}
