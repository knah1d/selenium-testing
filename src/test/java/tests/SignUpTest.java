package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.SignUpPage;
import pages.SignInPage;

public class SignUpTest extends BaseTest {

    @Test
    public void testSignUp() {
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.navigateTo();

        // Use unique email with timestamp to avoid email already taken error
        String timestamp = String.valueOf(System.currentTimeMillis());
        String testEmail = "test" + timestamp + "@example.com";

        signUpPage.signUp(
                "Test",
                "User",
                testEmail,
                "password123");

        // Verify we're redirected to the boards page after successful signup
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("add_new_board")));
        assertTrue(driver.findElement(By.id("add_new_board")).isDisplayed());
    }

    @Test
    public void testPasswordMismatch() {
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.navigateTo();

        // Fill in the form with mismatched passwords
        signUpPage.enterFirstName("Test")
                .enterLastName("User")
                .enterEmail("test@example.com")
                .enterPassword("password123");

        // Enter a different password in confirmation field
        signUpPage.enterPasswordConfirmation("different_password");

        // Click sign up
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Verify error message is displayed
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        WebElement errorElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("error")));
        assertTrue(errorElement.isDisplayed());
        assertTrue(errorElement.getText().contains("doesn't match"));
    }

    @Test
    public void testNavigateToSignIn() {
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.navigateTo();

        // Click on the "Sign in" link to navigate to sign in page
        SignInPage signInPage = signUpPage.clickSignInLink();

        // Verify we're on the sign in page
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/sign_in"));

        assertTrue(driver.getCurrentUrl().contains("/sign_in"));
    }

    @Test
    public void testInvalidEmail() {
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.navigateTo();

        // Fill in the form with invalid email
        signUpPage.enterFirstName("Test")
                .enterLastName("User")
                .enterEmail("invalid-email") // Not a valid email format
                .enterPassword("password123")
                .enterPasswordConfirmation("password123");

        // Click sign up
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Verify error message is displayed
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        WebElement errorElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("error")));
        assertTrue(errorElement.isDisplayed());
        assertTrue(errorElement.getText().toLowerCase().contains("email"));
    }
}
