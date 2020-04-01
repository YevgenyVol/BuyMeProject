import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


//registration to buyMe
public class RegistrationScreen {

    WebDriver driver;
    //login links
    @FindBy (css = ".seperator-link")
        WebElement signUpEnterToWebButton;
    @FindBy (css = ".text-btn")
        WebElement signUp;
    //registration data
    @FindBy (css = ".option.oldschool form div:nth-of-type(1) label input")
        WebElement name;
    @FindBy (css = ".option.oldschool form div:nth-of-type(2) label input")
        WebElement eMail;
    @FindBy (id = "valPass")
        WebElement password;
    @FindBy (css = ".option.oldschool form div:nth-of-type(4) label input")
        WebElement passwordConfirmation;
    @FindBy (css = ".ui-btn.orange.large")
        WebElement signToBuyMeButton;

    //constructor
    public RegistrationScreen (WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }//end of constructor

    //sign up to web
    public String signUpToBuyMe(String path) {
        WebDriverWait wait = new WebDriverWait(driver,3);
        //enter to sign up screen
        signUpEnterToWebButton.click();    //enter&sign up button
        signUp.click();                    //sign up

        //fill sign up data
        try {
                wait.until(ExpectedConditions.urlToBe("https://buyme.co.il/?modal=login"));
                name.sendKeys(General.readFromFile("name", path));                       //fill name
                eMail.sendKeys(General.readFromFile("email", path));                     //fill email
                password.sendKeys(General.readFromFile("password", path));               //fill password
                passwordConfirmation.sendKeys(General.readFromFile("password", path));   //fill password confirmation
                signToBuyMeButton.click();
            }
        catch (Exception e) {
            return "sign up failed to fill data";
        }

        //check if url changes to main page
        try {
            wait.until(ExpectedConditions.urlToBe("https://buyme.co.il/"));
            return driver.getCurrentUrl();
        }
        catch (Exception t){
            return driver.getCurrentUrl();
        }
    }//end of signUpToBuyMe
}//end of RegistrationScreen
