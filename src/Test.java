//Y


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

@FixMethodOrder
        (MethodSorters.NAME_ASCENDING)

public class Test {

        @Rule
        //get method name
        public TestName name = new TestName();
        //driver
        private static WebDriver driver;
        //extent report
        private static ExtentReports extent;
        private static ExtentTest myTests;
        //screenshot path
        static String imagePath = "E:\\intellij\\BuyMeProject\\Screenshots\\Test";
        static String browserXml = "E:\\intellij\\BuyMeProject\\browser.xml";
        private RegistrationScreen signUpToWeb;
        private Login login;
        private SearchGift search;
        private ChooseGift gift;
        private SenderAndReceiver sendAndReceive;
        private Loading load;

        @BeforeClass
        //choose browser and set up
        public static void chooseBrowser () throws Exception {
            //set up path of xml , config xml and report
            extent = new ExtentReports("E:\\intellij\\BuyMeProject\\BuyMeReport.html");                                  //report html path
            extent.loadConfig(new File("E:\\intellij\\BuyMeProject\\reportConfig.xml"));                            //xml config path

            String setBrowser = General.readFromFile("browser",General.browserXml);  //xml browser path
            setBrowser(setBrowser);                                                                                       //set browser

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
        }//end of chooseBrowser @BeforeClass

        @After
        //finish test
        public void closeTest(){
            myTests.log(LogStatus.INFO, "", "Test Finished");
            extent.endTest(myTests);
        }//end of closeTest

        @AfterClass
        //closing browser and saves report
        public static void closeBrowser(){
                driver.close();
                extent.flush();
        }//end closeBrowser - AfterClass


