package com.example.esm_javastocks;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    protected void onQuitButtonClick() {
        // Ferme la fenêtre (l'interface graphique)
        Platform.exit();
    }

    @FXML
    protected void onGestionArticlesButtonClick() {
        // Charger et afficher la page "Menu Article"
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gestion-des-articles.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Gestion des articles");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onAjouterCoureurButtonClick() {

        // Charger et afficher la page "Menu Coureur"
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gestion-coureur.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Gestion des coureurs");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onAjouterTypeEpreuveButtonClick() {
        // Charger et afficher la page "Menu Types d'épreuve"
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gestion-types-epreuve.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Gestion des types d'épreuves");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onAjouterReservationButtonClick() {
        // Charger et afficher la page "Menu reservation"
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gestion-reservation.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Gestion des reservations");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onArticleRuptureReservationAttenteButtonClick() {
        // Charger et afficher la page "Article en rupture / Réservation en attente"
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("articlerupture-reservationattente.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Article en rupture / Réservation en attente");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onHistoriqueButtonClick() {
        // Charger et afficher la page "Menu historique"
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("historique.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Historique");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
