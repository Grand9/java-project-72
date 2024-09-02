package hexlet.code;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.controller.UrlController;
import hexlet.code.repository.UrlRepository;
import hexlet.code.config.DatabaseConfig;

public class App {

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7000);
    }

    public static Javalin getApp() {
        // Initialize Javalin app
        Javalin app = Javalin.create(config -> {
            // Configure static file handling
            config.staticFiles.add("/public", Location.CLASSPATH);
            // Configure JTE template engine
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        // Initialize repository and controller
        UrlRepository urlRepository = new UrlRepository(DatabaseConfig.getDataSource());
        UrlController urlController = new UrlController(urlRepository);

        // Define routes
        app.get("/", ctx -> ctx.render("index.jte"));
        app.post("/urls", urlController::addUrlHandler);
        app.get("/urls", urlController::listUrlsHandler);
        app.get("/urls/:id", urlController::showUrlHandler);

        return app;
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }
}
