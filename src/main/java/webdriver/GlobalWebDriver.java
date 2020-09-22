package webdriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.service.DriverService;

import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalWebDriver {
    private static GlobalWebDriver globalWebDriver;

    public static GlobalWebDriver getInstance() {
        globalWebDriver = globalWebDriver == null ? new GlobalWebDriver() : globalWebDriver;
        return globalWebDriver;
    }

    private ChromeDriver chromeDriver;

    public void initWebDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
        System.setProperty("webdriver.chrome.logfile", System.getProperty("user.dir") + "/drivers/chromedriver.log");
        System.setProperty("webdriver.chrome.args", "--disable-logging");
        System.setProperty("webdriver.chrome.verboseLogging", "false");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        DriverService.Builder serviceBuilder = new ChromeDriverService.Builder().withSilent(true);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        ChromeDriverService chromeDriverService = (ChromeDriverService)serviceBuilder.build();
        chromeDriverService.sendOutputTo(new OutputStream(){@Override public void write(int b){}});
        chromeDriver = new ChromeDriver(chromeDriverService, options);
        chromeDriver.setLogLevel(Level.OFF);
        chromeDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }

    public void disposeWebDriver() {
        chromeDriver.quit();
    }

    public ChromeDriver getChromeDriver() {
        return chromeDriver;
    }
}
