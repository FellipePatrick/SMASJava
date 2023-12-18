module com.example.projsmas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.projsmas to javafx.fxml;
    exports com.example.projsmas;
    exports com.example.projsmas.visao;
    opens com.example.projsmas.visao to javafx.fxml;

    exports com.example.projsmas.aplicacao;
    opens com.example.projsmas.aplicacao to javafx.fxml;

    exports com.example.projsmas.persistencia;
    opens com.example.projsmas.persistencia to javafx.fxml;

}