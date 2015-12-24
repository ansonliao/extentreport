package TestCase;

import ExtentReport.Factory.ExtentManager;
import ExtentReport.Factory.ExtentTestManager;
import Selenium.WebDriverManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by AnsonLiao on 13/11/15.
 * @author AnsonLiao
 */
public abstract class AbstractBaseTest {
    public static ExtentReports extent;
    public ExtentTest test;
    public WebDriver driver;
    private static final int PASS = 1;
    private static final int FAIL = 2;
    private static final int SKIP = 3;



    @BeforeSuite
    public void extentSetup(ITestContext context) {
        ExtentManager.setOutPutDirectory(context);
        extent = ExtentManager.getInstance();
    }

    @BeforeClass
    public void beforeClass() {
        driver = WebDriverManager.getInstance();
    }

    @BeforeMethod
    public void startExtent(Method method) {
        ExtentTestManager.startTest(method.getName(), getMethodDesc(method));

        // Test categories assigned
        if (getMethodGroups(method) != null) {
            ExtentTestManager.getTest().assignCategory(getMethodGroups(method));
        }

        test = ExtentTestManager.getTest();
    }

    @AfterMethod
    public void afterEachTestMethod(ITestResult iTestResult) {
        WebDriverManager.closeDriver();

        ExtentTestManager.getTest().getTest().setStartedTime(getTime(iTestResult.getStartMillis()));
        ExtentTestManager.getTest().getTest().setEndedTime(getTime(iTestResult.getEndMillis()));

        switch (iTestResult.getStatus()) {
            case PASS:
                ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
                break;
            case FAIL:
                ExtentTestManager.getTest().log(LogStatus.FAIL, getStackTrace(iTestResult.getThrowable()));
                break;
            case SKIP:
                ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
                break;

            default:
                ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
        }

        ExtentTestManager.closeTest();
    }

    @AfterClass
    public void afterClass() {
        WebDriverManager.quitDriver();
    }

    /**
     *  After suite will be responsible to close the report properly at the end.
     *  You an have another afterSuite as well in the derived class and this one
     *  will be called in the end making it the last method to be called in test exe
     */
    @AfterSuite
    public void generateReport() {
        ExtentManager.writeReport();
        ExtentManager.closeReport();
    }


    protected synchronized String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    private synchronized String getMethodNameDesc(Method method) {
        Test test = method.getAnnotation(Test.class);

        return test.testName().trim();
    }

    private synchronized String getMethodDesc(Method method) {
        Test test = method.getAnnotation(Test.class);
        return test.description().trim();
    }

    private synchronized String[] getMethodGroups(Method method) {
        Test test = method.getAnnotation(Test.class);

        return test.groups().length > 0 ? removeDuplicatedArrayItem(test.groups()) : null;
    }

    private synchronized String[] removeDuplicatedArrayItem(String[] array) {
        Set<String> set = new HashSet<String>();
        set.addAll(Arrays.asList(array));
        //TODO Remove duplicated item in ignore-case

        return set.toArray(new String[0]);
    }

    private synchronized Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        return calendar.getTime();
    }

}
