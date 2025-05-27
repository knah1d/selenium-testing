package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page object representing the home page of the application
 */
public class HomePage extends BasePage {
    
    @FindBy(css = ".view-container")
    private WebElement viewContainer;
    
    @FindBy(css = "button")
    private WebElement mainActionButton;
    
    @FindBy(id = "add_new_board")
    private WebElement addNewBoardButton;
    
    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Navigate to the home page
     * 
     * @return HomePage instance
     */
    public HomePage navigateTo() {
        driver.get(BASE_URL);
        return this;
    }
    
    /**
     * Click on the view container
     * 
     * @return HomePage instance
     */
    public HomePage clickViewContainer() {
        clickElement(viewContainer);
        return this;
    }
    
    /**
     * Click on the main action button
     * 
     * @return HomePage instance
     */
    public HomePage clickMainActionButton() {
        clickElement(mainActionButton);
        return this;
    }
    
    /**
     * Click on the add new board button
     * 
     * @return BoardCreationPage instance
     */
    public BoardCreationPage clickAddNewBoard() {
        clickElement(addNewBoardButton);
        return new BoardCreationPage(driver);
    }
    
    /**
     * Create a new board in one fluent action
     * 
     * @param boardName Name of the board to create
     * @return BoardPage instance
     */
    public BoardPage createNewBoard(String boardName) {
        clickViewContainer();
        clickMainActionButton();
        clickAddNewBoard();
        return new BoardCreationPage(driver).createBoard(boardName);
    }
    
    /**
     * Check accessibility of the page
     * This is a simplified implementation - in a real app, you might use
     * a proper accessibility testing library
     * 
     * @return true if page elements have proper ARIA attributes
     */
    public boolean checkAccessibility() {
        // Check that interactive elements have aria-label or similar attributes
        boolean viewContainerAccessible = viewContainer.getAttribute("aria-label") != null 
                                       || viewContainer.getAttribute("role") != null;
        
        // Check buttons have appropriate attributes
        boolean buttonAccessible = mainActionButton.getAttribute("aria-label") != null 
                                || mainActionButton.getAttribute("title") != null;
        
        // Return true only if all checks pass
        return viewContainerAccessible && buttonAccessible;
    }
}
