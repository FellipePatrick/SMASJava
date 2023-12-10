package module;

import controler.Especie;
import controler.MunicipioEspecie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MunicipioEspecieDao {
    private Conexao connection;
    private final String selectAll = "SELECT * FROM\"municipioespecie\"";
    private final String selectId = "SELECT * FROM \"municipioespecie\" WHERE id = ?";
    private final String insert = "INSERT INTO \"municipioespecie\" (idespecie, idmunicipio, idalerta) values (?,?,?) ";
    private final String delete = "DELETE FROM \"municipioespecie\" WHERE id = ?";
    private final String deleteIdAlerta = "DELETE FROM \"municipioespecie\" WHERE idalerta = ?";
    public MunicipioEspecieDao() {
        this.connection = new Conexao("jdbc:postgresql://localhost:5432/BDSMAS", "postgres", "1234");

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
