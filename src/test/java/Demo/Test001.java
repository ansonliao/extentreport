package Demo;

import ExtentReport.BaseTest;
import Factory.ExtentReportFactory;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;

/**
 * Created by cpuser on 12/11/15.
 */
public class Test001 extends BaseTest {

    @Test(testName = "Test Demo 001", description = "Test Demo for Extent Report in Parallel Method", groups = {"Demo", "Demo1", "demo"})
    public void test001() {
        long startTime = System.nanoTime();
        logger = ExtentReportFactory.getTest();
        logger.log(LogStatus.INFO, "Starting test...");

        int a = 100;
        int b = 30;

        logger.log(LogStatus.INFO, "Loading the value of a to " + a);
        logger.log(LogStatus.PASS, "Loading the value of b to " + b);
        System.out.println("The time spent on this test: " + (System.nanoTime() - startTime));
    }

    @Test(testName = "Test Demo 002", description = "Test Demo for Extent Report in Parallel Method", groups = {"Demo", "Demo2"})
    public void test002() {
        long startTime = System.nanoTime();
        logger = ExtentReportFactory.getTest();
        logger.log(LogStatus.INFO, "Starting test...");

        int a = 100;
        int b = 30;

        logger.log(LogStatus.INFO, "Loading the value of a to " + a);
        logger.log(LogStatus.PASS, "Loading the value of b to " + b);
        System.out.println("The time spent on this test: " + (System.nanoTime() - startTime));
    }

}
