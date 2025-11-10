package by.tanya.intershop.hooks;

import by.tanya.intershop.driver.DriverFactory;
import by.tanya.intershop.utils.ScreenShots;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import org.openqa.selenium.WebDriver;


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