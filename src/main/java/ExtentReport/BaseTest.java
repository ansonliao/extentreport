package ExtentReport;

import ExtentReport.Factory.ExtReportFactory;
import Factory.ExtentReportFactory;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cpuser on 12/11/15.
 */
public class BaseTest {
    private ExtentReports report;
    protected  ExtentTest logger;

    String testDesc = "Empty Description for This Test";

    @BeforeMethod
    public void beforeMethod(Method method) {
        if (getTestCategories(method) == null) {
            ExtentReportFactory.getTest(getTestName(method), getTestDescription(method));
        }
        else {
            ExtentReportFactory.getTest(getTestName(method), getTestDescription(method), getTestCategories(method));
        }
    }

    @AfterMethod
    public void afterMethod(ITestResult iTestResult) {
        final int PASS = 1;
        final int FAIL = 2;
        final int SKIP = 3;

        switch (iTestResult.getStatus()) {
            case PASS:
                ExtentReportFactory.getTest().log(LogStatus.PASS, "Test Passed");
                break;
            case FAIL:
                ExtentReportFactory.getTest().log(LogStatus.FAIL, getStackTrace(iTestResult.getThrowable()));
                break;
            case SKIP:
                ExtentReportFactory.getTest().log(LogStatus.SKIP, "Test Skipped");
                break;
            default:
                ExtentReportFactory.getTest().log(LogStatus.PASS, "Test Passed");
        }

        ExtentReportFactory.closeTest(ExtentReportFactory.getTest());
    }

    /**
     *  After suite will be responsible to close the report properly at the end.
     *  You an have another afterSuite as well in the derived class and this one
     *  will be called in the end making it the last method to be called in test exe
     */
    @AfterSuite
    public void afterSuite() {
        ExtentReportFactory.closeReport();
    }

    private synchronized String getTestName(Method method) {
        Test test = method.getAnnotation(Test.class);

        return test.testName().trim().isEmpty()
                ? method.getName()
                : (test.testName() + "  [" + method.getName() + "]");
    }

    private synchronized String getTestDescription(Method method) {
        Test test = method.getAnnotation(Test.class);

        return test.description().trim().isEmpty()
                ? testDesc
                : test.description().trim();
    }

    private synchronized String[] getTestCategories(Method method) {
        Test test = method.getAnnotation(Test.class);

        if (test.groups().length == 0) {
            return null;
        }
        else {
            return removeDuplicated(test.groups());
        }
    }

    private synchronized String[] removeDuplicated(String[] array) {
        Set<String> set = new HashSet<String>();
        set.addAll(Arrays.asList(array));
        //TODO Remove duplicated item in ignore-case

        return set.toArray(new String[0]);
    }

    protected synchronized String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
}
