package utils;

import org.openqa.selenium.WebDriver;

public class DriverManager {

    // зачем? для каждого потока исп свой driver
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static void quit() {
        WebDriver driver = DRIVER.get();
        if(driver != null) {
            driver.quit();
        }
        DRIVER.remove();
    }
}
