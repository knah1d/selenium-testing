package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class BoardCreationPage extends BasePage {
    
    @FindBy(id = "board_name")
    private WebElement boardNameField;
    
    @FindBy(css = "button")
    private WebElement createButton;
    

    public BoardCreationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    

    public BoardCreationPage enterBoardName(String boardName) {
        enterText(boardNameField, boardName);
        return this;
    }
    

    public BoardPage clickCreateButton() {
        clickElement(createButton);
        return new BoardPage(driver);
    }
    

    public BoardPage createBoard(String boardName) {
        enterBoardName(boardName);
        return clickCreateButton();
    }
}
