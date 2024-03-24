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

public class SupprimerReservationController {

    @FXML
    private ComboBox<Integer> idComboBox; // ComboBox pour choisir la réservation
    @FXML
    private Label supprimReserveIDarticle; // Label pour afficher l'ID de la réservation
    @FXML
    private Label supprimReservelibelle; // Label pour afficher la date
    @FXML
    private Label supprimReservecoureur; // Label pour afficher le coureur
    @FXML
    private Label supprimReservetypeEpreuve; // Label pour afficher le type d'épreuve
    @FXML
    private Label supprimReservedate; // Label pour afficher la date de réservation
    @FXML
    private Label supprimReservequantite; // Label pour afficher la quantité
    @FXML
    private Label confirmationLabel;
    @FXML
    private Button buttonRetour;

    private DatabaseConnection databaseConnection;

    @FXML
    protected void initialize() {
        databaseConnection = new DatabaseConnection();
        loadReservationIDsFromDatabase();

        idComboBox.setOnAction(event -> {
            Integer selectedReservationID = idComboBox.getValue();
            loadReservationDetails(selectedReservationID);
        });
    }

    private void loadReservationIDsFromDatabase() {
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                String sql = "SELECT reservation_id FROM reservation";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int reservationID = resultSet.getInt("reservation_id");
                    idComboBox.getItems().add(reservationID);
                }

                databaseConnection.closeConnection();

                idComboBox.getItems().sort(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadReservationDetails(Integer reservationID) {
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            try {
                String sql = "SELECT reservation.*, coureur.coureur_nom, coureur.coureur_prenom, type_epreuve.type_epreuve_libelle, article.libelle\n" +
                        "FROM reservation\n" +
                        "JOIN coureur ON reservation.coureur_id = coureur.coureur_id\n" +
                        "JOIN type_epreuve ON reservation.type_epreuve_id = type_epreuve.type_epreuve_id\n" +
                        "JOIN article ON reservation.article_id = article.article_id\n" +
                        "WHERE reservation_id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, reservationID);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    supprimReserveIDarticle.setText(String.valueOf(resultSet.getInt("article_id")));
                    supprimReservelibelle.setText(resultSet.getString("libelle"));
                    supprimReservecoureur.setText(resultSet.getString("coureur_nom") + " " + resultSet.getString("coureur_prenom"));
                    supprimReservetypeEpreuve.setText(resultSet.getString("type_epreuve_libelle"));
                    supprimReservedate.setText(String.valueOf(resultSet.getDate("date_reservation")));
                    supprimReservequantite.setText(String.valueOf(resultSet.getInt("quantite_reservee")));
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

    private void clearFields() {
        supprimReserveIDarticle.setText("");
        supprimReservelibelle.setText("");
        supprimReservecoureur.setText("");
        supprimReservetypeEpreuve.setText("");
        supprimReservedate.setText("");
        supprimReservequantite.setText("");
    }

    @FXML
    protected void onSupprimerButtonClick() {
        Integer selectedReservationID = idComboBox.getValue();

        if (selectedReservationID != null) {
            Connection connection = databaseConnection.getConnection();

            if (connection != null) {
                try {
                    String sql = "DELETE FROM reservation WHERE reservation_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, selectedReservationID);

                    int rowsDeleted = preparedStatement.executeUpdate();

                    if (rowsDeleted > 0) {
                        confirmationLabel.setText("La réservation avec l'ID " + selectedReservationID + " a été supprimée avec succès.");
                        clearFields();
                    } else {
                        confirmationLabel.setText("Aucune réservation trouvée avec cet ID.");
                    }

                    databaseConnection.closeConnection();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Échec de la connexion à la base de données.");
            }
        } else {
            confirmationLabel.setText("Veuillez sélectionner un ID de réservation.");
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
}
