package hexlet.code;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.controller.UrlController;
import hexlet.code.repository.UrlRepository;
import hexlet.code.config.DatabaseConfig;

public class App {

    public static Javalin getApp() {
        // Создание экземпляра Javalin с конфигурацией
        Javalin app = Javalin.create(config -> {
            // Подключение движка JTE для рендеринга шаблонов
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        UrlRepository urlRepository = new UrlRepository(DatabaseConfig.createDataSource());
        UrlController urlController = new UrlController(urlRepository);

        // Регистрация маршрутов
        app.get("/urls/:id", urlController.showUrlHandler);
        app.post("/urls", urlController.createUrlHandler);

        return app;
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        // Получение порта из переменной окружения или установка по умолчанию
        String port = System.getenv("PORT");
        int portNumber = port != null ? Integer.parseInt(port) : 7000;
        app.start(portNumber);
    }
}
