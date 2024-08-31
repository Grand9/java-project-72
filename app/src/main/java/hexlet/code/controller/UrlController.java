package hexlet.code.controller;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlController {

    private UrlRepository urlRepository;

    public UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Handler showFormHandler = ctx -> ctx.render("index.jte", Map.of("flashMessages",
            getFlashMessages(ctx)));

    public Handler createUrlHandler = ctx -> {
        String urlInput = ctx.formParam("url");
        if (urlInput == null || urlInput.isEmpty()) {
            setFlashMessage(ctx, "URL не может быть пустым", "error");
            ctx.redirect("/");
            return;
        }

        try {
            URI uri = new URI(urlInput);
            URL url = uri.toURL();
            String domain = String.format("%s://%s%s", url.getProtocol(), url.getHost(),
                    url.getPort() != -1 ? ":" + url.getPort() : "");

            LocalDateTime now = LocalDateTime.now();
            Url urlObject = new Url(domain, now);

            if (urlRepository.existsByName(domain)) {
                setFlashMessage(ctx, "Страница уже существует", "error");
            } else {
                urlRepository.save(urlObject);
                setFlashMessage(ctx, "Страница успешно добавлена", "success");
            }
            ctx.redirect("/urls");

        } catch (Exception e) {
            setFlashMessage(ctx, "Некорректный URL", "error");
            ctx.redirect("/");
        }
    };

    public Handler listUrlsHandler = ctx -> {
        List<Url> urls;
        try {
            urls = urlRepository.findAll();
        } catch (SQLException e) {
            setFlashMessage(ctx, "Ошибка при извлечении данных из базы данных", "error");
            ctx.redirect("/");
            return;
        }
        Map<String, Object> model = new HashMap<>();
        model.put("urls", urls);
        model.put("flashMessages", getFlashMessages(ctx));
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
            setFlashMessage(ctx, "Ошибка при извлечении данных из базы данных", "error");
            ctx.redirect("/urls");
            return;
        }

        if (url == null) {
            ctx.status(404);
            ctx.render("404.jte");
            return;
        }

        ctx.render("url.jte", Map.of("url", url, "flashMessages", getFlashMessages(ctx)));
    };

    private void setFlashMessage(Context ctx, String message, String type) {
        Map<String, String> flashMessages = ctx.sessionAttribute("flashMessages");
        if (flashMessages == null) {
            flashMessages = new HashMap<>();
        }
        flashMessages.put("message", message);
        flashMessages.put("type", type);
        ctx.sessionAttribute("flashMessages", flashMessages);
    }

    private Map<String, String> getFlashMessages(Context ctx) {
        Map<String, String> flashMessages = ctx.sessionAttribute("flashMessages");
        ctx.sessionAttribute("flashMessages", null);
        return flashMessages != null ? flashMessages : Collections.emptyMap();
    }
}
