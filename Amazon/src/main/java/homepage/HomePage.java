package homepage;

import base.CommonAPI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import report.TestLogger;

import java.util.List;

public class HomePage extends CommonAPI {

    //TIPS: IF U HAVE thousands of test cases to run in one go we need .xml file.

    public void clickOnSearchBar() {
        clickOnElementByXpath("//input[@id='twotabsearchtextbox']");
        TestLogger.log("search bar clicked");
    }

    public void typeOnSearchBar(String value) { //here the value is parameterized because not everytime we are going to search
        //for same thing.if i make it param so that if i call the method typeOnElements i can put any value i want
        typeOnElementByXpath("//input[@id='twotabsearchtextbox']", value);
        TestLogger.log(value + " typed on the search bar");
    }

    public void clickOnSearchButton() {
        clickOnElementByXpath("//div[@class='nav-search-submit nav-sprite']//input[@class='nav-input']");
        TestLogger.log("search button clicked");
    }

    public List<WebElement> getAllElementFromList() {
        //List<WebElement> elementList = driver.findElements(By.xpath("//*[contains(@value,'search-alias')]"));
        //return elementList;
        return driver.findElements(By.xpath("//*[contains(@value,'search-alias')]"));
        //whenever we use List we have to say driver.findelementS not findelement.bcuz its returning list of webelement.
        //uses of contains(in xpath)>>if u want to print a long list of webelement together.
        // Structure://*[contains(@locator like value or class etc,'path which is same for all elements')]
    }


}