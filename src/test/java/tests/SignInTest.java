package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.SignInPage;

public class SignInTest extends BaseTest {

    @Test
    public void testSignIn() {
        SignInPage signInPage = new SignInPage(driver);
        signInPage.navigateTo();

        // Use John's credentials from Phoenix app documentation
        signInPage.signIn("john@phoenix-trello.com", "12345678");

        // Verify dashboard is loaded after sign in
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("add_new_board")));
        assertTrue(driver.findElement(By.id("add_new_board")).isDisplayed());
    }

    @Test
    public void testSignInWithInvalidCredentials() {
        SignInPage signInPage = new SignInPage(driver);
        signInPage.navigateTo();

        // Enter invalid credentials
        signInPage.enterEmail("invalid@example.com")
                .enterPassword("wrongpassword");

        signInPage.clickSignIn();

        // Verify error message is displayed
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        WebElement errorElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("error")));
        assertTrue(errorElement.isDisplayed());
        assertEquals("Invalid email or password", errorElement.getText());
    }

    @Test
    public void testNavigateToSignUp() {
        SignInPage signInPage = new SignInPage(driver);
        signInPage.navigateTo();

        // Add a way to navigate to sign up from sign in
        driver.findElement(By.linkText("Create a new account")).click();

        // Verify we're on the sign up page
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/sign_up"));

        assertTrue(driver.getCurrentUrl().contains("/sign_up"));
    }

    @Test
    public void testRememberMe() {
        SignInPage signInPage = new SignInPage(driver);
        signInPage.navigateTo();

        // Check remember me option if available
        WebElement rememberMeCheckbox = driver.findElement(By.name("remember_me"));
        if (!rememberMeCheckbox.isSelected()) {
            rememberMeCheckbox.click();
        }

        // Sign in with valid credentials
        signInPage.enterEmail("john@phoenix-trello.com")
                .enterPassword("12345678");

        signInPage.clickSignIn();

        // Verify dashboard is loaded
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("add_new_board")));
        assertTrue(driver.findElement(By.id("add_new_board")).isDisplayed());
    }
}
