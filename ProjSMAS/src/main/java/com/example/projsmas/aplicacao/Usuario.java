package com.example.projsmas.aplicacao;


public class Usuario {
    private String email;
    private String nome;
    private String senha;
    private int idMunicipio;
    private int funcao;
    public  Usuario(){

    }
    public Usuario(String email, String nome, String senha, int idMunicipio) {
        this.email = email;
        this.idMunicipio = idMunicipio;
        this.nome = nome;
        this.senha = senha;
    }
    public Usuario(String email, String nome, String senha, int idMunicipio, int funcao) {
        this.email = email;
        this.idMunicipio = idMunicipio;
        this.nome = nome;
        this.funcao = funcao;
        this.senha = senha;
    }
    public int getFuncao() {
        return funcao;
    }
    public void setFuncao(int funcao) {
        this.funcao = funcao;
    }
    public int getIdMunicipio() {
        return idMunicipio;
    }
    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}