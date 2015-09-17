package TestSuite2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;

public class remoteWebDriver {


    private RemoteWebDriver remoteWebDriver;
    private static String nodeURL = "http://192.168.1.103:5566/wd/hub/";

    @Before
    public void setUp()throws MalformedURLException{
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setJavascriptEnabled(true);
        remoteWebDriver = new RemoteWebDriver(new URL(nodeURL), capabilities);
        remoteWebDriver.manage().window().maximize();
    }

    @Test
    public void test(){
        remoteWebDriver.get("http://dir.bg");
    }

    @After
    public void tearDown(){
        remoteWebDriver.quit();
    }

}

