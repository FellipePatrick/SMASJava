package com.example.projsmas.visao;

import com.example.projsmas.aplicacao.Alerta;
import com.example.projsmas.persistencia.AlertaDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLAlertasController {

    @FXML
    private TableView<Alerta> tabelaAlertas;
    @FXML
    private TableColumn<Alerta, String> tbcautor;
    @FXML
    private TableColumn<Alerta, String> tbcdescricao;
    @FXML
    private TableColumn<Alerta, Integer> tbcmunicipio;
    @FXML
    private TableColumn<Alerta, Integer> tbcespecie;
    @FXML
    private TableColumn<Alerta, String> tbcdata;

    private Alerta a;
    private AlertaDao alertaDao = new AlertaDao();

    private ObservableList<Alerta> alertas;
//    @FXML
//    protected void consultarAlertas(){
//        this.tbcmunicipio.setCellValueFactory(new PropertyValueFactory("id"));
//        this.tbcautor.setCellValueFactory(new PropertyValueFactory("emailusuario"));
//        this.tbcdata.setCellValueFactory(new PropertyValueFactory("data"));
//        this.tbcdescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
//        this.tbcespecie.setCellValueFactory(new PropertyValueFactory("idespecie"));
//        this.alertas.addAll(this.alertaDao.selectAll());
//        this.tabelaAlertas.setItems(this.alertas);
//    }
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
