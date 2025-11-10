package by.tanya.intershop.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.nio.file.Files;

public class DriverFactory {

    public static WebDriver createDriver() throws IOException {
        ChromeOptions options = new ChromeOptions();
        String isCI = System.getenv("CI");

        if ("true".equals(isCI)) {
            String userDataDir = Files.createTempDirectory("chrome-profile-").toString();
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--user-data-dir=" + userDataDir);
        }


        WebDriver driver = new ChromeDriver(options);
        return driver;
    }
}
