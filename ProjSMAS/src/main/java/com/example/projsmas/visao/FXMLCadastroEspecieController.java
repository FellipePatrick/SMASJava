package com.example.projsmas.visao;

import com.example.projsmas.Main;
import com.example.projsmas.aplicacao.Especie;
import com.example.projsmas.persistencia.AlertaDao;
import com.example.projsmas.persistencia.EspecieDao;
import com.example.projsmas.persistencia.MunicipioEspecieDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLCadastroEspecieController extends LoginController {
    private EspecieDao especieDao = new EspecieDao();
    private Especie especie;
    @FXML
    private Stage stage;
    @FXML
    private Pane cadastrarEspecie, editarEspecie;
    @FXML
    private AnchorPane apnCadastroEspecie;
    @FXML
    private TextArea txtAreaAlerta, txtAreaAlerta1, txtAreaAlerta11, txtAreaAlerta3,txtAreaAlerta2, txtAreaAlerta4 ;
    @FXML
    private TextField txtNome,txtNome2, pesquisarEspecie;
    @FXML
    private Button excluirEspecie, btnCadastrar1;
    @FXML
    private Label warnings;
    @FXML
    protected void handleBtnCadastrarAction(){
        if(txtAreaAlerta.getText().equals("") || txtAreaAlerta1.getText().equals("") || txtAreaAlerta11.getText().equals("") || txtNome.getText().equals("")){
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            warnings.setText("Preencha todos os campos!");
        }else{
            especie = new Especie();
            especie.setComoCapturar(txtAreaAlerta1.getText());
            especie.setNome(txtNome.getText());
            especie.setComoCriar(txtAreaAlerta.getText());
            especie.setSobre(txtAreaAlerta11.getText());
            especie.setEmailUser(super.getUser().getEmail());
            especieDao.insert(especie);
            txtNome.setText("");
            txtAreaAlerta.setText("");
            txtAreaAlerta1.setText("");
            txtAreaAlerta11.setText("");
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#00f731"));
            warnings.setText("Especie Cadastrada!");
        }
    }

    @FXML
    protected void handleBtnMenuAction(){

    }
    @FXML
    protected void handleBtnPerfilAction(ActionEvent event) throws IOException {
        this.atualizaFrame("FXMLPerfil.fxml", event);
    }
    @FXML
    protected void handleBtnAlertarAction(ActionEvent event) throws IOException {
        this.atualizaFrame("FXMLCadastroAlerta.fxml", event);
    }

    @FXML
    private void handleBtnRastreamentoAction(ActionEvent event) throws IOException{
        this.atualizaFrame("FXMLCadastroEspecie.fxml", event);
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
        if((!txtNome2.getText().equals(especie.getNome())) && (!txtNome2.getText().equals(""))){
            flag = true;
            especie.setNome(txtNome2.getText());
        }
        if((!txtAreaAlerta2.getText().equals(especie.getComoCriar())) && (!txtAreaAlerta2.getText().equals(""))){
            flag = true;
            especie.setComoCriar(txtAreaAlerta2.getText());
        }
        if((!txtAreaAlerta3.getText().equals(especie.getComoCapturar())) && (!txtAreaAlerta3.getText().equals(""))){
            flag = true;
            especie.setComoCapturar(txtAreaAlerta3.getText());
        }
        if((!txtAreaAlerta4.getText().equals(especie.getSobre())) && (!txtAreaAlerta4.getText().equals(""))){
            flag = true;
            especie.setSobre(txtAreaAlerta4.getText());
        }
        if(flag){
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#00f731"));
            warnings.setText("Especie Alterada!");
            especieDao.update(especie.getId(), especie);
        }else{
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            warnings.setText("Altere algum campo para pode salvar!");
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
        if(!(pesquisarEspecie.getText().equals(""))){
            especie = especieDao.selectId(Integer.parseInt(pesquisarEspecie.getText()));
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
                warnings.setText("Especie não cadastrada");
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
    }
    @FXML
    protected void voltarEspecie(){
        warnings.setVisible(false);
        editarEspecie.setVisible(false);
        cadastrarEspecie.setVisible(true);
    }
    @FXML
    protected void irParaEditar(){
        warnings.setVisible(false);
        editarEspecie.setVisible(true);
        cadastrarEspecie.setVisible(false);
    }
    private void atualizaFrame(String frame, ActionEvent evente) throws IOException {;
        Stage stage = (Stage) ((Node) evente.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(frame));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setScene(scene);
        stage.show();
    }

}