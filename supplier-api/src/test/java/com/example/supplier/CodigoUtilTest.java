import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CodigoUtilTest {

    // Helper method to generate valid CNPJ for testing
    private long generateValidCNPJ() {
        return 12345678000195L; // Example of a valid CNPJ
    }

    // Helper method to generate invalid CNPJ for testing
    private long generateInvalidCNPJ() {
        return 12345678000194L; // Example of an invalid CNPJ
    }

    // Test for valid CNPJ
    @Test
    void isValidCNPJ_ShouldReturnTrue_ForValidCNPJ() {
        long validCNPJ = generateValidCNPJ();
        boolean result = CodigoUtil.isValidCNPJ(validCNPJ);
        assertTrue(result, "Expected valid CNPJ to return true.");
    }

    // Test for invalid CNPJ
    @Test
    void isValidCNPJ_ShouldReturnFalse_ForInvalidCNPJ() {
        long invalidCNPJ = generateInvalidCNPJ();
        boolean result = CodigoUtil.isValidCNPJ(invalidCNPJ);
        assertFalse(result, "Expected invalid CNPJ to return false.");
    }

    // Test for CNPJ with less than 14 digits
    @Test
    void isValidCNPJ_ShouldReturnFalse_ForCNPJWithLessThan14Digits() {
        long shortCNPJ = 12345678L; // Example of a CNPJ with less than 14 digits
        boolean result = CodigoUtil.isValidCNPJ(shortCNPJ);
        assertFalse(result, "Expected CNPJ with less than 14 digits to return false.");
    }

    // Test for CNPJ with more than 14 digits
    @Test
    void isValidCNPJ_ShouldReturnFalse_ForCNPJWithMoreThan14Digits() {
        long longCNPJ = 123456789012345L; // Example of a CNPJ with more than 14 digits
        boolean result = CodigoUtil.isValidCNPJ(longCNPJ);
        assertFalse(result, "Expected CNPJ with more than 14 digits to return false.");
    }

    // Test for CNPJ with non-numeric characters (should not be possible with long
    // type)
    @Test
    void isValidCNPJ_ShouldReturnFalse_ForNonNumericCNPJ() {
        long nonNumericCNPJ = -12345678000195L; // Example of a negative CNPJ
        boolean result = CodigoUtil.isValidCNPJ(nonNumericCNPJ);
        assertFalse(result, "Expected non-numeric CNPJ to return false.");
    }

    // Test for edge case: CNPJ with all zeros
    @Test
    void isValidCNPJ_ShouldReturnFalse_ForCNPJWithAllZeros() {
        long zeroCNPJ = 0L; // Example of a CNPJ with all zeros
        boolean result = CodigoUtil.isValidCNPJ(zeroCNPJ);
        assertFalse(result, "Expected CNPJ with all zeros to return false.");
    }

    // Test for edge case: CNPJ with maximum possible value
    @Test
    void isValidCNPJ_ShouldReturnFalse_ForMaximumPossibleCNPJ() {
        long maxCNPJ = 99999999999999L; // Example of the maximum possible CNPJ value
        boolean result = CodigoUtil.isValidCNPJ(maxCNPJ);
        assertFalse(result, "Expected maximum possible CNPJ to return false.");
    }

    // Test for exception handling
    @Test
    void isValidCNPJ_ShouldReturnFalse_WhenExceptionOccurs() {
        long invalidCNPJ = Long.MAX_VALUE; // Example of an invalid CNPJ causing exception
        boolean result = CodigoUtil.isValidCNPJ(invalidCNPJ);
        assertFalse(result, "Expected CNPJ causing exception to return false.");
    }
}
