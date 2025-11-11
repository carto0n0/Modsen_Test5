package by.tanya.intershop.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class PlannerPage {

    private final WebDriver driver;
    private final By entryInput = By.cssSelector(".baseTextarea__text");
    private final By addButton = By.cssSelector(".pageCreate__baseButton");
    private final By bucket = By.cssSelector(".articlePreview:first-child .articlePreview__buttons .articlePreview__button:nth-child(2)");
    private final By entriesContainer = By.cssSelector(".vb-content");
    private final By entries = By.cssSelector(".vb-content .articlePreview");
    private List<String> originalEntriesTexts;
    private final JavascriptExecutor js;

    public PlannerPage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
    }

    public PlannerPage rememberOriginalEntries() {

        List<WebElement> entriesList = driver.findElements(entries);
        originalEntriesTexts = entriesList.stream()
                .map(WebElement::getText)
                .filter(text -> !text.isBlank())
                .toList();

        return this;
    }

    private PlannerPage safeClick(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(locator));

            try {
                clickableElement.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", clickableElement);
            } catch (ElementNotInteractableException e) {
                js.executeScript("arguments[0].click();", clickableElement);
            }

        } catch (TimeoutException e) {
            throw new TimeoutException("Element " + locator + " not found");
        }
        return this;
    }

    public PlannerPage addEntries(int count) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        for (int i = 1; i <= count; i++) {
            int currentIndex = i;

            WebElement input = null;

            try {
                input = wait.until(ExpectedConditions.presenceOfElementLocated(entryInput));
            } catch (TimeoutException e) {
                throw new TimeoutException("textarea (.baseTextarea__text) don't found");
            }

            try {
                wait.until(ExpectedConditions.visibilityOf(input));
            } catch (TimeoutException e) {
                js.executeScript("arguments[0].style.display='block'; arguments[0].style.visibility='visible';", input);
            }

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", input);
            js.executeScript("arguments[0].focus();", input);

            try {
                input.sendKeys("Test recording " + i);
            } catch (ElementNotInteractableException e) {
                js.executeScript(
                        "arguments[0].value = arguments[1];" +
                                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                        input, "Test recording " + i
                );
            } catch (InvalidElementStateException  e) {
                js.executeScript(
                        "arguments[0].value = arguments[1];" +
                                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                        input, "Test recording " + i
                );
            }

            wait.until(driver -> {
                try {
                    WebElement button = driver.findElement(addButton);
                    if (!button.isDisplayed() || !button.isEnabled()) {
                        return false;
                    }
                    String pointerEvents = button.getCssValue("pointer-events");
                    if ("none".equals(pointerEvents)) {
                        return false;
                    }
                    String disabled = button.getAttribute("disabled");
                    if (disabled != null && ("true".equals(disabled) || disabled.isEmpty())) {
                        return false;
                    }
                    Object result = js.executeScript(
                            "var rect = arguments[0].getBoundingClientRect();" +
                                    "return rect.width > 0 && rect.height > 0 && " +
                                    "rect.top < (window.innerHeight || document.documentElement.clientHeight) && " +
                                    "rect.left < (window.innerWidth || document.documentElement.clientWidth) && " +
                                    "rect.bottom > 0 && rect.right > 0;",
                            button
                    );
                    return result instanceof Boolean && (Boolean) result;
                } catch (Exception e) {
                    return false;
                }
            });

            safeClick(addButton);

            wait.until(driver -> driver.findElements(entries).size() >= currentIndex);
        }
        return this;
    }

    public PlannerPage deleteTopEntry(Actions actions) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> beforeDelete = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(entries, 0));
        WebElement firstContainer = beforeDelete.get(0);

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstContainer);

        actions.moveToElement(firstContainer).pause(Duration.ofMillis(300)).perform();

        WebElement bucketButton = wait.until(ExpectedConditions.elementToBeClickable(bucket));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", bucketButton);
        js.executeScript("arguments[0].click();", bucketButton);

        try {
            wait.until(ExpectedConditions.invisibilityOf(firstContainer));
        } catch (TimeoutException e) {
            wait.until(driver -> {
                List<WebElement> after = driver.findElements(entries);
                return after.isEmpty() || !after.get(0).equals(firstContainer);
            });
        }
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
