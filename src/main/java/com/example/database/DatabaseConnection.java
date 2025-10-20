package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.UUID;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/testmodbd";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    public static void insertMessage(UUID playerUuid, String text) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO messages (uuid, text) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setObject(1, playerUuid);
                stmt.setString(2, text);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
