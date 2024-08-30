package hexlet.code;

import hexlet.code.config.DatabaseConfig;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static Javalin getApp() {
        var app = Javalin.create(config -> config.fileRenderer(new JavalinJte()));

        app.get("/", ctx -> {
            LOGGER.info("Request received at /");
            ctx.result("Hello World");
        });

        return app;
    }

    public static void main(String[] args) {
        DataSource dataSource = DatabaseConfig.createDataSource();

        initializeDatabase(dataSource);

        Javalin app = getApp();
        app.start(7070);
    }

    private static void initializeDatabase(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            InputStream inputStream = App.class.getClassLoader().getResourceAsStream("schema.sql");
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: schema.sql");
            }

            String schemaSql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            statement.execute(schemaSql);

            LOGGER.info("Database initialized successfully");
        } catch (Exception e) {
            LOGGER.error("Failed to initialize database", e);
        }
    }

}
