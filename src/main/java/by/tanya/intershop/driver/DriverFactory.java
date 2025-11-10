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
            String userDataDir = Files.createTempDirectory(
                    "chrome-profile-").toString();
            options.addArguments(
                    "--headless=new",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu",
                    "--remote-allow-origins=*",
                    "--user-data-dir=" + userDataDir,
                    "--window-size=1920,1080",
                    "--disable-extensions",
                    "--disable-popup-blocking",
                    "--disable-notifications",
                    "--disable-infobars",
                    "--disable-web-security",
                    "--disable-features=VizDisplayCompositor",
                    "--disable-background-timer-throttling",
                    "--disable-backgrounding-occluded-windows",
                    "--disable-renderer-backgrounding",
                    "--disable-component-extensions-with-background-pages"
            );
        }


        WebDriver driver = new ChromeDriver(options);
        return driver;
    }
}
