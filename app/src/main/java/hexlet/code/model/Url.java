package hexlet.code.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Url {

    private int id;
    private final String name;
    private final LocalDateTime createdAt;

    public Url(int id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Url(String name, LocalDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

}
