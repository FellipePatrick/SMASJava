package com.example.projsmas.visao;

import com.example.projsmas.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class testeController extends LoginController{
   private Stage stage;
   @FXML
   private Label userName;

   @FXML
   public void verificarUser(){
       System.out.println(getUser().getNome());
       userName.setText(getUser().getNome());
   }
   public void switchToScene(ActionEvent evente) throws IOException{
       stage = (Stage) ((Node)evente.getSource()).getScene().getWindow();
       FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
       Scene scene = new Scene(fxmlLoader.load(), 600, 400);
       stage.setScene(scene);
       stage.show();
   }

}
