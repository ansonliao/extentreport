package Utility;

import ExtentReport.Factory.ExtReportFactory;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.log4testng.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by AnsonLiao on 12/11/15.
 * @autho AnsonLiao
 */
public class ConfigReader {
    private static Logger logger = Logger.getLogger(ConfigReader.class);
    private static ConfigReader cr;
    private int retryCount = 0;
    private String sourceCodeDir = "src";
    private String sourceCodeEncoding = "UTF-8";

    private static final String RETRYCOUNT = "retryCount";
    private static final String SOURCEDIR = "sourcecodedir";
    private static final String SOURCEENCODING = "sourcecodeencoding";
    private static final String CONFIGFILE = "config/config.properties";

    private String testType;
    private String locatorYamlFile;
    private static final String TESTTYPE = "testvype";
    private static final String LOCATORYAMLFILE = "locatoryaml";

    // for web
    private String url;
    private String browser;
    private static final String URL = "url";
    private static final String BROWSER = "browser";

    // for mobile
    private String app;
    private String appDir;
    private String deviceName;
    private static final String APP = "app";
    private static final String APPDIR = "appdir";
    private static final String DEVICENAME = "devicename";

    // for iOS
    private static String platformVersion;
    private static final String PLATFORMVERSION = "platformversion";


    private ConfigReader() {
        readConfig(CONFIGFILE);
    }

    public static ConfigReader getInstance() {
        if(cr == null) {
            cr = new ConfigReader();
        }
        return cr;
    }

    private void readConfig(String fileName) {
        Properties properties = getConfig(fileName);
        if (properties != null) {
            String sRetryCount = null;

            Enumeration<?> en = properties.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                if (key.toLowerCase().equals(TESTTYPE)) {
                    testType = properties.getProperty(key);
                }
                if (key.toLowerCase().equals(LOCATORYAMLFILE)) {
                    locatorYamlFile = properties.getProperty(key);
                }
                if (key.toLowerCase().equals(URL)) {
                    url = properties.getProperty(key);
                }
                if (key.toLowerCase().equals(BROWSER)) {
                    browser = properties.getProperty(key);
                }
                if (key.toLowerCase().equals(APP)) {
                    app = properties.getProperty(key);
                }
                if (key.toLowerCase().equals(APPDIR)) {
                    appDir = properties.getProperty(key);
                }
                if (key.toLowerCase().equals(DEVICENAME)) {
                    deviceName = properties.getProperty(key);
                }
                if (key.toLowerCase().equals(PLATFORMVERSION)) {
                    platformVersion = properties.getProperty(key);
                }
                if(key.toLowerCase().equals(RETRYCOUNT)) {
                    sRetryCount = properties.getProperty(key);
                }

//                if(key.toLowerCase().equals(SOURCEDIR)) {
//                    sourceCodeDir = properties.getProperty(key);
//                }
//                if(key.toLowerCase().equals(SOURCEENCODING)) {
//                    sourceCodeEncoding = properties.getProperty(key);
//                }
            }
            if (sRetryCount != null) {
                sRetryCount = sRetryCount.trim();
                try {
                    retryCount = Integer.parseInt(sRetryCount);
                } catch (final NumberFormatException e) {
                    String emsg = "Parse " + RETRYCOUNT + " [" + sRetryCount + "] from String to Int Exception";
                    ExtReportFactory.getTest().log(LogStatus.ERROR, emsg);
                    throw new NumberFormatException(emsg);
                }
            }
        }
    }

    public String getTestType() {
        return testType;
    }

    public String getLocatorYamlFile() {
        return locatorYamlFile;
    }

    public String getUrl() {
        return url;
    }

    public String getBrowser() {
        return browser;
    }

    public String getApp() {
        return app;
    }

    public String getAppDir() {
        return appDir;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public int getRetryCount() {
        return retryCount;
    }

    /**
     *
     * @param propertyFileName
     *
     * @return
     */
    private Properties getConfig(String propertyFileName) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertyFileName));
        } catch (FileNotFoundException e) {
            properties = null;
            logger.warn("FileNotFoundException:" + propertyFileName);
        } catch (IOException e) {
            properties = null;
            logger.warn("IOException:" + propertyFileName);
        }
        return properties;
    }
}