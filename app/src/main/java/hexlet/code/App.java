package hexlet.code;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.config.DatabaseConfig;
import hexlet.code.controller.UrlController;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        DataSource dataSource = DatabaseConfig.createDataSource();

        try {
            initializeDatabase(dataSource);
        } catch (SQLException e) {
            LOGGER.error("Failed to initialize database", e);
            System.exit(1);
        }

        Javalin app = getApp();
        app.start(7070);
    }

    @SneakyThrows
    public static Javalin getApp() {
        UrlRepository urlRepository = new UrlRepository();
        UrlController urlController = new UrlController(urlRepository);

        Javalin app = Javalin.create(config -> config.fileRenderer(new JavalinJte(createTemplateEngine())));

        // Регистрация маршрутов
        app.get("/", urlController.showFormHandler);
        app.post("/urls", urlController.createUrlHandler);
        app.get("/urls", urlController.listUrlsHandler);
        app.get("/urls/{id}", urlController.showUrlHandler);

        return app;
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    private static void initializeDatabase(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            InputStream inputStream = App.class.getClassLoader().getResourceAsStream("schema.sql");
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: schema.sql");
            }

            String schemaSql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            statement.execute(schemaSql);

            LOGGER.info("Database initialized successfully");
        } catch (SQLException e) {
            throw new SQLException("Error initializing the database", e);
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred", e);
            throw new SQLException("An unexpected error occurred", e);
        }
    }
}
