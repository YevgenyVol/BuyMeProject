import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

//loading screen of BuyMe
public class Loading {
    WebDriver driver;
    @FindBy (css = ".bounce1")
        WebElement loadingElementBounce;

    //constructor
    public Loading(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }//end of constructor

    //returns loading screen element circle size
    public Object loading(){
            driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.SECONDS);
            try {
                driver.get("https://buyme.co.il/");
                return "";
            }
            catch (TimeoutException e){
                return loadingElementBounce.getSize();
            }
    }//end of loading
}//end of Loading
