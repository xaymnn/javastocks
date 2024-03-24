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

public class ModifArticleController {

    @FXML
    private ComboBox<Integer> idComboBox; // Définissez une ComboBox pour les IDs d'articles
    @FXML
    private TextField libelleTextField;
    @FXML
    private ComboBox<String> categorieComboBox;
    @FXML
    private ComboBox<String> tailleComboBox;
    @FXML
    private ComboBox<String> couleurComboBox;
    @FXML
    private TextField quantiteTextField; // Champ de texte pour la quantité
    @FXML
    private TextField poidsTextField;
    @FXML
    private Label confirmationLabel; // Référence au Label dans votre FXML

    @FXML
    protected void initialize() {
        // Initialisez la liste déroulante des IDs d'articles au démarrage de l'application
        loadArticleIDsFromDatabase();

        // Ajoutez un gestionnaire d'événements pour détecter la sélection d'un ID d'article
        idComboBox.setOnAction(event -> {
            // Récupérez l'ID de l'article sélectionné
            Integer selectedArticleID = idComboBox.getValue();

            // Chargez les détails de l'article correspondant depuis la base de données et affichez-les
            loadArticleDetails(selectedArticleID);
        });

        categorieComboBox.getItems().addAll("T", "B", "DS");

        // Définissez un texte d'invite par défaut
        categorieComboBox.setPromptText("Catégorie");
        // Initialisation de la ComboBox de taille
        tailleComboBox.getItems().addAll("XS", "S", "M", "L", "XL", "XXL");
        tailleComboBox.setPromptText("Taille");
        // Ajoutez les couleurs à la liste déroulante
        couleurComboBox.getItems().addAll("Blanche", "Rouge", "Bleue", "Verte", "Noire", "Jaune");

        // Définissez un texte d'invite par défaut
        couleurComboBox.setPromptText("Couleur");
    }

    @FXML
    protected void onModifierButtonClick() {
        // Récupérez les nouvelles valeurs des champs de texte pour la mise à jour
        Integer selectedArticleID = idComboBox.getValue();
        String libelle = libelleTextField.getText();
        String categorie = categorieComboBox.getValue();
        String taille = tailleComboBox.getValue();
        String couleur = couleurComboBox.getValue();
        String poids = poidsTextField.getText();
        String quantiteString = quantiteTextField.getText(); // Ajoutez le champ de texte pour la quantité dans votre interface

        // Vérifiez si l'ID de l'article est null
        if (selectedArticleID == null) {
            String errorMessage = "Le champ ID article est obligatoire ! Veuillez le remplir.";
            confirmationLabel.setText(errorMessage);
            return; // Sortez de la fonction car il y a une erreur
        }

        // Vérifiez si la quantité est un entier valide
        try {
            int quantite = Integer.parseInt(quantiteString);

            // Mettez à jour les détails de l'article dans la base de données en utilisant l'ID
            updateArticleDetails(selectedArticleID, libelle, categorie, taille, couleur, poids, quantite);

            // Affichez un message de confirmation de modification
            String confirmationMessage = "L'article avec l'ID " + selectedArticleID + " a été modifié avec succès.";
            confirmationLabel.setText(confirmationMessage);

        } catch (NumberFormatException e) {
            // La quantité n'est pas un entier valide
            confirmationLabel.setText("La quantité doit être un nombre entier.");
        }
    }

    private void loadArticleIDsFromDatabase() {
        // Assurez-vous d'avoir une connexion à la base de données
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                // Exécutez une requête SQL pour récupérer les IDs des articles
                String sql = "SELECT article_id FROM article WHERE est_supprime = false"; // Vous devrez adapter cette requête en fonction de votre schéma de base de données
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                // Ajoutez les IDs récupérés à la ComboBox
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
                String sql = "SELECT * FROM article WHERE article_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, articleID);

                // Exécutez la requête SQL
                ResultSet resultSet = preparedStatement.executeQuery();

                // Si un résultat est trouvé, affichez les détails dans l'interface utilisateur
                if (resultSet.next()) {
                    libelleTextField.setText(resultSet.getString("libelle"));
                    categorieComboBox.setValue(resultSet.getString("categorie"));
                    tailleComboBox.setValue(resultSet.getString("taille"));
                    couleurComboBox.setValue(resultSet.getString("couleur"));
                    poidsTextField.setText(resultSet.getString("poids"));
                    quantiteTextField.setText(resultSet.getString("quantite"));
                } else {
                    // Aucun article trouvé avec cet ID, vous pouvez afficher un message d'erreur ou effectuer une action appropriée
                    System.err.println("Aucun article trouvé avec l'ID : " + articleID);
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


    private void updateArticleDetails(Integer articleID, String libelle, String categorie, String taille, String couleur, String poids, int quantite) {
        // Établir la connexion à la base de données
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                // Construisez la requête SQL pour mettre à jour les détails de l'article en fonction de l'ID
                String sql = "UPDATE article SET libelle = ?, categorie = ?, taille = ?, couleur = ?, poids = ?, quantite = ? WHERE article_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, libelle);
                preparedStatement.setString(2, categorie);
                preparedStatement.setString(3, taille);
                preparedStatement.setString(4, couleur);
                preparedStatement.setString(5, poids);
                preparedStatement.setInt(6, quantite);
                preparedStatement.setInt(7, articleID);

                // Exécutez la requête SQL pour mettre à jour les détails de l'article
                int rowsUpdated = preparedStatement.executeUpdate();

                // Vérifiez si la mise à jour a réussi
                if (rowsUpdated > 0) {
                    // La mise à jour a réussi, vous pouvez afficher un message de confirmation
                    String confirmationMessage = "L'article avec l'ID " + articleID + " a été mis à jour avec succès.";
                    confirmationLabel.setText(confirmationMessage);
                } else {
                    // Aucune ligne mise à jour, cela peut signifier que l'article avec cet ID n'existe pas
                    System.err.println("Aucun article trouvé avec l'ID : " + articleID);
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









    @FXML
    private Button buttonRetour; // Déclaration du bouton

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
