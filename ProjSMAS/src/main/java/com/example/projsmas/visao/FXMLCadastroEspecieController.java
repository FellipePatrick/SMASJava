package com.example.projsmas.visao;

import com.example.projsmas.aplicacao.Especie;
import com.example.projsmas.persistencia.EspecieDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

public class FXMLCadastroEspecieController extends LoginController {
    private EspecieDao especieDao = new EspecieDao();
    private Especie especie;
    @FXML
    private AnchorPane apnCadastroEspecie;
    @FXML
    private TextArea txtAreaAlerta, txtAreaAlerta1, txtAreaAlerta11;
    @FXML
    private TextField txtNome;
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

}
