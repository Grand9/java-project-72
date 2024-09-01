package hexlet.code;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import hexlet.code.controller.UrlController;
import hexlet.code.repository.UrlRepository;
import hexlet.code.config.DatabaseConfig;

public class App {

    private static final int PORT = 7070;

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(PORT);
    }

    public static Javalin getApp() {
        Javalin app = Javalin.create(config -> {
            // Настройка шаблонизатора JTE
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        UrlRepository urlRepository = new UrlRepository(DatabaseConfig.createDataSource());
        UrlController urlController = new UrlController(urlRepository);

        app.get("/urls/{id}", urlController.showUrlHandler);
        app.post("/urls", urlController.createUrlHandler);

        return app;
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }
}
