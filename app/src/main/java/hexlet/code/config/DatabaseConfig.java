package hexlet.code.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public final class DatabaseConfig {

    private static final String JDBC_URL_ENV_VAR = "JDBC_DATABASE_URL";

    private DatabaseConfig() {
    }

    public static DataSource createDataSource() {
        String jdbcUrl = System.getenv(JDBC_URL_ENV_VAR);
        if (jdbcUrl == null || jdbcUrl.isEmpty()) {
            // Используем H2 по умолчанию
            jdbcUrl = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1";
            // Настройка H2
            return createH2DataSource(jdbcUrl);
        }

        // Настройка PostgreSQL
        return createPostgresDataSource(jdbcUrl);
    }

    private static DataSource createH2DataSource(String jdbcUrl) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName("org.h2.Driver");
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        return new HikariDataSource(config);
    }

    private static DataSource createPostgresDataSource(String jdbcUrl) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        return new HikariDataSource(config);
    }
}
