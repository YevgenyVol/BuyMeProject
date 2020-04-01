import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//sender and receiver class for sending a gift
public class SenderAndReceiver {
    WebDriver driver;
    //sender and receiver data elements
    @FindBy (css = ".mx1.ember-view.ui-radio.forWho.vertical .option:nth-of-type(1)")
        WebElement firstRadioButtonOption;
    //sender and receiver text box
    @FindBy (css = ".ui-grid .mx2.md1:nth-of-type(2) .ember-view.ui-field.ui-input:nth-of-type(1) input")
        WebElement receiverInfo;
    @FindBy (css = ".ui-grid .mx2.md1:nth-of-type(2) .ember-view.ui-field.ui-input:nth-of-type(2) input")
        WebElement senderInfo;
    //event info
    @FindBy (css = ".mx2.md1:nth-of-type(3) .ember-view.ui-field.ui-select .chosen-container")
        WebElement event;
    @FindBy (css = "li[data-option-array-index=\"8\"]")
        WebElement weddingEvent;
    @FindBy (css = ".mx2.md1:nth-of-type(3) textarea")
        WebElement blessing;
    //upload picture
    @FindBy (css = ".ui-file:nth-of-type(1) input")
        WebElement uploadPicture;
    @FindBy (css = ".send-now")
        WebElement sendNow;
    //email
    @FindBy (css = ".row.row--with-less-gutter.row--sm .col:nth-of-type(2) div .btn")
        WebElement mailSend;
    @FindBy (css = ".form-control.input-theme")
        WebElement mailAddress;
    @FindBy (css = ".btn.btn-theme.btn-save")
        WebElement submitEmail;
    @FindBy (css = ".step-title.highlighted")
        WebElement secondElement;
    //card data
    @FindBy (css = ".receiver .name")
        WebElement cardReceiver;
    @FindBy (css = ".sender .name")
        WebElement cardSender;
    @FindBy (css = ".card-text.cut-greeting")
        WebElement cardGreetings;
    private String uploadXml = "E:\\intellij\\BuyMeProject\\upload.jpg";

    //constructor
    public SenderAndReceiver(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }//end of constructor

    //fill sending gift data
    public void sendGift(String path) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOf(firstRadioButtonOption));
        //gift data
        if (firstRadioButtonOption.isEnabled()) {
            firstRadioButtonOption.click();
        }
        General.userText(General.readFromFile("receiver",path),receiverInfo);

        if (!(sendNow.isSelected())){
                sendNow.click();
        }
        General.userText(General.readFromFile("sender",path),senderInfo);
        //event
        event.click();
        weddingEvent.click();
        General.userText(General.readFromFile("blessing",path), blessing);
        uploadPicture.sendKeys(uploadXml);
        //mail
        mailSend.click();
        if (mailAddress.isEnabled()){
            General.userText(General.readFromFile("mail",path),mailAddress);
        }
        if (submitEmail.isEnabled()){
            submitEmail.click();
        }
    }//end of sendGift

    //get destination color text
    public String get2Color(){
        return secondElement.getCssValue("color");
    }//end of get2Color

    //get card data
    public String getCardDataSender() {
        return cardSender.getText();
    }//end of getCardDataSender

    public String getCardDataReceiver() {
        return cardReceiver.getText();
    }//end of getCardDataReceiver

    public String getCardDataGreetings(){
       return cardGreetings.getText();
    }//end of getCardDataGreetings

}//end of senderAndReceiver class




