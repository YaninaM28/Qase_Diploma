package tests.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ui.pages.*;
import utils.PropertyReader;

import java.util.HashMap;

import static com.codeborne.selenide.Selenide.closeWebDriver;

@Log4j2
public class BaseTest {

    protected LoginPage loginPage;
    protected DashboardPage dashboardPage;
    protected ProjectPage projectPage;
    protected SuitePage suitePage;
    protected TestCasePage testCasePage;

    protected String user;
    protected String password;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) {
        SelenideLogger.addListener(
                "AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
                        .includeSelenideSteps(true)
        );
        user = System.getProperty("user");
        password = System.getProperty("password");
        if (user == null) {
            user = PropertyReader.getProperty("user");
        }

        if (password == null) {
            password = PropertyReader.getProperty("password");
        }
        log.info("TEST USER = {}", user);
        log.info("PASSWORD EXISTS = {}", password != null);

        Configuration.browser = browser;
        Configuration.headless = true;
        Configuration.baseUrl = "https://app.qase.io";
        Configuration.timeout = 20000;
        Configuration.pageLoadTimeout = 30000;
        Configuration.clickViaJs = true;
        Configuration.browserSize = "1920x1080";

        if (browser.equalsIgnoreCase("chrome")) {

            ChromeOptions options = new ChromeOptions();
            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("credentials_enable_service", false);
            chromePrefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", chromePrefs);
            options.addArguments("--incognito");
            options.addArguments("--disable-notifications");
//            options.addArguments("--headless=new");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-infobars");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");


            Configuration.browserCapabilities = options;
        } else if (browser.equalsIgnoreCase("firefox")) {

            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-private");
            options.addPreference("dom.webnotifications.enabled", false);
//            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");

            Configuration.browserCapabilities = options;
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        projectPage = new ProjectPage();
        suitePage = new SuitePage();
        testCasePage = new TestCasePage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        closeWebDriver();
    }
}
