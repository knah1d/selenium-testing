package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object for the sign-up page
 */
public class SignUpPage extends BasePage {
    
    @FindBy(id = "user_first_name")
    private WebElement firstNameField;
    
    @FindBy(id = "user_last_name")
    private WebElement lastNameField;
    
    @FindBy(id = "user_email")
    private WebElement emailField;
    
    @FindBy(id = "user_password")
    private WebElement passwordField;
    
    @FindBy(id = "user_password_confirmation")
    private WebElement passwordConfirmationField;
    
    @FindBy(css = "button[type='submit']")
    private WebElement signUpButton;
    
    @FindBy(linkText = "Sign in")
    private WebElement signInLink;
    
    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public SignUpPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Navigate to the sign-up page
     * 
     * @return SignUpPage instance
     */
    public SignUpPage navigateTo() {
        driver.get(BASE_URL + "/sign_up");
        return this;
    }
    
    /**
     * Enter first name
     * 
     * @param firstName First name
     * @return SignUpPage instance
     */
    public SignUpPage enterFirstName(String firstName) {
        enterText(firstNameField, firstName);
        return this;
    }
    
    /**
     * Enter last name
     * 
     * @param lastName Last name
     * @return SignUpPage instance
     */
    public SignUpPage enterLastName(String lastName) {
        enterText(lastNameField, lastName);
        return this;
    }
    
    /**
     * Enter email
     * 
     * @param email Email address
     * @return SignUpPage instance
     */
    public SignUpPage enterEmail(String email) {
        enterText(emailField, email);
        return this;
    }
    
    /**
     * Enter password
     * 
     * @param password Password
     * @return SignUpPage instance
     */
    public SignUpPage enterPassword(String password) {
        enterText(passwordField, password);
        return this;
    }
    
    /**
     * Enter password confirmation
     * 
     * @param passwordConfirmation Password confirmation
     * @return SignUpPage instance
     */
    public SignUpPage enterPasswordConfirmation(String passwordConfirmation) {
        enterText(passwordConfirmationField, passwordConfirmation);
        return this;
    }
    
    /**
     * Click sign-up button
     * 
     * @return HomePage instance
     */
    public HomePage clickSignUp() {
        clickElement(signUpButton);
        return new HomePage(driver);
    }
    
    /**
     * Sign up with all required fields
     * 
     * @param firstName First name
     * @param lastName Last name
     * @param email Email address
     * @param password Password
     * @return HomePage instance
     */
    public HomePage signUp(String firstName, String lastName, String email, String password) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterPasswordConfirmation(password);
        return clickSignUp();
    }
    
    /**
     * Click the sign-in link to go to sign-in page
     * 
     * @return SignInPage instance
     */
    public SignInPage clickSignInLink() {
        clickElement(signInLink);
        return new SignInPage(driver);
    }
}
