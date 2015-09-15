import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestSuite1 {

    private WebDriver driver;
    private WebDriverWait driverWait;
    private Select dropDownSelector;
    private Alert popup;
    private String username = "dev_provers@abv.bg";
    private String password = "provers2015";

    @Before
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void validLoginABV() {
        driver.manage().window().maximize();
        driver.get("http://abv.bg");
        WebElement usernameField = driver.findElement(By.xpath(".//*[@id='username']"));
        usernameField.sendKeys(username);
        WebElement passwordField = driver.findElement(By.xpath(".//*[@id='password']"));
        passwordField.sendKeys(password);
        WebElement loginButton = driver.findElement(By.xpath(".//*[@id='loginBut']"));
        loginButton.click();

        String welcomeMessage = "("+username+")";
        assertEquals(welcomeMessage, driver.findElement(By.xpath(".//*[@id='middlePagePanel']/div[1]/div[2]")).getText());
    }

    @Test
    public void hiddenDropDownSelect() {

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

    @Test
    public void iFrameSelector() throws InterruptedException {
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
