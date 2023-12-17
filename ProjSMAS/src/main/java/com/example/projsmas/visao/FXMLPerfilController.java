package com.example.projsmas.visao;

import com.example.projsmas.Main;
import com.example.projsmas.aplicacao.Alerta;
import com.example.projsmas.aplicacao.Especie;
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
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FXMLPerfilController extends LoginController implements Initializable {
    @FXML
    private Stage stage;
    @FXML
    private Pane editarMeuPerfil, editarPerfis;
    @FXML
    private TextField txtNome, txtNome1;
    @FXML
    private PasswordField pwdSenha,  pwdSenha1;
    @FXML
    private SplitMenuButton selectFunction;
    @FXML
    private Button excluirUsuario, editarUsuarios, btnMunicipios, btnEspecies;
    @FXML
    private ComboBox<String> comboCidades;
    @FXML
    private ComboBox<String> comboCidadesEditar;
    @FXML
    private ComboBox<String> comboUsuarios;
    @FXML
    private Label warnings;
    private EspecieDao especieDao = new EspecieDao();
    private String aux, email;
    private Usuario u;
    private int id, funcao;
    private UsuarioDao usuarioDao = new UsuarioDao();
    private MunicipioDao m = new MunicipioDao();
    private ObservableList<String> listaMunicipios = FXCollections.observableArrayList();
    private ObservableList<String> listaUsuarios = FXCollections.observableArrayList();
    private MunicipioDao municipioDao =  new MunicipioDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(getUser().getFuncao() >= 2){
            btnEspecies.setVisible(true);
        }
        if(getUser().getFuncao() == 3){
            editarUsuarios.setVisible(true);
            btnMunicipios.setVisible(true);
        }
        listaMunicipios.addAll(m.relatorioNomes());
        listaUsuarios.addAll(usuarioDao.relatorioEmails());
        FXCollections.sort(listaMunicipios);
        FXCollections.sort(listaUsuarios);
        comboCidades.setItems(listaMunicipios);
        comboUsuarios.setItems(listaUsuarios);
        comboCidadesEditar.setItems(listaMunicipios);
        paintScreen();
    }

    private void paintScreen(){
        comboCidades.setValue((m.selectId(getUser().getIdMunicipio()).getNome()));
        txtNome1.setText(getUser().getNome());
        pwdSenha1.setText(getUser().getSenha());
    }
    @FXML
    protected void btnMunicipios(ActionEvent evente) throws IOException{
        this.atualizaFrame("FXMLMunicipio.fxml", evente);
    }
    @FXML
    protected void btnEspecies(ActionEvent event) throws  IOException{
        this.atualizaFrame("FXMLCadastroEspecie.fxml", event);
    }
    @FXML
    protected void pesquisarUsuario(){
        if(comboUsuarios.getValue() == null){
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            warnings.setText("Preencha o campo email do usuario!");
        }else{
            u = usuarioDao.selectEmail(comboUsuarios.getValue());
            if(u != null){
                if(!(u.getEmail().equals(getUser().getEmail())) && u.getFuncao() != 3){
                    selectFunction.setDisable(false);
                }
                excluirUsuario.setDisable(false);
                comboCidadesEditar.setValue(m.selectId(u.getIdMunicipio()).getNome());
                txtNome.setText(u.getNome());
                pwdSenha.setText(u.getSenha());
                switch (u.getFuncao()){
                    case 1:
                        selectFunction.setText("Usuario Comum");
                        break;
                    case 2:
                        selectFunction.setText("Apicultor");
                        break;
                    case 3:
                        selectFunction.setText("Administrador");
                        break;
                }
            }else{
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#ff0000"));
                warnings.setText("Email de usuario não cadastrado!");
            }
        }
    }

    @FXML
    protected void editarUsuarios(){
        warnings.setVisible(false);
        editarMeuPerfil.setVisible(false);
        editarPerfis.setVisible(true);
        txtNome.setText("");
        pwdSenha.setText("");
        excluirUsuario.setDisable(true);
        selectFunction.setDisable(true);
        warnings.setVisible(false);
        updateDados();
    }

    private void updateDados(){
        if(!comboCidadesEditar.getItems().contains("Municipio")){
            comboCidadesEditar.getItems().add("Municipio");
        }
        comboCidadesEditar.setValue("Municipio");
        if(!comboUsuarios.getItems().contains("Usuario")){
            comboUsuarios.getItems().add("Usuario");
        }
        comboUsuarios.setValue("Usuario");
    }

    @FXML
    protected void excluirUsuario(){
       if(getUser().getFuncao() == 3) {
           if (!(u.getEmail().equals(getUser().getEmail()))) {
               if(u.getFuncao() != 3) {
                   selectFunction.setDisable(true);
                   excluirUsuario.setDisable(true);
                   AlertaDao a = new AlertaDao();
                   MunicipioEspecieDao municipioEspecieDao = new MunicipioEspecieDao();
                   email = comboUsuarios.getValue();
                   ArrayList<Especie> especies = especieDao.selectEmail(email);
                   for (Especie e : especies) {
                       municipioEspecieDao.deleteIdEspecie(e.getId());
                       a.deleteIdEspecie(e.getId());
                   }
                   //Usuario comum
                   ArrayList<Integer> alertasUserId = a.selectEmail(email);
                   if(!alertasUserId.isEmpty()){
                       for(Integer idUser : alertasUserId){
                           municipioEspecieDao.deleteIdAlerta(idUser);
                           a.delete(idUser);
                       }
                   }
                   a.DeleteEmail(email);
                   especieDao.deleteEmail(comboUsuarios.getValue());
                   usuarioDao.delete(comboUsuarios.getValue());
                   warnings.setVisible(true);
                   txtNome.setText("");
                   pwdSenha.setText("");
                   selectFunction.setText("Usuario Comum");
                   warnings.setTextFill(Paint.valueOf("#00f731"));
                   warnings.setText("Usuario excluido");
                   comboUsuarios.getItems().clear();
                   listaUsuarios.addAll(usuarioDao.relatorioEmails());
                   comboUsuarios.setItems(listaUsuarios);
                   updateDados();
               }else{
                   warnings.setVisible(true);
                   warnings.setTextFill(Paint.valueOf("#ff0000"));
                   warnings.setText("Você não pode apagar outro adm");
               }
           } else {
               warnings.setVisible(true);
               warnings.setTextFill(Paint.valueOf("#ff0000"));
               warnings.setText("Você não pode se apagar");
           }
       }else{
           warnings.setVisible(true);
           warnings.setTextFill(Paint.valueOf("#ff0000"));
           warnings.setText("Você não tem permissão apagar usuários");
       }
    }
    @FXML
    protected void btnUser(){
        selectFunction.setText("Usuario Comum");
    }
    @FXML
    protected void btnAp(){
        selectFunction.setText("Apicultor");
    }
    @FXML
    protected void btnAdm(){
        selectFunction.setText("Administrador");
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
        Stage stage = (Stage) ((Node) evente.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void voltarEditarPerfil(){
        warnings.setVisible(false);
        editarPerfis.setVisible(false);
        editarMeuPerfil.setVisible(true);
        comboCidades.setValue(municipioDao.selectId(getUser().getIdMunicipio()).getNome());
    }
    @FXML
    protected void handleBtnCadastrarAction(){
        selectFunction.setDisable(true);
        boolean flag = false;
        if(!comboUsuarios.getValue().equals("Usuario")){
            if (!(txtNome.getText().equals(u.getNome())) && txtNome.getText() != "") {
                u.setNome(txtNome.getText());
                flag = true;
            }

            if(!(pwdSenha.getText().equals(u.getSenha())) && pwdSenha.getText() != ""){
                u.setSenha(pwdSenha.getText());
                flag = true;
            }

            if(selectFunction.getText().equals("Usuario Comum")) funcao = 1;
                else if(selectFunction.getText().equals("Apicultor")) funcao = 2;
                    else funcao = 3;

            if ( funcao != u.getFuncao() && selectFunction.getText() != null && u.getFuncao() != 3) {
                u.setFuncao(funcao);
                flag = true;
            }
            id = m.selectNameAndUf(comboCidadesEditar.getValue(), "RN").getId();
            if(id != u.getIdMunicipio()){
                u.setIdMunicipio(id);
                flag = true;
            }

            if (flag) {
                usuarioDao.update(u.getEmail(), u);
                if (u.getEmail().equals(getUser().getEmail())) {
                    getUser().setIdMunicipio(u.getIdMunicipio());
                    getUser().setNome(u.getNome());
                    getUser().setSenha(u.getSenha());
                }
                paintScreen();
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#00f731"));
                warnings.setText("Usuario atualizado");
                txtNome.setText("");
                pwdSenha.setText("");
                updateDados();
            }else{
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#ff0000"));
                warnings.setText("Atualize algum campo");
            }
        } else {
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            warnings.setText("Pesquise por um usuario");
        }
    }

    @FXML
    protected void handleBtnCadastrarNormal() {
        boolean flag = false;
        if (!(txtNome1.getText().equals(getUser().getNome()))) {
            getUser().setNome(txtNome1.getText());
            flag = true;
        }
        if (!(pwdSenha1.getText().equals(getUser().getSenha()))) {
            getUser().setSenha(pwdSenha1.getText());
            flag = true;
        }
        if (!(comboCidades.getValue().equals(m.selectId(getUser().getIdMunicipio()).getNome()))) {
            flag = true;
            getUser().setIdMunicipio((m.selectNameAndUf(comboCidades.getValue(), "RN").getId()));
        }
        if (flag) {
            usuarioDao.update(getUser().getEmail(), getUser());
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#00f731"));
            warnings.setText("Usuario atualizado");
            txtNome.setText(getUser().getNome());
            pwdSenha.setText(getUser().getSenha());
            comboCidades.setValue(m.selectId(getUser().getIdMunicipio()).getNome());
        } else {
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            warnings.setText("Atualize algum campo");
        }
    }
    private void atualizaFrame(String frame, ActionEvent evente) throws IOException {;
        stage = (Stage) ((Node) evente.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(frame));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setScene(scene);
        stage.show();
    }
}
