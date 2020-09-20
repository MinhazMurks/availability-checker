package checker;

import static org.apache.commons.lang3.StringUtils.*;

public enum Availability {
    SOLD_OUT("Sold Out"),

    AVAILABLE("Available");

    private final String val;

    Availability(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public static Availability isSoldOut(String availability) {
        switch (trimToEmpty(deleteWhitespace(availability)).toLowerCase()) {
            case "addtocart" :
                return AVAILABLE;
        }
        return SOLD_OUT;
    }
}
