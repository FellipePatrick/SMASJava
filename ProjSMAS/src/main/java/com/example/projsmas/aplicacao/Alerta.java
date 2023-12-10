package com.example.projsmas.aplicacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Alerta {
    private String data;
    private String descricao;
    private int id;
    private int idEspecie;
    private String emailUsuario;
    public Alerta(){
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataHoraFormatada = dataHoraAtual.format(formato);
        this.data = dataHoraFormatada;
    };
    public Alerta(String descricao,int idEspecie, String emailUsuario) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataHoraFormatada = dataHoraAtual.format(formato);
        this.data = dataHoraFormatada;
        this.descricao = descricao;
        this.idEspecie = idEspecie;
        this.emailUsuario = emailUsuario;
    }
    public Alerta(int id, String data, String descricao,int idEspecie, String emailUsuario) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataHoraFormatada = dataHoraAtual.format(formato);
        this.data = dataHoraFormatada;
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.idEspecie = idEspecie;
        this.emailUsuario = emailUsuario;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdEspecie() {
        return idEspecie;
    }
    public void setIdEspecie(int idEspecie) {
        this.idEspecie = idEspecie;
    }
    public String getEmailUsuario() {
        return emailUsuario;
    }
    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}