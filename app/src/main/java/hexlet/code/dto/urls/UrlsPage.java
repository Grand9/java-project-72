package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.util.Map;

public class UrlsPage extends BasePage {
    private final Map<Url, UrlCheck> urlsWithChecks;

    public UrlsPage(String flash, String flashType, Map<Url, UrlCheck> urlsWithChecks) {
        super(flash, flashType);
        this.urlsWithChecks = urlsWithChecks;
    }

    public Map<Url, UrlCheck> getUrlsWithChecks() {
        return urlsWithChecks;
    }
}
