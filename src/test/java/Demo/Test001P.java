package Demo;

import TestCase.AbstractBaseTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by cpuser on 13/11/15.
 */
public class Test001P extends AbstractBaseTest {

//    @Test(description = "failMethod")
//    public void failMethod() {
//        Assert.assertTrue(false);
//    }

    @Test
    public void failMethod() {
        driver.get("http://www.google.com");
        test.log(LogStatus.INFO, "Click: [Home --> Menu --> Login] button");
        test.log(LogStatus.INFO, "Google Test Step2...");
        Assert.assertTrue(false);
    }
}
