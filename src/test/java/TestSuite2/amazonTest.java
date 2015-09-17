package TestSuite2;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;



public class amazonTest {

    private WebDriver driver;
    private WebDriverWait driverWait;
    private Actions actionsNav;

    @Before
    public void setUp(){

        driver = new FirefoxDriver();
        driverWait = new WebDriverWait(driver, 10);
        actionsNav = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://amazon.com");
    }

    @Test
    public void mouseHoverMenu() {
        //Selecting an item from hover menu
        WebElement navMenu = driver.findElement(By.xpath("./*//*[@id='nav-link-yourAccount']"));
        actionsNav.moveToElement(navMenu).build().perform();
        WebElement innerMenuElement = driver.findElement(By.xpath("./*//*[@id='nav-flyout-yourAccount']/div[3]/a[1]/span"));
        innerMenuElement.click();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("./*//*[@id='fixed_width']/div[1]/div/h1")));
        assertEquals("Your Account", driver.findElement(By.xpath("./*//*[@id='fixed_width']/div[1]/div/h1")).getText());
    }

    /*@Test
    public void searchMenu(){
        //bug in Selenium&Firefox for this specific feature
        //Sending capital letters holding SHIFT button in the search box
        //We can use simple .sendKeys("CAPITAL letters"); but in some case we might need to simulate pressed key like F1, ALT
        WebElement searchBox = driver.findElement(By.xpath("./*//*[@id='nav-search']/form/div[3]/div[1]"));
        actionsNav.keyDown(Keys.SHIFT).moveToElement(searchBox).sendKeys("smallletters").build().perform();
        driver.findElement(By.xpath("./*//*[@id='nav-search']/form/div[2]/div/input")).click();
    }*/


    @After
    public void tearDown(){
        driver.quit();
    }
}
