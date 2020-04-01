import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;

//General class contains public methods
public class General {
    static String browserXml = "E:\\intellij\\BuyMeProject\\browser.xml";
    static String signUpXml = "E:\\intellij\\BuyMeProject\\buyMeSignUp1.xml";
    static String giftXml = "E:\\intellij\\BuyMeProject\\buyMeSignUpSearchValues.xml";

    //fill textBox
    public static void userText(String textValue, WebElement textBox)  {
        textBox.clear();
        textBox.sendKeys(textValue);
    }//end of userText

    //Take screenshot function
    public static String takeScreenShot(String ImagesPath,WebDriver driver) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(ImagesPath+".png");
        try {
            FileUtils.copyFile(screenShotFile, destinationFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ImagesPath+".png";
    }//end of takeScreenShot

    //read xml
    public static String readFromFile(String keyData, String path) throws Exception{
        File xmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbuilder = dbFactory.newDocumentBuilder();
        Document doc = dbuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(keyData).item(0).getTextContent();
    }//end of readFromFile
}
