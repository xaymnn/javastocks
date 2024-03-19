package com.example.esm_javastocks;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class AjoutReservationController {

    @FXML
    private ComboBox<Coureur> coureurComboBox;
    @FXML
    private ComboBox<TypeEpreuve> typeEpreuveComboBox;
    @FXML
    private ComboBox<Article> idComboBox;
    @FXML
    private DatePicker datePicker; // Ajout du DatePicker
    @FXML
    private TextField quantiteTextField; // Ajout du TextField
    @FXML
    private Button buttonRetour;
    @FXML
    private Label confirmationLabel;
    @FXML
    protected void initialize() {
        // Initialisez les listes déroulantes au démarrage de l'application
        loadCoureursFromDatabase();
        loadTypesEpreuveFromDatabase();
        loadArticlesFromDatabase();
    }
    @FXML
    protected void onAjouterButtonClick() {
        LocalDate selectedDate = datePicker.getValue();
        Coureur selectedCoureur = coureurComboBox.getValue();
        TypeEpreuve selectedTypeEpreuve = typeEpreuveComboBox.getValue();
        Article selectedArticle = idComboBox.getValue();
        String quantite = quantiteTextField.getText();

        if (selectedDate != null && selectedCoureur != null && selectedTypeEpreuve != null
                && selectedArticle != null && !quantite.isEmpty()) {
            // Vérifiez si la quantité est un entier valide
            try {
                int quantiteInt = Integer.parseInt(quantite);

                // Établir la connexion à la base de données
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection connection = databaseConnection.getConnection();

                if (connection != null) {
                    try {
                        // Obtenir le maximum actuel de reservation_id
                        String maxIdQuery = "SELECT MAX(reservation_id) FROM reserver";
                        PreparedStatement maxIdStatement = connection.prepareStatement(maxIdQuery);
                        ResultSet maxIdResultSet = maxIdStatement.executeQuery();
                        int maxReservationId = 0;

                        if (maxIdResultSet.next()) {
                            maxReservationId = maxIdResultSet.getInt(1);
                        }

                        // Incrémente manuellement l'ID
                        int nextReservationId = maxReservationId + 1;

                        // Construisez la requête SQL en fonction des données fournies par l'utilisateur
                        String sql = "INSERT INTO reserver (reservation_id, date_reservation, coureur_id, type_epreuve_id, article_id, quantite_reservee) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                        preparedStatement.setInt(1, nextReservationId);
                        preparedStatement.setDate(2, Date.valueOf(selectedDate));
                        preparedStatement.setInt(3, selectedCoureur.getCoureurID());
                        preparedStatement.setInt(4, selectedTypeEpreuve.getTypeEpreuveID());
                        preparedStatement.setInt(5, selectedArticle.getArticleID());
                        preparedStatement.setInt(6, quantiteInt);

                        // Exécutez la requête
                        int rowsInserted = preparedStatement.executeUpdate();

                        if (rowsInserted > 0) {
                            System.out.println("La réservation a été ajoutée avec succès dans la base de données.");

                            // Récupérez l'ID de la réservation insérée
                            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                            if (generatedKeys.next()) {
                                int lastInsertedID = generatedKeys.getInt(1);
                                String confirmationMessage = "La réservation a été ajoutée avec succès ! Le numéro de la réservation est le : " + lastInsertedID;
                                confirmationLabel.setText(confirmationMessage);
                            }

                        } else {
                            System.err.println("Erreur lors de l'insertion de la réservation.");
                            // Gérez l'erreur ou affichez un message d'erreur
                        }

                        // Ferme la connexion
                        databaseConnection.closeConnection();

                    } catch (SQLException e) {
                        // Gérez les exceptions SQL ici
                        String sqlState = e.getSQLState();
                        if ("45000".equals(sqlState) || "P0001".equals(sqlState)) {
                            // La quantité réservée est supérieure à la quantité disponible dans l'article
                            confirmationLabel.setText("La quantité réservée est supérieure à la quantité disponible dans l'article.");
                        } else {
                            e.printStackTrace();
                        }
                    }
                } else {
                    System.err.println("Échec de la connexion à la base de données.");
                    // Gérez l'échec de la connexion ici
                }

            } catch (NumberFormatException e) {
                // La quantité n'est pas un entier valide
                confirmationLabel.setText("La quantité doit être un nombre entier.");
            }

        } else {
            confirmationLabel.setText("Veuillez remplir tous les champs.");
        }
    }


    private void loadArticlesFromDatabase() {
        // Assurez-vous d'avoir une connexion à la base de données
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                // Exécutez une requête SQL pour récupérer les articles avec ID et libellé
                String sql = "SELECT article_id, libelle FROM Article WHERE est_supprime = false";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                // Ajoutez les articles à la ComboBox idComboBox
                while (resultSet.next()) {
                    int articleID = resultSet.getInt("article_id");
                    String libelle = resultSet.getString("libelle");
                    Article article = new Article(articleID, libelle);
                    idComboBox.getItems().add(article);
                }

                // Fermez la connexion
                databaseConnection.closeConnection();

                // Triez les articles dans la ComboBox
                idComboBox.getItems().sort(null);
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérez les exceptions SQL ici
            }

        }  else {
            System.err.println("Échec de la connexion à la base de données.");
            // Gérez l'échec de la connexion ici
    }

}

    private void loadCoureursFromDatabase() {
        // Assurez-vous d'avoir une connexion à la base de données
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {

            try {
                String sql = "SELECT coureur_id, coureur_nom, coureur_prenom FROM coureur";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    int coureurID = resultSet.getInt("coureur_id");
                    String nom = resultSet.getString("coureur_nom");
                    String prenom = resultSet.getString("coureur_prenom");

                    Coureur coureur = new Coureur(coureurID, nom, prenom);
                    // Ajoutez le coureur à la liste déroulante pour les coureurs
                    coureurComboBox.getItems().add(coureur);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            else {
            System.err.println("Échec de la connexion à la base de données.");
            // Gérez l'échec de la connexion ici


        }
    }

    private void loadTypesEpreuveFromDatabase() {
        // Assurez-vous d'avoir une connexion à la base de données
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {

            try {
                String sql = "SELECT type_epreuve_id, type_epreuve_libelle FROM type_epreuve";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    int typeEpreuveID = resultSet.getInt("type_epreuve_id");
                    String libelle = resultSet.getString("type_epreuve_libelle");

                    TypeEpreuve typeEpreuve = new TypeEpreuve(typeEpreuveID, libelle);
                    // Ajoutez le type d'épreuve à la liste déroulante pour les types d'épreuve
                    typeEpreuveComboBox.getItems().add(typeEpreuve);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Échec de la connexion à la base de données.");
            // Gérez l'échec de la connexion ici
        }

    }


    @FXML
    protected void onRetourButtonClick() {
        // Ferme la fenêtre actuelle
        Stage stage = (Stage) buttonRetour.getScene().getWindow();
        stage.close();

        // Charge et affiche la page du menu principal
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gestion-reservation.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage menuStage = new Stage();
            menuStage.setTitle("Gestion des réservations");
            menuStage.setScene(scene);
            menuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class Coureur implements Comparable<Coureur> {
        private int coureurID;
        private String nom;
        private String prenom;

        public Coureur(int coureurID, String nom, String prenom) {
            this.coureurID = coureurID;
            this.nom = nom;
            this.prenom = prenom;
        }

        @Override
        public String toString() {
            return nom + " " + prenom;
        }

        // Getters et setters (ajoutez-les selon vos besoins)

        @Override
        public int compareTo(Coureur otherCoureur) {
            // Comparez les coureurs par ID
            return Integer.compare(this.coureurID, otherCoureur.coureurID);
        }
        public int getCoureurID() {
            return this.coureurID;
        }
    }

    public class TypeEpreuve implements Comparable<TypeEpreuve> {
        private int typeEpreuveID;
        private String libelle;

        public TypeEpreuve(int typeEpreuveID, String libelle) {
            this.typeEpreuveID = typeEpreuveID;
            this.libelle = libelle;
        }

        @Override
        public String toString() {
            return libelle;
        }

        // Getters et setters (ajoutez-les selon vos besoins)

        @Override
        public int compareTo(TypeEpreuve otherTypeEpreuve) {
            // Comparez les types d'épreuve par ID
            return Integer.compare(this.typeEpreuveID, otherTypeEpreuve.typeEpreuveID);
        }
        public int getTypeEpreuveID() {
            return this.typeEpreuveID;
        }
    }


    public class Article implements Comparable<Article> {
        private int articleID;
        private String libelle;

        // Constructeur
        public Article(int articleID, String libelle) {
            this.articleID = articleID;
            this.libelle = libelle;
        }

        // Getters et setters (ajoutez-les selon vos besoins)

        @Override
        public int compareTo(Article otherArticle) {
            // Comparez les articles par ID
            return Integer.compare(this.articleID, otherArticle.articleID);
        }

        @Override
        public String toString() {
            // Déterminez comment l'article est affiché dans la ComboBox
            return articleID + " - " + libelle;
        }

        public int getArticleID() {
            return this.articleID;
        }
    }
}