package checker;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class AsusStoreChecker extends SeleniumAvailabilityChecker {
    public AsusStoreChecker(String... pageURLs) {
        super(pageURLs);
    }

    @Override
    protected Availability checkAvailability(Document document) {
        Element element = document.getElementsByClass("arrival-info-btn").get(0);
        element.getElementsByTag("style");
        System.out.println(element);
        System.out.println(element.attr("style"));
        return null;
    }
}
