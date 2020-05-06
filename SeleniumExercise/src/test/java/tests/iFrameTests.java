package tests;

import base.CommonAPI;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class iFrameTests extends CommonAPI {

    @Test()
    public void iFrameTests() {
        driver.switchTo().frame(1);//frame() takes different parameter::frameID,FrameName,FrameIndex
        //and we are using frameindex
        driver.get(driver.getCurrentUrl());//getCurrentUrl mean>get this url & go to this url
        sleepFor(5);
        WebElement element = getElementByLinkText("Sortable");
        Assert.assertEquals(element.getText(), "Sortablgge", "toolsQA interactions sortable assertion failed");
    }
}