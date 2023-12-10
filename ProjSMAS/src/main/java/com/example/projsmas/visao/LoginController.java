package com.example.projsmas.visao;

import com.example.projsmas.Main;
import com.example.projsmas.aplicacao.Usuario;
import com.example.projsmas.persistencia.UsuarioDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
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
    private SplitMenuButton selectMuni;
    @FXML
    private  Label warning;

    @FXML
    private TextField emailBox, nomeCadastro, emailCadastro, emailSenha;
    @FXML
    private PasswordField senhaSenha1, senhaSenha2, senhaCadastro1,senhaCadastro2, senhaBox;

    @FXML
    private Stage stage;
    public void entrarButton(ActionEvent evente) throws IOException {
        warning.setVisible(false);
        user = userDao.selectEmail(emailBox.getText());
        if(user != null){
            if (senhaBox.getText().equals(user.getSenha())) {
                warning.setVisible(false);
                login.setVisible(false);
                stage = (Stage) ((Node)evente.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("FXMLCadastroAlerta.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
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

    protected void entrarButton(){
        warning.setVisible(false);
        user = userDao.selectEmail(emailBox.getText());
        if(user != null){
            if (senhaBox.getText().equals(user.getSenha())) {
                emailBox.setText("");
                senhaBox.setText("");
                setUser(user);
                warning.setVisible(false);
                login.setVisible(false);
            } else {
                warning.setVisible(true);
                warning.setTextFill(Paint.valueOf("#ff0000"));
                warning.setText("Email ou senha invalidos!");
            }
        }else{
            warning.setVisible(true);
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
        }
    }

    @FXML
    protected void verificaSenhas(){
        warning.setVisible(false);
        if(senhaSenha1.getText().equals(senhaSenha2.getText())){
            getUser().setSenha(senhaSenha1.getText());
            password2.setVisible(false);
            login.setVisible(true);
            warning.setVisible(true);
            emailSenha.setText("");
            senhaSenha1.setText("");
            senhaSenha2.setText("");
            System.out.println(this.getUser().getEmail());
            userDao.update(this.getUser().getEmail(), this.getUser());
            warning.setTextFill(Paint.valueOf("#00f731"));
            warning.setText("Senha alterada!");
        }else{
            warning.setVisible(true);
            warning.setTextFill(Paint.valueOf("#ff0000"));
            warning.setText("Senhas diferentes!");
        }
    }
    @FXML
    protected void buttonCadastrar(){
        warning.setVisible(false);
        if(!(nomeCadastro.getText().equals("") || emailCadastro.getText().equals("")  || senhaCadastro1.getText().equals("") || senhaCadastro2.getText().equals(""))){
            if(userDao.selectEmail(emailCadastro.getText()) == null){
                if(senhaCadastro1.getText().equals(senhaCadastro2.getText())){
                    user = new Usuario();
                    user.setEmail(emailCadastro.getText());
                    user.setSenha(senhaCadastro1.getText());
                    user.setNome(nomeCadastro.getText());
                    System.out.println(selectMuni.getText());
                    System.out.println(userDao.municipalityName(selectMuni.getText()));
                    user.setIdMunicipio(userDao.municipalityName(selectMuni.getText()).getId());
                    userDao.insert(user);
                    cadastro.setVisible(false);
                    login.setVisible(true);
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
                    warning.setText("Senhas diferentes!");
                }
            }else{
                warning.setVisible(true);
                warning.setTextFill(Paint.valueOf("#ff0000"));
                warning.setText("O email já está cadastrado!");
            }
        }else{
            warning.setVisible(true);
            warning.setTextFill(Paint.valueOf("#ff0000"));
            warning.setText("Preencha todos os campos!");
        }
    }
    @FXML
    protected void mudarNatal(){
        selectMuni.setText("Natal");
    }
    @FXML
    protected void mudarSaoPedro(){
        selectMuni.setText("São Pedro");
    }
    @FXML
    protected void mudarMacaiba(){
        selectMuni.setText("Macaiba");
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
