package com.example.esm_javastocks;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
public class GestionArticlesController {

    @FXML
    protected void onAjoutArticleButtonClick() {
        // Charger et afficher la page "Article en rupture / Réservation en attente"
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ajoutarticle.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Ajouter un article");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onModifierArticleButtonClick() {
        // Charger et afficher la page "Article en rupture / Réservation en attente"
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modifarticle.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Modifier un article");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onConsulterArticleButtonClick() {
        // Charger et afficher la page "Article en rupture / Réservation en attente"
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("consulter-article.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Consulter un article");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @FXML
    protected void onSuppressionlogiqueArticleButtonClick() {
        // Charger et afficher la page "Article en rupture / Réservation en attente"
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("suppression-logique-article.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Supprimer un article");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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