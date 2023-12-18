package com.example.projsmas.visao;

import com.example.projsmas.Main;
import com.example.projsmas.aplicacao.Alerta;
import com.example.projsmas.persistencia.AlertaDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLAlertasController extends LoginController implements Initializable {

    @FXML
    private Stage stage;
    @FXML
    private TableView<Alerta> tabelaAlertas;
    @FXML
    private TableColumn<Alerta, String> tbcautor;
    @FXML
    private TableColumn<Alerta, String> tbcdescricao;
    @FXML
    private TableColumn<Alerta, Integer> tbcid;
    @FXML
    private TableColumn<Alerta, String> tbcespecie;
    @FXML
    private TableColumn<Alerta, String> tbcdata;

    private Alerta a;
    private AlertaDao alertaDao = new AlertaDao();

    private ObservableList<Alerta> alertas = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcid.setCellValueFactory(new PropertyValueFactory<Alerta, Integer>("id"));
        tbcautor.setCellValueFactory(new PropertyValueFactory<Alerta, String>("emailUsuario"));
        tbcdata.setCellValueFactory(new PropertyValueFactory<Alerta, String>("data"));
        tbcdescricao.setCellValueFactory(new PropertyValueFactory<Alerta, String>("descricao"));
        tbcespecie.setCellValueFactory(new PropertyValueFactory<Alerta, String>("idEspecie"));
        alertas.addAll(alertaDao.selectAll());
        FXCollections.reverse(alertas);
        tabelaAlertas.setItems(alertas);
    }
    @FXML
    protected void handleBtnAlertarAction(ActionEvent event) throws IOException {
        this.atualizaFrame("FXMLCadastroAlerta.fxml", event);
    }
    @FXML
    protected void handleBtnMenuAction(ActionEvent evente) throws IOException {
        Stage stage = (Stage) ((Node) evente.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("FXMLMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
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
    private void atualizaFrame(String frame, ActionEvent evente) throws IOException {;
        stage = (Stage) ((Node) evente.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(frame));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setScene(scene);
        stage.show();
    }
}
