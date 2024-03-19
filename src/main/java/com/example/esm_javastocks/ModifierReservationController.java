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
public class ModifierReservationController {

    @FXML
    private ComboBox<Integer> idReservationComboBox;
    @FXML
    private ComboBox<Coureur> coureurComboBox;
    @FXML
    private ComboBox<TypeEpreuve> typeEpreuveComboBox;
    @FXML
    private ComboBox<Article> idComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField quantiteTextField;
    @FXML
    private Label confirmationLabel;
    @FXML
    private Button buttonRetour;

    @FXML
    protected void initialize() {
        loadReservationIDsFromDatabase();

        idReservationComboBox.setOnAction(event -> {
            Integer selectedReservationID = idReservationComboBox.getValue();
            loadReservationDetails(selectedReservationID);
        });

        loadCoureursFromDatabase();
        loadTypesEpreuveFromDatabase();
        loadArticlesFromDatabase();

    }

    private void loadReservationIDsFromDatabase() {
        // Assurez-vous d'avoir une connexion à la base de données
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                String sql = "SELECT reservation_id FROM reserver";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    int reservationID = resultSet.getInt("reservation_id");
                    idReservationComboBox.getItems().add(reservationID);
                }

                databaseConnection.closeConnection();

                idReservationComboBox.getItems().sort(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    private void loadReservationDetails(Integer reservationID) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                String sql = "SELECT reserver.*, coureur.coureur_nom, coureur.coureur_prenom, type_epreuve.type_epreuve_libelle, article.libelle\n" +
                        "FROM reserver\n" +
                        "JOIN coureur ON reserver.coureur_id = coureur.coureur_id\n" +
                        "JOIN type_epreuve ON reserver.type_epreuve_id = type_epreuve.type_epreuve_id\n" +
                        "JOIN article ON reserver.article_id = article.article_id\n" +
                        "WHERE reservation_id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, reservationID);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {

                    coureurComboBox.setValue(new Coureur(resultSet.getInt("coureur_id"), resultSet.getString("coureur_nom"), resultSet.getString("coureur_prenom")));
                    typeEpreuveComboBox.setValue(new TypeEpreuve(resultSet.getInt("type_epreuve_id"), resultSet.getString("type_epreuve_libelle")));
                    idComboBox.setValue(new Article(resultSet.getInt("article_id"), resultSet.getString("libelle")));
                    datePicker.setValue(resultSet.getDate("date_reservation").toLocalDate());
                    quantiteTextField.setText(String.valueOf(resultSet.getInt("quantite_reservee")));
                } else {
                    clearFields();
                    confirmationLabel.setText("Aucune réservation trouvée avec cet ID.");
                }

                databaseConnection.closeConnection();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Échec de la connexion à la base de données.");
        }
    }


    private void loadCoureursFromDatabase() {
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

                    coureurComboBox.getItems().add(new Coureur(coureurID, nom, prenom));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnection.closeConnection();
            }
        } else {
            System.err.println("Échec de la connexion à la base de données.");
        }
    }

    private void loadTypesEpreuveFromDatabase() {
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

                    typeEpreuveComboBox.getItems().add(new TypeEpreuve(typeEpreuveID, libelle));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnection.closeConnection();
            }
        } else {
            System.err.println("Échec de la connexion à la base de données.");
        }
    }

    private void loadArticlesFromDatabase() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                String sql = "SELECT article_id, libelle FROM article WHERE est_supprime = false";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    int articleID = resultSet.getInt("article_id");
                    String libelle = resultSet.getString("libelle");

                    idComboBox.getItems().add(new Article(articleID, libelle));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnection.closeConnection();
            }
        } else {
            System.err.println("Échec de la connexion à la base de données.");
        }
    }


    @FXML
    protected void onModifierButtonClick() {
        Integer selectedReservationID = idReservationComboBox.getValue();
        Coureur selectedCoureur = coureurComboBox.getValue();
        TypeEpreuve selectedTypeEpreuve = typeEpreuveComboBox.getValue();
        Article selectedArticle = idComboBox.getValue();
        LocalDate selectedDate = datePicker.getValue();
        String quantiteString = quantiteTextField.getText();

        if (selectedReservationID != null && selectedCoureur != null && selectedTypeEpreuve != null
                && selectedArticle != null && selectedDate != null && !quantiteString.isEmpty()) {
            try {
                int quantite = Integer.parseInt(quantiteString);

                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection connection = databaseConnection.getConnection();

                if (connection != null) {
                    try {
                        String sql = "UPDATE reserver SET coureur_id = ?, type_epreuve_id = ?, article_id = ?, date_reservation = ?, quantite_reservee = ? WHERE reservation_id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setInt(1, selectedCoureur.getCoureurID());
                        preparedStatement.setInt(2, selectedTypeEpreuve.getTypeEpreuveID());
                        preparedStatement.setInt(3, selectedArticle.getArticleID());
                        preparedStatement.setDate(4, Date.valueOf(selectedDate));
                        preparedStatement.setInt(5, quantite);
                        preparedStatement.setInt(6, selectedReservationID);

                        int rowsUpdated = preparedStatement.executeUpdate();

                        if (rowsUpdated > 0) {
                            confirmationLabel.setText("La réservation avec l'ID " + selectedReservationID + " a été modifiée avec succès.");
                        } else {
                            confirmationLabel.setText("Aucune réservation trouvée avec cet ID.");
                        }

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
                }

            } catch (NumberFormatException e) {
                confirmationLabel.setText("La quantité doit être un nombre entier.");
            }

        } else {
            confirmationLabel.setText("Veuillez remplir tous les champs.");
        }
    }


    private void clearFields() {
        coureurComboBox.setValue(null);
        typeEpreuveComboBox.setValue(null);
        idComboBox.setValue(null);
        datePicker.setValue(null);
        quantiteTextField.clear();
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



    class Coureur implements Comparable<Coureur> {
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

        @Override
        public int compareTo(Coureur otherCoureur) {
            return Integer.compare(this.coureurID, otherCoureur.coureurID);
        }

        public int getCoureurID() {
            return this.coureurID;
        }
    }

    class TypeEpreuve implements Comparable<TypeEpreuve> {
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

        @Override
        public int compareTo(TypeEpreuve otherTypeEpreuve) {
            return Integer.compare(this.typeEpreuveID, otherTypeEpreuve.typeEpreuveID);
        }

        public int getTypeEpreuveID() {
            return this.typeEpreuveID;
        }
    }

    class Article implements Comparable<Article> {
        private int articleID;
        private String libelle;

        public Article(int articleID, String libelle) {
            this.articleID = articleID;
            this.libelle = libelle;
        }

        @Override
        public int compareTo(Article otherArticle) {
            return Integer.compare(this.articleID, otherArticle.articleID);
        }

        @Override
        public String toString() {
            return articleID + " - " + libelle;
        }

        public int getArticleID() {
            return this.articleID;
        }
    }
}
