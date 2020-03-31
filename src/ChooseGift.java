import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

//choose gift class
public class ChooseGift {
    WebDriver driver;

    //constructor
    public ChooseGift(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }//end of constructor

    //choose gift
    public void chooseGift(String path) throws Exception {
        driver.findElement(By.cssSelector(General.readFromFile("business",path))).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(General.readFromFile("gift",path))).click();
    }//end of chooseGift

}//end of ChooseGift
