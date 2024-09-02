package hexlet.code.repository;

import hexlet.code.model.Url;

import hexlet.code.model.UrlCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UrlRepository {

    private static final Logger logger = LoggerFactory.getLogger(UrlRepository.class);
    private final DataSource dataSource;

    public UrlRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Url url) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO urls (name, created_at) VALUES (?, ?) ON CONFLICT (name) DO NOTHING")) {
            ps.setString(1, url.getName());
            ps.setTimestamp(2, Timestamp.valueOf(url.getCreatedAt()));
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error saving URL: " + url, e);
            throw e; // Propagate exception to be handled by caller
        }
    }

    public Url getUrlById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM urls WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Url(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("created_at").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching URL by ID: " + id, e);
        }
        return null;
    }

    public Url getUrlByName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM urls WHERE name = ?")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Url(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("created_at").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching URL by name: " + name, e);
        }
        return null;
    }

    public List<Url> getAllUrls() {
        List<Url> urls = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM urls")) {
            while (rs.next()) {
                urls.add(new Url(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("created_at").toLocalDateTime()));
            }
        } catch (SQLException e) {
            logger.error("Error fetching all URLs", e);
        }
        return urls;
    }

    public List<UrlCheck> getUrlChecks(int urlId) {
        List<UrlCheck> checks = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM url_checks WHERE url_id = ?")) {
            ps.setInt(1, urlId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UrlCheck check = new UrlCheck(
                            rs.getInt("id"),
                            rs.getInt("url_id"),
                            rs.getInt("status_code"),
                            rs.getString("title"),
                            rs.getString("h1"),
                            rs.getString("description"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    checks.add(check);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checks;
    }
}
