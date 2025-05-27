package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


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
    

    public SignUpPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    

    public SignUpPage navigateTo() {
        driver.get(BASE_URL + "/sign_up");
        return this;
    }
    

    public SignUpPage enterFirstName(String firstName) {
        enterText(firstNameField, firstName);
        return this;
    }
    
 
    public SignUpPage enterLastName(String lastName) {
        enterText(lastNameField, lastName);
        return this;
    }
    

    public SignUpPage enterEmail(String email) {
        enterText(emailField, email);
        return this;
    }
    

    public SignUpPage enterPassword(String password) {
        enterText(passwordField, password);
        return this;
    }

    public SignUpPage enterPasswordConfirmation(String passwordConfirmation) {
        enterText(passwordConfirmationField, passwordConfirmation);
        return this;
    }
    

    public HomePage clickSignUp() {
        clickElement(signUpButton);
        return new HomePage(driver);
    }
    

    public HomePage signUp(String firstName, String lastName, String email, String password) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterPasswordConfirmation(password);
        return clickSignUp();
    }
    

    public SignInPage clickSignInLink() {
        clickElement(signInLink);
        return new SignInPage(driver);
    }
}
