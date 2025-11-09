package by.tanya.intershop.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ScrollPage {

    private final WebDriver driver;

    private final By arrowTop = By.cssSelector("#ak-top");

    public ScrollPage(WebDriver driver) {
        this.driver = driver;
    }

    public ScrollPage scrollDown() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        return this;
    }

    public boolean isArrowVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        WebElement arrow = wait.until(ExpectedConditions.visibilityOfElementLocated(arrowTop));
        return arrow.isDisplayed();
    }

    public ScrollPage clickOnArrow() {
        driver.findElement(arrowTop).click();
        return this;
    }

    public boolean isTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        try {
            return wait.until(webDriver -> {
                Number pagePosition = (Number) js.executeScript("return window.scrollY;");
                return pagePosition.intValue() <= 5;
            });
        } catch (TimeoutException e) {
            return false;
        }
    }
}
