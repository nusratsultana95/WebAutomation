package tests;

import base.CommonAPI;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class DropDownMouseHoverTest extends CommonAPI {

    @Test
    public void testDropDown() {
        WebElement element = getElement("//select[@id='gh-cat']");//this is a customise xpath.
        Select select = new Select(element);//Select class uses>> to select dropDown element.
        select.selectByIndex(3);//this is 3 index from dropDown list
        sleepFor(5);
    }


    @Test
    public void testMouseHover() {
        WebElement element = getElementByLinkText("Fashion");
        Actions actions = new Actions(driver);//everytime we use Actions we need to feed that with Webdriver's obj
        actions.moveToElement(element).build().perform();//everytime we use Actions we have to use build().perform()
        clickOnElementByLinkText("Jewelry");
        sleepFor(5);
    }


    @Test(enabled = false)//option 1
    public void scrollToView() {
        WebElement element = getElementByLinkText("eBay for Charity");
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;//here driver needs
        //to cast with javascriptExecutor
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);//this executeScript needs to feed with Webelemnt's obj
        //this string inside "" needs to be memorise.
        sleepFor(10);
    }

    @Test(enabled = false)//option 2
    public void testScrolling() { //this method will scroll screen for you
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("window.scrollBy(0,1000)"); //needs to memorize
        sleepFor(10);
    }


}