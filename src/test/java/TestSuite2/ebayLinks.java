package TestSuite2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ebayLinks {

    private WebDriver driver;


    @Before
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://ebay.com");

    }

    @Test
    public void siteLinksCount(){

        //Number of all links in the frontpage
        Integer allLinksCount = driver.findElements(By.tagName("a")).size();
        System.out.println("The number of all links in the page is: " + allLinksCount);

        //Number of all links in the header
        WebElement header = driver.findElement(By.xpath(".//*[@id='navigationFragment']/div"));
        Integer headerLinksCount = header.findElements(By.tagName("a")).size();
        System.out.println("The number of all links in the header is: " + headerLinksCount);

        //Number and names of all links in the footer's second column
        WebElement footerSecCol = driver.findElement(By.xpath(".//*[@id='gf-BIG']/table/tbody/tr/td[2]/ul"));
        Integer footerSecColLinksCount = footerSecCol.findElements(By.tagName("a")).size();
        List<WebElement> footerLinks = footerSecCol.findElements(By.tagName("a"));
        System.out.println("The number of all links in the footer's second column is: " + footerSecColLinksCount);
        String pageTitle = driver.getTitle();

        //Names for all links and click on a specific link
        for (int i=0; i<footerSecColLinksCount; i++){
            System.out.println(footerLinks.get(i).getText());
            if (footerLinks.get(i).getText().contains("Site map")){
                footerLinks.get(i).click();
                break;
            }
        }
        //Some asserts we need to do to be sure we have clicked Site map successfully
        String pageTitleAfter = driver.getTitle();
        assertNotEquals(pageTitle,pageTitleAfter);
        System.out.println(pageTitle + "\r\n" + pageTitleAfter);

        String siteMapExpected = "View our sitemap to help you find your way";
        String siteMapActual = driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td/table[2]/tbody/tr[3]/td/table/tbody/tr/td[2]")).getText();

        assertEquals(siteMapExpected, siteMapActual);
    }

    @After
    public void tearDown(){

        driver.quit();
    }
}
