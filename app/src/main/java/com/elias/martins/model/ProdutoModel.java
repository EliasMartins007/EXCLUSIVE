package com.elias.martins.model;
//classe adicionada por elias
public class ProdutoModel {

    private int produtoId;
    private String produtoNome;
    private float produtoValor;
    private int produtoQuantidade;

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public float getProdutoValor() {
        return produtoValor;
    }

    public void setProdutoValor(float produtoValor) {
        this.produtoValor = produtoValor;
    }

    public int getProdutoQuantidade() {
        return produtoQuantidade;
    }

    //retorna a descrção na tela do componente Spinner Serviço     sem esse metodo fica retornando o pacote do app no spinner  23/07/2019
    @Override
    public String toString() {
        return this.produtoNome;//seria descrição aki ainda tenho de confirmar 23/07/2019
    }


    public void setProdutoQuantidade(int produtoQuantidade) {
        this.produtoQuantidade = produtoQuantidade;
    }


}
