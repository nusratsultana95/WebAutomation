import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//I Have four web element list want to select one of them.
// Using for loop can you write the code for me.

public class InterviewPrep {
    public static WebElement element= null;
    public static List<WebElement> elements=null;
    public static WebElement returnOneElement(WebDriver driver){
      elements= driver.findElements(By.xpath("locator"));
      for(WebElement element:elements){
          if(element.getText().contains("someText")){
              return element;
          }
      }
        return null;
    }


    //dropdown
    WebDriver driver;
    public WebElement element1(){
      return driver.findElement(By.xpath("locator"));
    }
    @Test
    public void testWeb() {
        WebElement elementOfDropDown = element1();
        Select select = new Select(elementOfDropDown);
        select.selectByIndex(3);
    }

    //mouseho
    public void testMouseHover(){
        WebElement elemntmouse=element1();
        Actions actions = new Actions(driver);
        actions.moveToElement(elemntmouse).build().perform();

    }
 //element is displayed or not
    public void elementCheck(){
        if(driver.findElement(By.xpath("locator"))!=null){
            System.out.println("Elemenent is Displayed");
        }else{
            System.out.println("Element is not displayed");
        }
    }
    public WebElement getElement (){
        WebElement element3=driver.findElement(By.xpath(" "));
        Assert.assertEquals(element3.isDisplayed(),true,"Element is not displayed");
        return element3;
    }

    public static void screenshot(WebDriver driver,String nameOfsc){
        DateFormat dateFormat= new SimpleDateFormat("MM.DD.YYYY-HH.MM.SS");
        Date date= new Date();
        String unique=dateFormat.format(date);
        File file=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file,new File(System.getProperty("path")+" foldername"+ unique+nameOfsc+"png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

