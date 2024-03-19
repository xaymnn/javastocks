package com.example.esm_javastocks;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.sql.*;
public class ConsulterArticleController {
    @FXML
    private ComboBox<Integer> idComboBox; // ComboBox pour choisir l'article
    @FXML
    private Label consultArticlelibelle; // Label pour afficher le libellé
    @FXML
    private Label consultArticlecategorie; // Label pour afficher la catégorie
    @FXML
    private Label consultArticletaille; // Label pour afficher la taille
    @FXML
    private Label consultArticlecouleurs; // Label pour afficher les couleurs
    @FXML
    private Label consultArticlevolume; // Label pour afficher le volume
    @FXML
    private Label consultArticlequantite; // Label pour afficher la quantité
    @FXML
    private Label consultArticlepoids; // Label pour afficher le poids

    @FXML
    private Button buttonRetour; // Déclaration du bouton

    @FXML
    protected void initialize() {
        // Initialisez la liste déroulante des IDs d'articles au démarrage de l'application
        loadArticleIDsFromDatabase();
    }

    @FXML
    protected void onConsultButtonClick() {
        // Récupérez l'ID de l'article sélectionné
        Integer selectedArticleID = idComboBox.getValue();
        if (selectedArticleID != null) {
            // Chargez et affichez les détails de l'article correspondant depuis la base de données
            loadArticleDetails(selectedArticleID);
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
                    consultArticlelibelle.setText(resultSet.getString("libelle"));
                    consultArticlecategorie.setText(resultSet.getString("categorie"));
                    consultArticletaille.setText(resultSet.getString("taille"));
                    consultArticlecouleurs.setText(resultSet.getString("couleur"));
                    consultArticlevolume.setText(resultSet.getString("volume"));
                    consultArticlepoids.setText(resultSet.getString("poids"));
                    consultArticlequantite.setText(resultSet.getString("quantite"));

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

    private void clearLabels() {
        // Réinitialisez les Labels à une chaîne vide
        consultArticlelibelle.setText("");
        consultArticlecategorie.setText("");
        consultArticletaille.setText("");
        consultArticlecouleurs.setText("");
        consultArticlevolume.setText("");
        consultArticlequantite.setText("");
        consultArticlepoids.setText("");
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



