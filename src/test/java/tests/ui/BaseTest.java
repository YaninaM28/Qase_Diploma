package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.DashboardPage;
import pages.LoginPage;
import pages.ProjectPage;
import pages.SuitePage;

import java.util.HashMap;

import static com.codeborne.selenide.Selenide.closeWebDriver;

@Log4j2
public class BaseTest {

    LoginPage loginPage;
    DashboardPage dashboardPage;
    ProjectPage projectPage;
    SuitePage suitePage;

    @BeforeMethod
    public void setUp() {
        SelenideLogger.addListener(
                "AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
                        .includeSelenideSteps(true)
        );
//  для Дженкинса
//        Configuration.headless = true;
        Configuration.browser = "chrome";
        Configuration.baseUrl = "https://app.qase.io";
        Configuration.timeout = 10000;
        Configuration.clickViaJs = true;
        Configuration.browserSize = "1920x1080";

        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        Configuration.browserCapabilities = options;

        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        projectPage = new ProjectPage();
        suitePage = new SuitePage();
    }

    @AfterMethod
    public void tearDown() {
        closeWebDriver();
    }
}
