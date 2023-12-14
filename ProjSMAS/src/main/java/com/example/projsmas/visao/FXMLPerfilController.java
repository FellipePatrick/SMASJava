package com.example.projsmas.visao;

import com.example.projsmas.Main;
import com.example.projsmas.aplicacao.Alerta;
import com.example.projsmas.aplicacao.Especie;
import com.example.projsmas.aplicacao.Usuario;
import com.example.projsmas.persistencia.*;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FXMLPerfilController extends LoginController implements Initializable {
    @FXML
    private Stage stage;
    @FXML
    private Pane editarMeuPerfil, editarPerfis;
    @FXML
    private TextField txtNome, txtNome1, txtMunicipio, idusuario;
    @FXML
    private PasswordField pwdSenha,  pwdSenha1;
    @FXML
    private SplitMenuButton selectFunction, selectMuni;
    @FXML
    private Button excluirUsuario, editarUsuarios;
    @FXML
    private MenuItem usuario, administrador, apicultor;
    @FXML
    private Label warnings;
    private Usuario user = super.getUser();
    private EspecieDao especieDao = new EspecieDao();
    private String aux;
    private Usuario u;
    private UsuarioDao usuarioDao = new UsuarioDao();
    private MunicipioDao m = new MunicipioDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(getUser().getFuncao() == 3){
            editarUsuarios.setVisible(true);
        }
        selectMuni.setText(m.selectId(getUser().getIdMunicipio()).getNome());
        txtNome1.setText(getUser().getNome());
        pwdSenha1.setText(getUser().getSenha());
    }
    @FXML
    protected void pesquisarUsuario(){
        warnings.setVisible(false);
        selectFunction.setDisable(true);
        excluirUsuario.setDisable(true);
        if(idusuario.getText().equals("")){
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            warnings.setText("Preencha o campo email do usuario!");
        }else{
            u = usuarioDao.selectEmail(idusuario.getText());
            if(u != null){
                selectFunction.setDisable(false);
                excluirUsuario.setDisable(false);
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
    protected void excluirUsuario(){
       if(getUser().getFuncao() == 3) {
           if (!(idusuario.getText().equals(getUser().getEmail()))) {
               selectFunction.setDisable(true);
               excluirUsuario.setDisable(true);
               AlertaDao a = new AlertaDao();
               MunicipioEspecieDao municipioEspecieDao = new MunicipioEspecieDao();
               ArrayList<Alerta> listaAlertas = new ArrayList<>();
               ArrayList<Especie> especies = especieDao.selectEmail(idusuario.getText());
               for (Especie e : especies) {
                   municipioEspecieDao.deleteIdEspecie(e.getId());
                   a.deleteIdEspecie(e.getId());
               }
               especieDao.deleteEmail(idusuario.getText());
               usuarioDao.delete(idusuario.getText());
               warnings.setVisible(true);
               txtNome.setText("");
               pwdSenha.setText("");
               selectFunction.setText("Usuario Comum");
               warnings.setTextFill(Paint.valueOf("#00f731"));
               warnings.setText("Usuario excluido");
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
    protected  void mudarNomeUser(){
        selectFunction.setText(usuario.getText());
    }
    @FXML
    protected  void mudarNomeAp(){
        selectFunction.setText(apicultor.getText());
    }
    @FXML
    protected  void mudarNomeAdm(){
        selectFunction.setText(administrador.getText());
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
    protected void editarUsuarios(){
        warnings.setVisible(false);
        editarMeuPerfil.setVisible(false);
        editarPerfis.setVisible(true);
    }
    @FXML
    protected void voltarEditarPerfil(){
        warnings.setVisible(false);
        editarMeuPerfil.setVisible(true);
        editarPerfis.setVisible(false);
    }
    @FXML
    protected void handleBtnCadastrarAction(){
        if (getUser().getFuncao() == 3) {
            boolean flag = false;
            if (!(txtNome.getText().equals(u.getNome()))) {
                user.setNome(txtNome.getText());
                flag = true;
            }
            if (!(pwdSenha.getText().equals(u.getSenha()))) {
                user.setSenha(pwdSenha.getText());
                flag = true;
            }
            switch (u.getFuncao()) {
                case 1:
                    aux = "Usuario Comum";
                    break;
                case 2:
                    aux = "Apicultor";
                    break;
                case 3:
                    aux = "Administrador";
                    break;
            }
            if (!(selectFunction.getText().equals(aux))) {
                flag = true;
                switch (selectFunction.getText()) {
                    case "Usuario Comum":
                        u.setFuncao(1);
                        break;
                    case "Apicultor":
                        u.setFuncao(2);
                        break;
                    case "Administrador":
                        u.setFuncao(3);
                        break;
                }
            }
            if (flag) {
                usuarioDao.update(u.getEmail(), u);
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#00f731"));
                warnings.setText("Usuario atualizado");
                txtNome.setText("");
                pwdSenha.setText("");
            } else {
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#ff0000"));
                warnings.setText("Atualize algum campo");
            }
        }else{
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            warnings.setText("Você não tem permissão atualizar dados de usuários");
        }
    }

    @FXML
    protected void handleBtnCadastrarNormal() {
        boolean flag = false;
        if (!(txtNome1.getText().equals(getUser().getNome()))) {
            user.setNome(txtNome1.getText());
            flag = true;
        }
        if (!(pwdSenha1.getText().equals(getUser().getSenha()))) {
            user.setSenha(pwdSenha1.getText());
            flag = true;
        }
        if (!(selectMuni.getText().equals(m.selectId(getUser().getIdMunicipio()).getNome()))) {
            flag = true;
            switch (selectMuni.getText()) {
                case "São Pedro":
                    getUser().setIdMunicipio(m.selectNameAndUf("São Pedro", "RN").getId());
                    break;
                case "Macaiba":
                    getUser().setIdMunicipio(m.selectNameAndUf("Macaiba", "RN").getId());
                    break;
                case "Natal":
                    getUser().setIdMunicipio(m.selectNameAndUf("Natal", "RN").getId());
                    break;
            }
        }
        if (flag) {
            usuarioDao.update(getUser().getEmail(), getUser());
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#00f731"));
            warnings.setText("Usuario atualizado");
            txtNome.setText(getUser().getNome());
            pwdSenha.setText(getUser().getSenha());
            selectMuni.setText(m.selectId(getUser().getIdMunicipio()).getNome());
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
