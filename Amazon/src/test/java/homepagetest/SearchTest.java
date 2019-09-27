package homepagetest;

import homepage.Search;
import org.testng.annotations.Test;

public class SearchTest extends Search { //this class is extending Search which is in main java(Amazon).
    //and this class is to testing purpose.

    @Test(enabled = false)
    public  void typeAbilityTestForSeachField(){
        searchFieldIsTypeable();
}

    @Test
    public void validateSearchButtonWorks() {
        searchFieldIsTypeable();
        clickOnSearchButton();
    }
}
