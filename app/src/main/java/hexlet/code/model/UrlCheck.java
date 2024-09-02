package hexlet.code.model;

import java.time.LocalDateTime;

public class UrlCheck {

    private final int id;
    private final int urlId;
    private final int statusCode;
    private final String title;
    private final String h1;
    private final String description;
    private final LocalDateTime createdAt;

    public UrlCheck(int id, int urlId, int statusCode, String title, String h1, String description, LocalDateTime createdAt) {
        this.id = id;
        this.urlId = urlId;
        this.statusCode = statusCode;
        this.title = title;
        this.h1 = h1;
        this.description = description;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getUrlId() {
        return urlId;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getTitle() {
        return title;
    }

    public String getH1() {
        return h1;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
