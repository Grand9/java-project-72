package hexlet.code.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {

    public static DataSource createDataSource() {
        String jdbcUrl = "postgresql://grand_hexlet:tnkB7aEoSf1TcQuSKCfokHjYyVjGRFn1@"
                + "dpg-cr82he3tq21c739iksv0-a/"
                + "database_hexlet";
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName("org.postgresql.Driver");

        return new HikariDataSource(config);
    }
}
