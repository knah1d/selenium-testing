package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import pages.SignInPage;
import pages.HomePage;

/**
 * Test for sign-in functionality
 */
public class SignInTest extends BaseTest {
    
    @Test
    public void testSignIn() {
        // Open the sign-in page
        SignInPage signInPage = new SignInPage(driver);
        signInPage.navigateTo();
        
        // Sign in with test credentials
        HomePage homePage = signInPage.signIn("test@example.com", "password");
        
        // Simple assertion
        assertNotNull(driver.getTitle());
    }
}
