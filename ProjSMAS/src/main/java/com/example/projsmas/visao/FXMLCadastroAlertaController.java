package visao;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLCadastroAlertaController implements Initializable {
	
	@FXML 
	private AnchorPane apnCadastroAlerta;
	@FXML
	private ComboBox<String> cbxMunicipio, cbxEspecie;
	@FXML
	private TextArea txtAreaAlert;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		apnCadastroAlerta.setVisible(true);
		
	}
	
	@FXML
	private void handleBtnPerfilAction() {
		
	}
	
	@FXML
	private void handleBtnMenuAction() {
		
	}
	
	@FXML
	private void handleBtnAlertarAction() {
		
	}
	
	@FXML
	private void handleBtnRastreamentoAction() {
		
	}
	
	@FXML
	private void handleBtnSairAction() {
		
	}
	
	
	
	@FXML
	private void handleBtnCadastrarAction() {
		System.out.println("oi");
	}
	
	
}
