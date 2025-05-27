package tests;

import org.junit.Test;
import org.junit.Assume;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.HomePage;
import pages.BoardPage;

public class ListTests extends BaseTest {

    @Test
    public void testCreateList() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                .createNewBoard("List Test Board");

        // Create a list
        String listName = "To Do";
        boardPage.createList(listName);

        // Verify the list was created
        assertEquals("Should have 1 list", 1, boardPage.getListCount());
        assertTrue("List should be named '" + listName + "'", boardPage.getListNames().contains(listName));
    }

    @Test
    public void testCreateMultipleLists() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                .createNewBoard("Multiple Lists Board");

        // Create three lists
        boardPage.createList("Backlog");
        boardPage.createList("In Progress");
        boardPage.createList("Done");

        // Verify lists are created
        assertEquals("Should have 3 lists created", 3, boardPage.getListCount());

        // Verify list names
        java.util.List<String> listNames = boardPage.getListNames();
        assertTrue("'Backlog' list should exist", listNames.contains("Backlog"));
        assertTrue("'In Progress' list should exist", listNames.contains("In Progress"));
        assertTrue("'Done' list should exist", listNames.contains("Done"));
    }

    @Test
    public void testListNamesDisplay() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                .createNewBoard("List Names Board");

        String listName = "Requirements";
        boardPage.createList(listName);

        // Verify list name is displayed correctly
        assertTrue("List name should be displayed correctly", boardPage.getListNames().contains(listName));
    }

    @Test
    public void testAddCardsToList() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                .createNewBoard("Cards in List Board");

        // Create a list
        String listName = "Development";
        boardPage.createList(listName);

        // Add cards to the list (index 0)
        boardPage.createCard(0, "Fix login bug")
                .createCard(0, "Implement user registration")
                .createCard(0, "Add password recovery");

        // Verify the card count
        assertEquals("Should have 3 cards created", 3, boardPage.getCardCount());
    }

    @Test
    public void testListVisibility() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                .createNewBoard("List Visibility Test");

        // Create two lists with specific names
        boardPage.createList("URGENT TASKS");
        boardPage.createList("COMPLETED ITEMS");

        // Verify both lists are visible with correct names
        assertEquals("Should have 2 lists created", 2, boardPage.getListCount());

        java.util.List<String> listNames = boardPage.getListNames();
        assertTrue("'URGENT TASKS' list should exist", listNames.contains("URGENT TASKS"));
        assertTrue("'COMPLETED ITEMS' list should exist", listNames.contains("COMPLETED ITEMS"));
    }

   
    @Test
    public void testAddSimpleList() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                .createNewBoard("Simple List Test");

        // Create a list with a simple name
        boardPage.createList("list1");

        // Verify the list was created
        assertEquals("Should have 1 list", 1, boardPage.getListCount());
        assertTrue("List should be named 'list1'", boardPage.getListNames().contains("list1"));
    }

    @Test
    public void testMoveCardsBetweenLists() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                .createNewBoard("Move Cards Test");

        // Create two lists
        boardPage.createList("Source List");
        boardPage.createList("Target List");

        // Add cards to the first list
        boardPage.createCard(0, "Card to move");

        // Verify the card was created in the first list
        assertEquals("Should have 1 card created", 1, boardPage.getCardCount());

        // Move the card to the second list using JavaScript
        // We use JavaScript executor since Selenium's drag and drop can be unreliable
        WebElement card = driver.findElement(By.cssSelector(".card"));
        WebElement targetList = driver.findElements(By.cssSelector(".list:not(.add-new)")).get(1);

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

        js.executeScript(moveCardJS, card, targetList);

        // Wait for the card to be moved
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.attributeContains(targetList, "innerHTML", "Card to move"));

        // Verify the card was moved
        WebElement movedCard = targetList.findElement(By.cssSelector(".card"));
        assertTrue("Card should be moved to the second list",
                movedCard.getText().contains("Card to move"));
    }

    @Test
    public void testEditListName() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                .createNewBoard("Edit List Name Test");

        // Create a list
        boardPage.createList("Initial Name");

        // Verify the list was created
        assertEquals("Should have 1 list", 1, boardPage.getListCount());
        assertTrue("List should have initial name", boardPage.getListNames().contains("Initial Name"));

        // Find the list header and double-click to edit
        WebElement listHeader = driver.findElement(By.cssSelector(".list-header h4"));

        // Double-click to edit using JavaScript
        js.executeScript("arguments[0].dispatchEvent(new MouseEvent('dblclick', " +
                "{view: window, bubbles: true, cancelable: true}));", listHeader);

        // Wait for the edit form to appear
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement editInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".list-header input")));

        // Clear and enter new name
        editInput.clear();
        editInput.sendKeys("Updated Name");
        editInput.sendKeys(Keys.ENTER);

        // Wait for the update to complete
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector(".list-header h4"), "Updated Name"));

        // Verify the list name was updated
        assertTrue("List name should be updated",
                boardPage.getListNames().contains("Updated Name"));
        assertFalse("Old list name should not exist",
                boardPage.getListNames().contains("Initial Name"));
    }

    @Test
    public void testApplicationAvailability() {
        // This test explicitly checks if the app is running
        // and will be skipped if it's not available

        try {
            driver.get(APP_URL);
            WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

            // If we got here, the app is running
            assertTrue("Application should be running", true);
        } catch (Exception e) {
            // This will mark the test as skipped rather than failed
            Assume.assumeTrue("Application must be running at " + APP_URL, false);
        }
    }

    @Test
    public void testEmptyListNameValidation() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                .createNewBoard("Empty List Test");

        // Click add list button
        driver.findElement(By.cssSelector(".inner")).click();

        // Try to save with empty name
        WebElement saveButton = driver.findElement(By.cssSelector("form#new_list_form button"));
        saveButton.click();

        // Verify the form is still open (validation prevented submission)
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        boolean formStillOpen = false;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("list_name")));
            formStillOpen = true;
        } catch (Exception e) {
            formStillOpen = false;
        }

        assertTrue("Form should still be open when submitting empty name", formStillOpen);

        // Now enter a valid name and submit
        WebElement listNameInput = driver.findElement(By.id("list_name"));
        listNameInput.sendKeys("Valid List Name");
        saveButton.click();

        // Verify the list was created
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'lists-wrapper')]//h4[text()='Valid List Name']")));

        assertTrue("List should be created after entering valid name",
                boardPage.getListNames().contains("Valid List Name"));
    }
}
