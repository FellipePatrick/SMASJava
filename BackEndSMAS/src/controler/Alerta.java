package controler;

public class Alerta {
    private String data;
    private String descricao;
    private int id;
    private int idEspecie;
    private String emailUsuario;
    public Alerta(){};
    public Alerta(String descricao,int idEspecie, String emailUsuario) {
        this.descricao = descricao;
        this.idEspecie = idEspecie;
        this.emailUsuario = emailUsuario;
    }
    public Alerta(int id, String data, String descricao,int idEspecie, String emailUsuario) {
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
