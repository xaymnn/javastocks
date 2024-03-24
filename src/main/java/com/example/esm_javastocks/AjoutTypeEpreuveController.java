package com.example.esm_javastocks;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AjoutTypeEpreuveController {


    @FXML
    private TextField libelle; // Référence au champ de texte dans votre FXML
    @FXML
    private Label confirmationLabel; // Référence au Label dans votre FXML
    @FXML
    private Button buttonRetour; // Déclaration du bouton

    @FXML
    protected void onAjoutButtonClick() {
        // Récupérez la valeur du libellé depuis le champ de texte
        String libelleEpreuve = libelle.getText();

        if (!libelleEpreuve.isEmpty()) {
            // Ajoutez le type d'épreuve à la base de données
            if (ajouterTypeEpreuve(libelleEpreuve)) {
                // Affichez le message de succès
                confirmationLabel.setText("Type d'épreuve ajouté avec succès : " + libelleEpreuve);
            } else {
                // Affichez un message d'erreur si l'ajout a échoué
                confirmationLabel.setText("Erreur lors de l'ajout du type d'épreuve.");
            }
        } else {
            // Affichez un message d'erreur si le champ de texte est vide
            confirmationLabel.setText("Veuillez saisir le libellé du type d'épreuve.");
        }
    }

    private boolean ajouterTypeEpreuve(String libelleEpreuve) {
        // Établissez la connexion à la base de données (utilisez votre code existant)
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                // Créez une requête SQL pour insérer le type d'épreuve dans la table "type_epreuve"
                String sql = "INSERT INTO type_epreuve (type_epreuve_libelle) VALUES (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, libelleEpreuve);

                // Exécutez la requête SQL pour effectuer l'ajout
                int rowsAffected = preparedStatement.executeUpdate();

                // Fermez la connexion à la base de données
                databaseConnection.closeConnection();

                // Si au moins une ligne a été ajoutée, retournez vrai (succès)
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérez les exceptions SQL ici
            }
        } else {
            System.err.println("Échec de la connexion à la base de données.");
            // Gérez l'échec de la connexion ici
        }

        return false; // L'ajout a échoué
    }

    @FXML
    protected void onRetourButtonClick() {
        // Ferme la fenêtre actuelle
        Stage stage = (Stage) buttonRetour.getScene().getWindow();
        stage.close();

        // Charge et affiche la page du menu principal
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gestion-types-epreuve.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage menuStage = new Stage();
            menuStage.setTitle("Gestion des types d'épreuves");
            menuStage.setScene(scene);
            menuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
