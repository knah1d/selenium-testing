package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import pages.HomePage;
import pages.BoardPage;

/**
 * Simple tests for board creation
 */
public class BoardTests extends BaseTest {
    
    /**
     * Test creating a single board
     */
    @Test
    public void testCreateBoard() {
        // Create a board with a simple name
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                                      .createNewBoard("My Test Board");
        
        // Simple assertion for board creation
        assertFalse(driver.getTitle().isEmpty());
    }
    
    /**
     * Test creating a list on a board
     */
    @Test
    public void testCreateList() {
        // Create a board first
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                                      .createNewBoard("List Test Board");
        
        // Add a single list
        boardPage.createList("To Do");
        
        // Simple assertion
        assertTrue(boardPage.getListCount() > 0);
    }
    
    /**
     * Test creating a board with lists and cards
     */
    @Test
    public void testCreateBoardWithListsAndCards() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                                      .createNewBoard("Feature Development");
        
        // Add lists
        boardPage.createList("Backlog")
                .createList("In Progress");
        
        // Add cards to lists
        boardPage.createCard(0, "Implement login")
                .createCard(0, "Add user profile")
                .createCard(1, "Fix navigation bug");
        
        // Assert cards were created
        assertEquals(3, boardPage.getCardCount());
    }
}
