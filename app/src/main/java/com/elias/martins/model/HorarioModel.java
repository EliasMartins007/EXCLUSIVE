package com.elias.martins.model;

import java.sql.Date;

//classe adicionado por elias 22/07/2019
public class HorarioModel {

    //teste data 22/08/2019
    private Date data;

    private int id_horario;
    //    date hora_atendimento;//no do joao data e hora estão text no banco e string no codigo !!09/07/2019
    private String status;//definir se esta ocupado ou livre
    private float valor;

    private String codigo;

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

    private String descricao;

    //retorna a descrição no componente spinner    sem esse metodo fica retornando o pacote do app no spinner  23/07/2019
    @Override
    public String toString() {

        return this.descricao;
    }



    //teste data 22/08/2019

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }




    public HorarioModel(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

}
