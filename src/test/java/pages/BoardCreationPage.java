package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object representing the board creation page
 */
public class BoardCreationPage extends BasePage {
    
    @FindBy(id = "board_name")
    private WebElement boardNameField;
    
    @FindBy(css = "button")
    private WebElement createButton;
    
    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public BoardCreationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Enter board name
     * 
     * @param boardName Name of the board to create
     * @return BoardCreationPage instance
     */
    public BoardCreationPage enterBoardName(String boardName) {
        enterText(boardNameField, boardName);
        return this;
    }
    
    /**
     * Click the create button
     * 
     * @return BoardPage instance
     */
    public BoardPage clickCreateButton() {
        clickElement(createButton);
        return new BoardPage(driver);
    }
    
    /**
     * Create a board with the given name
     * 
     * @param boardName Name of the board to create
     * @return BoardPage instance
     */
    public BoardPage createBoard(String boardName) {
        enterBoardName(boardName);
        return clickCreateButton();
    }
}
