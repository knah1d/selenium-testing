package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import pages.HomePage;
import pages.BoardPage;

import java.util.List;

public class BoardTests extends BaseTest {

    @Test
    public void testCreateBoard() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                .createNewBoard("My Test Board");

        assertFalse(driver.getTitle().isEmpty());
    }

    @Test
    public void testCreateBoardWithListsAndCards() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                .createNewBoard("Feature Development");

        boardPage.createList("Backlog")
                .createList("In Progress");

        boardPage.createCard(0, "Implement login")
                .createCard(0, "Add user profile")
                .createCard(1, "Fix navigation bug");

        assertEquals(3, boardPage.getCardCount());
    }

    @Test
    public void testDeleteBoard() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.findElement(By.cssSelector("button")).click();

        // Create a new board
        driver.findElement(By.id("add_new_board")).click();
        driver.findElement(By.id("board_name")).sendKeys("Board to Delete");
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Wait for board to be created and loaded
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("board-header")));

        // Click the menu button
        driver.findElement(By.cssSelector(".header-right .dropdown-toggle")).click();

        // Click on "Delete board" option
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Delete board')]")));
        driver.findElement(By.xpath("//a[contains(text(), 'Delete board')]")).click();

        // Wait for confirmation modal
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal")));

        // Confirm deletion
        driver.findElement(By.xpath("//button[contains(text(), 'Delete')]")).click();

        // Verify we're redirected to the boards page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("add_new_board")));
        assertTrue(driver.findElement(By.id("add_new_board")).isDisplayed());
    }

    @Test
    public void testBoardNameValidation() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.findElement(By.cssSelector("button")).click();

        // Try to create a board with empty name
        driver.findElement(By.id("add_new_board")).click();
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Verify form is still open (validation error)
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        assertTrue(driver.findElement(By.id("board_name")).isDisplayed());

        // Now enter a valid name
        driver.findElement(By.id("board_name")).sendKeys("Valid Board Name");
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Verify board was created
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("board-header")));
        assertTrue(driver.findElement(By.className("board-header")).isDisplayed());
    }

    @Test
    public void testRenameBoard() {
        HomePage homePage = new HomePage(driver);
        homePage.navigateTo()
                .createNewBoard("Original Board Name");

        // Click on the board title to edit
        WebElement boardTitle = driver.findElement(By.cssSelector(".board-header h3"));

        // Double-click on board title to rename
        Actions actions = new Actions(driver);
        actions.doubleClick(boardTitle).perform();

        // Wait for the input field to appear
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        WebElement titleInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".board-header input")));

        // Clear and enter new name
        titleInput.clear();
        titleInput.sendKeys("Renamed Board");
        titleInput.sendKeys(Keys.ENTER);

        // Verify the board title was updated
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector(".board-header h3"), "Renamed Board"));

        assertEquals("Renamed Board", driver.findElement(By.cssSelector(".board-header h3")).getText());
    }

    @Test
    public void testMultiBoardOperations() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.findElement(By.cssSelector("button")).click();

        // Create multiple boards
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Create first board
        driver.findElement(By.id("add_new_board")).click();
        driver.findElement(By.id("board_name")).sendKeys("Board One");
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Wait for board to be created and navigate back to home
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("board-header")));
        driver.findElement(By.className("logo")).click();

        // Wait for homepage to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("add_new_board")));

        // Create second board
        driver.findElement(By.id("add_new_board")).click();
        driver.findElement(By.id("board_name")).sendKeys("Board Two");
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Wait for board to be created and navigate back to home
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("board-header")));
        driver.findElement(By.className("logo")).click();

        // Verify both boards exist
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("add_new_board")));
        List<WebElement> boards = driver.findElements(By.cssSelector(".boards-wrapper .board"));

        assertTrue("Should have at least 2 boards", boards.size() >= 2);

        // Check board names
        List<String> boardNames = boards.stream()
                .map(board -> board.findElement(By.cssSelector(".inner h4")).getText())
                .toList();

        assertTrue(boardNames.contains("Board One"));
        assertTrue(boardNames.contains("Board Two"));
    }
}
