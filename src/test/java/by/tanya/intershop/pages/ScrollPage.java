package by.tanya.intershop.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ScrollPage extends SharedPage {

    private final By arrowTop = By.cssSelector("#ak-top");

    public ScrollPage(WebDriver driver) {
        super(driver);
    }

    public ScrollPage scrollDown() {
        scrollToBottom();
        return this;
    }

    public boolean isArrowVisible() {
        WebElement arrow = waitForVisibility(arrowTop);
        return arrow.isDisplayed();
    }

    public ScrollPage clickOnArrow() {
        safeClick(arrowTop);
        return this;
    }

    public boolean isTop() {
        return isAtTop();
    }
}
