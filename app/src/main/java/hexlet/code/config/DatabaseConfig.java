package hexlet.code.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {

    public static DataSource createDataSource() {
        String jdbcUrl = "jdbc:postgresql://dpg-cr82he3tq21c739iksv0-a:5432/"
                + "database_hexlet?user=grand_hexlet&password=tnkB7aEoSf1TcQuSKCfokHjYyVjGRFn1";
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName("org.postgresql.Driver");

        return new HikariDataSource(config);
    }
}
