import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import java.util.HashMap;
import java.util.Map;

import pages.HomePage;
import pages.BoardPage;
import tests.BaseTest;

/**
 * Simple main test class
 */
public class Test1Test extends BaseTest {
  
  /**
   * Original test simplified
   */
  @Test
  public void test1() {
    // Create a simple board
    HomePage homePage = new HomePage(driver);
    homePage.navigateTo()
           .createNewBoard("board1");
    
    // Basic assertion
    assertFalse(driver.getTitle().isEmpty());
  }
  
  // @Test
  // public void testAnotherPage() {
  //   // Test with a different URL (replace with your actual test page URL)
  //   TestUtil.navigateToUrl(driver, "http://localhost:4000/anotherpage", "board2");
    
  //   // Perform assertions after navigation
  //   assertFalse(driver.getTitle().isEmpty());
  // }
}