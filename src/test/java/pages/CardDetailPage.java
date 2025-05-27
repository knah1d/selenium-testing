package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page object representing the card detail page
 */
public class CardDetailPage extends BasePage {
    
    @FindBy(css = ".card-detail-title")
    private WebElement cardTitle;
    
    @FindBy(css = ".description-edit")
    private WebElement descriptionEditButton;
    
    @FindBy(css = ".card-description-edit")
    private WebElement descriptionTextarea;
    
    @FindBy(css = ".save-description")
    private WebElement saveDescriptionButton;
    
    @FindBy(css = ".card-description")
    private WebElement cardDescription;
    
    @FindBy(css = ".add-label")
    private WebElement addLabelButton;
    
    @FindBy(css = ".label-input")
    private WebElement labelInput;
    
    @FindBy(css = ".save-label")
    private WebElement saveLabelButton;
    
    @FindBy(css = ".card-labels .label")
    private List<WebElement> cardLabels;
    
    @FindBy(css = ".close-dialog")
    private WebElement closeDialogButton;
    
    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public CardDetailPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Get card title
     * 
     * @return Card title
     */
    public String getCardTitle() {
        return wait.until(ExpectedConditions.visibilityOf(cardTitle)).getText();
    }
    
    /**
     * Add description to card
     * 
     * @param description Description to add
     * @return CardDetailPage instance
     */
    public CardDetailPage addDescription(String description) {
        clickElement(descriptionEditButton);
        enterText(descriptionTextarea, description);
        clickElement(saveDescriptionButton);
        wait.until(ExpectedConditions.textToBePresentInElement(cardDescription, description));
        return this;
    }
    
    /**
     * Get card description
     * 
     * @return Card description
     */
    public String getDescription() {
        return wait.until(ExpectedConditions.visibilityOf(cardDescription)).getText();
    }
    
    /**
     * Add label to card
     * 
     * @param labelName Label name
     * @return CardDetailPage instance
     */
    public CardDetailPage addLabel(String labelName) {
        clickElement(addLabelButton);
        enterText(labelInput, labelName);
        clickElement(saveLabelButton);
        
        // Wait for label to appear
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
            By.cssSelector(".card-labels .label"), cardLabels.size() - 1));
        return this;
    }
    
    /**
     * Check if card has label
     * 
     * @param labelName Label name
     * @return true if card has label, false otherwise
     */
    public boolean hasLabel(String labelName) {
        for (WebElement label : cardLabels) {
            if (label.getText().equals(labelName)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Close card detail dialog
     * 
     * @return BoardPage instance
     */
    public BoardPage closeDialog() {
        clickElement(closeDialogButton);
        return new BoardPage(driver);
    }
}
