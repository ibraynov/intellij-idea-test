package TestSuite1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class taskBitrix {


        private WebDriver driver;
        private WebDriverWait driverWait;
        private String username = "dev_provers@abv.bg";
        private String password = "provers2015";

        @Before
        public void setUp(){
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driverWait = new WebDriverWait(driver, 40);
            driver.manage().window().maximize();
            driver.get("https://provers.bitrix24.com");
            //Login
            WebElement loginBtr = driver.findElement(By.xpath(".//*[@id='form_auth']/div[1]/input"));
            loginBtr.sendKeys(username);
            WebElement passBtr = driver.findElement(By.xpath(".//*[@id='form_auth']/div[2]/input"));
            passBtr.sendKeys(password);
            Boolean isRemembered = driver.findElement(By.id("USER_REMEMBER")).isSelected();
            //System.out.println(isRemembered);
            if (isRemembered){
                WebElement checkBox = driver.findElement(By.xpath(".//*[@id='form_auth']/div[3]/div[1]/label[1]"));
                checkBox.click();
            }
            WebElement submitBut = driver.findElement(By.xpath(".//*[@id='AUTH_SUBMIT']"));
            submitBut.click();

            assertEquals("Activity Stream", driver.findElement(By.xpath(".//*[@id='pagetitle']/span")).getText());

        }

        @Test
        public void iFrameSelector() throws InterruptedException {

            //Selecting the "TASK" menu
            WebElement taskMenu = driver.findElement(By.xpath(".//*[@id='menu_tasks']/a/span[1]"));
            taskMenu.click();

            //New task creation
            WebElement newTaskBut = driver.findElement(By.xpath("./*//*[@id='pagetitle-menu']/div/div[1]/a[1]/span[3]"));
            newTaskBut.click();

            //New task fields are populated with some values. Here is the iframe which needs to be handled
            Random ranNum = new Random();
            String title = "New Task Title" + ranNum.nextInt();

            driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("./*//*[@id='lwPopup-task-title']")));
            WebElement taskTitle = driver.findElement(By.xpath("./*//*[@id='lwPopup-task-title']"));

            taskTitle.sendKeys(title);

            //iframe handler
            WebDriver iframe = driver.switchTo().frame(driver.findElement(By.xpath("/html/body/div[12]/table/tbody/tr[2]/td[2]/div/div/div/div[3]/div[1]/div[1]/div[2]/div/div/div[3]/div[1]/iframe")));
            iframe.findElement(By.xpath("/html/body")).sendKeys("task description");
            driver.switchTo().defaultContent();

            driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("./*//*[@id='task-submit-button-text']")));

            //submitting the task
            WebElement submitTaskBut = driver.findElement(By.xpath("./*//*[@id='task-submit-button-text']"));
            submitTaskBut.click();

            // Every task is with different id, so we put all task names in a list and we take the first of them, which is the last created task.
            Thread.sleep(2000);
            List<WebElement> taskLists = driver.findElements(By.className("task-title-container"));
            String taskTitleConfirm = taskLists.get(0).getText();

            assertEquals(title, taskTitleConfirm );
        }

        @After
        public void tearDown(){

            driver.quit();
        }
    }
