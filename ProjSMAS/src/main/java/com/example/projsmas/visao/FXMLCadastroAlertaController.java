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
	private MunicipioEspecieDao munnicipioEspecieDao = new MunicipioEspecieDao();
	private MunicipioDao municipioDao = new MunicipioDao();
	@FXML
	private AnchorPane apnCadastroAlerta, EditarAlerta, CriarAlerta;
	@FXML
	private Label warnings;
	@FXML
	private Button btnCadastrar1, btnExcluir;
	@FXML
	private TextField txtMunicipio, txtEspecie, txtNome, txtData, digitarIDAlerta;
	@FXML
	private TextArea txtAlerta, txtAlerta1;
	@FXML
	private SplitMenuButton menuNomeAbelhas, menuNomeCidades, menuNomeCidades1, menuNomeAbelhas1;
	@FXML
	private MenuItem jatai, urucu, mirim, saopedro, natal, macaiba, jatai1, urucu1, mirim1, saopedro1, natal1, macaiba1;;
	@FXML
	private Stage stage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		apnCadastroAlerta.setVisible(true);
	}

	@FXML
	private void mudarNomeJatai(){
		menuNomeAbelhas.setText(jatai.getText());
		menuNomeAbelhas1.setText(jatai1.getText());
	}
	@FXML
	private void mudarNomeUrucu(){
		menuNomeAbelhas.setText(urucu.getText());
		menuNomeAbelhas1.setText(urucu1.getText());
	}
	@FXML
	private void mudarNomeSaoPedro(){
		menuNomeCidades.setText(saopedro.getText());
		menuNomeCidades1.setText(saopedro1.getText());
	}
	@FXML
	private void mudarNomeMacaiba(){
		menuNomeCidades.setText(macaiba.getText());
		menuNomeCidades1.setText(macaiba1.getText());
	}
	@FXML
	private void mudarNomeNatal(){
		menuNomeCidades.setText(natal.getText());
		menuNomeCidades1.setText(natal1.getText());
	}
	@FXML
	private void mudarNomeMirim(){
		menuNomeAbelhas.setText(mirim.getText());
		menuNomeAbelhas1.setText(mirim1.getText());
	}
	@FXML
	private void handleBtnCadastrarAction() {
		warnings.setVisible(false);
		if(!(txtAlerta.getText().equals(""))){
			try {
				Alerta alerta = new Alerta();
				alerta.setDescricao(txtAlerta.getText());
				alerta.setEmailUsuario(getUser().getEmail());
				alerta.setIdEspecie(especieDao.selectName(menuNomeAbelhas.getText().toUpperCase()).getId());
				alertaDao.insert(alerta);
				ArrayList<Alerta> lista = alertaDao.selectAll();
				alerta = lista.get(lista.size() - 1);
				MunicipioEspecie m = new MunicipioEspecie(municipioDao.selectNameAndUf(menuNomeCidades.getText(), "RN").getId(), alerta.especiId(), alerta.getId());
				munnicipioEspecieDao.insert(m);
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
		menuNomeAbelhas1.setDisable(true);
		menuNomeCidades1.setDisable(true);
		txtAlerta1.setDisable(true);
		if(digitarIDAlerta.getText().equals("")){
			warnings.setVisible(true);
			warnings.setTextFill(Paint.valueOf("#ff0000"));
			warnings.setText("Preencha o campo ID!");
		}else{
			alerta = alertaDao.selectId(Integer.parseInt(digitarIDAlerta.getText()));
			if(alerta != null && alerta.getEmailUsuario().equals(getUser().getEmail())){
			 	btnCadastrar1.setDisable(false);
				btnExcluir.setDisable(false);
				menuNomeAbelhas1.setDisable(false);
				menuNomeCidades1.setDisable(false);
				txtAlerta1.setDisable(false);
				menuNomeAbelhas1.setText(especieDao.selectId(alerta.especiId()).getNome());
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
		try{
			municipioEspecieDao.deleteIdAlerta(alerta.getId());
			alertaDao.delete(alerta.getId());
			btnCadastrar1.setDisable(true);
			btnExcluir.setDisable(true);
			menuNomeAbelhas1.setDisable(true);
			menuNomeCidades1.setDisable(true);
			txtAlerta1.setDisable(true);
			digitarIDAlerta.setText("");
			txtAlerta1.setText("");
			menuNomeCidades1.setText("São Pedro");
			menuNomeAbelhas1.setText("Jataí");
			warnings.setVisible(true);
			warnings.setTextFill(Paint.valueOf("#00f731"));
			warnings.setText("Alerta Exluido!");
		}catch (Exception e){
			System.out.println(e);
		}
	}
	@FXML
	private void handleBtnSalvarAction() {
		boolean flag = false;
		if(!(txtAlerta1.getText().equals(alerta.getDescricao())) && !(txtAlerta1.getText().equals(""))){
			alerta.setDescricao(txtAlerta1.getText());
			flag = true;
		};
		if(!(menuNomeAbelhas1.getText().equals(especieDao.selectId(alerta.especiId()).getNome())) && !(menuNomeAbelhas1.getText().equals(""))){
			alerta.setIdEspecie(especieDao.selectName(menuNomeAbelhas1.getText()).getId());
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