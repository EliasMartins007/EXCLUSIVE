package com.elias.martins.model;

public class EstadoCivilModel {

    private String codigo;
    private String descricao;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    //retorna a descrição no componente spinner   sem esse metodo fica retornando o pacote do app no spinner  23/07/2019
    @Override
    public String toString() {

        return this.descricao;
    }

    public EstadoCivilModel() {

    }

    public EstadoCivilModel(String codigo, String descricao) {

        this.codigo = codigo;
        this.descricao = descricao;

    }

}
