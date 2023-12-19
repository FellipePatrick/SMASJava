package com.example.projsmas.visao;

import com.example.projsmas.Main;
import com.example.projsmas.aplicacao.Especie;
import com.example.projsmas.aplicacao.Municipio;
import com.example.projsmas.aplicacao.MunicipioEspecie;
import com.example.projsmas.aplicacao.Usuario;
import com.example.projsmas.persistencia.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FXMLMunicipioController  extends LoginController implements Initializable {
    private Stage stage;
    @FXML
    private Pane cadastrarMunicipio, editarMunicipio;
    @FXML
    private Label warnings;
    @FXML
    TextField nomeMunicipio, nomeMunicipio1, ufMunicipio, ufMunicipio1;
    @FXML
    private ComboBox<String> comboMunicipios;
    @FXML
    private Button btnExcluir, btnSalvar;
    private MunicipioDao m = new MunicipioDao();
    private MunicipioEspecieDao municipioEspecieDao = new MunicipioEspecieDao();
    private ArrayList<MunicipioEspecie> municipioEspecies;
    private ArrayList<Especie> especies = new ArrayList<>();
    private Especie especie;
    private UsuarioDao usuarioDao = new UsuarioDao();
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private AlertaDao alertaDao = new AlertaDao();
    private EspecieDao especieDao = new EspecieDao();
    private ObservableList<String> listaMunicipios = FXCollections.observableArrayList();
    //inicialização da tela
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaMunicipios.addAll(m.relatorioNomes());
        FXCollections.sort(listaMunicipios);
        comboMunicipios.setItems(listaMunicipios);
        warnings.setVisible(false);
    }
    @FXML
    protected void handleBtnCadastrarMunicipio(){
        if(!nomeMunicipio.getText().equals("")){
            Municipio municipio;
            if(m.selectNameAndUf(nomeMunicipio.getText().toUpperCase(), "RN") == null){
                municipio = new Municipio();
                municipio.setNome(nomeMunicipio.getText().toUpperCase());
                municipio.setUf("RN");
                m.insert(municipio);
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#00f731"));
                warnings.setText("Municipio cadastrado!");
                nomeMunicipio.setText("");
                nomeMunicipio.setDisable(true);
                comboMunicipios.getItems().clear();
                listaMunicipios.addAll(m.relatorioNomes());
                FXCollections.sort(listaMunicipios);
                comboMunicipios.setItems(listaMunicipios);
                nomeMunicipio.setDisable(false);
            }else{
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#ff0000"));
                warnings.setText("Municipio ja cadastrado!");
            }
        }else{
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            if(nomeMunicipio.getText().equals("")){
                warnings.setText("O campo municipio precisa ser preenchido!");
            }else{
                warnings.setText("Municipio ja cadastrado!");
            }
        }
    }
    @FXML
    protected void editarMunicipios(){
        cadastrarMunicipio.setVisible(false);
        editarMunicipio.setVisible(true);
        nomeMunicipio1.setText("");
        warnings.setVisible(false);
        if(!comboMunicipios.getItems().contains("Municipio")){
            comboMunicipios.getItems().add("Municipio");
        }
        comboMunicipios.setValue("Municipio");
        nomeMunicipio.setPromptText("Nome");
        nomeMunicipio1.setDisable(true);
        btnSalvar.setDisable(true);
        btnExcluir.setDisable(true);
    }
    @FXML
    protected void handleBtnSalvarMunicipio(){
        Municipio municipio = m.selectNameAndUf(comboMunicipios.getValue(), "RN");
        if(!nomeMunicipio1.getText().toUpperCase().equals(municipio.getNome().toUpperCase()) && !nomeMunicipio1.getText().equals("")){
            if(m.selectNameAndUf(nomeMunicipio1.getText().toUpperCase(),"RN").getNome() == null) {
                municipio.setNome(nomeMunicipio1.getText().toUpperCase());
                m.update(municipio.getId(), municipio);
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#00f731"));
                warnings.setText("Municipio atualizado!");
                comboMunicipios.getItems().clear();
                listaMunicipios.addAll(m.relatorioNomes());
                FXCollections.sort(listaMunicipios);
                comboMunicipios.setItems(listaMunicipios);
                nomeMunicipio.setText("");
                nomeMunicipio1.setDisable(true);
                btnSalvar.setDisable(true);
                btnExcluir.setDisable(true);
                if(!comboMunicipios.getItems().contains("Municipio")){
                    comboMunicipios.getItems().add("Municipio");
                }
                comboMunicipios.setValue("Municipio");
            }else{
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#ff0000"));
                warnings.setText("Estado já cadastrado!");
            }
        }else{
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            warnings.setText("Realize alguma mudançã no Municipio!");
        }
    }
    @FXML
    protected void voltarPerfil(ActionEvent event) throws IOException {
        this.atualizaFrame("FXMLPerfil.fxml", event);
    }
    @FXML
    protected void voltarCadastro(){
        cadastrarMunicipio.setVisible(true);
        editarMunicipio.setVisible(false);
        warnings.setVisible(false);
    }
    @FXML
    protected void pesquisarMunicipio(){
        if(comboMunicipios.getValue() != null && !comboMunicipios.getValue().equals("Municipio")){
            warnings.setVisible(false);
            Municipio municipio = m.selectNameAndUf(comboMunicipios.getValue(), "RN");
            nomeMunicipio1.setText(municipio.getNome());
            ufMunicipio1.setText(municipio.getUf());
            nomeMunicipio1.setDisable(false);
            btnSalvar.setDisable(false);
            btnExcluir.setDisable(false);
        }else{
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            warnings.setText("Selecione algum municipio!");
        }
    }
    @FXML
    protected void handleBtnExcluirMunicipio(){
        if(comboMunicipios.getValue() != null && !comboMunicipios.getValue().equals("Municipio")) {
            Municipio municipio = m.selectNameAndUf(comboMunicipios.getValue(), "RN");
            municipioEspecies = municipioEspecieDao.selectIdAlerta(municipio.getId());
            municipioEspecieDao.deleteIdMunicipio(municipio.getId());
            for(MunicipioEspecie m : municipioEspecies){
                System.out.println(m.getIdAlerta());
                alertaDao.DeleteIdMunicipio(m.getIdAlerta());
            }
            usuarios = usuarioDao.selectMunicipio(municipio.getId());
            for (Usuario u : usuarios){
                alertaDao.DeleteEmail(u.getEmail());
                especieDao.deleteEmail(u.getEmail());
                especies = especieDao.selectEmail(u.getEmail());
                for(Especie e : especies){
                    System.out.println(e.getId());
                    alertaDao.deleteIdEspecie(e.getId());
                }
            }
            usuarioDao.deleteIdMunicipio(municipio.getId());
            m.delete(municipio.getId());
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#00f731"));
            warnings.setText("Municipio deletado!");
            nomeMunicipio1.setText("Nome");
            nomeMunicipio1.setDisable(true);
            btnSalvar.setDisable(true);
            btnExcluir.setDisable(true);
            comboMunicipios.getItems().clear();
            listaMunicipios.addAll(m.relatorioNomes());
            FXCollections.sort(listaMunicipios);
            comboMunicipios.setItems(listaMunicipios);
            if(!comboMunicipios.getItems().contains("Municipio")){
                comboMunicipios.getItems().add("Municipio");
            }
            comboMunicipios.setValue("Municipio");
        }else{
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            warnings.setText("Selecione algum municipio!");
        }
    }
    //Menu do navbar
    @FXML
    protected void handleBtnAlertarAction(ActionEvent event) throws IOException {
        this.atualizaFrame("FXMLCadastroAlerta.fxml", event);
    }
    @FXML
    protected void handleBtnMenuAction(ActionEvent evente) throws IOException {
        Stage stage = (Stage) ((Node) evente.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("FXMLMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void handleBtnPerfilAction(ActionEvent event) throws IOException {
        this.atualizaFrame("FXMLPerfil.fxml", event);
    }
    @FXML
    private void handleBtnRastreamentoAction(ActionEvent event) throws IOException{
        this.atualizaFrame("FXMLRastreamento.fxml", event);
    }
    @FXML
    protected void handleBtnSairAction(ActionEvent evente) throws IOException {
        this.setUser(null);
        stage = (Stage) ((Node) evente.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    private void atualizaFrame(String frame, ActionEvent evente) throws IOException {;
        stage = (Stage) ((Node) evente.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(frame));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setScene(scene);
        stage.show();
    }
}
