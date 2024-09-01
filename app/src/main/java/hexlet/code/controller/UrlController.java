package hexlet.code.controller;

import io.javalin.http.Handler;
import io.javalin.http.NotFoundResponse;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class UrlController {

    private UrlRepository urlRepository;

    public UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Handler showUrlHandler = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Url url = urlRepository.getUrlById(id);
        if (url == null) {
            throw new NotFoundResponse("URL not found");
        }
        Map<String, Object> model = new HashMap<>();
        model.put("url", url);
        ctx.render("url/show.jte", model);
    };


    public Handler createUrlHandler = ctx -> {
        String name = ctx.formParam("name");
        if (name == null || name.isEmpty()) {
            ctx.status(400).result("Name is required");
            return;
        }
        Url url = new Url(name, LocalDateTime.now());
        try {
            urlRepository.save(url);
            ctx.redirect("/urls");
        } catch (SQLException e) {
            ctx.status(500).result("Database error");
        }
    };
}
