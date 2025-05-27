package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import pages.HomePage;
import pages.BoardPage;
import pages.CardDetailPage;

/**
 * Simple workflow test
 */
public class WorkflowTests extends BaseTest {
    
    /**
     * Test a basic workflow
     */
    @Test
    public void testSimpleWorkflow() {
        // Create a board with lists
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                                     .createNewBoard("Workflow Test");
        
        // Create two lists
        boardPage.createList("To Do")
                .createList("Done");
        
        // Add a card
        boardPage.createCard(0, "Test Task");
        
        // Simple assertions
        assertEquals(2, boardPage.getListCount());
        assertEquals(1, boardPage.getCardCount());
    }
    
    /**
     * Test a minimal sprint planning workflow
     */
    @Test
    public void testSprintPlanningWorkflow() {
        // Create a new board for sprint planning
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                                     .createNewBoard("Sprint Planning");
        
        // Create lists for sprint planning
        boardPage.createList("Product Backlog")
                .createList("In Progress")
                .createList("Done");
        
        // Add user stories to Product Backlog
        boardPage.createCard(0, "User story: Login")
                .createCard(0, "User story: Registration");
        
        // Assert cards were created
        assertEquals(2, boardPage.getCardCount());
    }
}
