package com.example.projsmas.visao;

import com.example.projsmas.aplicacao.Alerta;
import com.example.projsmas.persistencia.AlertaDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class tabelaController implements Initializable {
    @FXML
    private TableView<Alerta> tabela;
    @FXML
    TableColumn<Alerta, String> email;
    @FXML
    private TableColumn<Alerta, String> data;
    @FXML
    TableColumn<Alerta, Integer> id;
    @FXML
    TableColumn<Alerta, Integer> especie;
    @FXML
    TableColumn<Alerta, String> descricao;
    AlertaDao alertaDao = new AlertaDao();
    private ObservableList<Alerta> alertas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       email.setCellValueFactory(new PropertyValueFactory<>("emailUsuario"));
       data.setCellValueFactory(new PropertyValueFactory<Alerta, String>("data"));
       especie.setCellValueFactory(new PropertyValueFactory<>("idEspecie"));
       id.setCellValueFactory(new PropertyValueFactory<>("id"));
       descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
       alertas.addAll(alertaDao.selectAll());
       tabela.setItems(alertas);
    }
}
