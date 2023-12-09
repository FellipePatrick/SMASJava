package com.example.projsmas.visao;

import com.example.projsmas.aplicacao.Usuario;
import com.example.projsmas.persistencia.UsuarioDao;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;



public class LoginController {
    private Usuario user;
    private Usuario userLogado;
    private UsuarioDao userDao = new UsuarioDao();

    @FXML
    private AnchorPane container, password, cadastro, login, password2, logado;

    @FXML
    private SplitMenuButton selectMuni;
    @FXML
    private  Label warning, userName;

    @FXML
    private TextField emailBox, nomeCadastro, emailCadastro, emailSenha;
    @FXML
    private PasswordField senhaSenha1, senhaSenha2, senhaCadastro1,senhaCadastro2, senhaBox;

    @FXML
    protected void entrarButton(){
        user = userDao.selectEmail(emailBox.getText());
        if(user != null){
            if (senhaBox.getText().equals(user.getSenha())) {
                warning.setVisible(false);
                login.setVisible(false);
                userLogado = user;
                logado.setVisible(true);
                userName.setText(user.getNome());
            } else {
                warning.setVisible(true);
                warning.setText("Email ou senha invalidos!");
            }
        }else{
            warning.setVisible(true);
            warning.setText("Email ou senha invalidos!");
        }
    }
    @FXML
    protected void verificarEmail(){
        user = userDao.selectEmail(emailSenha.getText());
        if(user != null){
            password.setVisible(false);
            password2.setVisible(true);
        }else{
            warning.setVisible(true);
            warning.setText("Email!");
        }
    }

    @FXML
    protected void verificaSenhas(){
        if(senhaSenha1.getText().equals(senhaSenha2.getText())){
            password2.setVisible(false);
            login.setVisible(true);
            warning.setVisible(true);
            warning.setText("Senha alterada!");
        }else{
            warning.setVisible(true);
            warning.setText("Senhas diferentes!");
        }
    }

    @FXML
    protected void buttonCadastrar(){
        if(!(nomeCadastro.getText().equals("") || emailCadastro.getText().equals("")  || senhaCadastro1.getText().equals("") || senhaCadastro2.getText().equals(""))){
            if(senhaCadastro1.getText().equals(senhaCadastro2.getText())){
                user = new Usuario();
                user.setEmail(emailCadastro.getText());
                user.setSenha(senhaCadastro1.getText());
                user.setNome(nomeCadastro.getText());
                switch (selectMuni.getText().toUpperCase()){
                    case "SÃO PEDRO":
                        user.setIdMunicipio(3);
                        break;
                    case "MACAIBA":
                        user.setIdMunicipio(4);
                        break;
                    case "NATAL":
                        user.setIdMunicipio(5);
                        break;
                    default:user.setIdMunicipio(5);
                }
                userDao.insert(user);
                cadastro.setVisible(false);
                login.setVisible(true);
                warning.setVisible(true);
                warning.setText("Usuario cadastrado!");

            }else{
                warning.setVisible(true);
                warning.setText("Senhas diferentes!");
            }
        }else{
            warning.setVisible(true);
            warning.setText("Preencha todos os campos!");
        }
    }
    @FXML
    protected void voltarLogado(){
        logado.setVisible(false);
        login.setVisible(true);
    }
    @FXML
    protected void clickcadastre(){
        login.setVisible(false);
        cadastro.setVisible(true);
    }
    @FXML
    protected void clicksenha(){
        login.setVisible(false);
        password.setVisible(true);
    }
    @FXML
    protected void voltarCadastro(){
        login.setVisible(true);
        cadastro.setVisible(false);
    }
    @FXML
    protected void voltarEmail(){
        password.setVisible(false);
        login.setVisible(true);
    }
    @FXML
    protected void voltarSenha() {
        password.setVisible(false);
        login.setVisible(true);
    }
}
