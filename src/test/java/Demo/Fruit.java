package Demo;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ExtentReport.BaseTest;
import Factory.ExtentReportFactory;

import com.relevantcodes.extentreports.LogStatus;

/**
 * Created by cpuser on 12/11/15.
 * @author
 */
public class Fruit {

    @BeforeMethod
    public void beforeMethod(ITestContext context) {
        System.out.println(context.getOutputDirectory());
    }

    @Test
    public void f1() {
        System.out.println("in test method f1");
    }

//    @Test
//    public void testPass() {
//        logger = ExtentReportFactory.getTest();
//        logger.log(LogStatus.PASS, "details");
//    }
//
//    @Test
//    public void testError() {
//        logger = ExtentReportFactory.getTest();
//        logger.log(LogStatus.ERROR, "details");
//    }
}
