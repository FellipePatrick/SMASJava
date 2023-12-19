package com.example.projsmas.persistencia;



import com.example.projsmas.aplicacao.Municipio;
import com.example.projsmas.aplicacao.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioDao {
    private Conexao connection;
    private final String selectAll = "SELECT * FROM\"usuario\"";
    private final String selectEmail = "SELECT * FROM \"usuario\" WHERE email = ?";
    private final String selectMunicipio = "SELECT * FROM \"usuario\" WHERE idmunicipio = ?";
    private final String insert = "INSERT INTO \"usuario\" (nome, email, senha, idMunicipio) values (?,?,?,?) ";
    private final String delete = "DELETE FROM \"usuario\" WHERE email = ?";
    private final String deleteMunicipio = "DELETE FROM \"usuario\" WHERE idmunicipio = ?";
    private final String update = "UPDATE \"usuario\" SET email = ?, nome = ? , senha = ?, idMunicipio = ?, funcao = ? WHERE email = ?";
    public UsuarioDao(){
        this.connection = new Conexao("jdbc:postgresql://localhost:5432/BDSMAS", "postgres", "123");
    }
    public void delete(String email){
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

    public void deleteIdMunicipio(int idMunicipio){
        try{
            this.connection.conectar();
            PreparedStatement instrucao = connection.getConexao().prepareStatement(this.deleteMunicipio);
            instrucao.setInt(1,idMunicipio);
            instrucao.execute();
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro na exclusão: " + e.getMessage());
        }
    }
    public void insert(Usuario user){
        try{
            connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.insert);
            instrucao.setString(1, user.getNome());
            instrucao.setString(2,user.getEmail());
            instrucao.setString(3,user.getSenha());
            instrucao.setInt(4,user.getIdMunicipio());
            instrucao.execute();
            connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro na inclusão: " + e.getMessage());
        }
    }

    public ArrayList<String> relatorioEmails() {
        ArrayList<String> usuarios = new ArrayList<>();
        Usuario usuario;
        try {
            this.connection.conectar();
            Statement instrucao = this.connection.getConexao().createStatement();
            ResultSet rs = instrucao.executeQuery(this.selectAll);
            while (rs.next()) {
                usuario = new Usuario();
                usuario.setEmail(rs.getString("email"));
                usuarios.add(usuario.getEmail());
            }
            this.connection.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return usuarios;
    }
    public void update(String email, Usuario user){
        try{
            this.connection.conectar();
            PreparedStatement instrucao = connection.getConexao().prepareStatement(this.update);
            instrucao.setString(1,user.getEmail());
            instrucao.setString(2,user.getNome());
            instrucao.setString(3,user.getSenha());
            instrucao.setInt(4,user.getIdMunicipio());
            instrucao.setInt(5, user.getFuncao());
            instrucao.setString(6,email);
            instrucao.execute();
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro na atualização: " + e.getMessage());
        }
    }
    public Usuario selectEmail(String email){
        Usuario user = null;
        try{
            this.connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.selectEmail);
            instrucao.setString(1, email);
            ResultSet rs = instrucao.executeQuery();
            if(rs.next())
                user = new Usuario(rs.getString("email"), rs.getString("nome"), rs.getString("senha"), rs.getInt("idMunicipio"),rs.getInt("funcao"));
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro no relatório por email: " + e.getMessage());
        }
        return user;
    }

    public ArrayList<Usuario> selectMunicipio(int idmunicipio){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Usuario user = null;
        try{
            this.connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.selectMunicipio);
            instrucao.setInt(1, idmunicipio);
            ResultSet rs = instrucao.executeQuery();
            while(rs.next()) {
                user = new Usuario(rs.getString("email"), rs.getString("nome"), rs.getString("senha"), rs.getInt("idMunicipio"), rs.getInt("funcao"));
                usuarios.add(user);
            }
            this.connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro no relatório por email: " + e.getMessage());
        }
        return usuarios;
    }
}
