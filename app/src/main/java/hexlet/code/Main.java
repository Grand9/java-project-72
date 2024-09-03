package hexlet.code;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        App.getApp().start(getPort());
    }

    private static int getPort() {
        String port = System.getenv("PORT");
        return port != null ? Integer.parseInt(port) : 7000;
    }
}
