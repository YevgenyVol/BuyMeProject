import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.TimeUnit;

//search for items
public class SearchGift {
    WebDriver driver;
    //main page data elements
    @FindBy (css = "form div.chosen-container.chosen-container-single.form-control.dib:nth-of-type(1)")
        WebElement price;
    @FindBy (css = "form div.chosen-container.chosen-container-single.form-control.dib:nth-of-type(2)")
        WebElement area;
    @FindBy (css = "form div.chosen-container.chosen-container-single.form-control.dib:nth-of-type(3)")
        WebElement category;
    @FindBy (css = "a.ui-btn.search.ember-view")
        WebElement searchButton;

    //constructor
    public SearchGift(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }//end of constructor

    //fill data and search for gift
    public void searchItem(String path) throws Exception {
        //price
        price.click();
        driver.findElement(By.cssSelector(General.readFromFile("priceRange",path))).click();
        //area
        area.click();
        driver.findElement(By.cssSelector(General.readFromFile("area",path))).click();
        //category
        category.click();
        driver.findElement(By.cssSelector(General.readFromFile("category",path))).click();
        //find gift
        searchButton.click();
        searchButton.click();
        Thread.sleep(1000);
    }//end of searchItem


    //scroll to end of page
    public void rollToLastGift(String path) throws Exception {
        driver.get("https://buyme.co.il/");
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        price.click();
        driver.findElement(By.cssSelector(General.readFromFile("priceRange",path))).click();
        category.click();
        driver.findElement(By.cssSelector(General.readFromFile("category",path))).click();
        searchButton.click();

        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        for (int i=0; i<2000 ;i++) {
            js.executeScript("javascript:window.scrollBy(0,4)", "");
        }

    }//end of rollToLastGift
}//end of SearchGift class
