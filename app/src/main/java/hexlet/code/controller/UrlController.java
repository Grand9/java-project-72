package hexlet.code.controller;

import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlController {

    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);
    private final UrlRepository urlRepository;

    public UrlController(UrlRepository urlRepository) {
        if (urlRepository == null) {
            throw new IllegalArgumentException("UrlRepository cannot be null");
        }
        this.urlRepository = urlRepository;
    }

    public Handler showFormHandler = ctx -> {
        String flashMessage = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");
        if (flashMessage == null) flashMessage = "";
        if (flashType == null) flashType = "info";

        Map<String, Object> model = new HashMap<>();
        model.put("flashMessage", flashMessage);
        model.put("flashType", flashType);
        ctx.render("index.jte", model);
    };

    public Handler createUrlHandler = ctx -> {
        String urlInput = ctx.formParam("url");
        String message;
        String type;

        if (urlInput == null || urlInput.isEmpty()) {
            message = "URL не может быть пустым";
            type = "error";
        } else {
            try {
                URI uri = new URI(urlInput);
                String domain = String.format("%s://%s%s",
                        uri.getScheme(), uri.getAuthority(), uri.getPath() != null ? uri.getPath() : "");

                LocalDateTime now = LocalDateTime.now();
                Url urlObject = new Url(domain, now);

                if (urlRepository.existsByName(domain)) {
                    message = "Страница уже существует";
                    type = "error";
                } else {
                    urlRepository.save(urlObject);
                    message = "Страница успешно добавлена";
                    type = "success";
                }
            } catch (URISyntaxException e) {
                message = "Некорректный URL";
                type = "error";
                logger.error("Error processing URL: {}", urlInput, e);
            }
            ctx.sessionAttribute("flashType", type);
            ctx.sessionAttribute("flash", message);
            ctx.redirect(NamedRoutes.homePath());
            return;
        }

        ctx.redirect(NamedRoutes.homePath() + "?message=" + message + "&type=" + type);
    };

    public Handler listUrlsHandler = ctx -> {
        List<Url> urls = urlRepository.findAll();
        String flashMessage = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");
        if (flashMessage == null) flashMessage = "";
        if (flashType == null) flashType = "info";

        UrlsPage page = new UrlsPage(urls, flashMessage, flashType);
        ctx.render("urls.jte", Map.of("page", page));
    };

    public Handler showUrlHandler = ctx -> {
        Url url;
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            url = urlRepository.findById(id);
        } catch (NumberFormatException e) {
            ctx.status(400);
            ctx.render("400.jte");
            return;
        }

        if (url == null) {
            ctx.status(404);
            ctx.render("404.jte");
            return;
        }

        ctx.render("url.jte", Map.of("url", url));
    };
}
