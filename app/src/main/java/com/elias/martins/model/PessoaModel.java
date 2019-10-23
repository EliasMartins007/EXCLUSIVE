package com.elias.martins.model;

public class PessoaModel {
    private Integer codigo;
    private String nome;
    private String endereco;

    //adicionei esses campos no dia 27/06/2019
    private String email;
    private String telefone;
    private String servico;

    private String sexo;
    private String dataNascimento;//String  voltei  de int para String 09/07/2019
    private String estadoCivil;
    private byte registroAtivo;




    //teste campos expecificos do serviço

    private String gramasCabelo;
    private String cmCabelo;
    private String manutencoesCabelo;
    private String observacoesCabelo;

    //adicionar data de aniversario aki
    //exemplo  https://respostas.guj.com.br/36605-calculando-idade---java

    public Integer getCodigo() {

        return codigo;
    }

    public void setCodigo(Integer codigo) {

        this.codigo = codigo;
    }

    public String getNome() {

        return nome;
    }


    public void setNome(String nome) {

        this.nome = nome;
    }

    public String getEndereco() {

        return endereco;
    }

    public void setEndereco(String endereco) {

        this.endereco = endereco;
    }

    public String getSexo() {

        return sexo;
    }

    public void setSexo(String sexo) {

        this.sexo = sexo;
    }

    public String getDataNascimento() {//alterei tipo de string para integer e depois para int 09/07/2019   voltei

        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {//alterei tipo de string para integer e depois para int 09/07/2019 voltei

        this.dataNascimento = dataNascimento;
    }

    public String getEstadoCivil() {

        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {

        this.estadoCivil = estadoCivil;
    }

    public byte getRegistroAtivo() {

        return registroAtivo;
    }

    public void setRegistroAtivo(byte registroAtivo) {

        this.registroAtivo = registroAtivo;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getServico() {
        return servico;
    }

    private String tipoCabelo;

    public String getTipoCabelo() {
        return tipoCabelo;
    }

    public void setTipoCabelo(String tipoCabelo) {
        this.tipoCabelo = tipoCabelo;
    }

    public String getGramasCabelo() {
        return gramasCabelo;
    }

    public void setGramasCabelo(String gramasCabelo) {
        this.gramasCabelo = gramasCabelo;
    }

    public String getCmCabelo() {
        return cmCabelo;
    }

    public void setCmCabelo(String cmCabelo) {
        this.cmCabelo = cmCabelo;
    }

    public String getManutencoesCabelo() {
        return manutencoesCabelo;
    }

    public void setManutencoesCabelo(String manutencoesCabelo) {
        this.manutencoesCabelo = manutencoesCabelo;
    }

    public String getObservacoesCabelo() {
        return observacoesCabelo;
    }

    public void setObservacoesCabelo(String observacoesCabelo) {
        this.observacoesCabelo = observacoesCabelo;
    }


    public void setServico(String servico) {
        this.servico = servico;
    }



    //retorna a descrção na tela do componente Spinner Serviço     sem esse metodo fica retornando o pacote do app no spinner  23/07/2019
    @Override
    public String toString() {
        return this.nome;//seria descrição aki ainda tenho de confirmar 24/07/2019 teste para spinner clientesCadastradasHoraio    OK validado
    }


}
