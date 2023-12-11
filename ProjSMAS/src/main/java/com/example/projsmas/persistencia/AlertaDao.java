package com.example.projsmas.persistencia;



import com.example.projsmas.aplicacao.Alerta;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AlertaDao {
    private Conexao connection;
    private final String selectAll = "SELECT * FROM\"alerta\"";
    private final String selectId = "SELECT * FROM \"alerta\" WHERE id = ?";
    private final String selectData = "SELECT * FROM \"alerta\" WHERE data = ?";
    private final String selectEmail = "SELECT * FROM \"alerta\" WHERE emailusuario = ?";
    private final String selectEspecie = "SELECT * FROM \"alerta\" WHERE idespecie = ?";
    private final String insert = "INSERT INTO \"alerta\" (data, descricao, emailusuario, idespecie) values (?,?,?,?) ";
    private final String delete = "DELETE FROM \"alerta\" WHERE id = ?";
    private final String deleteEmail = "DELETE FROM \"alerta\" WHERE emailusuario = ?";
    private final String deleteIdEspecie = "DELETE FROM \"alerta\" WHERE idespecie = ?";
    private final String update = "UPDATE \"alerta\" SET data = ?, descricao = ?, emailusuario = ?, idespecie = ? WHERE id = ?";
    public AlertaDao(){
        this.connection = new Conexao("jdbc:postgresql://localhost:5432/BDSMAS", "postgres", "1234");
    }
    public void update(int id, Alerta alerta){
        try{
            this.connection.conectar();
            LocalDateTime dataHoraAtual = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dataHoraFormatada = dataHoraAtual.format(formato);
            PreparedStatement instrucao = connection.getConexao().prepareStatement(this.update);
            instrucao.setString(1, dataHoraFormatada);
            instrucao.setString(2, alerta.getDescricao());
            instrucao.setString(3, alerta.getEmailUsuario());
            instrucao.setInt(4,alerta.getIdEspecie());
            instrucao.setInt(5,id);
            instrucao.execute();
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro na atualização: " + e.getMessage());
        }
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

    public void DeleteEmail(String email){
        try{
            this.connection.conectar();
            PreparedStatement instrucao = connection.getConexao().prepareStatement(this.delete);
            instrucao.setString(1,email);
            instrucao.execute();
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro na exclusão: " + e.getMessage());
        }
    }
    public void insert(Alerta alerta){
        try{
            connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.insert);
            instrucao.setString(1, alerta.getData());
            instrucao.setString(2, alerta.getDescricao());
            instrucao.setString(3, alerta.getEmailUsuario());
            instrucao.setInt(4,alerta.getIdEspecie());
            instrucao.execute();
            connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro na inclusão: " + e.getMessage());
        }
    }
    public ArrayList<Alerta> selectAll() {
        ArrayList<Alerta> alertas = new ArrayList<>();
        Alerta alerta;
        try {
            this.connection.conectar();
            Statement instrucao = this.connection.getConexao().createStatement();
            ResultSet rs = instrucao.executeQuery(this.selectAll);
            while (rs.next()) {
                alerta = new Alerta(rs.getInt("id"), rs.getString("data"), rs.getString("descricao"), rs.getInt("idespecie"), rs.getString("emailusuario"));
                alertas.add(alerta);
            }
            this.connection.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return alertas;
    }
    public Alerta selectId(int id){
        Alerta alerta = null;
        try{
            this.connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.selectId);
            instrucao.setInt(1, id);
            ResultSet rs = instrucao.executeQuery();
            if(rs.next())
                alerta = new Alerta(rs.getInt("id"), rs.getString("data"), rs.getString("descricao"), rs.getInt("idespecie"), rs.getString("emailusuario"));
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro no relatório por id: " + e.getMessage());
        }
        return alerta;
    }
    public ArrayList<Alerta> selectEmail(String email){
        ArrayList<Alerta> alertas = new ArrayList<>();
        Alerta alerta;
        try {
            this.connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.selectEmail);
            instrucao.setString(1,email);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
                alerta = new Alerta(rs.getInt("id"), rs.getString("data"), rs.getString("descricao"), rs.getInt("idespecie"), rs.getString("emailusuario"));
                alertas.add(alerta);
            }
            this.connection.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return alertas;
    }

    public ArrayList<Alerta> selectEspecie(int idespecie){
        ArrayList<Alerta> alertas = new ArrayList<>();
        Alerta alerta;
        try {
            this.connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.selectEspecie);
            instrucao.setInt(1,idespecie);
            ResultSet rs = instrucao.executeQuery();
            while (rs.next()) {
                alerta = new Alerta(rs.getInt("id"), rs.getString("data"), rs.getString("descricao"), rs.getInt("idespecie"), rs.getString("emailusuario"));
                alertas.add(alerta);
            }
            this.connection.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return alertas;
    }
    public Alerta selectData(String data){
        Alerta alerta = null;
        try{
            this.connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.selectData);
            instrucao.setString(1,data);
            ResultSet rs = instrucao.executeQuery();
            if(rs.next())
                alerta = new Alerta(rs.getInt("id"), rs.getString("data"), rs.getString("descricao"), rs.getInt("idespecie"), rs.getString("emailusuario"));
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro no relatório por id: " + e.getMessage());
        }
        return alerta;
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
}