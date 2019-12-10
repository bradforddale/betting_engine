package utilities;

import org.junit.Test;
import utilities.BettingValidation;

import static org.junit.Assert.*;

public class BettingValidationTest {
    @Test
    public void validateDescription_returns_true_forValidDescription() {
        assert (BettingValidation.validateDescription("A proper description"));
    }

    @Test
    public void validateDescription_returns_false_forEmptyDescription() {
        assertFalse (BettingValidation.validateDescription(""));
    }

}