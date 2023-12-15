package com.example.projsmas.visao;

import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.ResourceBundle;

import com.example.projsmas.Main;
import com.example.projsmas.aplicacao.Alerta;
import com.example.projsmas.aplicacao.MunicipioEspecie;
import com.example.projsmas.persistencia.AlertaDao;
import com.example.projsmas.persistencia.EspecieDao;
import com.example.projsmas.persistencia.MunicipioDao;
import com.example.projsmas.persistencia.MunicipioEspecieDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

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


public class FXMLCadastroAlertaController extends LoginController implements Initializable {
	private EspecieDao especieDao = new EspecieDao();
	private AlertaDao alertaDao = new AlertaDao();
	private MunicipioEspecieDao municipioEspecieDao = new MunicipioEspecieDao();
	private Alerta alerta;
	private MunicipioDao municipioDao = new MunicipioDao();
	@FXML
	private AnchorPane apnCadastroAlerta, EditarAlerta, CriarAlerta;
	@FXML
	private Label warnings;
	@FXML
	private Button btnCadastrar1, btnExcluir;
	@FXML
	private TextField txtMunicipio, txtEspecie, txtNome, txtData;
	@FXML
	private TextArea txtAlerta, txtAlerta1;
	@FXML
	private ComboBox<Integer> comboBoxAlertas;
	@FXML
	private ComboBox<String> comboEspecie;
	@FXML
	private ComboBox<String> comboEspecieCadastrar;
	@FXML
	private ComboBox<String> comboCidades;
	@FXML
	private ComboBox<String> comboCidadesCadastrar;
	@FXML
	private Stage stage;
	private ObservableList<Integer> idAlertas = FXCollections.observableArrayList();
	private ObservableList<String> relatorioEspecie = FXCollections.observableArrayList();
	private ObservableList<String> listaMunicipios = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		apnCadastroAlerta.setVisible(true);
		idAlertas.addAll(alertaDao.selectEmail(getUser().getEmail()));
		comboBoxAlertas.setItems(idAlertas);
		relatorioEspecie.addAll(especieDao.relatorioNomes());
		comboEspecie.setItems(relatorioEspecie);
		comboEspecieCadastrar.setItems(relatorioEspecie);
		listaMunicipios.addAll(municipioDao.relatorioNomes());
		comboCidades.setItems(listaMunicipios);
		comboCidadesCadastrar.setItems(listaMunicipios);
	}
	@FXML
	private void handleBtnCadastrarAction() {
		warnings.setVisible(false);
		if(!(txtAlerta.getText().equals(""))){
			try {
				Alerta alerta = new Alerta();
				alerta.setDescricao(txtAlerta.getText());
				alerta.setEmailUsuario(getUser().getEmail());
				alerta.setIdEspecie(especieDao.selectName(comboEspecieCadastrar.getValue()).getId());
				alertaDao.insert(alerta);
				ArrayList<Alerta> lista = alertaDao.selectAll();
				alerta = lista.get(lista.size() - 1);
				MunicipioEspecie m = new MunicipioEspecie(municipioDao.selectNameAndUf(comboCidadesCadastrar.getValue(), "RN").getId(), alerta.especiId(), alerta.getId());
				municipioEspecieDao.insert(m);
				txtAlerta.setText("");
				warnings.setVisible(true);
				warnings.setTextFill(Paint.valueOf("#00f731"));
				warnings.setText("Alerta cadastrado!");
			}catch (Exception e){
				System.out.println(e);
			}
		}else{
			warnings.setVisible(true);
			warnings.setTextFill(Paint.valueOf("#ff0000"));
			warnings.setText("Preencha todos os campos!");
		}
	}
	@FXML
	private void EditarAlertas() {
		CriarAlerta.setVisible(false);
		 EditarAlerta.setVisible(true);
	}
	@FXML
	private void voltarEditarAlerta() {
		CriarAlerta.setVisible(true);
		EditarAlerta.setVisible(false);
	}
	@FXML
	private void pesquisasrAlertaID() {
		warnings.setVisible(false);
		btnCadastrar1.setDisable(true);
		btnExcluir.setDisable(true);
		comboEspecie.setDisable(true);
		comboCidades.setDisable(true);
		txtAlerta1.setDisable(true);
		if(comboBoxAlertas.getValue()==null){
			warnings.setVisible(true);
			warnings.setTextFill(Paint.valueOf("#ff0000"));
			warnings.setText("Selecione um id o campo ID!");
		}else{
			alerta = alertaDao.selectId(comboBoxAlertas.getValue());
			if(alerta != null){
			 	btnCadastrar1.setDisable(false);
				btnExcluir.setDisable(false);
				comboEspecie.setDisable(false);
				comboCidades.setDisable(false);
				txtAlerta1.setDisable(false);
				comboEspecie.setValue(especieDao.selectId(alerta.especiId()).getNome());
				txtAlerta1.setText(alerta.getDescricao());
			}else{
				warnings.setVisible(true);
				warnings.setTextFill(Paint.valueOf("#ff0000"));
				warnings.setText("Alerta não encontrado!");
			}
		}
	}
	@FXML
	protected void ExcluirAlerta(){
		municipioEspecieDao.deleteIdAlerta(alerta.getId());
		alertaDao.delete(alerta.getId());
		btnCadastrar1.setDisable(true);
		btnExcluir.setDisable(true);
		comboEspecie.setDisable(true);
		comboCidades.setDisable(true);
		txtAlerta1.setDisable(true);
		txtAlerta1.setText("");
		idAlertas.addAll(alertaDao.selectEmail(getUser().getEmail()));
		comboBoxAlertas.setItems(idAlertas);
		comboEspecie.setPromptText("Especie");
		comboCidades.setPromptText("Municipio");
		warnings.setVisible(true);
		warnings.setTextFill(Paint.valueOf("#00f731"));
		warnings.setText("Alerta Exluido!");
	}
	@FXML
	private void handleBtnSalvarAction() {
		boolean flag = false;
		if(!(txtAlerta1.getText().equals(alerta.getDescricao())) && !(txtAlerta1.getText().equals(""))){
			alerta.setDescricao(txtAlerta1.getText());
			flag = true;
		};
		if(!(comboEspecie.getValue().equals(especieDao.selectId(alerta.especiId()).getNome())) && !(comboEspecie.getValue().equals(""))){
			alerta.setIdEspecie(especieDao.selectName(comboEspecie.getValue()).getId());
			flag = true;
		};
		if(flag){
			alertaDao.update(alerta.getId(), alerta);
			warnings.setVisible(true);
			warnings.setTextFill(Paint.valueOf("#00f731"));
			warnings.setText("Alerta Editado!");
		}else{
			warnings.setVisible(true);
			warnings.setTextFill(Paint.valueOf("#ff0000"));
			warnings.setText("Altere algum campo para atualizar o alerta!");
		}
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
	private void atualizaFrame(String frame, ActionEvent evente) throws IOException {;
		stage = (Stage) ((Node) evente.getSource()).getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(frame));
		Scene scene = new Scene(fxmlLoader.load(), 600, 500);
		stage.setScene(scene);
		stage.show();
	}

}