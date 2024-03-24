package com.example.esm_javastocks;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ConsulterReservationController {
    @FXML
    private ComboBox<Integer> idComboBox; // ComboBox pour choisir la réservation
    @FXML
    private Label consultReserveIDarticle; // Label pour afficher l'ID de la réservation
    @FXML
    private Label consultReservelibelle; // Label pour afficher la date
    @FXML
    private Label consultReservecoureur; // Label pour afficher le coureur
    @FXML
    private Label consultReservetypeEpreuve; // Label pour afficher le type d'épreuve
    @FXML
    private Label consultReservedate; // Label pour afficher la date de réservation
    @FXML
    private Label consultReservequantite; // Label pour afficher la quantité
    @FXML
    private Button buttonRetour;

    @FXML
    protected void initialize() {
        // Initialisez la liste déroulante des IDs de réservation au démarrage de l'application
        loadReservationIDsFromDatabase();
    }

    @FXML
    protected void onConsultButtonClick() {
        // Récupérez l'ID de la réservation sélectionnée
        Integer selectedReservationID = idComboBox.getValue();
        if (selectedReservationID != null) {
            // Chargez et affichez les détails de la réservation correspondante depuis la base de données
            loadReservationDetails(selectedReservationID);
        }
    }

    private void loadReservationIDsFromDatabase() {
        // Assurez-vous d'avoir une connexion à la base de données
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                // Exécutez une requête SQL pour récupérer les IDs des réservations
                String sql = "SELECT reservation_id FROM reservation";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                // Ajoutez les IDs récupérés à la ComboBox idComboBox
                while (resultSet.next()) {
                    int reservationID = resultSet.getInt("reservation_id");
                    idComboBox.getItems().add(reservationID);
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

    private void loadReservationDetails(Integer reservationID) {
        // Établir la connexion à la base de données
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                // Construisez la requête SQL pour récupérer les détails de la réservation en fonction de l'ID
                String sql = "SELECT reservation.*, coureur.coureur_nom, coureur.coureur_prenom, type_epreuve.type_epreuve_libelle, article.libelle\n" +
                        "FROM reservation\n" +
                        "JOIN coureur ON reservation.coureur_id = coureur.coureur_id\n" +
                        "JOIN type_epreuve ON reservation.type_epreuve_id = type_epreuve.type_epreuve_id\n" +
                        "JOIN article ON reservation.article_id = article.article_id\n" +
                        "WHERE reservation_id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, reservationID);

                // Exécutez la requête SQL
                ResultSet resultSet = preparedStatement.executeQuery();

                // Si un résultat est trouvé, affichez les détails dans les Labels correspondants
                if (resultSet.next()) {
                    consultReserveIDarticle.setText(String.valueOf(resultSet.getInt("article_id")));
                    consultReservelibelle.setText(resultSet.getString("libelle"));
                    String coureurNomPrenom = resultSet.getString("coureur_nom") + " " + resultSet.getString("coureur_prenom");
                    consultReservecoureur.setText(coureurNomPrenom);
                    consultReservetypeEpreuve.setText(resultSet.getString("type_epreuve_libelle"));
                    consultReservedate.setText(resultSet.getString("date_reservation"));
                    consultReservequantite.setText(resultSet.getString("quantite_reservee"));
                } else {
                    // Aucune réservation trouvée avec cet ID, vous pouvez afficher un message d'erreur ou effectuer une action appropriée
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

    // Méthode pour effacer le contenu des Labels
    private void clearLabels() {
        consultReserveIDarticle.setText("");
        consultReservelibelle.setText("");
        consultReservecoureur.setText("");
        consultReservetypeEpreuve.setText("");
        consultReservedate.setText("");
        consultReservequantite.setText("");
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
}
