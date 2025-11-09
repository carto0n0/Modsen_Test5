package by.tanya.intershop.steptoscenario;

import by.tanya.intershop.utils.ConfigReader;
import by.tanya.intershop.hooks.BeforeAfterSteps;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class OpenStep {

    private final WebDriver driver = BeforeAfterSteps.getDriver();
    protected final Logger logger = LogManager.getLogger(getClass());

    @Given("site is open")
    public void openWebSite() {
        String url = ConfigReader.get("intershopUrl");
        driver.get(url);
        driver.manage().window().maximize();
        logger.info("Site opened: {}", url);
    }

    @Given("planner site is open")
    public void openPlanner(){
        String url = ConfigReader.get("plannerUrl");
        driver.get(url);
        driver.manage().window().maximize();
        logger.info("Site opened: {}", url);
    }
}
