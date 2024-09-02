package hexlet.code.dto;

public class BasePage {

    private final String flash;
    private final String flashType;

    public BasePage(String flash, String flashType) {
        this.flash = flash;
        this.flashType = flashType;
    }

    public String getFlash() {
        return flash;
    }

    public String getFlashType() {
        return flashType;
    }
}
