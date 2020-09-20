package checker;

import java.io.IOException;
import java.util.Map;

public interface AvailabilityChecker {
    public Map<String, Availability> isAvailable() throws IOException, InterruptedException;
}