        @org.junit.Test
        //sign up to buyMe test
        public void test01_signUp() {
            driver.get("https://buyme.co.il/");
            myTests = extent.startTest("Sign up");
            myTests.log(LogStatus.INFO, "Test '" + name.getMethodName() + "' started");
            signUpToWeb = new RegistrationScreen(driver);
            String urlMain = "https://buyme.co.il/";
            String urlAfterLogin = signUpToWeb.signUpToBuyMe(General.signUpXml);

            //check success of login , url assertion
            try {
                Assert.assertEquals(urlMain, urlAfterLogin);
                myTests.log(LogStatus.PASS, name.getMethodName() + ". sign up completed");
                myTests.log(LogStatus.PASS, "", myTests.addScreenCapture(General.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
            }
            catch (AssertionError e) {
                myTests.log(LogStatus.ERROR, name.getMethodName() + ". sign up failed");
                myTests.log(LogStatus.ERROR, "", myTests.addScreenCapture(General.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
            }
        }//end of test01_signUp

        @org.junit.Test
        //login to buyMe test
        public void test02_login() throws Exception {
            //set up browser
            driver.close();
            String setBrowser = General.readFromFile("browser",browserXml);  //xml browser path
            setBrowser(setBrowser);                                                                                        //set browser

            driver.get("https://buyme.co.il/");
            myTests = extent.startTest("Login");
            myTests.log(LogStatus.INFO, "Test '" + name.getMethodName() + "' started");
            login = new Login(driver);
            String urlMain = "https://buyme.co.il/";
            String urlAfterLogin = login.loginToWeb(General.signUpXml);

            //login check
            try {
                Assert.assertEquals(urlMain, urlAfterLogin);
                myTests.log(LogStatus.PASS, name.getMethodName() + ". login completed");
            }
            catch (AssertionError e) {
                myTests.log(LogStatus.ERROR, name.getMethodName() + ". login failed");
                myTests.log(LogStatus.ERROR, "", myTests.addScreenCapture(General.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
            }

            search = new SearchGift(driver);
            try {
                //search for gift
                search.searchItem(General.giftXml);
                myTests.log(LogStatus.PASS, name.getMethodName() + ". search successful");
            }
            catch (Exception e){
                myTests.log(LogStatus.FAIL, name.getMethodName() + ". cannot find item");
                myTests.log(LogStatus.FAIL, "", myTests.addScreenCapture(General.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
            }
        }//end of login

        @org.junit.Test
        //choose gift from list test
        public void test03_chooseGift() {
            myTests = extent.startTest("Choose gift");
            myTests.log(LogStatus.INFO, "Test '" + name.getMethodName() + ". started");
            gift = new ChooseGift(driver);
            //choosing gift from list
            try {
                gift.chooseGift(General.giftXml);
                myTests.log(LogStatus.PASS, name.getMethodName() + ". gift chose");
            }
            catch (Exception e){
                myTests.log(LogStatus.FAIL, name.getMethodName() + ". cannot find gift");
                myTests.log(LogStatus.FAIL, "", myTests.addScreenCapture(General.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
            }

        }//end of test03_chooseGift

        @org.junit.Test
        //fill information for sending a gift and asserts color and card data test
        public void test04_sendGift() throws Exception{
            myTests = extent.startTest("Send gift");
            myTests.log(LogStatus.INFO, "Test '" + name.getMethodName() + "' started");
            sendAndReceive = new SenderAndReceiver(driver);
            //fill gift info
            sendAndReceive.sendGift(General.giftXml);

            //asserts color of receiver 2
            String colorOfReceiverTab = sendAndReceive.get2Color();
            try {
                Assert.assertEquals(General.readFromFile("colorReceiverTab",General.giftXml),colorOfReceiverTab);
                myTests.log(LogStatus.PASS, name.getMethodName() + ". color is " + colorOfReceiverTab + " correct");
            }
            catch (AssertionError e)
            {
                myTests.log(LogStatus.WARNING, name.getMethodName() + ". wrong color, actual color is : " +  colorOfReceiverTab);
                myTests.log(LogStatus.WARNING, "", myTests.addScreenCapture(General.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
            }

            //asserts card data:
            //asserts card sender
            String cardSender = sendAndReceive.getCardDataSender();
            try {
                Assert.assertEquals(General.readFromFile("sender",General.giftXml),cardSender);
                myTests.log(LogStatus.PASS, name.getMethodName() + ". sender is " + cardSender);
            }
            catch (AssertionError e)
            {
                myTests.log(LogStatus.FAIL, name.getMethodName() + ". wrong sender");
                myTests.log(LogStatus.FAIL, "", myTests.addScreenCapture(General.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
            }

            //asserts card receiver
            String cardReceiver = sendAndReceive.getCardDataReceiver();
            try {
                Assert.assertEquals(General.readFromFile("receiver",General.giftXml),cardReceiver);
                myTests.log(LogStatus.PASS, name.getMethodName() + ". receiver is " + cardReceiver);
            }
            catch (AssertionError e)
            {
                myTests.log(LogStatus.FAIL, name.getMethodName() + ". wrong receiver");
                myTests.log(LogStatus.FAIL, "", myTests.addScreenCapture(General.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
            }

            //asserts card greeting
            String cardGreeting = sendAndReceive.getCardDataGreetings();
            try {
                Assert.assertEquals(General.readFromFile("blessing",General.giftXml),cardGreeting);
                myTests.log(LogStatus.PASS, name.getMethodName() + ". blessing is " + cardGreeting);
            }
            catch (AssertionError e)
            {
                myTests.log(LogStatus.FAIL, name.getMethodName() + ". wrong blessing");
                myTests.log(LogStatus.ERROR, "", myTests.addScreenCapture(General.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
            }
        }//end of test04_sendGift

        @org.junit.Test
        //screenshot end of page test
        public void test05_screenEndOfPage() throws Exception {
            myTests = extent.startTest("End of page");
            myTests.log(LogStatus.INFO, "Test '" + name.getMethodName() + "' started");

            driver.close();
            String setBrowser = General.readFromFile("browser",browserXml);  //xml browser path
            setBrowser(setBrowser);                                                                                        //set browser
            driver.get("https://buyme.co.il/");

            search = new SearchGift(driver);
            //roll to end of page
            search.rollToLastGift(General.giftXml);
            //report a screen
            myTests.log(LogStatus.PASS, name.getMethodName() + ". end of page");
            myTests.log(LogStatus.PASS, "", myTests.addScreenCapture(General.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
        }//end of test05_screenEndOfPage
//
        @org.junit.Test
        //login without credentials test
        public void test06_loginWithoutData() throws Exception {
            //set up driver
            myTests = extent.startTest("Login without data");
            myTests.log(LogStatus.INFO, "Test '" + name.getMethodName() + "' started");
            driver.close();
            String setBrowser = General.readFromFile("browser",browserXml);  //xml browser path
            setBrowser(setBrowser);                                                                                        //set browser

            login = new Login(driver);
            driver.get("https://buyme.co.il/");
            String loginActualMessage = login.loginWithoutCredentials();
            String loginExpectedMessage = General.readFromFile("signUpErrMessage",General.signUpXml);
            //asserts login error messages
            try {
                Assert.assertEquals(loginExpectedMessage, loginActualMessage);
                myTests.log(LogStatus.PASS, name.getMethodName() + ". login unsuccessful, as expected");
                myTests.log(LogStatus.PASS, "", myTests.addScreenCapture(General.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
            }
            catch (AssertionError e) {
                myTests.log(LogStatus.ERROR, name.getMethodName() + ". login succeed");
                myTests.log(LogStatus.ERROR, "", myTests.addScreenCapture(General.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
            }
        }//end of test06_loginWithoutData

        @org.junit.Test
        //loading element get size test
        public void test07_loading() {
            myTests = extent.startTest("Loading ...");
            myTests.log(LogStatus.INFO, "Test '" + name.getMethodName() + "' started");
            load = new Loading(driver);
            //get circle object dimension on loading screen
            try {
                Object point = load.loading();
                myTests.log(LogStatus.PASS, name.getMethodName() + ". loading point dimension/size is " + point);
            }
            catch (Exception e){
                myTests.log(LogStatus.FAIL, name.getMethodName() + ". test failed");
            }
        }//end of test07_loading


        //sets browser
        private static void setBrowser(String userChoice) {

                //choose chrome or Firefox
                switch (userChoice){
                        //Chrome
                        case "Chrome": {

                                System.setProperty("webdriver.chrome.driver", "E:\\Selenium\\browser\\chromedriver.exe");
                                ChromeOptions options = new ChromeOptions();
                                options.addArguments("-incognito");
                                options.addArguments("--start-maximized");
                                options.addArguments("--disable-popup-blocking");
                                driver = new ChromeDriver(options);
                                break;
                        }
                        //Firefox
                        case "Firefox" : {
                            System.setProperty("webdriver.gecko.driver","E:\\Selenium\\browser\\geckodriver.exe");
                            File pathBinary = new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
                            FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);
                            DesiredCapabilities desired = DesiredCapabilities.firefox();
                            FirefoxOptions options = new FirefoxOptions();
                            options.addArguments("-private");
                            desired.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options.setBinary(firefoxBinary));
                            desired.setCapability("moz:firefoxOptions",options);
//                            desired.setCapability("browser.privatebrowsing.autostart", true);
                            driver = new FirefoxDriver(options);            //default path C:\Program Files\Mozilla Firefox\firefox.exe
                //              driver = new FirefoxDriver();   //default path C:\Program Files\Mozilla Firefox\firefox.exe
                            driver.manage().window().maximize();
                                break;
                        }
                        default:
                                System.out.println("Wrong value");
                }
        }//end of setBrowser
}//end of Test
