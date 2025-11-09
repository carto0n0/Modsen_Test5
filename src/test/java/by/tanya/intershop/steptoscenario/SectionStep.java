package by.tanya.intershop.steptoscenario;

import by.tanya.intershop.hooks.BeforeAfterSteps;
import by.tanya.intershop.pages.SectionPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static by.tanya.intershop.utils.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SectionStep {

    private final WebDriver driver = BeforeAfterSteps.getDriver();
    private final SectionPage sectionPage = new SectionPage(driver);
    protected final Logger logger = LogManager.getLogger(getClass());


    @And("Click on the {string} section")
    public void clickOnSection(String sectionname) {
        sectionPage.clickOnSection(sectionname);
    }

    @Then("There is a transition to the {string} page.")
    public void IsToTheSection(String sectionName) {
        String actualTitlePhone = sectionPage.getTitle();
        String expectedTitlePhone = switch (sectionName) {
            case "Phones" -> PHONE_TITLE;
            case "Tablets" -> TABLET_TITLE;
            case "Televisions" -> TELEVISION_TITLE;
            case "Photos/videos" -> PHOTO_VIDEO_TITLE;
            case "Watches" -> WATCH_TITLE;
            default -> throw new IllegalStateException("Unexpected value: " + sectionName);
        };

        if (!expectedTitlePhone.equals(actualTitlePhone)) {
            logger.error("Error: Expected '{}', Actual '{}'",
                    expectedTitlePhone, actualTitlePhone);
        } else {
            logger.info("Success '{}'", sectionName);
        }

        assertEquals(expectedTitlePhone, actualTitlePhone, "The page title does not match");
    }
}
