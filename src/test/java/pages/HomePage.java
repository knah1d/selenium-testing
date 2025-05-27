package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage extends BasePage {
    
    @FindBy(css = ".view-container")
    private WebElement viewContainer;
    
    @FindBy(css = "button")
    private WebElement mainActionButton;
    
    @FindBy(id = "add_new_board")
    private WebElement addNewBoardButton;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    

    public HomePage navigateTo() {
        driver.get(BASE_URL);
        return this;
    }
    

    public HomePage clickViewContainer() {
        clickElement(viewContainer);
        return this;
    }
    

    public HomePage clickMainActionButton() {
        clickElement(mainActionButton);
        return this;
    }
    

    public BoardCreationPage clickAddNewBoard() {
        clickElement(addNewBoardButton);
        return new BoardCreationPage(driver);
    }

    public BoardPage createNewBoard(String boardName) {
        clickViewContainer();
        clickMainActionButton();
        clickAddNewBoard();
        return new BoardCreationPage(driver).createBoard(boardName);
    }
    

    public boolean checkAccessibility() {
        boolean viewContainerAccessible = viewContainer.getAttribute("aria-label") != null 
                                       || viewContainer.getAttribute("role") != null;
        
        boolean buttonAccessible = mainActionButton.getAttribute("aria-label") != null 
                                || mainActionButton.getAttribute("title") != null;
        
        return viewContainerAccessible && buttonAccessible;
    }
}
