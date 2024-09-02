package hexlet.code.controller;

import io.javalin.http.Handler;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlRepository;

import java.util.List;
import java.util.Map;

public class UrlController {

    public Handler createUrlHandler;
    public Handler listUrlsHandler;
    private UrlRepository urlRepository;

    public UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Handler showUrlHandler = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Url url = urlRepository.getUrlById(id);
        if (url == null) {
            ctx.status(404).result("URL not found");
            return;
        }

        List<UrlCheck> urlChecks = urlRepository.getUrlChecks(id);
        UrlPage urlPage = new UrlPage(url, urlChecks);

        ctx.render("show.jte", Map.of("page", urlPage));
    };
}
