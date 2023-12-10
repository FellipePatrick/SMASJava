package com.example.projsmas.visao;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.projsmas.aplicacao.Alerta;
import com.example.projsmas.aplicacao.MunicipioEspecie;
import com.example.projsmas.persistencia.AlertaDao;
import com.example.projsmas.persistencia.EspecieDao;
import com.example.projsmas.persistencia.MunicipioDao;
import com.example.projsmas.persistencia.MunicipioEspecieDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

public class FXMLCadastroAlertaController extends LoginController implements Initializable {
	EspecieDao especieDao = new EspecieDao();
	AlertaDao alertaDao = new AlertaDao();
	MunicipioEspecieDao munnicipioEspecieDao = new MunicipioEspecieDao();
	MunicipioDao municipioDao = new MunicipioDao();
	@FXML 
	private AnchorPane apnCadastroAlerta;
	@FXML
	private Label warnings;
	@FXML
	private TextField txtMunicipio, txtEspecie, txtNome, txtData;
	@FXML
	private TextArea txtAlerta;
	@FXML
	private SplitMenuButton menuNomeAbelhas, menuNomeCidades;
	@FXML
	private MenuItem jatai, urucu, mirim, saopedro, natal, macaiba;;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		apnCadastroAlerta.setVisible(true);
		
	}

	@FXML
	private void mudarNomeJatai(){
		menuNomeAbelhas.setText(jatai.getText());
	}
	@FXML
	private void mudarNomeUrucu(){
		menuNomeAbelhas.setText(urucu.getText());
	}
	@FXML
	private void mudarNomeSaoPedro(){
		menuNomeCidades.setText(saopedro.getText());
	}
	@FXML
	private void mudarNomeMacaiba(){
		menuNomeCidades.setText(macaiba.getText());
	}
	@FXML
	private void mudarNomeNatal(){
		menuNomeCidades.setText(natal.getText());
	}
	@FXML
	private void mudarNomeMirim(){
		menuNomeAbelhas.setText(mirim.getText());
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
				MunicipioEspecie m = new MunicipioEspecie(municipioDao.selectNameAndUf(menuNomeCidades.getText(), "RN").getId(), alerta.getIdEspecie(), alerta.getId());
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
	private void handleBtnPerfilAction() {
		System.out.println(txtMunicipio.getText());
	}
	
	@FXML
	private void handleBtnMenuAction() {
		System.out.println(txtEspecie.getText());
	}
	
	@FXML
	private void handleBtnAlertarAction() {
		System.out.println(txtNome.getText());
	}
	
	@FXML
	private void handleBtnRastreamentoAction() {
		System.out.println(txtData.getText());
	}
	
	@FXML
	private void handleBtnSairAction() {
		System.out.println(txtAlerta.getText());
	}

}
