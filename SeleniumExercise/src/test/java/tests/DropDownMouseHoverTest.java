package tests;

import base.CommonAPI;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class DropDownMouseHoverTest extends CommonAPI {

    @Test(enabled = false)
    public void testDropDown() {
        WebElement element = getElement("//select[@id='gh-cat']");
        Select select = new Select(element);//Select class uses>> to select dropDown element.
        select.selectByIndex(3);//this is 3 index from dropDown list
        sleepFor(5);
    }


    @Test(enabled = false)
    public void testMouseHover() {
        WebElement element = getElementByLinkText("Fashion");
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
        clickOnElementByLinkText("Jewelry");
        sleepFor(5);
    }


    @Test(enabled = false)
    public void scrollToView() {
        WebElement element = getElementByLinkText("eBay for Charity");
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;//here driver needs
        //to cast with javascriptExecutor
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        //this string inside "" need to be memorise.
        sleepFor(10);
    }

    @Test(enabled = false)
    public void testScrolling() { //this method will scroll screen for you
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("window.scrollBy(0,1000)");
        sleepFor(10);
    }


}