package hexlet.code.config;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseInitializer {

    public static void initialize(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            String schemaSql = new String(Files.readAllBytes(Paths.get("app/src/main/resources/schema.sql")));

            statement.execute(schemaSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
