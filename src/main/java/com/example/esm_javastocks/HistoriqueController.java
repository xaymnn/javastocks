package com.example.esm_javastocks;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HistoriqueController {
    @FXML
    protected void onQuitButtonClick() {
        // Ferme la fenêtre (l'interface graphique)
        Platform.exit();
    }

    @FXML
    private Button buttonRevenirAuMenu; // Déclaration du bouton

    @FXML
    protected void onRevenirAuMenuButtonClick() {
        // Ferme la fenêtre actuelle
        Stage stage = (Stage) buttonRevenirAuMenu.getScene().getWindow();
        stage.close();

        // Charge et affiche la page du menu principal
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage menuStage = new Stage();
            menuStage.setTitle("JavaStocks - Menu principal");
            menuStage.setScene(scene);
            menuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
