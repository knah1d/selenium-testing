package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import pages.HomePage;
import pages.BoardPage;

import java.util.List;

public class CardTests extends BaseTest {

    @Test
    public void testCreateCard() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.manage().window().setSize(new Dimension(1123, 1064));
        driver.findElement(By.cssSelector("button")).click();

        // Create a new board
        driver.findElement(By.id("add_new_board")).click();
        driver.findElement(By.id("board_name")).click();
        driver.findElement(By.id("board_name")).sendKeys("Card Test");
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Create a list
        driver.findElement(By.cssSelector(".inner")).click();
        WebElement listNameInput = driver.findElement(By.id("list_name"));
        listNameInput.sendKeys("Tasks");
        driver.findElement(By.cssSelector("button")).click();

        // Wait for list to be created
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'lists-wrapper')]//h4[text()='Tasks']")));

        // Get the list element
        WebElement listElement = driver.findElement(By.xpath(
                "//div[contains(@class, 'lists-wrapper')]//div[contains(@class, 'list')][.//h4[text()='Tasks']]"));
        String listId = listElement.getAttribute("id");

        // Add a card to the list
        driver.findElement(By.cssSelector("#" + listId + " .add-new")).click();
        driver.findElement(By.id("card_name")).sendKeys("Test Task");
        driver.findElement(By.cssSelector("button")).click();

        // Verify the card was created
        List<WebElement> cards = driver.findElements(By.cssSelector("#" + listId + " .cards-wrapper .card"));
        assertTrue("Card should be created", cards.size() > 0);
    }

    @Test
    public void testCreateMultipleCards() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.manage().window().setSize(new Dimension(1123, 1064));
        driver.findElement(By.cssSelector("button")).click();

        // Create a new board
        driver.findElement(By.id("add_new_board")).click();
        driver.findElement(By.id("board_name")).click();
        driver.findElement(By.id("board_name")).sendKeys("Multiple Cards");
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Create a list
        driver.findElement(By.cssSelector(".inner")).click();
        WebElement listNameInput = driver.findElement(By.id("list_name"));
        listNameInput.sendKeys("Development");
        driver.findElement(By.cssSelector("button")).click();

        // Wait for list to be created
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'lists-wrapper')]//h4[text()='Development']")));

        // Get the list element
        WebElement listElement = driver.findElement(By.xpath(
                "//div[contains(@class, 'lists-wrapper')]//div[contains(@class, 'list')][.//h4[text()='Development']]"));
        String listId = listElement.getAttribute("id");

        // Add first card
        driver.findElement(By.cssSelector("#" + listId + " .add-new")).click();
        driver.findElement(By.id("card_name")).sendKeys("Task 1");
        driver.findElement(By.cssSelector("button")).click();

        // Add second card
        driver.findElement(By.cssSelector("#" + listId + " .add-new")).click();
        driver.findElement(By.id("card_name")).sendKeys("Task 2");
        driver.findElement(By.cssSelector("button")).click();

        // Verify the cards were created
        List<WebElement> cards = driver.findElements(By.cssSelector("#" + listId + " .cards-wrapper .card"));
        assertEquals("Should have 2 cards created", 2, cards.size());
    }

    @Test
    public void testOpenCardDetail() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                .createNewBoard("Card Detail Test")
                .createList("Features")
                .createCard(0, "New Feature");

        boardPage.openCard(0);

        assertNotNull(driver.getCurrentUrl());
    }

    @Test
    public void testAddCardDescription() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.findElement(By.cssSelector("button")).click();

        // Create a new board
        driver.findElement(By.id("add_new_board")).click();
        driver.findElement(By.id("board_name")).sendKeys("Card Description Test");
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Create a list
        driver.findElement(By.cssSelector(".inner")).click();
        driver.findElement(By.id("list_name")).sendKeys("Tasks");
        driver.findElement(By.cssSelector("button")).click();

        // Wait for list to be created
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement listElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(
                        "//div[contains(@class, 'lists-wrapper')]//div[contains(@class, 'list')][.//h4[text()='Tasks']]")));
        String listId = listElement.getAttribute("id");

        // Add a card
        driver.findElement(By.cssSelector("#" + listId + " .add-new")).click();
        driver.findElement(By.id("card_name")).sendKeys("Card with description");
        driver.findElement(By.cssSelector("button")).click();

        // Open the card detail
        WebElement card = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#" + listId + " .cards-wrapper .card")));
        card.click();

        // Add description
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-modal")));
        WebElement descriptionArea = driver.findElement(By.id("card_description"));
        descriptionArea.click();
        descriptionArea.sendKeys("This is a detailed description of the card");

        // Save description
        driver.findElement(By.cssSelector(".form-controls button")).click();

        // Verify description was saved
        wait.until(ExpectedConditions.textToBePresentInElementValue(
                By.id("card_description"), "This is a detailed description of the card"));

        String description = driver.findElement(By.id("card_description")).getAttribute("value");
        assertEquals("This is a detailed description of the card", description);
    }

    @Test
    public void testAddCommentToCard() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.findElement(By.cssSelector("button")).click();

        // Create a new board
        driver.findElement(By.id("add_new_board")).click();
        driver.findElement(By.id("board_name")).sendKeys("Card Comments Test");
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Create a list
        driver.findElement(By.cssSelector(".inner")).click();
        driver.findElement(By.id("list_name")).sendKeys("Features");
        driver.findElement(By.cssSelector("button")).click();

        // Wait for list to be created
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement listElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(
                        "//div[contains(@class, 'lists-wrapper')]//div[contains(@class, 'list')][.//h4[text()='Features']]")));
        String listId = listElement.getAttribute("id");

        // Add a card
        driver.findElement(By.cssSelector("#" + listId + " .add-new")).click();
        driver.findElement(By.id("card_name")).sendKeys("Card with comments");
        driver.findElement(By.cssSelector("button")).click();

        // Open the card detail
        WebElement card = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#" + listId + " .cards-wrapper .card")));
        card.click();

        // Add a comment
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-modal")));
        WebElement commentArea = driver.findElement(By.id("comment_text"));
        commentArea.click();
        commentArea.sendKeys("This is a comment on the card");

        // Submit the comment
        driver.findElement(By.cssSelector(".new-comment button")).click();

        // Verify comment was added
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".card-comments .comment")));
        List<WebElement> comments = driver.findElements(By.cssSelector(".card-comments .comment"));
        assertTrue(comments.size() > 0);

        // Check the comment text
        String commentText = comments.get(0).findElement(By.tagName("p")).getText();
        assertEquals("This is a comment on the card", commentText);
    }

    @Test
    public void testAddTagsToCard() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.findElement(By.cssSelector("button")).click();

        // Create a new board
        driver.findElement(By.id("add_new_board")).click();
        driver.findElement(By.id("board_name")).sendKeys("Card Tags Test");
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Create a list
        driver.findElement(By.cssSelector(".inner")).click();
        driver.findElement(By.id("list_name")).sendKeys("Tasks");
        driver.findElement(By.cssSelector("button")).click();

        // Wait for list to be created
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement listElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(
                        "//div[contains(@class, 'lists-wrapper')]//div[contains(@class, 'list')][.//h4[text()='Tasks']]")));
        String listId = listElement.getAttribute("id");

        // Add a card
        driver.findElement(By.cssSelector("#" + listId + " .add-new")).click();
        driver.findElement(By.id("card_name")).sendKeys("Card with tags");
        driver.findElement(By.cssSelector("button")).click();

        // Open the card detail
        WebElement card = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#" + listId + " .cards-wrapper .card")));
        card.click();

        // Add tags
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-modal")));

        // Find the tags section and click on it
        driver.findElement(By.cssSelector(".tags .tag")).click();

        // Select a tag color (e.g., green)
        driver.findElement(By.cssSelector(".tags-form .green")).click();

        // Wait for tag to be added and verify
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".tags .tag.green")));
        assertTrue(driver.findElement(By.cssSelector(".tags .tag.green")).isDisplayed());
    }
}
