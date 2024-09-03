package hexlet.code.controller;

import io.javalin.http.Context;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class UrlController {

    private final UrlRepository urlRepository;

    public UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public void addUrlHandler(Context ctx) {
        String urlStr = ctx.formParam("url");

        try {
            URI uri = new URI(urlStr);
            URL url = uri.toURL();
            String domain = url.getProtocol() + "://" + url.getHost();
            if (url.getPort() != -1) {
                domain += ":" + url.getPort();
            }

            // Check if URL already exists
            if (urlRepository.urlExists(domain)) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.redirect("/");
                return;
            }

            // Save new URL
            Url newUrl = new Url(0, domain, LocalDateTime.now());
            urlRepository.addUrl(newUrl);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
        } catch (URISyntaxException | IllegalArgumentException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
        } catch (SQLException e) {
            ctx.sessionAttribute("flash", "Ошибка при добавлении URL");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        ctx.redirect("/");
    }

    public void listUrlsHandler(Context ctx) {
        try {
            List<Url> urls = urlRepository.getAllUrls();
            ctx.render("urls/index.jte", Map.of("urls", urls));
        } catch (SQLException e) {
            ctx.sessionAttribute("flash", "Ошибка при загрузке URL");
            ctx.redirect("/");
        }
    }

    public void showUrlHandler(Context ctx) {
        String id = ctx.pathParam("id");

        try {
            Url url = urlRepository.getUrlById(Integer.parseInt(id));
            if (url == null) {
                ctx.status(404);
                ctx.render("404.jte");
                return;
            }

            ctx.render("urls/show.jte", Map.of("url", url));
        } catch (SQLException e) {
            ctx.sessionAttribute("flash", "Ошибка при загрузке URL");
            ctx.redirect("/");
        }
    }
}
