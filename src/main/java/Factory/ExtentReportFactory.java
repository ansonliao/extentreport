package Factory;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.util.*;

/**
 * Created by AnsonLiao on 12/11/15.
 * @author AnsonLiao
 */
public class ExtentReportFactory {

    public static ExtentReports reporter;
    public static Map<Long, String> threadToExtentTestMap = new HashMap<Long, String>();
    public static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();
    private static final String REPORT_DOCUMENT_TITLE = "CherryPicks Alpha QA Team Test Report";
    private static final String REPORTNAME = "QA Demo";

    private synchronized  static ExtentReports getExtentReport() {
        if (reporter == null) {
            // you can get the file name and other parameters here from a
            // config file or global variables
            reporter = new ExtentReports("./reports/ComplexReport.html", true, DisplayOrder.NEWEST_FIRST);
            reporter.config()
                    .documentTitle(REPORT_DOCUMENT_TITLE)
                    .reportName(REPORTNAME);
        }

        return reporter;
    }

    public synchronized static ExtentTest getTest(String testName, String testDescription) {

        // if this test have already been created and then return it
        if (!nameToTestMap.containsKey(testName)) {
            Long threadID = Thread.currentThread().getId();
            ExtentTest test = getExtentReport().startTest(testName, testDescription);
            nameToTestMap.put(testName, test);
            threadToExtentTestMap.put(threadID, testName);
        }
        return nameToTestMap.get(testName);
    }

    public synchronized static ExtentTest getTest(String testName, String testDescription, String[] categories) {
        // if this test hae already been created, return it
        if (!nameToTestMap.containsKey(testName)) {
            Long threadID = Thread.currentThread().getId();
            ExtentTest test = getExtentReport().startTest(testName, testDescription);

            // assign category
            if (categories.length != 0) {
                test.assignCategory(categories);
            }
            nameToTestMap.put(testName, test);
            threadToExtentTestMap.put(threadID, testName);
        }
        return nameToTestMap.get(testName);
    }

    public synchronized static ExtentTest getTest(String testName) {
        return getTest(testName, "");
    }

    /**
     *  At any given instance there will be only one test running in a thread. We
     *  are already maintaining a thread to extentext map. This method should be
     *  used after some part of the code has already called getTest with proper
     *  testcase name and/or description.
     *
     *  Reason: This method is not for creating test but getting an existing test
     *  reporter. Sometime you are in a utility function and want to log something
     *  from there. Utility function or similar code sections are not bound to a
     *  test hence we cannot get a reporter via test name, unlese we pass test
     *  name in all method calls. Which will be an overhead and a rigid design.
     *  However, there is one common association which is the thread ID.
     *  When TestNG executes a test it puts it launches a new thread, assuming
     *  test level threading, all tests will have a unique thread ID hence call
     *  to this function will return the right extent test to use
     */
    public synchronized static ExtentTest getTest() {
        Long threadID = Thread.currentThread().getId();

        if (threadToExtentTestMap.containsKey(threadID)) {
            String testName = threadToExtentTestMap.get(threadID);
            return nameToTestMap.get(testName);
        }

        // System log, this shouldn't happen but in this crazy times if it did happen log it.
        return null;
    }

    public synchronized static void closeTest(String testName) {
        if (!testName.isEmpty()) {
            ExtentTest test = getTest(testName);
            getExtentReport().endTest(test);
        }
    }

    public synchronized static void closeTest(ExtentTest test) {
        if (test != null) {
            getExtentReport().endTest(test);
        }
    }

    public synchronized static void closeTest() {
        ExtentTest test = getTest();
        closeTest(test);
    }

    public synchronized static void closeReport() {
        if (reporter != null) {
            reporter.flush();
            reporter.close();
        }
    }
}
