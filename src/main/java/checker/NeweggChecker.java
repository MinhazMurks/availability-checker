package checker;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Checker for Newegg Store.
 * @see <a href="https://newegg.com">https://newegg.com</a>
 */
public class NeweggChecker extends SeleniumAvailabilityChecker {
    public NeweggChecker(String... pageURLs) {
        super(pageURLs);
    }

    @Override
    protected Availability checkAvailability(Document document) {
        Element element = document.getElementById("landingpage-cart");
        return Availability.isSoldOut(element.getElementsByClass("btn").text());
    }
}
