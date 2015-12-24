package Selenium;

import Utility.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AnsonLiao on 13/11/15.
 * @author AnsonLiao
 */
public class WebDriverManager {

    private static WebDriver driver;
    private static Map<Long, WebDriver> webDriverMap = new HashMap<Long, WebDriver>();
    private static String testType;
    private static String browser;
    private static String urlLink;
    private static int mxRetryCount;

    private static final String BROWSER1 = "firefox";
    private static final String BROWSER2 = "chrome";
    private static final String BROWSER3 = "ie";

    static {
        ConfigReader config = ConfigReader.getInstance();
        testType = config.getTestType();
        urlLink = config.getUrl();
        browser = config.getBrowser();
        mxRetryCount = config.getRetryCount();
    }

    public static WebDriver getInstance() {
        Long threadID = Thread.currentThread().getId();

        if (!webDriverMap.containsKey(threadID)) {
            if (browser.toLowerCase().trim().equals(BROWSER1)) {
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
            }
            if (browser.toLowerCase().trim().equals(BROWSER2)) {
                // TODO add chrome handle code here
            }
            if (browser.toLowerCase().trim().equals(BROWSER3)) {
                // TODO add ie handle code here
            }

            webDriverMap.put(threadID, driver);
        }

        return webDriverMap.get(threadID);
    }

    public static void closeDriver() {
        Long threadID = Thread.currentThread().getId();

        webDriverMap.get(threadID).close();
    }

    public static void quitDriver() {
        Long threadID = Thread.currentThread().getId();

        webDriverMap.get(threadID).quit();
    }

}
