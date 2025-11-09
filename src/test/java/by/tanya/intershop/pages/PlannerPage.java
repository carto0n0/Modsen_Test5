package by.tanya.intershop.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PlannerPage {

    private final WebDriver driver;
    private final By entryInput = By.cssSelector("#__layout textarea");
    private final By addButton = By.cssSelector("#__layout .page__content button");
    private final By bucket = By.cssSelector("#__layout > div > div:nth-child(1) button:nth-child(2)");
    private final By entriesContainer = By.cssSelector("#__layout .vb-content");
    private final By firstEntry = By.cssSelector("#__layout > div > div:nth-child(1)");
    private final By entries = By.cssSelector("#__layout .vb-content > div");
    private List<String> originalEntriesTexts;
    private final JavascriptExecutor js;

    public PlannerPage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
    }

    private PlannerPage safeClick(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
        } catch (ElementClickInterceptedException e) {
            WebElement element = driver.findElement(locator);
            js.executeScript("arguments[0].click();", element);
        }
        return this;
    }


    public PlannerPage rememberOriginalEntries() {
        List<WebElement> entriesList = driver.findElements(entries);
        originalEntriesTexts = entriesList.stream()
                .map(WebElement::getText)
                .filter(text -> !text.isBlank())
                .toList();

        return this;
    }

    public PlannerPage addEntries(int count) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        for (int i = 1; i <= count; i++) {
            WebElement input = wait.until(ExpectedConditions.elementToBeClickable(entryInput));

            input.sendKeys("Test recording " + i);
            safeClick(addButton);

            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(entries, i - 1));
        }
        return this;
    }

    public PlannerPage deleteTopEntry(Actions actions) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement firstContainer = wait.until(ExpectedConditions
                .visibilityOfElementLocated(firstEntry));

        actions.moveToElement(firstContainer).perform();

        WebElement bucketButton = wait.until(ExpectedConditions.presenceOfElementLocated(bucket));

        js.executeScript("arguments[0].click();", bucketButton);

        return this;
    }

    public PlannerPage scrollToBottom() {
        WebElement entries = driver.findElement(entriesContainer);
        js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", entries);
        return this;
    }

    public boolean areOriginalEntriesPresent() {
        List<WebElement> currentEntries = driver.findElements(entries);
        List<String> currentTexts = currentEntries.stream()
                .map(WebElement::getText)
                .filter(text -> !text.isBlank())
                .toList();

        return currentTexts.containsAll(originalEntriesTexts);
    }

}
