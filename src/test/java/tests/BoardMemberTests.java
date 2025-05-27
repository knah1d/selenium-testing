package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;

import pages.HomePage;
import pages.BoardPage;

import java.util.List;

public class BoardMemberTests extends BaseTest {

    @Test
    public void testAddBoardMember() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.findElement(By.cssSelector("button")).click();

        // Create a new board
        driver.findElement(By.id("add_new_board")).click();
        driver.findElement(By.id("board_name")).sendKeys("Shared Board Test");
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Wait for board to be created and loaded
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("board-header")));

        // Click on "Add member" button
        driver.findElement(By.cssSelector(".board-users .add-new")).click();

        // Wait for add member form to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("crawljax_member_email")));

        // Enter member email and add
        driver.findElement(By.id("crawljax_member_email")).sendKeys("new.member@example.com");
        driver.findElement(By.xpath("//button[contains(text(), 'Add member')]")).click();

        // Verify member was added (should be at least 2 members now - owner + new
        // member)
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector(".board-users .react-gravatar"), 1));

        List<WebElement> members = driver.findElements(By.cssSelector(".board-users .react-gravatar"));
        assertTrue("Board should have at least 2 members", members.size() >= 2);
    }

    @Test
    public void testShowBoardMembers() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.findElement(By.cssSelector("button")).click();

        // Create a new board
        driver.findElement(By.id("add_new_board")).click();
        driver.findElement(By.id("board_name")).sendKeys("Members Board");
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Wait for board to be created and loaded
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("board-header")));

        // Add multiple members
        driver.findElement(By.cssSelector(".board-users .add-new")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("crawljax_member_email")));
        driver.findElement(By.id("crawljax_member_email")).sendKeys("member1@example.com");
        driver.findElement(By.xpath("//button[contains(text(), 'Add member')]")).click();

        // Add another member
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".board-users .add-new")));
        driver.findElement(By.cssSelector(".board-users .add-new")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("crawljax_member_email")));
        driver.findElement(By.id("crawljax_member_email")).sendKeys("member2@example.com");
        driver.findElement(By.xpath("//button[contains(text(), 'Add member')]")).click();

        // Verify members are displayed
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector(".board-users .react-gravatar"), 2));

        List<WebElement> members = driver.findElements(By.cssSelector(".board-users .react-gravatar"));
        assertTrue("Board should have at least 3 members", members.size() >= 3);
    }

    @Test
    public void testAddMemberWithInvalidEmail() {
        // Navigate to sign in
        driver.get("http://localhost:4000/sign_in");
        driver.findElement(By.cssSelector("button")).click();

        // Create a new board
        driver.findElement(By.id("add_new_board")).click();
        driver.findElement(By.id("board_name")).sendKeys("Invalid Member Test");
        driver.findElement(By.id("board_name")).sendKeys(Keys.ENTER);

        // Wait for board to be created and loaded
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("board-header")));

        // Click on "Add member" button
        driver.findElement(By.cssSelector(".board-users .add-new")).click();

        // Wait for add member form to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("crawljax_member_email")));

        // Enter invalid email and add
        driver.findElement(By.id("crawljax_member_email")).sendKeys("invalid-email");
        driver.findElement(By.xpath("//button[contains(text(), 'Add member')]")).click();

        // Verify error message is displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".drop-down .error")));
        assertTrue(driver.findElement(By.cssSelector(".drop-down .error")).isDisplayed());
    }
}
