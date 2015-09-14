import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestSuit1 {

    private WebDriver driver;

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

    @After
    public void tearDown(){

        driver.quit();
    }
}
