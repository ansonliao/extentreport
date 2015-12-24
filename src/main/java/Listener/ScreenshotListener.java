package Listener;

import ExtentReport.Factory.ExtentTestManager;
import Selenium.Snapshot;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * Created by AnsonLiao on 13/11/15.
 * @author AnsonLiao
 */
public class ScreenshotListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        ITestContext iTestContext = iTestResult.getTestContext();
        String screenshotFile = Snapshot.takeScreenShot(iTestContext);

        ExtentTestManager.getTest().log(
                LogStatus.FAIL, "Failure Screenshot below: "
                + ExtentTestManager.getTest().addScreenCapture(screenshotFile)
        );
    }
}
