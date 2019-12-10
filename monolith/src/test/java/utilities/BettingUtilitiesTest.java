package utilities;

import org.junit.Test;

public class BettingUtilitiesTest {
    @Test
    public void generateId_returns_validGuid() {
        String testPrefix = "TEST";
        String guid = BettingUtilities.generateId(testPrefix);
        assert(guid.length() == (36 + testPrefix.length() + 1));
        assert(guid.indexOf(testPrefix) == 0);

    }

}