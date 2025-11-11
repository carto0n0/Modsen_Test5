package by.tanya.intershop.pages;

import by.tanya.intershop.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SharedPage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final JavascriptExecutor js;

    protected SharedPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
    }

    protected void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    protected void safeClick(By locator) {
        try {
            WebElement element = waitForPresence(locator);
            scrollIntoView(element);

            WaitUtils.waitFor(Duration.ofMillis(500));

            wait.until(driver -> {
                try {
                    WebElement el = driver.findElement(locator);
                    String disabled = el.getAttribute("disabled");
                    return el.isEnabled() && (disabled == null || disabled.equals("false"));
                } catch (Exception e) {
                    return false;
                }
            });

            jsClick(driver.findElement(locator));

        } catch (TimeoutException e) {
            throw new TimeoutException("Element " + locator + " not found or not clickable");
        }
    }

    protected boolean isAtTop() {
        try {
            return wait.until(driver -> {
                Number y = (Number) js.executeScript("return window.scrollY;");
                return y.intValue() <= 5;
            });
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
}

