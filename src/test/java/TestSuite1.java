import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestSuite1 {

    private WebDriver driver;
    private Select dropDownSelector;

    @Before
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void validLogin() {
        driver.manage().window().maximize();
        driver.get("http://abv.bg");
        String username = "dev_provers@abv.bg";
        String password = "provers2015";
        String welcomeMessage = "("+username+")";
        WebElement usernameField = driver.findElement(By.xpath(".//*[@id='username']"));
        usernameField.sendKeys(username);
        WebElement passwordField = driver.findElement(By.xpath(".//*[@id='password']"));
        passwordField.sendKeys(password);
        WebElement loginButton = driver.findElement(By.xpath(".//*[@id='loginBut']"));
        loginButton.click();

        assertEquals(welcomeMessage, driver.findElement(By.xpath(".//*[@id='middlePagePanel']/div[1]/div[2]")).getText());
    }

    @Test
    public void hiddenDropDownSelect() throws InterruptedException {

        driver.get("https://book.goindigo.in");
        WebElement dropDown = driver.findElement(By.xpath(".//input[@id='txtOriginStationRound']"));
        dropDown.click();
        WebElement DropDownInner = driver.findElement(By.xpath("//div[3]/ul/li[27]/a/span"));
        DropDownInner.click();
        /*Thread.sleep(5000);
        dropDownSelector = new Select(dropDown);
        dropDownSelector.selectByVisibleText("Goa (GOI)");*/
        Alert popup = driver.switchTo().alert();
        popup.accept();

    }

    @After
    public void tearDown(){

        driver.quit();
    }
}
