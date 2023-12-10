package controler;

public class MunicipioEspecie {
    private int idMunicipio;
    private int idEspecie;
    private int id;
    private int idAlerta;
    public  MunicipioEspecie(){};
    public MunicipioEspecie(int idMunicipio, int idEspecie, int idAlerta) {
        this.idMunicipio = idMunicipio;
        this.idEspecie = idEspecie;
        this.idAlerta = idAlerta;
    }
    public MunicipioEspecie(int idMunicipio, int idEspecie, int idAlerta, int id) {
        this.id = id;
        this.idAlerta = idAlerta;
        this.idMunicipio = idMunicipio;
        this.idEspecie = idEspecie;

    }

    public int getIdAlerta() {
        return idAlerta;
    }
    public void setIdAlerta(int idAlerta) {
        this.idAlerta = idAlerta;
    }
    public int getId() {
        return id;
    }
    public int getIdMunicipio() {
        return idMunicipio;
    }
    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }
    public int getIdEspecie() {
        return idEspecie;
    }
    public void setIdEspecie(int idEspecie) {
        this.idEspecie = idEspecie;
    }
}
