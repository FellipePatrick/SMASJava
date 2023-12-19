package com.example.projsmas.persistencia;

import com.example.projsmas.aplicacao.Especie;
import com.example.projsmas.aplicacao.MunicipioEspecie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MunicipioEspecieDao {
    private Conexao connection;
    private final String selectAll = "SELECT * FROM\"municipioespecie\"";
    private final String selectId = "SELECT * FROM \"municipioespecie\" WHERE id = ?";
    private final String selectIdEspecie = "SELECT * FROM \"municipioespecie\" WHERE idespecie = ?";
    private final String selectIdMunicipio = "SELECT * FROM \"municipioespecie\" WHERE idmunicipio = ?";
    private String selectIdAlerta = "SELECT * FROM \"municipioespecie\" WHERE idmunicipio = ?";
    private final String insert = "INSERT INTO \"municipioespecie\" (idespecie, idmunicipio, idalerta) values (?,?,?) ";
    private final String delete = "DELETE FROM \"municipioespecie\" WHERE id = ?";
    private final String deleteIdEspecie = "DELETE FROM \"municipioespecie\" WHERE idespecie = ?";
    private final String deleteIdAlerta = "DELETE FROM \"municipioespecie\" WHERE idalerta = ?";
    private String deleteIdMunicipio = "DELETE FROM \"municipioespecie\" WHERE idmunicipio = ?";
    public MunicipioEspecieDao() {
        this.connection = new Conexao("jdbc:postgresql://localhost:5432/BDSMAS", "postgres", "123");

    }
    public void deleteIdAlerta(int id){
        try{
            this.connection.conectar();
            PreparedStatement instrucao = connection.getConexao().prepareStatement(this.deleteIdAlerta);
            instrucao.setInt(1,id);
            instrucao.execute();
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro na exclusão: " + e.getMessage());
        }
    }

    public void deleteIdEspecie(int idespecie){
        try{
            this.connection.conectar();
            PreparedStatement instrucao = connection.getConexao().prepareStatement(this.deleteIdEspecie);
            instrucao.setInt(1,idespecie);
            instrucao.execute();
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro na exclusão: " + e.getMessage());
        }
    }

    public void deleteIdMunicipio(int idmunicipio){
        try{
            this.connection.conectar();
            PreparedStatement instrucao = connection.getConexao().prepareStatement(this.deleteIdMunicipio);
            instrucao.setInt(1,idmunicipio);
            instrucao.execute();
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro na exclusão: " + e.getMessage());
        }
    }


    public void insert(MunicipioEspecie m){
        try{
            connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.insert);
            instrucao.setInt(1, m.getIdEspecie());
            instrucao.setInt(2, m.getIdMunicipio());
            instrucao.setInt(3, m.getIdAlerta());
            instrucao.execute();
            connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro na inclusão: " + e.getMessage());
        }
    }

    public ArrayList<MunicipioEspecie> selectIdAlerta(int idalerta) {
        ArrayList<MunicipioEspecie> list = new ArrayList<>();
        MunicipioEspecie m;
        try {
            this.connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.selectIdAlerta);
            instrucao.setInt(1,idalerta);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
                m = new MunicipioEspecie( rs.getInt("idmunicipio"), rs.getInt("idespecie"), rs.getInt("idalerta") , rs.getInt("id"));
                list.add(m);
            }
            this.connection.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return list;
    }
    public ArrayList<MunicipioEspecie> selectAll() {
        ArrayList<MunicipioEspecie> list = new ArrayList<>();
        MunicipioEspecie m;
        try {
            this.connection.conectar();
            Statement instrucao = this.connection.getConexao().createStatement();
            ResultSet rs = instrucao.executeQuery(this.selectAll);
            while (rs.next()) {
                m = new MunicipioEspecie(rs.getInt("idespecie"), rs.getInt("idmunicipio") ,rs.getInt("id"));
                list.add(m);
            }
            this.connection.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return list;
    }
    public ArrayList<Integer> relatorioMunicioTemEspecie(int idMunicipio) {
        ArrayList<Integer> list = new ArrayList<>();
        MunicipioEspecie m;
        try {
            this.connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.selectIdMunicipio);
            instrucao.setInt(1, idMunicipio);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
                m = new MunicipioEspecie();
                m.setIdEspecie(rs.getInt("idespecie"));
                list.add(m.getIdEspecie());
            }
            this.connection.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return list;
    }
    public ArrayList<Integer> relatorioEspecieTaEmMunicipio(int idEspecie) {
        ArrayList<Integer> list = new ArrayList<>();
        MunicipioEspecie m;
        try {
            this.connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.selectIdEspecie);
            instrucao.setInt(1, idEspecie);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
                m = new MunicipioEspecie();
                m.setIdMunicipio(rs.getInt("idMunicipio"));
                list.add(m.getIdMunicipio());
            }
            this.connection.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return list;
    }
    public Especie selectId(int id){
        Especie especie = null;
        try{
            this.connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.selectId);
            instrucao.setInt(1, id);
            ResultSet rs = instrucao.executeQuery();
            if(rs.next())
                especie = new Especie(rs.getString("nome"), rs.getString("comocapturar"),rs.getString("comocriar"),rs.getString("sobre"),rs.getString("emailuser"), rs.getInt("id"));
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro no relatório por id: " + e.getMessage());
        }
        return especie;
    }
}
