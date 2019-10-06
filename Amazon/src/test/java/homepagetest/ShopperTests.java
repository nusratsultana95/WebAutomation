package homepagetest;

import POM.HomePagePOM;
import base.CommonAPI;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class ShopperTests extends CommonAPI {
    @Test
    public void testShopperToolkit() {
        HomePagePOM homePagePOM = PageFactory.initElements(driver, HomePagePOM.class);
        homePagePOM.validateShopperDisplayed();
        homePagePOM.validateShopperClickAble();
    }

}