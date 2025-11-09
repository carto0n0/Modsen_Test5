package by.tanya.intershop.steptoscenario;

import by.tanya.intershop.hooks.BeforeAfterSteps;
import by.tanya.intershop.pages.ScrollPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScrollStep {

    private final WebDriver driver = BeforeAfterSteps.getDriver();
    private final ScrollPage scrollPage = new ScrollPage(driver);

    @When("Scroll down")
    public void scrollDown() {
        scrollPage.scrollDown();
    }

    @And("Check visability of the arrow")
    public void checkVisabilityOfArrow() {
        assertTrue(scrollPage.isArrowVisible(), "The arrow is not visible when scrolling down.");
    }

    @And("Click on arrow")
    public void clickOnArrow() {
        scrollPage.clickOnArrow();
    }

    @Then("Go back to the header")
    public void isTop() {
        assertTrue(scrollPage.isTop(), "The page didn't go back to the beginning");
    }
}
