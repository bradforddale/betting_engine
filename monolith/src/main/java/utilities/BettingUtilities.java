package utilities;

import java.util.UUID;

public class BettingUtilities {
    public static String generateId (String prefix) {
        return prefix + "_" + UUID.randomUUID().toString();
    }
}
