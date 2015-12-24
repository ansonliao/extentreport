package Selenium;

import org.openqa.selenium.WebDriver;

/**
 * Created by cpuser on 13/11/15.
 */
public abstract class AbstractWebDriver {

    void setupWebDriver() {
    }

    WebDriver getInstance() {
        return null;
    }

    void closeInstance() {}

    void quitInstance() {}

}
