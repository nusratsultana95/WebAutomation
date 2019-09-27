package homepage;

import base.CommonAPI;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Search extends CommonAPI { //this class is in main java which is extending commonAPI which is also in main java in generic module
   public void searchFieldIsTypeable(){
       boolean condition= isItDisplayed("//input[@id='twotabsearchtextbox']");
       Assert.assertEquals(condition,true);
       // Assert.assertEquals(isElementDisplayed("//input[@id='twotabsearchtextbox']"), true);
       clickOnElements("//input[@id='twotabsearchtextbox']");
       typeOnElementsbyxpath("//input[@id='twotabsearchtextbox']","java books");
   }

    public void clickOnSearchButton() {
        clickOnElements("//input[@type='submit' and @value='Go']");
    }
}
