package controler;

public class Especie {
    private String nome;
    private String comoCapturar;
    private String comoCriar;
    private String sobre;
    private int id;
    private String emailUser;
    public Especie(){

    }
    public Especie(String nome,String comoCapturar, String comoCriar, String sobre, String emailUser) {
        this.nome = nome;
        this.comoCapturar = comoCapturar;
        this.comoCriar = comoCriar;
        this.sobre = sobre;
        this.emailUser = emailUser;
    }
    public Especie(String nome,String comoCapturar, String comoCriar, String sobre, String emailUser, int id) {
        this.nome = nome;
        this.comoCapturar = comoCapturar;
        this.comoCriar = comoCriar;
        this.sobre = sobre;
        this.emailUser = emailUser;
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getComoCapturar() {
        return comoCapturar;
    }
    public void setComoCapturar(String comoCapturar) {
        this.comoCapturar = comoCapturar;
    }
    public String getComoCriar() {
        return comoCriar;
    }
    public void setComoCriar(String comoCriar) {
        this.comoCriar = comoCriar;
    }
    public String getSobre() {
        return sobre;
    }
    public void setSobre(String sobre) {
        this.sobre = sobre;
    }
    public int getId() {
        return id;
    }
    public String getEmailUser() {
        return emailUser;
    }
    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }
}
