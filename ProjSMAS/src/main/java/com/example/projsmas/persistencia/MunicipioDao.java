package com.example.projsmas.persistencia;


import com.example.projsmas.aplicacao.Municipio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
public class MunicipioDao {
    private Conexao connection;
    private final String selectAll = "SELECT * FROM\"municipio\"";
    private final String selectNameAndUf= "SELECT * FROM \"municipio\" WHERE nome = ? and uf = ?";
    private final String insert = "INSERT INTO \"municipio\" (nome, uf) values (?,?) ";
    private final String delete = "DELETE FROM \"municipio\" WHERE id = ?";
    private final String update = "UPDATE \"municipio\" SET id = ?, nome = ? , uf = ? WHERE id = ?";
    public MunicipioDao(){
        this.connection = new Conexao("jdbc:postgresql://localhost:5432/BDSMAS", "postgres", "123");
    }
    public void delete(int id){
        try{
            this.connection.conectar();
            PreparedStatement instrucao = connection.getConexao().prepareStatement(this.delete);
            instrucao.setInt(1,id);
            instrucao.execute();
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro na exclusão: " + e.getMessage());
        }
    }
    public void update(int id, Municipio municipio){
        try{
            this.connection.conectar();
            PreparedStatement instrucao = connection.getConexao().prepareStatement(this.update);
            instrucao.setInt(1, municipio.getId());
            instrucao.setString(2, municipio.getNome());
            instrucao.setString(3, municipio.getUf());
            instrucao.setInt(4,id);
            instrucao.execute();
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro na atualização: " + e.getMessage());
        }
    }
    public void insert(Municipio municipio){
        try{
            connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.insert);
            instrucao.setString(1,municipio.getNome());
            instrucao.setString(2, municipio.getUf());
            instrucao.execute();
            connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro na inclusão: " + e.getMessage());
        }
    }
    public ArrayList<Municipio> selectAll() {
        ArrayList<Municipio> municipios = new ArrayList<>();
        Municipio municipio;
        try {
            this.connection.conectar();
            Statement instrucao = this.connection.getConexao().createStatement();
            ResultSet rs = instrucao.executeQuery(this.selectAll);
            while (rs.next()) {
                municipio = new Municipio(rs.getInt("id"), rs.getString("nome"), rs.getString("uf"));
                municipios.add(municipio);
            }
            this.connection.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return municipios;
    }
    public Municipio selectNameAndUf(String name, String uf){
        Municipio municipio = null;
        try{
            this.connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.selectNameAndUf);
            instrucao.setString(1, name);
            instrucao.setString(2, uf);
            ResultSet rs = instrucao.executeQuery();
            if(rs.next())
                municipio = new Municipio(rs.getInt("id"),rs.getString("nome"), rs.getString("uf"));
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro no relatório por Nome e Uf: " + e.getMessage());
        }
        return municipio;
    }
}
