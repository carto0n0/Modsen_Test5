package by.tanya.intershop.hooks;

import by.tanya.intershop.driver.DriverFactory;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BeforeAfterSteps {

    private static WebDriver driver;
    protected final Logger logger = LogManager.getLogger(getClass());

    @Before
    public void setUp() throws Exception {
        driver = DriverFactory.createDriver();
        logger.info("Driver has been created: {}", driver);
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String status = scenario.isFailed() ? "FAILED" : "PASSED";
                String screenshotName = "src/test/resources/screenshots/"
                        + scenario.getName().replaceAll("[^a-zA-Z0-9_]", "_")
                        + "_" + status + ".png";

                Files.createDirectories(Paths.get("src/test/resources/screenshots/"));
                Files.copy(screenshot.toPath(), Paths.get(screenshotName));
                logger.info("Screenshot saved: {}", screenshotName);

                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Screenshot (" + status + ")", new ByteArrayInputStream(screenshotBytes));
            }
        } catch (IOException e) {
            logger.error("Error saving screenshot: {}", e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
                logger.info("Driver was successfully closed");
            }
        }
    }


    public static WebDriver getDriver() {
        return driver;
    }
}