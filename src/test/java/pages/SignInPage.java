package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SignInPage extends BasePage {
    
    @FindBy(id = "user_email")
    private WebElement emailField;
    
    @FindBy(id = "user_password")
    private WebElement passwordField;
    
    @FindBy(css = "button[type='submit']")
    private WebElement signInButton;
    

    public SignInPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    

    public SignInPage navigateTo() {
        driver.get(BASE_URL + "/sign_in");
        return this;
    }
    
  
    public SignInPage enterEmail(String email) {
        enterText(emailField, email);
        return this;
    }
    
 
    public SignInPage enterPassword(String password) {
        enterText(passwordField, password);
        return this;
    }
    

    public HomePage clickSignIn() {
        clickElement(signInButton);
        return new HomePage(driver);
    }
    
 
    public HomePage signIn(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        return clickSignIn();
    }
}
