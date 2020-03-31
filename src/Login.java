import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//login to web
public class Login {
    WebDriver driver;

    //log in data
    @FindBy(css = ".nav-bar.buttons li:nth-of-type(3)")                     //login
    WebElement signUpEnterToWebButton;
    @FindBy (css = ".entry.ember-view .field:nth-of-type(1) label input")   //email
    WebElement mailToLogin;
    @FindBy (css = ".entry.ember-view .field:nth-of-type(2) label input")   //password
    WebElement passwordToLogin;
    @FindBy  (css = ".ember-view.ui-field.ui-checkbox label input")         //remember me check button
    WebElement rememberMeCheckbox;
    @FindBy (css = ".ui-btn.orange.large")                                  //entrance button
    WebElement enterBuyMe;
    @FindBy (css = ".entry.ember-view div:nth-of-type(1) ul li")
    WebElement emailVerificationMessage;
    @FindBy (css = ".entry.ember-view div:nth-of-type(2) ul li")
    WebElement passwordVerificationMessage;

    //constructor
    public Login (WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }//end of constructor

    //login to web
    public String loginToWeb(String path){
        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.visibilityOf(signUpEnterToWebButton));
        signUpEnterToWebButton.click();    //enter&sign up button
        //login succeed
        try {
            wait.until(ExpectedConditions.urlToBe("https://buyme.co.il/?modal=login"));
            mailToLogin.sendKeys(General.readFromFile("email", path));                     //fill email
            passwordToLogin.sendKeys(General.readFromFile("password", path));              //fill password
            if (!(rememberMeCheckbox.isSelected()))
            {
                rememberMeCheckbox.click();
            }
            enterBuyMe.click();
        }
        //login failed
        catch (Exception e) {
            return "Login failed";
        }
        //get url
        try {
            wait.until(ExpectedConditions.urlToBe("https://buyme.co.il/"));
            return driver.getCurrentUrl();
        }
        catch (Exception t){
            return driver.getCurrentUrl();
        }
    }//end of loginToWeb

    //login without credentials
    public String loginWithoutCredentials(){
        signUpEnterToWebButton.click();    //enter&sign up button
        enterBuyMe.click();
        //get login info
        if (emailVerificationMessage.isDisplayed() && passwordVerificationMessage.isDisplayed())
        {
            System.out.println(emailVerificationMessage.getText());
            System.out.println(passwordVerificationMessage.getText());
            return "can't login, missing info";
        }
        return "logged in";
    }//end of loginWithoutCredentials
}//end of Login class
