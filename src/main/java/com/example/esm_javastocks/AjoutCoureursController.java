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

public class AjoutCoureursController {

    @FXML
    private TextField prenom; // Référence au Label dans votre FXML
    @FXML
    private TextField nom; // Référence au Label dans votre FXML
    @FXML
    private Label confirmationLabel; // Référence au Label dans votre FXML
    @FXML
    private Button buttonRetour; // Déclaration du bouton

    @FXML
    protected void onAjoutButtonClick() {
        // Récupérez les valeurs du nom et du prénom depuis les champs de texte
        String nomCoureur = nom.getText();
        String prenomCoureur = prenom.getText();

        if (!nomCoureur.isEmpty() && !prenomCoureur.isEmpty()) {
            // Ajoutez le coureur à la base de données et récupérez l'ID généré
            int coureur_id = ajouterCoureur(nomCoureur, prenomCoureur);

            if (coureur_id != -1) {
                // Si l'ajout a réussi, affichez le message de succès avec l'ID
                confirmationLabel.setText("Coureur ajouté avec succès avec l'ID numéro " + coureur_id);
            } else {
                // Affichez un message d'erreur si l'ajout a échoué
                confirmationLabel.setText("Erreur lors de l'ajout du coureur.");
            }
        } else {
            // Affichez un message d'erreur si les champs de texte sont vides
            confirmationLabel.setText("Veuillez saisir le nom et le prénom du coureur.");
        }
    }

    private int ajouterCoureur(String nom, String prenom) {
        // Établissez la connexion à la base de données
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                // Créez une requête SQL pour insérer le coureur dans la table "coureur"
                String sql = "INSERT INTO coureur (coureur_nom, coureur_prenom) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, prenom);

                // Exécutez la requête SQL pour effectuer l'ajout
                int rowsAffected = preparedStatement.executeUpdate();

                // Récupérez l'ID généré
                int coureurID = -1;
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    coureurID = generatedKeys.getInt(1);
                }

                // Fermez la connexion à la base de données
                databaseConnection.closeConnection();

                // Si au moins une ligne a été ajoutée, retournez l'ID généré
                if (rowsAffected > 0) {
                    return coureurID;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérez les exceptions SQL ici
            }
        } else {
            System.err.println("Échec de la connexion à la base de données.");
            // Gérez l'échec de la connexion ici
        }

        return -1; // L'ajout a échoué
    }






    @FXML
    protected void onRetourButtonClick() {
        // Ferme la fenêtre actuelle
        Stage stage = (Stage) buttonRetour.getScene().getWindow();
        stage.close();

        // Charge et affiche la page du menu principal
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gestion-coureur.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage menuStage = new Stage();
            menuStage.setTitle("Gestion des coureurs");
            menuStage.setScene(scene);
            menuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
