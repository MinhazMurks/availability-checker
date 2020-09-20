package checker;

import notification.SystemTrayNotifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.service.DriverService;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class SeleniumAvailabilityChecker implements AvailabilityChecker {
    private static final Logger logger = LogManager.getLogger(SeleniumAvailabilityChecker.class);
    private final List<String> pageURLs;

    SeleniumAvailabilityChecker(String... pageURLs) {
        this.pageURLs = Arrays.asList(pageURLs);
    }

    protected abstract Availability checkAvailability(Document document);

    public Map<String, Availability> isAvailable() throws IOException, InterruptedException {
        DriverService.Builder serviceBuilder = new ChromeDriverService.Builder().withSilent(true);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--log-level=3", "--silent");
        ChromeDriverService chromeDriverService = (ChromeDriverService)serviceBuilder.build();
        chromeDriverService.sendOutputTo(new OutputStream(){@Override public void write(int b){}});

        Map<String, Availability> productAvailability = new HashMap<>();
        for(String pageURL : pageURLs) {
            WebDriver webDriver = new ChromeDriver(chromeDriverService, options);
            webDriver.get(pageURL);
            String pageSource = webDriver.getPageSource();
            webDriver.quit();
            Availability availability = checkAvailability(Jsoup.parse(pageSource));
            if(availability == Availability.AVAILABLE) {
                try {
                    SystemTrayNotifier.notify(pageURL, "RTX 3080 Found!", pageURL);
                } catch (AWTException ignored) {
                }
            }
            logger.log(Level.INFO, availability + " : " + pageURL);
            productAvailability.put(pageURL, availability);
        }
        return productAvailability;
    }
}
