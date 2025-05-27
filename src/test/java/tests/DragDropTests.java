package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;

import pages.HomePage;
import pages.BoardPage;

import java.util.List;

public class DragDropTests extends BaseTest {

    /**
     * This test uses JavaScript to simulate drag and drop since Selenium's native
     * dragAndDrop
     * can be unreliable, especially with modern web applications.
     */
    @Test
    public void testDragCardBetweenLists() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.findElement(By.cssSelector("button")).click();

        // Create a new board
        driver.findElement(By.id("add_new_board")).click();
        driver.findElement(By.id("board_name")).sendKeys("Drag Drop Test");
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Wait for board to be created and loaded
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("board-header")));

        // Create source list
        driver.findElement(By.cssSelector(".inner")).click();
        driver.findElement(By.id("list_name")).sendKeys("Source List");
        driver.findElement(By.cssSelector("button")).click();

        // Wait for list to be created
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'lists-wrapper')]//h4[text()='Source List']")));

        // Create target list
        driver.findElement(By.cssSelector(".add-new > .inner")).click();
        driver.findElement(By.id("list_name")).sendKeys("Target List");
        driver.findElement(By.cssSelector("button")).click();

        // Wait for list to be created
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'lists-wrapper')]//h4[text()='Target List']")));

        // Get the source list element
        WebElement sourceList = driver.findElement(By.xpath(
                "//div[contains(@class, 'lists-wrapper')]//div[contains(@class, 'list')][.//h4[text()='Source List']]"));
        String sourceListId = sourceList.getAttribute("id");

        // Add a card to the source list
        driver.findElement(By.cssSelector("#" + sourceListId + " .add-new")).click();
        driver.findElement(By.id("card_name")).sendKeys("Card to move");
        driver.findElement(By.cssSelector("button")).click();

        // Wait for card to be created
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#" + sourceListId + " .cards-wrapper .card")));

        // Get the card element
        WebElement card = driver.findElement(By.cssSelector("#" + sourceListId + " .cards-wrapper .card"));

        // Get the target list element
        WebElement targetList = driver.findElement(By.xpath(
                "//div[contains(@class, 'lists-wrapper')]//div[contains(@class, 'list')][.//h4[text()='Target List']]"));
        String targetListId = targetList.getAttribute("id");

        // JavaScript to simulate drag and drop
        String moveCardJS = "function createEvent(typeOfEvent) {\n" +
                "   var event = document.createEvent(\"CustomEvent\");\n" +
                "   event.initCustomEvent(typeOfEvent, true, true, null);\n" +
                "   event.dataTransfer = {\n" +
                "       data: {},\n" +
                "       setData: function (key, value) {\n" +
                "           this.data[key] = value;\n" +
                "       },\n" +
                "       getData: function (key) {\n" +
                "           return this.data[key];\n" +
                "       }\n" +
                "   };\n" +
                "   return event;\n" +
                "}\n" +
                "\n" +
                "function dispatchEvent(element, event, transferData) {\n" +
                "   if (transferData !== undefined) {\n" +
                "       event.dataTransfer = transferData;\n" +
                "   }\n" +
                "   if (element.dispatchEvent) {\n" +
                "       element.dispatchEvent(event);\n" +
                "   } else if (element.fireEvent) {\n" +
                "       element.fireEvent(\"on\" + event.type, event);\n" +
                "   }\n" +
                "}\n" +
                "\n" +
                "function simulateDragDrop(sourceElement, destinationElement) {\n" +
                "   var dragStartEvent = createEvent('dragstart');\n" +
                "   dispatchEvent(sourceElement, dragStartEvent);\n" +
                "   var dropEvent = createEvent('drop');\n" +
                "   dispatchEvent(destinationElement, dropEvent, dragStartEvent.dataTransfer);\n" +
                "   var dragEndEvent = createEvent('dragend');\n" +
                "   dispatchEvent(sourceElement, dragEndEvent, dropEvent.dataTransfer);\n" +
                "}\n" +
                "\n" +
                "simulateDragDrop(arguments[0], arguments[1])";

        // Execute JavaScript to simulate drag and drop
        ((JavascriptExecutor) driver).executeScript(moveCardJS, card, targetList);

        // Wait for the card to be moved
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#" + targetListId + " .cards-wrapper .card")));

        // Verify card was moved to target list
        List<WebElement> sourceCards = driver
                .findElements(By.cssSelector("#" + sourceListId + " .cards-wrapper .card"));
        List<WebElement> targetCards = driver
                .findElements(By.cssSelector("#" + targetListId + " .cards-wrapper .card"));

        assertEquals("Source list should have 0 cards", 0, sourceCards.size());
        assertEquals("Target list should have 1 card", 1, targetCards.size());
        assertEquals("Card text should match", "Card to move", targetCards.get(0).getText());
    }

    @Test
    public void testReorderLists() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.findElement(By.cssSelector("button")).click();

        // Create a new board
        driver.findElement(By.id("add_new_board")).click();
        driver.findElement(By.id("board_name")).sendKeys("Reorder Lists Test");
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Wait for board to be created and loaded
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("board-header")));

        // Create first list
        driver.findElement(By.cssSelector(".inner")).click();
        driver.findElement(By.id("list_name")).sendKeys("List 1");
        driver.findElement(By.cssSelector("button")).click();

        // Wait for list to be created
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'lists-wrapper')]//h4[text()='List 1']")));

        // Create second list
        driver.findElement(By.cssSelector(".add-new > .inner")).click();
        driver.findElement(By.id("list_name")).sendKeys("List 2");
        driver.findElement(By.cssSelector("button")).click();

        // Wait for list to be created
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'lists-wrapper')]//h4[text()='List 2']")));

        // Get the two lists
        List<WebElement> lists = driver.findElements(By.cssSelector(".lists-wrapper .list:not(.add-new)"));
        WebElement list1 = lists.get(0);
        WebElement list2 = lists.get(1);

        // Verify initial order
        assertEquals("List 1", list1.findElement(By.cssSelector("h4")).getText());
        assertEquals("List 2", list2.findElement(By.cssSelector("h4")).getText());

        // Get the headers for dragging
        WebElement list1Header = list1.findElement(By.cssSelector("header"));
        WebElement list2Header = list2.findElement(By.cssSelector("header"));

        // Perform drag and drop using Actions
        Actions actions = new Actions(driver);
        actions.clickAndHold(list2Header)
                .moveToElement(list1, -10, 0) // Move to the left of list1
                .release()
                .perform();

        // Wait for reordering to take effect
        try {
            Thread.sleep(1000); // Give the UI time to update
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify the order has changed (list2 should now be before list1)
        List<WebElement> reorderedLists = driver.findElements(By.cssSelector(".lists-wrapper .list:not(.add-new)"));
        WebElement newFirstList = reorderedLists.get(0);
        WebElement newSecondList = reorderedLists.get(1);

        // Note: This assertion may fail if the app's drag-and-drop reordering doesn't
        // work with Selenium's Actions
        // In that case, you might need to rely on the JavaScript-based approach like in
        // the first test
        assertEquals("List 2", newFirstList.findElement(By.cssSelector("h4")).getText());
        assertEquals("List 1", newSecondList.findElement(By.cssSelector("h4")).getText());
    }
}
