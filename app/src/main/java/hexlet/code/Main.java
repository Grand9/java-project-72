package hexlet.code;

public class Main {
    public static void main(String[] args) {
        App.getApp().start(getPort());
    }

    private static int getPort() {
        String port = System.getenv("PORT");
        return port != null ? Integer.parseInt(port) : 7000;
    }
}
