package by.tanya.intershop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class NavigationPage extends SharedPage {

    public NavigationPage(WebDriver driver) {
        super(driver);
    }

    public NavigationPage hoverOverDirectory(Actions actions) {
        WebElement directory = waitForVisibility(By.cssSelector("#menu-item-46 > a"));
        actions.moveToElement(directory).pause(Duration.ofMillis(500)).perform();
        return this;
    }

    public NavigationPage hoverOverElectronics(Actions actions) {
        WebElement electronics = waitForVisibility(By.cssSelector("#menu-item-47 > a"));
        actions.moveToElement(electronics).pause(Duration.ofMillis(500)).perform();
        return this;
    }

}
