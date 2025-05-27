package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page object for the sign-in page
 */
public class SignInPage extends BasePage {
    
    @FindBy(id = "user_email")
    private WebElement emailField;
    
    @FindBy(id = "user_password")
    private WebElement passwordField;
    
    @FindBy(css = "button[type='submit']")
    private WebElement signInButton;
    
    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public SignInPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Navigate to the sign-in page
     * 
     * @return SignInPage instance
     */
    public SignInPage navigateTo() {
        driver.get(BASE_URL + "/sign_in");
        return this;
    }
    
    /**
     * Enter email
     * 
     * @param email Email address
     * @return SignInPage instance
     */
    public SignInPage enterEmail(String email) {
        enterText(emailField, email);
        return this;
    }
    
    /**
     * Enter password
     * 
     * @param password Password
     * @return SignInPage instance
     */
    public SignInPage enterPassword(String password) {
        enterText(passwordField, password);
        return this;
    }
    
    /**
     * Click sign-in button
     * 
     * @return HomePage instance
     */
    public HomePage clickSignIn() {
        clickElement(signInButton);
        return new HomePage(driver);
    }
    
    /**
     * Sign in with email and password
     * 
     * @param email Email address
     * @param password Password
     * @return HomePage instance
     */
    public HomePage signIn(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        return clickSignIn();
    }
}
