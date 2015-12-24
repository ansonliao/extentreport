package ExtentReport;

import Factory.ExtentReportFactory;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cpuser on 12/11/15.
 */
public class BaseTest2 {
    private ExtentReports report;
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
    public void afterMethod(Method method) {
        ExtentReportFactory.getTest().log(LogStatus.PASS, "Completed", getTestName(method) + " Completed.");
        ExtentReportFactory.closeTest(getTestName(method));
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
}
