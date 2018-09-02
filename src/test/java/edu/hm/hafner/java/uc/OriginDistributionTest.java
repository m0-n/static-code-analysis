package edu.hm.hafner.java.uc;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for OriginDistribution.
 */
class OriginDistributionTest {

    /**
     * Ensure no blank or null values.
     * @author Stefan Spirkl
     */
    @Test
    void shouldThrowException() {
        assertThatThrownBy(() -> new OriginDistribution(null, null)).isInstanceOf(NullPointerException.class);
    }
}