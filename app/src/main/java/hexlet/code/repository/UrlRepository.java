package hexlet.code.repository;

import hexlet.code.model.Url;
import hexlet.code.config.DatabaseConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UrlRepository {

    private final DataSource dataSource;

    public UrlRepository() {
        this.dataSource = DatabaseConfig.createDataSource();
    }

    public void save(Url url) {
        String query = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, Timestamp.valueOf(url.getCreatedAt()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Логирование ошибки или дополнительная обработка
            throw new RuntimeException("Error saving URL to database", e);
        }
    }

    public boolean existsByName(String name) {
        String query = "SELECT COUNT(*) FROM urls WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            // Логирование ошибки или дополнительная обработка
            throw new RuntimeException("Error checking if URL exists", e);
        }
        return false;
    }

    public List<Url> findAll() {
        String query = "SELECT * FROM urls ORDER BY created_at DESC";
        List<Url> urls = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("created_at");
                LocalDateTime createdAt = timestamp.toLocalDateTime();
                urls.add(new Url(rs.getInt("id"), rs.getString("name"), createdAt));
            }
        } catch (SQLException e) {
            // Логирование ошибки или дополнительная обработка
            throw new RuntimeException("Error finding all URLs", e);
        }
        return urls;
    }

    public Url findById(int id) {
        String query = "SELECT * FROM urls WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp("created_at");
                    LocalDateTime createdAt = timestamp.toLocalDateTime();
                    return new Url(rs.getInt("id"), rs.getString("name"), createdAt);
                }
            }
        } catch (SQLException e) {
            // Логирование ошибки или дополнительная обработка
            throw new RuntimeException("Error finding URL by ID", e);
        }
        return null;
    }
}
