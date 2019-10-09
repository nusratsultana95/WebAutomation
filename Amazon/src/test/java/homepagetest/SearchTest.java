package homepagetest;

import SearchResult.ResultPage;
import homepage.HomePage;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import report.TestLogger;

import java.util.List;

public class SearchTest extends HomePage {
    ResultPage resultPage = new ResultPage();//in order to get the values from ResultPage class we need to create obj of that class
    //and we have to put this under class(belongs to class) NOT UNDER A METHOD.

    //here in this class we are validating if our test is giving us the same result we are expecting
   //for more info see note book.(practice test steps)
    @Test
    public void validateSearchButtonWorks() {
        clickOnSearchBar();
        typeOnSearchBar("Java Books");
        clickOnSearchButton();
        sleepFor(2);
        resultPage.validateSearchPageDisplayed();
    }
    @Test(enabled = false)
    public void testAllDropDownMenu() {
        List<WebElement> elementList = getAllElementFromList();//to perform different action on those list of web element
        //we can store it into a List<Webelement>.since this is returning list of webelement thats why we are feeding the List with Webelement.
        //and that whole list we are storing into new elementList(obj).
        System.out.println(elementList.size());
        for(int i=0;i<elementList.size();i++){
            System.out.println(elementList.get(i).getText());//uses for loop>>getting all names from the list of webelement
        }
    }
}