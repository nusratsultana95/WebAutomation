package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CommonAPI {
    public static WebDriver driver;
    /**
     * @param browser  the browser you want to execute your test case
     * @param platform in the operating system you want to execute your test case
     * @return WebDriver Object
     */
    public static WebDriver getLocalDriver(String browser,String platform){
        if(platform.equalsIgnoreCase("windows")&& browser.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver","..\\Generic\\src\\main\\resources\\chromedriver2.exe");
            //if running from different module use../Generic/
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

//    public static String userNameForSauce="";
//    public static String keyForSauce="";
//    public static String userNameForBrowserStack="";
//    public static String keyForBrowserStack="";
   //https://+username+:+key+specific url for cloud
    public static String SAUCE_URL="http://nusrat1995:dbc7b863-b668-4d5e-ab39-34ab595b88ea@ondemand.saucelabs.com:80/wd/hub";
    public static String BROWSERSTACK_URL="";//@hub-cloud.browserstack.com:80/wd/hub
    public static WebDriver getCloudDriver(String browser,String browserVersion,String platform,
                                           String envName) throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("name","Colud Execution");
        //setcapability is a property which takes key and value
        desiredCapabilities.setCapability("browserName",browser);
        desiredCapabilities.setCapability("browser_version",browserVersion);
        desiredCapabilities.setCapability("os",platform);
        desiredCapabilities.setCapability("os_version","Windows 10");

        if(envName.equalsIgnoreCase("saucelabs")){
            desiredCapabilities.setCapability("resolution","1600x1200");
            driver= new RemoteWebDriver(new URL(SAUCE_URL),desiredCapabilities);//in this line we are launching the driver
            //remotewebdriver means the driver is in remote version(virtual)
        }else if(envName.equalsIgnoreCase("browserstack")){
            desiredCapabilities.setCapability("resolution","1024x768");
            driver=new RemoteWebDriver(new URL(BROWSERSTACK_URL),desiredCapabilities);
        }
        return driver;
    }
    /**
     * @param platform       -
     * @param url            -
     * @param browser        -
     * @param cloud          -
     * @param browserVersion -
     * @param envName        -
     * @return
     * @throws MalformedURLException
     * @Parameters - values are coming from the runner.xml file of the project modules
     */
    @Parameters({"platform", "url", "browser", "cloud", "browserVersion", "envName"})
    //in order to run the test we need this parameters annotation.but the value has to be same as xml file
    @BeforeMethod
    public static WebDriver setupDriver(String platform, String url, String browser,
                                        boolean cloud, String browserVersion, String envName) throws MalformedURLException {
        if (cloud) {
            driver = getCloudDriver(browser,browserVersion,platform,envName);
        } else {
            driver = getLocalDriver(browser, platform);
        }
        driver.get(url);
        return driver;
    }
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

    //*********************************
    //common methods

    public void clickOnElementByXpath(String locator) {
        driver.findElement(By.xpath(locator)).click();
    }

    public void clickOnElementById(String locator) {
        driver.findElement(By.id(locator)).click();
    }

    public void clickOnElementByLinkText(String locator) {
        driver.findElement(By.linkText(locator)).click();
    }

    public void typeOnElementByXpath(String locator, String value) {
        driver.findElement(By.xpath(locator)).sendKeys(value);
    }

    public void typeOnElementById(String locator, String value) {
        driver.findElement(By.id(locator)).sendKeys(value);
    }

    public String getTextByXpath(String locator) {//getText() is a string.by using it,we can get the text of the actual element.
        return driver.findElement(By.xpath(locator)).getText();
        //whatever string we are getting from .getText that we are storing into value(check next class amazon)
    }

    public boolean isElementDisplayed(String locator) {
        return driver.findElement(By.xpath(locator)).isDisplayed();//it is saying what is the value i am getting return me that
    }

    public boolean isElementEnabled(String locator) {
        boolean flag = true;
        flag = driver.findElement(By.xpath(locator)).isEnabled();
        return flag;
    }

    public boolean isElementSelected(String locator) {
        boolean flag = true;
        flag = driver.findElement(By.xpath(locator)).isSelected();
        return flag;
    }
    /**
     * @param locator - xpath that we are trying to make webElement of
     * @return WebElement - WebElement of the xpath
     */
    public WebElement getElement(String locator) { //webelement is an interface.
        //findelement is a webelement itself.and we are storing findelement inside a webelement object.
        WebElement webElement = driver.findElement(By.xpath(locator));//when we use webelement we cannot use click()/getText() etc.
        //and those has to go before this webelement
        return webElement;
    }

    public WebElement getElementByLinkText(String locator) {
        return driver.findElement(By.linkText(locator));
    }

    public void dragNdropByXpaths(String fromLocator, String toLocator) {
        Actions actions = new Actions(driver);
        WebElement from = getElement(fromLocator);
        WebElement to = getElement(toLocator);
        actions.dragAndDrop(from, to).build().perform();
    }

    public void scrollIntoView(String locator) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", getElementByLinkText(locator));
    }
    //************************************

    //reporting starts
    //@Beforesuite will execute at first.like before whole frameworks method.

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
