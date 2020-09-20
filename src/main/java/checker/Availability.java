package checker;

import static org.apache.commons.lang3.StringUtils.*;

/**
 * Represents whether or not a product is available.
 */
public enum Availability {
    /**
     * A product is sold out if is not able to be purchased.
     */
    SOLD_OUT("Sold Out"),

    /**
     * A product is available if it can be purchased.
     */
    AVAILABLE("Available");

    private final String val;

    Availability(String val) {
        this.val = val;
    }

    /**
     * Gets the value associated with the {@link Availability}.
     * @return the value associated with the availability
     */
    public String getVal() {
        return val;
    }

    /**
     * Checks whether {@code availability} and returns the {@link Availability} associated with it.
     * @return the value associated with the {@code availability} passed in if it makes sense, {@link #SOLD_OUT} otherwise
     */
    public static Availability isSoldOut(String availability) {
        switch (trimToEmpty(deleteWhitespace(availability)).toLowerCase()) {
            case "addtocart" :
                return AVAILABLE;
        }
        return SOLD_OUT;
    }
}
