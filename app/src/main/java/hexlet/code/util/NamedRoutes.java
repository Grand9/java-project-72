package hexlet.code.util;

public class NamedRoutes {

    public static String urlsPath() {
        return "/urls";
    }

    public static String urlPath(int id) {
        return "/urls/" + id;
    }

    public static String homePath() {
        return "/";
    }

    public static String checkPath(int urlId) {
        return "/urls/" + urlId + "/check";
    }
}
