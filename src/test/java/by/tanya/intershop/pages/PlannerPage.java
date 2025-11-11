package by.tanya.intershop.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    protected final Logger logger = LogManager.getLogger(getClass());

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
        logger.info("safeClick: Starting click process for locator: {}", locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            logger.debug("safeClick: Waiting for element presence...");
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            logger.debug("safeClick: Element found, scrolling into view...");

            js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            logger.debug("safeClick: Waiting for element to be enabled...");
            wait.until(driver -> {
                try {
                    WebElement el = driver.findElement(locator);
                    String disabled = el.getAttribute("disabled");
                    boolean isEnabled = el.isEnabled();
                    boolean disabledAttrOk = (disabled == null || disabled.equals("false"));
                    boolean ready = isEnabled && disabledAttrOk;

                    logger.debug("safeClick: Element state - isEnabled: {}, disabled attr: '{}', ready: {}",
                            isEnabled, disabled, ready);

                    return ready;
                } catch (Exception e) {
                    logger.error("safeClick: Error checking element state: {}", e.getMessage());
                    return false;
                }
            });

            logger.info("safeClick: Element is ready, performing JS click...");
            WebElement elementForClick = driver.findElement(locator);
            js.executeScript("arguments[0].click();", elementForClick);
            logger.info("safeClick: Click performed successfully");

        } catch (TimeoutException e) {
            logger.error("safeClick: Timeout - Element {} not found", locator);
            throw new TimeoutException("Element " + locator + " not found");
        }
        return this;
    }

    public PlannerPage addEntries(int count) {
        logger.info("addEntries: Starting to add {} entries", count);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        for (int i = 1; i <= count; i++) {
            int currentIndex = i;
            logger.info("addEntries: Processing entry {}/{}", i, count);

            WebElement input = null;

            try {
                logger.debug("addEntries: Looking for input field...");
                input = wait.until(ExpectedConditions.presenceOfElementLocated(entryInput));
                logger.debug("addEntries: Input field found");
            } catch (TimeoutException e) {
                logger.error("addEntries: Input field not found!");
                throw new TimeoutException("textarea (.baseTextarea__text) don't found");
            }

            try {
                wait.until(ExpectedConditions.visibilityOf(input));
            } catch (TimeoutException e) {
                js.executeScript("arguments[0].style.display='block'; arguments[0].style.visibility='visible';", input);
            }

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", input);
            js.executeScript("arguments[0].focus();", input);

            String textToEnter = "Test recording " + i;
            logger.info("Attempting to fill input field with text: '{}' (entry {})", textToEnter, i);

            try {
                logger.debug("Trying to fill field using sendKeys");
                input.sendKeys(textToEnter);
                js.executeScript(
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                        input
                );
                logger.debug("sendKeys completed, events dispatched");
            } catch (InvalidElementStateException e) {
                logger.warn("sendKeys failed, using JS to fill field. Exception: {}", e.getClass().getSimpleName());
                js.executeScript(
                        "arguments[0].value = arguments[1];" +
                                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                        input, textToEnter
                );
                logger.debug("Field filled using JS");
            }


            logger.info("Waiting for field to be filled with text...");
            wait.until(driver -> {
                try {
                    WebElement inputField = driver.findElement(entryInput);
                    String value = inputField.getAttribute("value");
                    logger.debug("Current field value: '{}'", value);

                    if (value == null || value.trim().isEmpty() || !value.contains("Test recording")) {
                        logger.warn("Field is empty or doesn't contain expected text. Value: '{}'. Filling via JS...", value);
                        js.executeScript(
                                "arguments[0].value = arguments[1];" +
                                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                                inputField, textToEnter
                        );
                        value = inputField.getAttribute("value");
                        logger.debug("Field value after JS fill: '{}'", value);
                    }
                    boolean isValid = value != null && !value.trim().isEmpty() && value.contains("Test recording");
                    if (isValid) {
                        logger.info("Field successfully filled with text: '{}'", value);
                    }
                    return isValid;
                } catch (Exception e) {
                    logger.error("Error checking field value: {}", e.getMessage());
                    return false;
                }
            });

            logger.debug("Waiting 300ms for events to process...");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            logger.info("Waiting for button to become enabled...");
            wait.until(driver -> {
                try {
                    WebElement button = driver.findElement(addButton);
                    String disabled = button.getAttribute("disabled");
                    boolean isEnabled = button.isEnabled();
                    boolean disabledAttrOk = (disabled == null || disabled.equals("false"));
                    boolean buttonReady = isEnabled && disabledAttrOk;

                    logger.debug("Button state - isEnabled: {}, disabled attr: '{}', ready: {}",
                            isEnabled, disabled, buttonReady);

                    if (buttonReady) {
                        logger.info("Button is now enabled and ready to click");
                    }

                    return buttonReady;
                } catch (Exception e) {
                    logger.error("Error checking button state: {}", e.getMessage());
                    return false;
                }
            });

            logger.debug("Waiting 500ms for DOM updates...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // safeClick сам проверит кликабельность и прокрутит элемент
            logger.info("Calling safeClick to click the button...");
            safeClick(addButton);
            logger.info("Button clicked, waiting for entry to appear in list...");

            wait.until(driver -> driver.findElements(entries).size() >= currentIndex);
            logger.info("Entry {}/{} successfully added", i, count);
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
