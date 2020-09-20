package checker;

import notification.SystemTrayNotifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class JSoupAvailabilityChecker implements AvailabilityChecker {
    private static final Logger logger = LogManager.getLogger(AvailabilityChecker.class);
    private final List<String> pageURLs;

    JSoupAvailabilityChecker(String... pageURLs) {
        this.pageURLs = Arrays.asList(pageURLs);
    }

    protected abstract Availability checkAvailability(Document document);

    public Map<String, Availability> isAvailable() throws IOException {
        Map<String, Availability> productAvailability = new HashMap<>();
        for(String pageURL : pageURLs) {
            Availability availability = checkAvailability(Jsoup.connect(pageURL).get());

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
