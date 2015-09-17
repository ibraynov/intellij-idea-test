package TestSuite2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class hdfcbankNewTab {

    private WebDriver driver;
    private Actions actNav;
    private WebDriverWait driverWait;
    @Before
    public void setUp(){
        driver = new FirefoxDriver();
        driverWait = new WebDriverWait(driver, 10);
        actNav = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://hdfcbank.com/");
    }


    @Test
    public void tabNavigation() {
        //very tricky to handle <li> & new window
        String winHandleBefore = driver.getWindowHandle();
        WebElement navMenu = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/div[2]/ul/li[7]/div[1]/span"));
        actNav.moveToElement(navMenu).build().perform();
        WebElement insideMenu = driver.findElement(By.cssSelector("a[href*='branchauto']"));
        actNav.moveToElement(insideMenu).build().perform();
        insideMenu.sendKeys(Keys.ENTER);

        for (String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
            driver.manage().window().maximize();
        }
        String verText = "Choose the option that best describes you";
        String verNewWin = driver.findElement(By.xpath(".//*[@id='divHome']/div[1]/span")).getText();

        assertEquals(verText, verNewWin);

        driver.close();

        driver.switchTo().window(winHandleBefore);
        driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[1]/ul/li[3]/a/span")).click();

    }

    @After
    public void tearDown(){
        //driver.quit();
    }

}
