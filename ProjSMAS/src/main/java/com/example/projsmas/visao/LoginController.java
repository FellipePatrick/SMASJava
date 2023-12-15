package com.example.projsmas.visao;

import com.example.projsmas.Main;
import com.example.projsmas.aplicacao.Usuario;
import com.example.projsmas.persistencia.MunicipioDao;
import com.example.projsmas.persistencia.UsuarioDao;
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
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    private static Usuario user;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    private UsuarioDao userDao = new UsuarioDao();

    @FXML
    private AnchorPane password, cadastro, login, password2;
    @FXML
    private  Label warning;

    @FXML
    private TextField emailBox, nomeCadastro, emailCadastro, emailSenha;
    @FXML
    private PasswordField senhaSenha1, senhaSenha2, senhaCadastro1,senhaCadastro2, senhaBox;
    @FXML
    private Stage stage;
    @FXML
    private ComboBox<String> comboCidades;
    private ObservableList<String> listaMunicipios = FXCollections.observableArrayList();
    private MunicipioDao municipioDao = new MunicipioDao();
    private String email;
    private String senha;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        listaMunicipios.addAll(municipioDao.relatorioNomes());
        comboCidades.setItems(listaMunicipios);
    }
    public void entrarButton(ActionEvent evente) throws IOException {
        warning.setVisible(false);
        user = userDao.selectEmail(emailBox.getText());
        if(user != null){
            if (senhaBox.getText().equals(user.getSenha())) {
                warning.setVisible(false);
                login.setVisible(false);
                stage = (Stage) ((Node)evente.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("FXMLCadastroEspecie.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 500);
                stage.setScene(scene);
                stage.show();
            } else {
                warning.setVisible(true);
                warning.setTextFill(Paint.valueOf("#ff0000"));
                warning.setText("Email ou senha invalidos!");
            }
        }else{
            warning.setVisible(true);
            warning.setTextFill(Paint.valueOf("#ff0000"));
            warning.setText("Email ou senha invalidos!");
        }
    }

    @FXML
    protected void verificarEmail(){
        warning.setVisible(false);
        this.setUser(userDao.selectEmail(emailSenha.getText()));
        if(this.getUser() != null){
            password.setVisible(false);
            password2.setVisible(true);
        }else{
            warning.setVisible(true);
            warning.setTextFill(Paint.valueOf("#ff0000"));
            warning.setText("Email invalido!");
        }
    }

    @FXML
    protected void verificaSenhas(){
        warning.setVisible(false);
        this.senha  = senhaSenha1.getText();
        if(senhaSenha1.getText().equals(senhaSenha2.getText()) && (!senhaSenha1.getText().equals("")) && (!senhaSenha2.getText().equals(""))) {
            if (this.senha.length() > 5) {
                getUser().setSenha(senhaSenha1.getText());
                password2.setVisible(false);
                login.setVisible(true);
                warning.setVisible(true);
                emailSenha.setText("");
                senhaSenha1.setText("");
                senhaSenha2.setText("");
                userDao.update(this.getUser().getEmail(), this.getUser());
                warning.setTextFill(Paint.valueOf("#00f731"));
                warning.setText("Senha alterada!");
            }else{
                warning.setVisible(true);
                warning.setTextFill(Paint.valueOf("#ff0000"));
                warning.setText("Senha com menos de 6 digitos!");
            }
        }else{
            warning.setVisible(true);
            warning.setTextFill(Paint.valueOf("#ff0000"));
            warning.setText("Senhas diferentes!");
        }
    }

    private void validarEmail(){
        warning.setVisible(false);
        email = emailCadastro.getText();
        user = userDao.selectEmail(email);
        if(user == null){
            if(email.contains("@gmail.com")) {
                validarSenha();
            }else{
                warning.setVisible(true);
                warning.setTextFill(Paint.valueOf("#ff0000"));
                warning.setText("Email precisa ter @gmail.com!");
            }
        }else{
            warning.setVisible(true);
            warning.setTextFill(Paint.valueOf("#ff0000"));
            warning.setText("Email invalido!");
        }
    }

    protected void validarSenha(){
        warning.setVisible(false);
        this.senha = senhaCadastro1.getText();
        if (this.senha.equals(senhaCadastro2.getText())) {
            if(senha.length() > 5) {
                user = new Usuario();
                user.setEmail(emailCadastro.getText());
                user.setSenha(senhaCadastro1.getText());
                user.setNome(nomeCadastro.getText());
                user.setIdMunicipio(userDao.municipalityName(comboCidades.getValue()).getId());
                userDao.insert(user);
                login.setVisible(true);
                password2.setVisible(false);
                cadastro.setVisible(false);
                warning.setVisible(true);
                warning.setTextFill(Paint.valueOf("#00f731"));
                warning.setText("Usuario cadastrado!");
                nomeCadastro.setText("");
                emailCadastro.setText("");
                senhaCadastro1.setText("");
                senhaCadastro2.setText("");
            }else{
                warning.setVisible(true);
                warning.setTextFill(Paint.valueOf("#ff0000"));
                warning.setText("Senha com menos de 6 digitos!");
            }
        } else {
            warning.setVisible(true);
            warning.setTextFill(Paint.valueOf("#ff0000"));
            warning.setText("Senhas diferentes!");
        }
    }
    @FXML
    protected void buttonCadastrar(){
        warning.setVisible(false);
        if(!(nomeCadastro.getText().equals("") || emailCadastro.getText().equals("")  || senhaCadastro1.getText().equals("") || senhaCadastro2.getText().equals(""))){
            validarEmail();
        }else{
            warning.setVisible(true);
            warning.setTextFill(Paint.valueOf("#ff0000"));
            warning.setText("Preencha todos os campos!");
        }
    }
    @FXML
    protected void clickcadastre(){
        warning.setVisible(false);
        login.setVisible(false);
        cadastro.setVisible(true);
    }
    @FXML
    protected void clicksenha(){
        warning.setVisible(false);
        login.setVisible(false);
        password.setVisible(true);
    }
    @FXML
    protected void voltarCadastro(){
        warning.setVisible(false);
        login.setVisible(true);
        cadastro.setVisible(false);
    }
    @FXML
    protected void voltarEmail(){
        warning.setVisible(false);
        password.setVisible(false);
        login.setVisible(true);
    }
    @FXML
    protected void voltarSenha() {
        warning.setVisible(false);
        password2.setVisible(false);
        password.setVisible(true);
    }
}
