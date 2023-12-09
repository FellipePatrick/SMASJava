package module;

import controler.Alerta;

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
    private final String insert = "INSERT INTO \"alerta\" (data, descricao, emailusuario, idespecie) values (TO_TIMESTAMP(?, 'yyyy-MM-dd HH:mi:ss'),?,?,?) ";
    private final String delete = "DELETE FROM \"alerta\" WHERE id = ?";
    private final String update = "UPDATE \"alerta\" SET data = ?, descricao = ?, emailusuario = ?, idespecie = ? WHERE id = ?";
    public AlertaDao(){
        this.connection = new Conexao("jdbc:postgresql://localhost:5432/BDSMAS", "postgres", "123");
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
    public void insert(Alerta alerta){
        try{
            connection.conectar();
            LocalDateTime dataHoraAtual = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter dataHoraFormatada = DateTimeFormatter.ofPattern(dataHoraAtual.format(formato));
            PreparedStatement instrucao = this.connection.getConexao().prepareStatement(this.insert);
            instrucao.setString(1, String.valueOf(dataHoraFormatada));
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
}
