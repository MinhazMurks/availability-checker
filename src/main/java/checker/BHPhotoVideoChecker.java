package checker;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Checker for BestBuy Store.
 * @see <a href="https://www.bhphotovideo.com/">https://bhphotovideo.com</a>
 */
public class BHPhotoVideoChecker extends  JSoupAvailabilityChecker{
    public BHPhotoVideoChecker(String... pageURLs) {
        super(pageURLs);
    }

    @Override
    protected Availability checkAvailability(Document document) {
        return document.getElementsByAttributeValue("data-selenium", "addToCartButton").size() == 0 ? Availability.SOLD_OUT : Availability.AVAILABLE;
    }
}
