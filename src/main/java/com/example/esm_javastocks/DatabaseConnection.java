package com.example.esm_javastocks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "javastocks";
        String databaseUser = "postgres";
        String databasePassword = "root";
        String url = "jdbc:postgresql://localhost:5432/" + databaseName;

        try {
            Class.forName("org.postgresql.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return databaseLink;
    }

    public void closeConnection() {
        try {
            if (databaseLink != null) {
                databaseLink.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
