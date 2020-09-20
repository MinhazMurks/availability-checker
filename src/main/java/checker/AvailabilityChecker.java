package checker;

import java.io.IOException;
import java.util.Map;

/**
 * A checker to check if certain products are available.
 */
public interface AvailabilityChecker {
    public Map<String, Availability> isAvailable() throws IOException, InterruptedException;
}
