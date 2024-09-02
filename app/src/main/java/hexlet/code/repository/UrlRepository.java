package hexlet.code.repository;

import hexlet.code.model.Url;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UrlRepository {

    private final DataSource dataSource;

    public UrlRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean urlExists(String domain) throws SQLException {
        String query = "SELECT COUNT(*) FROM urls WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, domain);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        }
    }

    public void addUrl(Url url) throws SQLException {
        String query = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, url.getName());
            statement.setTimestamp(2, Timestamp.valueOf(url.getCreatedAt()));
            statement.executeUpdate();
        }
    }

    public List<Url> getAllUrls() throws SQLException {
        String query = "SELECT id, name, created_at FROM urls";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            List<Url> urls = new ArrayList<>();
            while (resultSet.next()) {
                urls.add(new Url(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                ));
            }
            return urls;
        }
    }

    public Url getUrlById(int id) throws SQLException {
        String query = "SELECT id, name, created_at FROM urls WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Url(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                );
            }
            return null;
        }
    }
}
