package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pages.SignInPage;
import pages.HomePage;
import pages.BoardPage;

/**
 * Integrated test that uses the correct selectors from the Phoenix application
 */
public class IntegratedTest extends BaseTest {
    
    @Test
    public void testSignInAndCreateBoard() {
        // Step 1: Sign in using the page object
        SignInPage signInPage = new SignInPage(driver);
        signInPage.navigateTo();
        
        // Use test credentials
        HomePage homePage = signInPage.signIn("test@example.com", "password");
        
        // Step 2: Create a board with a unique name
        String boardName = "Test Board " + System.currentTimeMillis();
        BoardPage boardPage = homePage.createNewBoard(boardName);
        
        // Step 3: Create a list on the board
        boardPage.createList("Test List");
        
        // Simple assertions
        assertTrue(driver.getCurrentUrl().contains("boards"));
        assertTrue(boardPage.getListCount() > 0);
    }
    
    @Test
    public void testCreateMultipleLists() {
        // Sign in
        SignInPage signInPage = new SignInPage(driver);
        HomePage homePage = signInPage.navigateTo()
                                     .signIn("john@phoenix-trello.com", "12345678");
        
        // Create a board
        String boardName = "Multiple Lists " + System.currentTimeMillis();
        BoardPage boardPage = homePage.createNewBoard(boardName);
        
        // Create multiple lists
        boardPage.createList("To Do")
                .createList("In Progress")
                .createList("Done");
        
        // Simple assertion
        assertEquals(3, boardPage.getListCount());
    }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
}
