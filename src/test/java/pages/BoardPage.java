package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page object representing a board page
 * Simplified version
 */
public class BoardPage extends BasePage {
    
    // Board elements
    @FindBy(css = ".board-header")
    private WebElement boardHeader;
    
    // List elements
    @FindBy(css = ".add-list")
    private WebElement addListButton;
    
    @FindBy(css = ".list-name-input")
    private WebElement listNameInput;
    
    @FindBy(css = ".add-list-button")
    private WebElement saveListButton;
    
    @FindBy(css = ".list")
    private List<WebElement> lists;
    
    // Card elements
    @FindBy(css = ".add-card")
    private List<WebElement> addCardButtons;
    
    @FindBy(css = ".card-composer-textarea")
    private WebElement cardNameTextarea;
    
    @FindBy(css = ".add-card-button")
    private WebElement addCardButton;
    
    @FindBy(css = ".card")
    private List<WebElement> cards;
    
    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public BoardPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Get board title
     * 
     * @return Board title
     */
    public String getBoardTitle() {
        return wait.until(ExpectedConditions.visibilityOf(boardHeader)).getText();
    }
    
    /**
     * Create a new list
     * 
     * @param listName Name of the list to create
     * @return BoardPage instance
     */
    public BoardPage createList(String listName) {
        clickElement(addListButton);
        enterText(listNameInput, listName);
        clickElement(saveListButton);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".list"), lists.size() - 1));
        return this;
    }
    
    /**
     * Create a new card in the specified list
     * 
     * @param listIndex Index of the list to add the card to (0-based)
     * @param cardName Name of the card to create
     * @return BoardPage instance
     */
    public BoardPage createCard(int listIndex, String cardName) {
        if (listIndex >= 0 && listIndex < addCardButtons.size()) {
            clickElement(addCardButtons.get(listIndex));
            enterText(cardNameTextarea, cardName);
            clickElement(addCardButton);
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".card"), cards.size() - 1));
        }
        return this;
    }
    
    /**
     * Get the number of lists
     * 
     * @return Number of lists
     */
    public int getListCount() {
        return lists.size();
    }
    
    /**
     * Get the number of cards
     * 
     * @return Number of cards
     */
    public int getCardCount() {
        return cards.size();
    }
    
    /**
     * Open a card to view its details
     * 
     * @param cardIndex Index of the card to open (0-based)
     * @return CardDetailPage instance
     */
    public CardDetailPage openCard(int cardIndex) {
        if (cardIndex >= 0 && cardIndex < cards.size()) {
            clickElement(cards.get(cardIndex));
            return new CardDetailPage(driver);
        }
        throw new IndexOutOfBoundsException("Card index out of bounds: " + cardIndex);
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
        boolean boardHeaderAccessible = boardHeader.getAttribute("aria-label") != null;
        
        // Check buttons have appropriate attributes
        boolean addListButtonAccessible = addListButton.getAttribute("aria-label") != null 
                                       || addListButton.getAttribute("title") != null;
        
        // Check if cards have appropriate roles
        boolean cardsAccessible = true;
        if (!cards.isEmpty()) {
            WebElement sampleCard = cards.get(0);
            cardsAccessible = sampleCard.getAttribute("role") != null;
        }
        
        // Return true only if all checks pass
        return boardHeaderAccessible && addListButtonAccessible && cardsAccessible;
    }
}
