package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import report.ExtentManager;
import report.ExtentTestManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CommonAPI {
    public static WebDriver driver;

    public static WebDriver getLocalDriver(String browser,String platform){
        if(platform.equalsIgnoreCase("windows")&& browser.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver","..\\Generic\\src\\main\\resources\\chromedriver2.exe");
            driver=new ChromeDriver();
        }else if(platform.equalsIgnoreCase("mac")&& browser.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver","..\\Generic\\src\\main\\resources\\chromedriver");
           driver= new ChromeDriver();
        }else if(platform.equalsIgnoreCase("windows")&& browser.equalsIgnoreCase("firefox")){
            System.setProperty("webdriver.gecko.driver","..\\Generic\\src\\main\\resources\\geckodriver.exe");
            driver= new FirefoxDriver();
        }else if(platform.equalsIgnoreCase("mac")&& browser.equalsIgnoreCase("firefox")){
            System.setProperty("webdriver.gecko.driver","..\\Generic\\src\\main\\resources\\geckodriver");
            driver= new FirefoxDriver();
        }
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        return  driver;
    }

    @AfterMethod
    public void cleanUp() {
        driver.close();
        driver.quit();
    }

    public void sleepFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static WebDriver getCloudDriver() { //this part we will do when we have our cloud service
        return driver;
    }

    @Parameters({"platform", "url", "browser", "cloud", "browserVersion", "envName"})
    //in order to run the test we need this parameters annotation.but the value has to be same as xml file
    @BeforeMethod
    public static WebDriver setupDriver(String platform, String url, String browser,
                                        boolean cloud, String browserVersion, String envName) {
        if (cloud) {
            driver = getCloudDriver();
        } else {
            driver = getLocalDriver(browser, platform);
        }
        driver.get(url);
        return driver;
    }


    //coomon api


   // public static void captureScreenshot(String testcasename) {

    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        DateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy-HH.mm.ss");//in order to having a name of the screenshot we need this dateformat.
        Date date = new Date();
        String uniqueName = dateFormat.format(date);//this line is giving a name of the screenshot
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);//outputType is an interface
        //in this line File class is telling the the driver to store the screenshots into file object.
        try {
            FileUtils.copyFile(file, new File(System.getProperty("user.dir") + "/screnshots/" + screenshotName + uniqueName + ".png"));//user.dir>> we are providing the path where we want our folder
        } catch (IOException e) { //but that file is not a real file so in order to store the screenshots we need a physical file.Thats why we need this FileUtil class.
            //and here we are copying the file to FileUtils.
            // everytime we take a screenshot this will store in File class.
            e.printStackTrace();
        }
    }

    public void clickOnElements(String locator) { //making xpath parameterized
        driver.findElement(By.xpath(locator)).click();
    }

    public void clickOnElementsID(String locator1) { //making id parameterized
        driver.findElement(By.id(locator1)).click();
    }

    public void typeOnElementsbyxpath(String locator, String value) { //here "String value" is sendkeys method value
        driver.findElement(By.xpath(locator)).click();
    }

    public String getTextFromBrowser(String location) { //getText() is a string.by using it,we can get the text of the actual element.
        return driver.findElement(By.xpath(location)).getText();
        //whatever string we are getting from .getText that we are storing into value(check next class amazon)
    }

    public boolean isItDisplayed(String loctn) { //this method is to test that,the browser/element you want to see is displayed or not
        return driver.findElement(By.xpath(loctn)).isDisplayed();
    }

    public boolean isItEnabled(String lo) {

        return driver.findElement(By.xpath(lo)).isEnabled();//this method is to test that,the browser/element you want to see is selected or not
    }

    public boolean isItSelected(String lo) {
        return driver.findElement(By.xpath(lo)).isSelected();
    }

    public WebElement getElement(String locator) { //webelement is an interface.
        //findelement is a webelement itself.and we are storing findelement inside a webelement object.
        WebElement webElement = driver.findElement(By.xpath(locator));//when we use webelement we cannot use click()/getText() etc.
        //and those has to go before this webelement
        return webElement;
    }

    //reporting starts

    public static ExtentReports extent;

    @BeforeSuite
    public void extentSetup(ITestContext context) {
        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void startExtent(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        ExtentTestManager.startTest(method.getName());
        ExtentTestManager.getTest().assignCategory(className);
    }

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    @AfterMethod
    public void afterEachTestMethod(ITestResult result) {
        ExtentTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));
        ExtentTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));
        for (String group : result.getMethod().getGroups()) {
            ExtentTestManager.getTest().assignCategory(group);
        }

        if (result.getStatus() == 1) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
        } else if (result.getStatus() == 2) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));
        } else if (result.getStatus() == 3) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
        }

        ExtentTestManager.endTest();
        extent.flush();
        if (result.getStatus() == ITestResult.FAILURE) {
           captureScreenshot (driver,result.getName());
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    @AfterSuite
    public void generateReport() {
        extent.close();
    }
    //reporting finish
}
