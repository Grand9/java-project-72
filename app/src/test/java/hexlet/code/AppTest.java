package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
//
public final class AppTest {

    private static Javalin app;
    private static MockWebServer mockServer;
    private static String urlName;
    private static final String HTML_PATH = "src/test/resources/index.html";

    @BeforeAll
    public static void beforeAll() throws IOException {
        // Настройка MockWebServer и создание мока ответа
        mockServer = new MockWebServer();
        urlName = mockServer.url("/").toString();
        var mockResponse = new MockResponse().setBody(getContentOfHtmlFile());
        mockServer.enqueue(mockResponse);
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
        // Инициализация приложения и очистка базы данных перед каждым тестом
        app = App.getApp();
        UrlCheckRepository.deleteAllRows();
        UrlRepository.deleteAllRows();
    }

    @AfterAll
    public static void afterAll() throws IOException {
        // Завершение работы MockWebServer после всех тестов
        mockServer.shutdown();
    }

    private static String getContentOfHtmlFile() throws IOException {
        // Получение содержимого HTML-файла для MockWebServer
        var path = Paths.get(HTML_PATH);
        var lines = Files.readAllLines(path);
        return String.join("\n", lines);
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.mainPath());
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testPagesPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testCreatePage() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://www.example.com";
            var response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            assertThat(response.body().string()).contains("https://www.example.com");
            assertThat(UrlRepository.getEntities()).hasSize(1);
        });
    }

    @Test
    public void testCreateTheSamePage() throws SQLException {
        // Добавляем URL в базу данных и проверяем, что дубли не создаются
        var url = new Url("https://www.example.com");
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://www.example.com";
            var response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            assertThat(response.body().string()).contains("https://www.example.com");
            assertThat(UrlRepository.getEntities()).hasSize(1); // Проверка, что URL добавлен только один раз
        });
    }

    @Test
    public void testPagePage() throws SQLException {
        // Добавляем URL и проверяем его отображение на странице по ID
        var url = new Url("https://www.example.com");
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            String requestUrl = NamedRoutes.urlPath(url.getId());
            var response = client.get(requestUrl);
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlNotFound() {
        // Проверка на несуществующий URL
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlPath(999999L));
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testCheckUrl() throws SQLException {
        // Проверка добавления проверки URL и корректного получения данных
        var url = new Url(urlName);
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.post(NamedRoutes.urlChecksPath(url.getId()));
            assertThat(response.code()).isEqualTo(200);

            var urlCheck = UrlCheckRepository.getLastCheck(url.getId()).get();
            var title = urlCheck.getTitle();
            var h1 = urlCheck.getH1();
            var description = urlCheck.getDescription();

            assertThat(title).isEqualTo("title");
            assertThat(h1).isEqualTo("header");
            assertThat(description).isEqualTo("some description");
        });
    }
}
