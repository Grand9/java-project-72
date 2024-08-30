package hexlet.code.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {

    public static DataSource createDataSource() {
        String jdbcUrl = "jdbc:postgresql://dpg-cr8ntrd6l47c73bmhkeg-a:5432/"
                + "grand_db?user=grand&password=3CYROPcoXXSOBszgyI1LkaGq9gH1FeK9";
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName("org.postgresql.Driver");

        return new HikariDataSource(config);
    }
}
