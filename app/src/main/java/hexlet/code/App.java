package hexlet.code;

import io.javalin.Javalin;
import hexlet.code.controller.UrlController;
import hexlet.code.repository.UrlRepository;
import hexlet.code.config.DatabaseConfig;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.rendering.template.JavalinJte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static Javalin getApp() {
        UrlRepository urlRepository = new UrlRepository(DatabaseConfig.createDataSource());
        UrlController urlController = new UrlController(urlRepository);

        Javalin app = Javalin.create(config -> {
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get("/", urlController.listUrlsHandler);
        app.get("/urls/{id}", urlController.showUrlHandler);
        app.post("/urls", urlController.createUrlHandler);

        return app;
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    private static int getPort() {
        String port = System.getenv("PORT");
        return port != null ? Integer.parseInt(port) : 7000;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(getPort());
        logger.info("Server started on port " + getPort());
    }
}
