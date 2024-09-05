package hexlet.code.controller;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;
import java.util.Objects;
//
public class UrlCheckController {
    public static void create(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(urlId)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + urlId + " not found"));

        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            Document doc = Jsoup.parse(response.getBody());

            var statusCode = response.getStatus();
            var title = doc.title();

            var h1 = doc.selectFirst("h1") != null ? Objects.requireNonNull(doc.selectFirst("h1"))
                    .text() : "";
            var description = doc.selectFirst("meta[name=description]") != null
                    ? Objects.requireNonNull(doc.selectFirst("meta[name=description]"))
                    .attr("content")
                    : "";

            var urlCheck = new UrlCheck(statusCode, title, h1, description, urlId);
            UrlCheckRepository.save(urlCheck);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flash-type", "success");
        } catch (UnirestException e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
            ctx.sessionAttribute("flash-type", "danger");
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Произошла ошибка при проверке страницы");
            ctx.sessionAttribute("flash-type", "danger");
        }

        ctx.redirect(NamedRoutes.urlPath(urlId));
    }
}
