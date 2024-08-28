package hexlet.code;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static Javalin getApp() {
        return Javalin.create(config -> {
        });
    }

    public static void main(String[] args) {
        Javalin app = getApp();

        app.get("/", ctx -> {
            logger.info("Request received at /");
            ctx.result("Hello World");
        });

        app.start(7070);
    }
}
