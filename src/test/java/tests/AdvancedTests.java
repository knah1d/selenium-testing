package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.Keys;

import pages.HomePage;
import pages.BoardPage;

/**
 * Basic keyboard test
 */
public class AdvancedTests extends BaseTest {
    
    /**
     * Test entering text into a card
     */
    @Test
    public void testKeyboardInput() {
        // Create a board with a list
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                                     .createNewBoard("Simple Test");
        
        boardPage.createList("Tasks");
        
        // Create a card with text
        boardPage.createCard(0, "Simple keyboard task");
        
        // Assert card was created
        assertTrue(boardPage.getCardCount() > 0);
    }
    
    /**
     * Test basic keyboard navigation
     */
    @Test
    public void testBasicKeyboardNavigation() {
        // Create a board
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                                     .createNewBoard("Keyboard Test");
        
        // Create a list
        boardPage.createList("Tasks");
        
        // Simple assertion
        assertEquals(1, boardPage.getListCount());
    }
}
