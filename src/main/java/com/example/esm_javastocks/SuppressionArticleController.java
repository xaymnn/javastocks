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

public class SuppressionArticleController {
    @FXML
    private ComboBox<Integer> idComboBox; // ComboBox pour choisir l'article
    @FXML
    private Label afficheArticlelibelle; // Label pour afficher le libellé de l'article
    @FXML
    private Label afficheArticlecategorie; // Label pour afficher la catégorie de l'article
    @FXML
    private Label afficheArticletaille; // Label pour afficher la taille de l'article
    @FXML
    private Label afficheArticlecouleurs; // Label pour afficher la couleur de l'article
    @FXML
    private Label afficheArticlequantite; // Label pour afficher la quantité de l'article
    @FXML
    private Label afficheArticlepoids; // Label pour afficher le poids de l'article
    @FXML
    private Label confirmationLabel; // Référence au Label dans votre FXML
    @FXML
    private Button buttonRetour; // Déclaration du bouton


    @FXML
    protected void initialize() {
        // Initialisez la liste déroulante des IDs d'articles au démarrage de l'application
        loadArticleIDsFromDatabase();

        // Définissez un gestionnaire d'événements pour la ComboBox lorsque la sélection change
        idComboBox.setOnAction(event -> {
            Integer selectedArticleID = idComboBox.getValue();
            if (selectedArticleID != null) {
                // Chargez et affichez les détails de l'article correspondant depuis la base de données
                loadArticleDetails(selectedArticleID);
            }
        });
    }
    @FXML
    protected void onSupprimeButtonClick() {
        // Récupérez l'ID de l'article sélectionné
        Integer selectedArticleID = idComboBox.getValue();
        if (selectedArticleID != null) {
            // Effectuez la suppression logique en mettant "est_supprime" à TRUE dans la base de données
            if (performLogicalDelete(selectedArticleID)) {
                // Affichez un message de confirmation
                confirmationLabel.setText("Article " + selectedArticleID + " supprimé avec succès.");
                // Vous pouvez également effacer les détails de l'article ou faire d'autres actions nécessaires ici
                clearLabels();
            } else {
                // En cas d'erreur lors de la suppression logique, affichez un message d'erreur
                confirmationLabel.setText("Erreur lors de la suppression de l'article.");
            }
        } else {
            // Aucun article sélectionné, affichez un message demandant de choisir un article
            confirmationLabel.setText("Choisissez un article.");
        }
    }




    private void loadArticleIDsFromDatabase() {
        // Assurez-vous d'avoir une connexion à la base de données
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                // Exécutez une requête SQL pour récupérer les IDs des articles
                String sql = "SELECT article_id FROM Article WHERE est_supprime = false"; // Vous devrez adapter cette requête en fonction de votre schéma de base de données
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                // Ajoutez les IDs récupérés à la ComboBox idComboBox
                while (resultSet.next()) {
                    int articleID = resultSet.getInt("article_id");
                    idComboBox.getItems().add(articleID);
                }

                // Fermez la connexion
                databaseConnection.closeConnection();

                // Triez les IDs dans la ComboBox
                idComboBox.getItems().sort(null);
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérez les exceptions SQL ici
            }
        }
    }

    private void loadArticleDetails(Integer articleID) {
        // Établir la connexion à la base de données
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                // Construisez la requête SQL pour récupérer les détails de l'article en fonction de l'ID
                String sql = "SELECT * FROM Article WHERE article_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, articleID);

                // Exécutez la requête SQL
                ResultSet resultSet = preparedStatement.executeQuery();

                // Si un résultat est trouvé, affichez les détails dans les Labels correspondants
                if (resultSet.next()) {
                    afficheArticlelibelle.setText(resultSet.getString("libelle"));
                    afficheArticlecategorie.setText(resultSet.getString("categorie"));
                    afficheArticletaille.setText(resultSet.getString("taille"));
                    afficheArticlecouleurs.setText(resultSet.getString("couleur"));
                    afficheArticlepoids.setText(resultSet.getString("poids"));
                    afficheArticlequantite.setText(resultSet.getString("quantite"));

                } else {
                    // Aucun article trouvé avec cet ID, vous pouvez afficher un message d'erreur ou effectuer une action appropriée
                    clearLabels();
                }

                // Fermez la connexion à la base de données
                databaseConnection.closeConnection();

            } catch (SQLException e) {
                e.printStackTrace();
                // Gérez les exceptions SQL ici
            }
        } else {
            System.err.println("Échec de la connexion à la base de données.");
            // Gérez l'échec de la connexion ici
        }
    }


    private boolean performLogicalDelete(Integer articleID) {
        // Établir la connexion à la base de données
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                // Construisez la requête SQL pour effectuer la suppression logique
                String sql = "UPDATE Article SET est_supprime = TRUE WHERE article_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, articleID);

                // Exécutez la requête SQL
                int rowsAffected = preparedStatement.executeUpdate();

                // Fermez la connexion à la base de données
                databaseConnection.closeConnection();

                // Vérifiez si la suppression logique a réussi (au moins une ligne mise à jour)
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérez les exceptions SQL ici
            }
        } else {
            System.err.println("Échec de la connexion à la base de données.");
            // Gérez l'échec de la connexion ici
        }

        return false; // La suppression logique a échoué
    }

    private void clearLabels() {
        // Réinitialisez les Labels à une chaîne vide
        afficheArticlelibelle.setText("");
        afficheArticlecategorie.setText("");
        afficheArticletaille.setText("");
        afficheArticlecouleurs.setText("");
        afficheArticlequantite.setText("");
        afficheArticlepoids.setText("");
    }
    @FXML
    protected void onRetourButtonClick() {
        // Ferme la fenêtre actuelle
        Stage stage = (Stage) buttonRetour.getScene().getWindow();
        stage.close();

        // Charge et affiche la page du menu principal
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gestion-des-articles.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage menuStage = new Stage();
            menuStage.setTitle("Gestion des articles");
            menuStage.setScene(scene);
            menuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

