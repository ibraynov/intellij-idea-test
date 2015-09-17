package TestSuite1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class bookGoIndiGo {

        private WebDriver driver;
        private WebDriverWait driverWait;
        private Select dropDownSelector;
        private Alert popup;


        @Before
        public void setUp(){
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        }

        @Test
        public void hiddenDropDownSelect() {
            driver.manage().window().maximize();
            driver.get("https://book.goindigo.in");
            WebElement dropDown = driver.findElement(By.xpath(".//input[@id='txtOriginStationRound']"));
            dropDown.click();
            WebElement DropDownInner = driver.findElement(By.xpath("//div[3]/ul/li[27]/a/span"));
            DropDownInner.click();

            assertEquals("Goa", driver.findElement(By.xpath(".//input[@id='txtOriginStationRound']")).getAttribute("value"));
            //String selectedValue = driver.findElement(By.xpath(".//input[@id='txtOriginStationRound']")).getAttribute("value");
            //assertEquals("Goa", selectedValue);

            //input dropdown handler
            /*dropDownSelector = new Select(dropDown);
            dropDownSelector.selectByVisibleText("Goa (GOI)");
            Alert popup = driver.switchTo().alert();
            popup.accept();*/
        }



        @After
        public void tearDown(){

            driver.quit();
        }
    }

