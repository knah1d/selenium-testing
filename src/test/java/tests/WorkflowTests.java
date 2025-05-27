package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import pages.HomePage;
import pages.BoardPage;

public class WorkflowTests extends BaseTest {
    
 
    @Test
    public void testSimpleWorkflow() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                                     .createNewBoard("Workflow Test");
        
        boardPage.createList("To Do")
                .createList("Done");
        
        boardPage.createCard(0, "Test Task");
        
        assertEquals(2, boardPage.getListCount());
        assertEquals(1, boardPage.getCardCount());
    }
    
 
    @Test
    public void testSprintPlanningWorkflow() {
        HomePage homePage = new HomePage(driver);
        BoardPage boardPage = homePage.navigateTo()
                                     .createNewBoard("Sprint Planning");
        
        boardPage.createList("Product Backlog")
                .createList("In Progress")
                .createList("Done");
        
        boardPage.createCard(0, "User story: Login")
                .createCard(0, "User story: Registration");
        
        assertEquals(2, boardPage.getCardCount());
    }
}
