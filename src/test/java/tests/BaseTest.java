package tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Base test class for all tests
 */
public abstract class BaseTest {
    protected WebDriver driver;
    protected Map<String, Object> vars;
    protected JavascriptExecutor js;
    
    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "/snap/bin/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);
        options.addPreference("dom.webnotifications.enabled", false);
        
        driver = new FirefoxDriver(options);
        driver.manage().window().setSize(new Dimension(1280, 720));
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
