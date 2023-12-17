package com.example.projsmas.visao;

import com.example.projsmas.Main;
import com.example.projsmas.aplicacao.Especie;
import com.example.projsmas.persistencia.AlertaDao;
import com.example.projsmas.persistencia.EspecieDao;
import com.example.projsmas.persistencia.MunicipioEspecieDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ResourceBundle;

public class FXMLCadastroEspecieController extends LoginController implements Initializable {
    private EspecieDao especieDao = new EspecieDao();
    private Especie especie;
    @FXML
    private Pane cadastrarEspecie, editarEspecie;
    @FXML
    private TextArea txtAreaAlerta, txtAreaAlerta1, txtAreaAlerta11, txtAreaAlerta3,txtAreaAlerta2, txtAreaAlerta4 ;
    @FXML
    private TextField txtNome,txtNome2;
    @FXML
    private ComboBox<String> comboBoxEspecie;
    @FXML
    private Button excluirEspecie, btnCadastrar1;
    @FXML
    private Label warnings;
    private ObservableList<String> relatorioEspecie = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if(getUser().getFuncao() == 3){
            relatorioEspecie.addAll(especieDao.relatorioNomes());
        }else{
            relatorioEspecie.addAll(especieDao.selectEmailTwo(getUser().getEmail()));
        }
        comboBoxEspecie.setItems(relatorioEspecie);
    }

    public void limparTela(){
        txtAreaAlerta3.setDisable(true);
        txtAreaAlerta3.setText("");
        txtAreaAlerta2.setDisable(true);
        txtAreaAlerta2.setText("");
        txtAreaAlerta4.setDisable(true);
        txtAreaAlerta4.setText("");
        txtNome2.setDisable(true);
        txtNome2.setText("");
    }

    @FXML
    protected void handleBtnCadastrarAction(){
        if(txtAreaAlerta.getText().equals("") || txtAreaAlerta1.getText().equals("") || txtAreaAlerta11.getText().equals("") || txtNome.getText().equals("")){
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            warnings.setText("Preencha todos os campos!");
        }else{
            especie = new Especie();
            if(especieDao.selectName(txtNome.getText().toUpperCase()) == null){
                especie.setComoCapturar(txtAreaAlerta1.getText());
                especie.setNome(txtNome.getText().toUpperCase());
                especie.setComoCriar(txtAreaAlerta.getText());
                especie.setSobre(txtAreaAlerta11.getText());
                especie.setEmailUser(super.getUser().getEmail());
                especieDao.insert(especie);
                configuraTela();
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#00f731"));
                warnings.setText("Especie Cadastrada!");
            }else{
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#ff0000"));
                warnings.setText("Nome de Especie já Cadastrada!");
            }
        }
    }

    @FXML
    protected void btnVoltarPerfil(ActionEvent event) throws IOException{
        this.atualizaFrame("FXMLPerfil.fxml", event);
    }
    @FXML
    protected void handleBtnAlertarAction(ActionEvent event) throws IOException {
        this.atualizaFrame("FXMLCadastroAlerta.fxml", event);
    }
    @FXML
    protected void handleBtnMenuAction(ActionEvent event) throws IOException {
        this.atualizaFrame("FXMLAlertas.fxml", event);
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
        Stage stage = (Stage) ((Node) evente.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    protected void salvarEspecie(){
        boolean flag = false;
        if(especieDao.selectName(txtNome2.getText().toUpperCase()).getNome() == null) {
            if (!(txtNome2.getText().toUpperCase().equals(comboBoxEspecie.getValue())) && !(txtNome2.getText().equals(""))) {
                flag = true;
                especie.setNome(txtNome2.getText());
            }
            if ((!txtAreaAlerta2.getText().equals(especie.getComoCriar())) && (!txtAreaAlerta2.getText().equals(""))) {
                flag = true;
                especie.setComoCriar(txtAreaAlerta2.getText());
            }
            if ((!txtAreaAlerta3.getText().equals(especie.getComoCapturar())) && (!txtAreaAlerta3.getText().equals(""))) {
                flag = true;
                especie.setComoCapturar(txtAreaAlerta3.getText());
            }
            if ((!txtAreaAlerta4.getText().equals(especie.getSobre())) && (!txtAreaAlerta4.getText().equals(""))) {
                flag = true;
                especie.setSobre(txtAreaAlerta4.getText());
            }
            if (flag) {
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#00f731"));
                warnings.setText("Especie Alterada!");
                especieDao.update(especie.getId(), especie);
                configuraTela();
            } else {
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#ff0000"));
                warnings.setText("Altere algum campo para pode salvar!");
            }
        }else{
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            warnings.setText("Especie já existente!");
        }
    }
    @FXML
    protected void pesquisarEspecie(){
        txtAreaAlerta3.setDisable(true);
        txtAreaAlerta3.setText("");
        txtAreaAlerta2.setDisable(true);
        txtAreaAlerta2.setText("");
        txtAreaAlerta4.setDisable(true);
        txtAreaAlerta4.setText("");
        txtNome2.setDisable(true);
        txtNome2.setText("");
        excluirEspecie.setDisable(true);
        btnCadastrar1.setDisable(true);
        if(!(comboBoxEspecie.getValue() == null)){
            especie = especieDao.selectName(comboBoxEspecie.getValue());
            if(especie != null){
                txtAreaAlerta3.setDisable(false);
                txtAreaAlerta3.setText(especie.getComoCapturar());
                txtAreaAlerta2.setDisable(false);
                txtAreaAlerta2.setText(especie.getComoCriar());
                txtAreaAlerta4.setDisable(false);
                txtAreaAlerta4.setText(especie.getSobre());
                txtNome2.setDisable(false);
                txtNome2.setText(especie.getNome());
                excluirEspecie.setDisable(false);
                btnCadastrar1.setDisable(false);
            }else{
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#ff0000"));
                warnings.setText("Nenhuma especie selecionada!");
            }
        }else{
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            warnings.setText("Preencha o campo ID");
        }
    }
    @FXML
    protected void excluirEspecie(){
        MunicipioEspecieDao m = new MunicipioEspecieDao();
        AlertaDao a = new AlertaDao();
        m.deleteIdEspecie(especie.getId());
        a.deleteIdEspecie(especie.getId());
        especieDao.delete(especie.getId());
        warnings.setVisible(true);
        warnings.setTextFill(Paint.valueOf("#00f731"));
        warnings.setText("Especie Deletada!");
        configuraTela();
    }
    @FXML
    protected void voltarEspecie(){
        warnings.setVisible(false);
        editarEspecie.setVisible(false);
        cadastrarEspecie.setVisible(true);
        txtNome.setText("");
        txtAreaAlerta.setText("");
        txtAreaAlerta2.setText("");
        txtAreaAlerta3.setText("");
        txtAreaAlerta4.setText("");
        txtAreaAlerta11.setText("");
        txtAreaAlerta3.setText("");
        txtAreaAlerta2.setText("");
        txtAreaAlerta4.setText("");
        txtAreaAlerta.setText("");
        txtAreaAlerta1.setText("");
    }
    @FXML
    protected void irParaEditar(){
        warnings.setVisible(false);
        editarEspecie.setVisible(true);
        cadastrarEspecie.setVisible(false);
        configuraTela();
    }

    private void configuraTela(){
        comboBoxEspecie.getItems().clear();
        relatorioEspecie.addAll(especieDao.selectEmailTwo(getUser().getEmail()));
        FXCollections.sort(relatorioEspecie);
        comboBoxEspecie.setItems(relatorioEspecie);
        if(!comboBoxEspecie.getItems().contains("Especie")) {
            comboBoxEspecie.getItems().add("Especie");
        }
        comboBoxEspecie.setValue("Especie");
        limparTela();
        excluirEspecie.setDisable(true);
        btnCadastrar1.setDisable(true);

    }
    private void atualizaFrame(String frame, ActionEvent evente) throws IOException {;
        Stage stage = (Stage) ((Node) evente.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(frame));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setScene(scene);
        stage.show();
    }

}