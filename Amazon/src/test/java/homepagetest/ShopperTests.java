package homepagetest;

import POM.HomePagePOM;
import base.CommonAPI;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
public class ShopperTests extends CommonAPI {

    HomePagePOM homePagePOM;

    @BeforeMethod
    public void method1(){
        homePagePOM = PageFactory.initElements(driver, HomePagePOM.class);
    }
    @Test
    public void testShopperToolkit() {
        homePagePOM.validateShopperDisplayed();
        homePagePOM.validateShopperClickAble();
    }

}