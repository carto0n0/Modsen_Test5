package by.tanya.intershop.driver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DriverFactory {

    public static WebDriver createDriver() throws IOException {
        ChromeOptions options = new ChromeOptions();
        String isCI = System.getenv("CI");

        if ("true".equals(isCI)) {
            Path tempProfile = Files.createTempDirectory("chrome-profile-");
            options.addArguments(
                    "--headless=new",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu",
                    "--remote-allow-origins=*",
                    "--user-data-dir=" + tempProfile.toString(),
                    "--incognito",
                    "--window-size=1920,1080",
                    "--disable-extensions",
                    "--disable-popup-blocking",
                    "--disable-notifications",
                    "--disable-infobars",
                    "--disable-web-security"
            );
        }


        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        return driver;
    }
}
