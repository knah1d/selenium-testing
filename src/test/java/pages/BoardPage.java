package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class BoardPage extends BasePage {

    @FindBy(css = ".board-header")
    private WebElement boardHeader;

    @FindBy(css = ".inner")
    private WebElement addListButton;

    @FindBy(id = "list_name")
    private WebElement listNameInput;

    @FindBy(css = "form#new_list_form button")
    private WebElement saveListButton;

    @FindBy(css = ".lists-wrapper .list:not(.add-new)")
    private List<WebElement> lists;

    @FindBy(css = ".list-header")
    private List<WebElement> listHeaders;

    @FindBy(css = ".add-card")
    private List<WebElement> addCardButtons;

    @FindBy(css = ".card-composer-textarea")
    private WebElement cardNameTextarea;

    @FindBy(css = ".add-card-button")
    private WebElement addCardButton;

    @FindBy(css = ".card")
    private List<WebElement> cards;

    public BoardPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getBoardTitle() {
        return wait.until(ExpectedConditions.visibilityOf(boardHeader)).getText();
    }

    public BoardPage createList(String listName) {
        // Click on "Add a new list..." button
        clickElement(addListButton);

        // Enter the list name
        enterText(listNameInput, listName);

        // Click the Save list button
        clickElement(saveListButton);

        // Wait for the new list to appear in the DOM
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".list"), lists.size() - 1));
        return this;
    }

    public BoardPage createCard(int listIndex, String cardName) {
        if (listIndex >= 0 && listIndex < addCardButtons.size()) {
            clickElement(addCardButtons.get(listIndex));
            enterText(cardNameTextarea, cardName);
            clickElement(addCardButton);
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".card"), cards.size() - 1));
        }
        return this;
    }

    public int getListCount() {
        return lists.size();
    }

    public int getCardCount() {
        return cards.size();
    }

    public CardDetailPage openCard(int cardIndex) {
        if (cardIndex >= 0 && cardIndex < cards.size()) {
            clickElement(cards.get(cardIndex));
            return new CardDetailPage(driver);
        }
        throw new IndexOutOfBoundsException("Card index out of bounds: " + cardIndex);
    }

    public boolean checkAccessibility() {
        boolean boardHeaderAccessible = boardHeader.getAttribute("aria-label") != null;

        boolean addListButtonAccessible = addListButton.getAttribute("aria-label") != null
                || addListButton.getAttribute("title") != null;

        boolean cardsAccessible = true;
        if (!cards.isEmpty()) {
            WebElement sampleCard = cards.get(0);
            cardsAccessible = sampleCard.getAttribute("role") != null;
        }

        return boardHeaderAccessible && addListButtonAccessible && cardsAccessible;
    }

    public List<String> getListNames() {
        List<String> names = new java.util.ArrayList<>();
        for (WebElement header : listHeaders) {
            names.add(header.getText());
        }
        return names;
    }
}
