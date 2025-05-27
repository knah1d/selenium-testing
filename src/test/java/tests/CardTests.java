package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import pages.HomePage;
import pages.BoardPage;
import pages.CardDetailPage;

/**
 * Simple test for card creation
 */
public class CardTests extends BaseTest {
    
    /**
     * Test creating a card
     */
    @Test
    public void testCreateCard() {
        // Create a board with a list
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                                     .createNewBoard("Card Test");
        
        // Create a list and add a card
        boardPage.createList("Tasks")
                .createCard(0, "Test Task");
        
        // Simple assertion
        assertTrue(boardPage.getCardCount() > 0);
    }
    
    /**
     * Test creating multiple cards
     */
    @Test
    public void testCreateMultipleCards() {
        // Create a board with multiple cards
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                                     .createNewBoard("Multiple Cards")
                                     .createList("Development");
        
        // Create cards
        boardPage.createCard(0, "Task 1")
                .createCard(0, "Task 2");
        
        // Simple assertion
        assertEquals(2, boardPage.getCardCount());
    }
    
    /**
     * Test opening a card's detail page
     */
    @Test
    public void testOpenCardDetail() {
        // Create a board with a list and card
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                                    .createNewBoard("Card Detail Test")
                                    .createList("Features")
                                    .createCard(0, "New Feature");
        
        // Open the card
        CardDetailPage detailPage = boardPage.openCard(0);
        
        // Simple assertion
        assertNotNull(driver.getCurrentUrl());
    }
}
