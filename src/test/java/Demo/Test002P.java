package Demo;

import TestCase.AbstractBaseTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by cpuser on 13/11/15.
 */
public class Test002P extends AbstractBaseTest {

    @Test(groups = { "functest", "checkintest" })
    public void passMethod() {
        driver.get("http://www.baidu.com");
        test.log(LogStatus.INFO, "Click: [BaiDu --> Submit] button");
        test.log(LogStatus.INFO, "BaiDu Next Step...");
        Assert.assertTrue(false);
    }
}
