package com.example.projsmas.visao;

import com.example.projsmas.Main;
import com.example.projsmas.aplicacao.Municipio;
import com.example.projsmas.persistencia.EspecieDao;
import com.example.projsmas.persistencia.MunicipioDao;
import com.example.projsmas.persistencia.MunicipioEspecieDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FXMLRastreamentoController extends LoginController implements Initializable {
    @FXML
    private ComboBox<String> comboCidades;
    @FXML
    private ComboBox<String> comboEspecies;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label warnings, whatIs;
    @FXML
    private Button btnPesquisar;
    private Label newLabel;
    private ObservableList<String> listaMunicipios = FXCollections.observableArrayList();
    private ObservableList<String> listaEspecies = FXCollections.observableArrayList();
    private MunicipioDao m = new MunicipioDao();
    private MunicipioEspecieDao mE = new MunicipioEspecieDao();
    private EspecieDao e = new EspecieDao();
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaMunicipios.addAll(m.relatorioNomes());
        listaEspecies.addAll(e.relatorioNomes());
        comboCidades.setItems(listaMunicipios);
        comboEspecies.setItems(listaEspecies);
    }
    @FXML
    protected void selectMunicipios(){
        warnings.setVisible(false);
        comboCidades.setVisible(true);
        comboEspecies.setVisible(false);
        btnPesquisar.setVisible(true);
    }
    @FXML
    protected void selectEspecies(){
        warnings.setVisible(false);
        comboCidades.setVisible(false);
        comboEspecies.setVisible(true);
        btnPesquisar.setVisible(true);
    }
    @FXML
    protected void btnPesquisar(){
        rootPane.getChildren().clear();
        whatIs.setVisible(false);
        if(comboEspecies.isVisible()){
            if(comboEspecies.getValue()!=null){
                double x = 1, y = 1;
                ArrayList<Integer> lista = new ArrayList<>();
                lista.addAll(mE.relatorioEspecieTaEmMunicipio(e.selectName(comboEspecies.getValue()).getId()));
                if(lista.size() < 1){
                    newLabel = new Label("Essa abelha não está em nenhum Municipio!");
                    newLabel.setTextFill(Paint.valueOf("#ff0000"));
                    rootPane.setTopAnchor(newLabel, y);
                    rootPane.setLeftAnchor(newLabel, x);
                    rootPane.getChildren().add(newLabel);
                }else {
                    for (Integer a : lista) {
                        newLabel = new Label(m.selectId(a).getNome());
                        rootPane.setTopAnchor(newLabel, y);
                        rootPane.setLeftAnchor(newLabel, x);
                        y += 30;
                        rootPane.getChildren().add(newLabel);
                    }
                }
            }else{
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#ff0000"));
                warnings.setText("Selecione alguma Especie!");
            }
        }else{
            if(comboCidades.getValue()!=null){
                double x = 1, y = 1;
                ArrayList<Integer> lista = new ArrayList<>();
                lista.addAll(mE.relatorioMunicioTemEspecie(m.selectNameAndUf(comboCidades.getValue(), "RN").getId()));
                if(lista.size() < 1){
                    newLabel = new Label("Nenhuma especie de abelha nesse Municipio!");
                    newLabel.setTextFill(Paint.valueOf("#ff0000"));
                    rootPane.setTopAnchor(newLabel, y);
                    rootPane.setLeftAnchor(newLabel, x);
                    rootPane.getChildren().add(newLabel);
                }else{
                    for(Integer a: lista){
                        newLabel = new Label(e.selectId(a).getNome());
                        rootPane.setTopAnchor(newLabel, y);
                        rootPane.setLeftAnchor(newLabel, x);
                        y+=30;
                        rootPane.getChildren().add(newLabel);
                    }
                }
            }else{
                warnings.setVisible(true);
                warnings.setTextFill(Paint.valueOf("#ff0000"));
                warnings.setText("Selecione algum Municipio!");
            }
        }


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
        //this.setUser(null);
        Stage stage = (Stage) ((Node) evente.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    private void atualizaFrame(String frame, ActionEvent evente) throws IOException {;
        Stage stage = (Stage) ((Node) evente.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(frame));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setScene(scene);
        stage.show();
    }
}
