package edu.hm.hafner.java.uc;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * Test for priorityDistribution.
 */
class PriorityDistributionTest {
    /**
     * Ensure no blank or null values.
     * @author Lukas Gollwitzer
     */
    @Test
    void shouldThrowException() {
        assertThatThrownBy(() -> new PriorityDistribution(null, null)).isInstanceOf(NullPointerException.class);
    }
}