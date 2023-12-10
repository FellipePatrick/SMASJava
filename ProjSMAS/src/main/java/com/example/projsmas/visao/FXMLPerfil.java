package com.example.projsmas.visao;

import com.example.projsmas.aplicacao.Usuario;
import com.example.projsmas.persistencia.MunicipioDao;
import com.example.projsmas.persistencia.UsuarioDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

public class FXMLPerfil extends LoginController {
    private Usuario user = super.getUser();
    private MunicipioDao m = new MunicipioDao();
    @FXML
    private TextField txtNome, txtMunicipio;
    @FXML
    private PasswordField pwdSenha;
    @FXML
    private Label warnings;
    @FXML
    protected void handleBtnPerfilAction(){

    }
    @FXML
    protected void handleBtnMenuAction(){

    }
    @FXML
    protected void handleBtnAlertarAction(){

    }
    @FXML
    protected void handleBtnRastreamentoAction(){

    }
    @FXML
    protected void handleBtnSairAction(){

    }
    @FXML
    protected void handleBtnCadastrarAction(){
//        txtNome.setText(super.getUser().getNome());
//        txtMunicipio.setText(m.selectId(user.getIdMunicipio()).getNome());
//        pwdSenha.setText(user.getSenha());
        boolean flag = false;
        if(!(txtNome.getText().equals(""))){
            user.setNome(txtNome.getText());
            flag = true;
        }
        if(!(pwdSenha.getText().equals(""))){
            user.setSenha(pwdSenha.getText());
            flag = true;
        }
        if(flag){
            UsuarioDao usuarioDao = new UsuarioDao();
            usuarioDao.update(getUser().getEmail(), user);
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#00f731"));
            warnings.setText("Usuario atualizado");
            txtNome.setText("");
            pwdSenha.setText("");
        }else{
            warnings.setVisible(true);
            warnings.setTextFill(Paint.valueOf("#ff0000"));
            warnings.setText("Atualize algum campo");
        }
    }
}
