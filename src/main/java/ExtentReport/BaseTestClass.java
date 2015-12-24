package ExtentReport;

import Factory.ExtentReportFactory;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by AnsonLiao on 12/11/15.
 * @author AnsonLiao
 *
 * This is a base Test Class. All TestNG tests need to inherited form
 * this class. It contains common reporting infra
 */
public class BaseTestClass {
    String testDesc = "Empty Description for This Test";

    @BeforeMethod
    public void beforeMethod(Method method) {

//        getTestCategories(method) == null
//                ? ExtentReportFactory.getTest(getTestName(method), getTestDescription(method))
//                : ExtentReportFactory.getTest(getTestName(method), getTestDescription(method), getTestCategories(method));

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

}
