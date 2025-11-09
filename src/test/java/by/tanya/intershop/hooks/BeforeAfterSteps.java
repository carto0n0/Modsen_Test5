package by.tanya.intershop.hooks;

import by.tanya.intershop.driver.DriverFactory;
import by.tanya.intershop.utils.ScreenShots;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.ScreenshotException;

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
        System.setProperty("allure.results.directory", "target/allure-results");
        logger.info("Allure results directory: {}", System.getProperty("allure.results.directory"));

        driver = DriverFactory.createDriver();
        logger.info("Driver has been created: {}", driver);
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            String status = scenario.isFailed() ? "FAILED" : "PASSED";
            ScreenShots.attachScreenshot(driver, "Final Screenshot - " + status);
            logger.info("Screenshot attached for scenario '{}'", scenario.getName());
        } catch (Exception e) {
            logger.error("Error attaching screenshot: {}", e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
                logger.info("Driver closed");
            }
        }
    }


    public static WebDriver getDriver() {
        return driver;
    }
}