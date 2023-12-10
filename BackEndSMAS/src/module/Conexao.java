package module;
import java.sql.Connection;
import java.sql.DriverManager;
public class Conexao {
    private String caminho;
    private String usuario;
    private String senha;
    private Connection con;
    public Conexao(String caminho, String usuario, String senha) {
        this.caminho = caminho;
        this.usuario = usuario;
        this.senha = senha;
    }
    public void conectar() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(caminho, usuario, senha);
        }catch(Exception e){
            System.out.println("Erro de conexão: " + e.getMessage());
        }
    }
    public void desconectar() {
        try {
            con.close();
        }catch(Exception e){
            System.out.println("Erro de conexão: " + e.getMessage());
        }
    }
    public Connection getConexao() {
        return con;
    }

}
