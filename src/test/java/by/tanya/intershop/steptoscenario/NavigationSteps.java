package by.tanya.intershop.steptoscenario;

import by.tanya.intershop.hooks.BeforeAfterSteps;
import by.tanya.intershop.pages.NavigationPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class NavigationSteps {

    private final WebDriver driver = BeforeAfterSteps.getDriver();
    private final Actions actions = new Actions(driver);
    private final NavigationPage navigationPage = new NavigationPage(driver);

    @When("Hover the cursor over Directory")
    public void hoverOverDirectory() {
        navigationPage.hoverOverDirectory(actions);
    }

    @And("Hover the cursor over Electronics")
    public void hoverOverElectronics() {
        navigationPage.hoverOverElectronics(actions);
    }
}
