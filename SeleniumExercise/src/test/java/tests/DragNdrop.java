package tests;

import base.CommonAPI;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.interactions.Actions;
        import org.testng.annotations.Test;

public class DragNdrop extends CommonAPI {
    @Test(enabled = false)//enable=false mean to skip this from running.

    //option 1
    public void testDragNDrop() {
        sleepFor(2);
        Actions actions = new Actions(driver);//in order to drag and drop we need to bring Actions class
        WebElement from = getElement("//*[@id='credit2']/a");//if any locator has 2 lines,take parent line &child line 1st word
        WebElement to = getElement("//*[@id='bank']/li");
        //previous 2 lines saying,take this(whtevr the locator indicates) from here (line 1)
        //& drop it to here.
        actions.dragAndDrop(from, to).build().perform();//whenever u use Actions u need build().perform().
        sleepFor(5);
    }

    //option 2
    @Test(enabled = false)
    public void testDragNDropUsingCommonAPIMethod() { //coming from parameterized method
        dragNdropByXpaths("//*[@id='credit2']/a", "//*[@id='bank']/li");
    }


}




