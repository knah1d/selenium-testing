package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Assume;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseTest {
    protected WebDriver driver;
    protected Map<String, Object> vars;
    protected JavascriptExecutor js;
    protected static final String APP_URL = "http://localhost:4000";

    @Before
    public void setUp() {
        // Check if the application is running
        boolean isAppRunning = checkIfAppIsRunning(APP_URL);
        Assume.assumeTrue("Application must be running at " + APP_URL + " for tests to execute", isAppRunning);

        System.setProperty("webdriver.gecko.driver", "/snap/bin/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);
        options.addPreference("dom.webnotifications.enabled", false);

        driver = new FirefoxDriver(options);
        driver.manage().window().setSize(new Dimension(1280, 720));
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();

        // Sign in to the application
        signIn();
    }

    private boolean checkIfAppIsRunning(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.connect();
            return connection.getResponseCode() < 400;
        } catch (IOException e) {
            System.out.println("Application is not running at " + url);
            return false;
        }
    }

    /**
     * Sign in to the application with a test account
     */
    private void signIn() {
        driver.get(APP_URL + "/sign_in");
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Enter email
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("user_email")));
        emailField.clear();
        emailField.sendKeys("test@example.com");

        // Enter password
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("user_password")));
        passwordField.clear();
        passwordField.sendKeys("password");

        // Click sign in button
        WebElement signInButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        signInButton.click();

        // Wait for home page to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".view-container")));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
