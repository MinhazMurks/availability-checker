package checker;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EvgaStoreChecker extends JSoupAvailabilityChecker{
    public EvgaStoreChecker(String... pageURLs) {
        super(pageURLs);
    }

    @Override
    protected Availability checkAvailability(Document document) {
        return document.getElementsByClass("AddToChat").size() == 0 ? Availability.SOLD_OUT : Availability.AVAILABLE;
    }
}
