package SearchResult;

import base.CommonAPI;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import report.TestLogger;

public class ResultPage extends CommonAPI {
    public void validateSearchPageDisplayed() {
        WebElement element = getElement("//div[@id='s-refinements']");
    //we have this getElement() in commonapi,which takes string & return webelent of that String.like here it is
    //returning a location of an element/webelement.
    //and this string we are storing inside another element(obj of webelement).
        Assert.assertEquals(element.isDisplayed(), true, "element isn't displayed");
        //here in assert the 3rd parameter is a negative msg if the test fails console will show this msg.
        TestLogger.log("search result panel is displayed : " + element.isDisplayed());
        //testlogger is coming from reporting classes. we need this to see how test case ran
        //and we need to provide a string however we want to see our repot msg.(to make it more understandable to a 3rd person)
        //after we do any assertion or action we can provide testlogger
        //************************************************************************
        //TIPS: IN EXTENTMANAGER CLASS there is something "--" there u need to provide your username & company name
        //as host and ur company url. Or do how ur company instruct you.
    }
}