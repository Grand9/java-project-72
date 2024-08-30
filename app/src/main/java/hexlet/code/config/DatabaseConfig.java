package hexlet.code.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {

    public static DataSource createDataSource() {
        String jdbcUrl = System.getenv("JDBC_DATABASE_URL");
        
        if (jdbcUrl == null || jdbcUrl.isEmpty()) {
            throw new IllegalStateException("JDBC_DATABASE_URL environment variable is not set");
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName("org.postgresql.Driver");

        return new HikariDataSource(config);
    }
}
