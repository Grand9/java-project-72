package hexlet.code.repository;

import hexlet.code.model.Url;
import hexlet.code.config.DatabaseConfig;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UrlRepository {

    private final Connection connection;

    public UrlRepository() throws SQLException {
        this.connection = DatabaseConfig.createDataSource().getConnection();
    }

    public void save(Url url) throws SQLException {
        String query = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, Timestamp.valueOf(url.getCreatedAt()));
            stmt.executeUpdate();
        }
    }

    public boolean existsByName(String name) throws SQLException {
        String query = "SELECT COUNT(*) FROM urls WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public List<Url> findAll() throws SQLException {
        String query = "SELECT * FROM urls ORDER BY created_at DESC";
        List<Url> urls = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("created_at");
                LocalDateTime createdAt = timestamp.toLocalDateTime();
                urls.add(new Url(rs.getInt("id"), rs.getString("name"), createdAt));
            }
        }
        return urls;
    }

    public Url findById(int id) throws SQLException {
        String query = "SELECT * FROM urls WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp("created_at");
                    LocalDateTime createdAt = timestamp.toLocalDateTime();
                    return new Url(rs.getInt("id"), rs.getString("name"), createdAt);
                }
            }
        }
        return null;
    }
}
