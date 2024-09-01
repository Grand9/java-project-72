package hexlet.code.dto.urls;

import hexlet.code.model.Url;
import lombok.Getter;

import java.util.List;

@Getter
public class UrlsPage {

    private final List<Url> urls;
    private final String flashMessage;
    private final String flashType;

    public UrlsPage(List<Url> urls, String flashMessage, String flashType) {
        this.urls = urls;
        this.flashMessage = flashMessage;
        this.flashType = flashType;
    }

}
