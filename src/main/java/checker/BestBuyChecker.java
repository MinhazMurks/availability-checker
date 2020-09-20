package checker;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class BestBuyChecker extends JSoupAvailabilityChecker {
    public BestBuyChecker(String... pageURLs) {
        super(pageURLs);
    }

    @Override
    protected Availability checkAvailability(Document document) {
        Element availabilityElement = document.getElementsByClass("fulfillment-add-to-cart-button").get(0);
        Element addToCartButton = availabilityElement.getElementsByClass("add-to-cart-button").get(0);
        return Availability.isSoldOut(addToCartButton.text());
    }
}
