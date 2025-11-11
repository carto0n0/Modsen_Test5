package by.tanya.intershop.pages;

import by.tanya.intershop.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PlannerPage extends SharedPage {

    private final By entryInput = By.cssSelector(".baseTextarea__text");
    private final By addButton = By.cssSelector(".pageCreate__baseButton");
    private final By bucket = By.cssSelector(".articlePreview:first-child .articlePreview__buttons .articlePreview__button:nth-child(2)");
    private final By entriesContainer = By.cssSelector(".vb-content");
    private final By entries = By.cssSelector(".vb-content .articlePreview");
    private List<String> originalEntriesTexts;

    public PlannerPage(WebDriver driver) {
        super(driver);
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
        for (int i = 1; i <= count; i++) {
            int currentIndex = i;
            WebElement input = waitForPresence(entryInput);
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", input);
            js.executeScript("arguments[0].focus();", input);

            String textToEnter = "Test recording " + i;
            input.sendKeys(textToEnter);

            WaitUtils.waitFor(Duration.ofSeconds(1));
            safeClick(addButton);

            wait.until(driver -> driver.findElements(entries).size() >= currentIndex);
        }
        return this;
    }

    public PlannerPage deleteTopEntry(Actions actions) {

        List<WebElement> beforeDelete = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(entries, 0));
        WebElement firstContainer = beforeDelete.get(0);

        scrollIntoView(firstContainer);
        actions.moveToElement(firstContainer).pause(Duration.ofMillis(300)).perform();

        WebElement bucketButton = waitForVisibility(bucket);
        scrollIntoView(bucketButton);
        jsClick(bucketButton);
        wait.until(ExpectedConditions.invisibilityOf(firstContainer));

        return this;
    }

    public PlannerPage scrollToDown() {
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
