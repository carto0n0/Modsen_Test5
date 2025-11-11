package by.tanya.intershop.steptoscenario;

import by.tanya.intershop.hooks.BeforeAfterSteps;
import by.tanya.intershop.pages.PlannerPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlannerStep {

    private WebDriver driver = BeforeAfterSteps.getDriver();
    private Actions actions = new Actions(driver);
    private final PlannerPage plannerPage = new PlannerPage(driver);

    @When("Add 10 new entries")
    public void addEntriesToPlanner() {
        plannerPage.rememberOriginalEntries()
                .addEntries(10);
    }

    @And("delete top entry")
    public void deleteFirstEntry() {
        plannerPage.deleteTopEntry(actions);
    }

    @And("scroll down")
    public void ScrollBottom() {
        plannerPage.scrollToDown();
    }

    @Then("check the original records")
    public void AreEntries() {
        boolean areEntry = plannerPage.areOriginalEntriesPresent();
        assertTrue(areEntry, "original records are changed");
    }
}
