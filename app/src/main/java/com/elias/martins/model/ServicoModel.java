package com.elias.martins.model;
//classe adicionada por elias
public class ServicoModel {
    private String servicoCodigo;
    private String servicoDecricao;
    private float servicoValor;

    public String getServicoCodigo() {
        return servicoCodigo;
    }

    public void setServicoCodigo(String servicoCodigo) {
        this.servicoCodigo = servicoCodigo;
    }

    public String getServicoDecricao() {
        return servicoDecricao;
    }

    public void setServicoDecricao(String servicoDecricao) {
        this.servicoDecricao = servicoDecricao;
    }

    public float getServicoValor() {
        return servicoValor;
    }

    public void setServicoValor(float servicoValor) {
        this.servicoValor = servicoValor;
    }


    //retorna a descrção na tela do componente Spinner Serviço     sem esse metodo fica retornando o pacote do app no spinner  23/07/2019
    @Override
    public String toString() {
        return this.servicoDecricao;
    }

    public ServicoModel() {
    }

    public ServicoModel(String servicoCodigo, String servicoDecricao) {//, float servicoValor  // tenho de colocar o valor aki ? 05/07/2019

        this.servicoCodigo = servicoCodigo;
        this.servicoDecricao = servicoDecricao;
        //   this.servicoValor = servicoValor;//teste desse campo a mais 05/07/2019
    }

}
