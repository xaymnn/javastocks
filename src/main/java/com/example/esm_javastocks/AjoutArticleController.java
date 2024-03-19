package com.example.esm_javastocks;

import javafx.application.Platform;
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



public class AjoutArticleController {

    @FXML
    private TextField libelleTextField; // Champ de texte pour le libellé

    @FXML
    private ComboBox<String> categorieComboBox; // ComboBox pour la catégorie

    @FXML
    private ComboBox<String> tailleComboBox; // ComboBox pour la taille

    @FXML
    private ComboBox<String> couleurComboBox; // ComboBox pour les couleurs

    @FXML
    private TextField poidsTextField; // Champ de texte pour le poids
    @FXML
    private TextField quantiteTextField; // Champ de texte pour le quantite
    @FXML
    private Label confirmationLabel; // Référence au Label dans votre FXML


    @FXML
    protected void onAjouterButtonClick() {
        String libelle = libelleTextField.getText();
        String categorie = categorieComboBox.getValue();
        String taille = tailleComboBox.getValue();
        String couleur = couleurComboBox.getValue();
        String poids = poidsTextField.getText();
        String quantite = quantiteTextField.getText(); // Ajoutez le champ de texte pour la quantité dans votre interface

        // Vérifiez si les champs obligatoires sont vides
        if (libelle.isEmpty() || categorie == null || quantite.isEmpty()) {
            String errorMessage = "Les champs Libelle, Catégorie et Quantité sont obligatoires ! Veuillez les remplir.";
            confirmationLabel.setText(errorMessage);
        } else {
            try {
                // Convertissez la quantité en entier
                int quantiteInt = Integer.parseInt(quantite);

                // Établir la connexion à la base de données
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection connection = databaseConnection.getConnection();

                if (connection != null) {
                    try {
                        // Construisez la requête SQL en fonction des données fournies par l'utilisateur
                        String sql = "INSERT INTO Article (libelle, categorie, taille, couleur, poids, quantite) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                        preparedStatement.setString(1, libelle);
                        preparedStatement.setString(2, categorie);
                        preparedStatement.setString(3, taille);
                        preparedStatement.setString(4, couleur);
                        preparedStatement.setString(5, poids);
                        preparedStatement.setInt(6, quantiteInt); // Utilisez la version int

                        // Exécutez la requête
                        int rowsInserted = preparedStatement.executeUpdate();

                        if (rowsInserted > 0) {
                            System.out.println("L'article a été inséré avec succès dans la base de données.");

                            // Récupérez l'ID de l'article inséré
                            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                            if (generatedKeys.next()) {
                                int lastInsertedID = generatedKeys.getInt(1);
                                String confirmationMessage = "L'article a été ajouté avec succès ! Le numéro de l'article est le : " + lastInsertedID;
                                confirmationLabel.setText(confirmationMessage);
                            }

                        } else {
                            System.err.println("Erreur lors de l'insertion de l'article.");
                            // Gérez l'erreur ou affichez un message d'erreur
                        }

                        // Ferme la connexion
                        databaseConnection.closeConnection();

                    } catch (SQLException e) {
                        e.printStackTrace();
                        // Gérez les exceptions SQL ici
                    }
                } else {
                    System.err.println("Échec de la connexion à la base de données.");
                    // Gérez l'échec de la connexion ici
                }
            } catch (NumberFormatException e) {
                // La quantité n'est pas un entier valide
                confirmationLabel.setText("La quantité doit être un nombre entier.");
            }
        }
    }
    public void initialize() {
        // Ajoutez les catégories à la liste déroulante
        categorieComboBox.getItems().addAll("T", "B", "DS");

        // Définissez un texte d'invite par défaut
        categorieComboBox.setPromptText("Catégorie");
        // Initialisation de la ComboBox de taille
        tailleComboBox.getItems().addAll("XS", "S", "M", "L", "XL", "XXL");
        tailleComboBox.setPromptText("Taille");
        // Ajoutez les couleurs à la liste déroulante
        couleurComboBox.getItems().addAll("Blanche", "Rouge", "Bleue", "Verte", "Noire", "Jaune");

        // Définissez un texte d'invite par défaut
        couleurComboBox.setPromptText("Couleurs");
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