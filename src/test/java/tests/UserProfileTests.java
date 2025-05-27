package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;

import pages.HomePage;

import java.util.List;

public class UserProfileTests extends BaseTest {

    @Test
    public void testViewUserProfile() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.findElement(By.cssSelector("button")).click();

        // Wait for dashboard to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("add_new_board")));

        // Click on user menu
        driver.findElement(By.cssSelector(".current-user")).click();

        // Click on profile option
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'My profile')]")));
        driver.findElement(By.xpath("//a[contains(text(), 'My profile')]")).click();

        // Verify profile page is loaded
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("view-container")));
        assertTrue(driver.findElement(By.xpath("//h3[contains(text(), 'Edit profile')]")).isDisplayed());
    }

    @Test
    public void testEditUserProfile() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.findElement(By.cssSelector("button")).click();

        // Wait for dashboard to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("add_new_board")));

        // Click on user menu
        driver.findElement(By.cssSelector(".current-user")).click();

        // Click on profile option
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'My profile')]")));
        driver.findElement(By.xpath("//a[contains(text(), 'My profile')]")).click();

        // Verify profile page is loaded and edit fields
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("view-container")));

        // Generate a random string for unique identification
        String timestamp = String.valueOf(System.currentTimeMillis());

        // Update first name
        WebElement firstNameField = driver.findElement(By.id("user_first_name"));
        firstNameField.clear();
        firstNameField.sendKeys("John" + timestamp);

        // Update last name
        WebElement lastNameField = driver.findElement(By.id("user_last_name"));
        lastNameField.clear();
        lastNameField.sendKeys("Doe" + timestamp);

        // Click on update button
        driver.findElement(By.xpath("//button[contains(text(), 'Update profile')]")).click();

        // Verify profile was updated (check for success message)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("header-inner")));

        // Go back to profile to verify changes
        driver.findElement(By.cssSelector(".current-user")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'My profile')]")));
        driver.findElement(By.xpath("//a[contains(text(), 'My profile')]")).click();

        // Verify the fields contain the updated values
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user_first_name")));
        String updatedFirstName = driver.findElement(By.id("user_first_name")).getAttribute("value");
        String updatedLastName = driver.findElement(By.id("user_last_name")).getAttribute("value");

        assertEquals("John" + timestamp, updatedFirstName);
        assertEquals("Doe" + timestamp, updatedLastName);
    }

    @Test
    public void testChangePassword() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.findElement(By.cssSelector("button")).click();

        // Wait for dashboard to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("add_new_board")));

        // Click on user menu
        driver.findElement(By.cssSelector(".current-user")).click();

        // Click on profile option
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'My profile')]")));
        driver.findElement(By.xpath("//a[contains(text(), 'My profile')]")).click();

        // Verify profile page is loaded
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("view-container")));

        // Find and click on "Change password" tab/link
        driver.findElement(By.xpath("//a[contains(text(), 'Change password')]")).click();

        // Fill in password fields
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user_current_password")));

        driver.findElement(By.id("user_current_password")).sendKeys("12345678"); // Current password
        driver.findElement(By.id("user_password")).sendKeys("87654321"); // New password
        driver.findElement(By.id("user_password_confirmation")).sendKeys("87654321"); // Confirm new password

        // Click on update password button
        driver.findElement(By.xpath("//button[contains(text(), 'Update password')]")).click();

        // Verify password was updated (check for success message or redirect)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("header-inner")));

        // Sign out
        driver.findElement(By.cssSelector(".current-user")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Sign out')]")));
        driver.findElement(By.xpath("//a[contains(text(), 'Sign out')]")).click();

        // Sign in with new password
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user_email")));
        driver.findElement(By.id("user_email")).sendKeys("john@phoenix-trello.com");
        driver.findElement(By.id("user_password")).sendKeys("87654321");
        driver.findElement(By.cssSelector("button")).click();

        // Verify successful login
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("add_new_board")));
        assertTrue(driver.findElement(By.id("add_new_board")).isDisplayed());

        // IMPORTANT: Reset password back to original so other tests don't fail
        driver.findElement(By.cssSelector(".current-user")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'My profile')]")));
        driver.findElement(By.xpath("//a[contains(text(), 'My profile')]")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("view-container")));
        driver.findElement(By.xpath("//a[contains(text(), 'Change password')]")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user_current_password")));
        driver.findElement(By.id("user_current_password")).sendKeys("87654321"); // Current password
        driver.findElement(By.id("user_password")).sendKeys("12345678"); // Original password
        driver.findElement(By.id("user_password_confirmation")).sendKeys("12345678"); // Confirm original password

        driver.findElement(By.xpath("//button[contains(text(), 'Update password')]")).click();
    }
}
