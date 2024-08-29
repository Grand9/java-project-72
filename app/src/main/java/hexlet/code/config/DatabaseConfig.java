package hexlet.code.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {

    public static DataSource createDataSource() {
        String jdbcUrl = System.getenv("JDBC_DATABASE_URL");
        HikariConfig config = new HikariConfig();

        if (jdbcUrl == null || jdbcUrl.isEmpty()) {
            config.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1");
            config.setDriverClassName("org.h2.Driver");
            config.setUsername("sa");
            config.setPassword("");
        } else {
            config.setJdbcUrl(jdbcUrl);
            config.setDriverClassName("org.postgresql.Driver");
        }

        return new HikariDataSource(config);
    }
}
