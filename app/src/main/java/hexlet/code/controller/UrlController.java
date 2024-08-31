package hexlet.code.controller;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Handler;

import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
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
        Map<String, Object> model = new HashMap<>();
        model.put("flashMessage", message != null ? message : "");
        model.put("flashType", type != null ? type : "info");
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
            ctx.redirect("/?message=" + message + "&type=" + type);
        }
    };

    public Handler listUrlsHandler = ctx -> {
        List<Url> urls;
        try {
            urls = urlRepository.findAll();
        } catch (SQLException e) {
            ctx.redirect("/?message=Ошибка%20при%20извлечении%20данных%20из%20базы%20данных&type=error");
            return;
        }
        Map<String, Object> model = new HashMap<>();
        model.put("urls", urls);
        ctx.render("urls.jte", model);
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
        } catch (SQLException e) {
            ctx.redirect("/?message=Ошибка%20при%20извлечении%20данных%20из%20базы%20данных&type=error");
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
