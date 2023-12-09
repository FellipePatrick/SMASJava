package controler;

public class Municipio {
    private String nome;
    private String uf;
    private int id;
    public Municipio(){

    }
    public Municipio(String nome, String uf) {
        this.nome = nome;
        this.uf = uf;
    }
    public Municipio(int id, String nome, String uf) {
        this.nome = nome;
        this.id = id;
        this.uf = uf;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }
    public int getId() {
        return id;
    }
}
