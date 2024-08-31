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
            throw new IllegalStateException("Environment variable " + JDBC_URL_ENV_VAR + " is not set");
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000);

        return new HikariDataSource(config);
    }
}
