import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Utility class for common test operations
 */
public class CreateBoard {
    
    /**
     * Navigate to a URL and perform common test operations
     * 
     * @param driver WebDriver instance to use
     * @param url URL to navigate to
     * @param boardName Name of the board to create
     */
    public static void navigateToUrl(WebDriver driver, String url, String boardName) {
        driver.get(url);
        driver.manage().window().setSize(new Dimension(977, 1068));
        
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".view-container"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add_new_board"))).click();
        
        WebElement boardNameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("board_name")));
        boardNameField.click();
        boardNameField.sendKeys(boardName);
        
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button"))).click();
    }
}
