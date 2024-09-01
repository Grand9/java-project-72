package hexlet.code.controller;

import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Handler;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class UrlController {

    private UrlRepository urlRepository;

    public UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Handler showFormHandler = ctx -> {
        String message = ctx.queryParam("message");
        String type = ctx.queryParam("type");

        ctx.render("index.jte", Map.of(
                "flashMessage", message != null ? message : "",
                "flashType", type != null ? type : "info"
        ));
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
                URL url = uri.toURL();
                String domain = String.format("%s://%s%s", url.getProtocol(), url.getHost(),
                        url.getPort() != -1 ? ":" + url.getPort() : "");

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
            } catch (Exception e) {
                message = "Некорректный URL";
                type = "error";
            }
            ctx.redirect(NamedRoutes.homePath() + "?message=" + message + "&type=" + type);
        }
    };

    public Handler listUrlsHandler = ctx -> {
        List<Url> urls = urlRepository.findAll();
        String flashMessage = ctx.queryParam("message");
        String flashType = ctx.queryParam("type");

        UrlsPage page = new UrlsPage(urls, flashMessage, flashType);
        ctx.render("urls.jte", Map.of("page", page));
    };

    public Handler showUrlHandler = ctx -> {
        Url url;
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            url = urlRepository.findById(id);
        } catch (Exception e) {
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
