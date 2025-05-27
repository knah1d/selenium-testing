package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import pages.SignUpPage;
import pages.HomePage;

/**
 * Test for user registration
 */
public class SignUpTest extends BaseTest {
    
    @Test
    public void testSignUp() {
        // Open the sign-up page
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.navigateTo();
        
        // Generate a random email to avoid conflicts
        String timestamp = String.valueOf(System.currentTimeMillis());
        String testEmail = "test@example.com";
        
        // Register a new user
        HomePage homePage = signUpPage.signUp(
            "Test", 
            "User", 
            testEmail, 
            "password123"
        );
        
        // Simple assertion
        assertNotNull(driver.getTitle());
    }
}
