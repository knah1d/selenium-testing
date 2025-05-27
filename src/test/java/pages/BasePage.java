package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base class for all page objects
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public static final String BASE_URL = "http://localhost:4000";

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }
    
    /**
     * Wait for element to be clickable and click on it
     * 
     * @param element WebElement to click
     */
    protected void clickElement(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
    
    /**
     * Enter text into an input field
     * 
     * @param element Input element
     * @param text Text to enter
     */
    protected void enterText(WebElement element, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).clear();
        element.sendKeys(text);
    }
    
    /**
     * Get the current page title
     * 
     * @return Current page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
}
