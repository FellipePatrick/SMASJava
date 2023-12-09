package module;
import controler.Especie;
import controler.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EspecieDao {
    private Conexao connection;
    private final String selectAll = "SELECT * FROM\"especie\"";
    private final String selectId = "SELECT * FROM \"especie\" WHERE id = ?";
    private final String selectName = "SELECT * FROM \"especie\" WHERE nome = ?";

    private final String insert = "INSERT INTO \"especie\" (nome, comocriar, comocapturar, sobre, emailuser) values (?,?,?,?,?) ";
    private final String delete = "DELETE FROM \"especie\" WHERE id = ?";
    private final String update = "UPDATE \"especie\" SET nome = ?, comocriar = ?, comocapturar = ?, sobre = ?, emailuser = ? WHERE id = ?";
    public EspecieDao(){
        this.connection = new Conexao("jdbc:postgresql://localhost:5432/BDSMAS", "postgres", "123");
    }
    public void update(int id, Especie especie){
        try{
            this.connection.conectar();
            PreparedStatement instrucao = connection.getConexao().prepareStatement(this.update);
            instrucao.setString(1, especie.getNome());
            instrucao.setString(2, especie.getComoCriar());
            instrucao.setString(3, especie.getComoCapturar());
            instrucao.setString(4, especie.getSobre());
            instrucao.setString(5, especie.getEmailUser());
            instrucao.setInt(6, id);
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
    public void insert(Especie especie){
        try{
            connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.insert);
            instrucao.setString(1, especie.getNome());
            instrucao.setString(2, especie.getComoCriar());
            instrucao.setString(3, especie.getComoCapturar());
            instrucao.setString(4, especie.getSobre());
            instrucao.setString(5, especie.getEmailUser());
            instrucao.execute();
            connection.desconectar();
        }catch(Exception e){
            System.out.println("Erro na inclusão: " + e.getMessage());
        }
    }
    public ArrayList<Especie> selectAll() {
        ArrayList<Especie> especies = new ArrayList<>();
        Especie especie;
        try {
            this.connection.conectar();
            Statement instrucao = this.connection.getConexao().createStatement();
            ResultSet rs = instrucao.executeQuery(this.selectAll);
            while (rs.next()) {
                especie = new Especie(rs.getString("nome"), rs.getString("comocapturar"),rs.getString("comocriar"),rs.getString("sobre"),rs.getString("emailuser"), rs.getInt("id"));
                especies.add(especie);
            }
            this.connection.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return especies;
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
    public Especie selectName(String nome){
        Especie especie = null;
        try{
            this.connection.conectar();
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.selectName);
            instrucao.setString(1, nome);
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

