package by.tanya.intershop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SectionPage {

    private final WebDriver driver;
    private final By titleSection = By.cssSelector("#title_bread_wrap h1");

    public SectionPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.findElement(titleSection).getText().strip();
    }

    public SectionPage clickOnSection(String sectionName) {
        String selector = switch (sectionName.toLowerCase()) {
            case "phones" -> "#menu-item-114 > a";
            case "tablets" -> "#menu-item-116 > a";
            case "televisions" -> "#menu-item-118 > a";
            case "photos/videos" -> "#menu-item-117 > a";
            case "watches" -> "#menu-item-115 > a";
            default -> throw new IllegalStateException("Unknown section: " + sectionName);
        };
        driver.findElement(By.cssSelector(selector)).click();
        return this;
    }
}
