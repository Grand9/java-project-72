package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.util.List;

public class UrlPage extends BasePage {

    private final Url url;
    private final List<UrlCheck> urlChecks;

    public UrlPage(String flash, String flashType, Url url, List<UrlCheck> urlChecks) {
        super(flash, flashType);
        this.url = url;
        this.urlChecks = urlChecks;
    }

    public Url getUrl() {
        return url;
    }

    public List<UrlCheck> getUrlChecks() {
        return urlChecks;
    }
}
