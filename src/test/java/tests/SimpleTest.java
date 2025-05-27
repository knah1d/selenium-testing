package tests;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Very basic page loading test
 */
public class SimpleTest extends BaseTest {
    
    @Test
    public void testPageLoad() {
        // Just open the homepage and check title
        driver.get(pages.BasePage.BASE_URL);
        
        // Simple assertion to verify page loaded
        assertNotNull(driver.getTitle());
    }
}
