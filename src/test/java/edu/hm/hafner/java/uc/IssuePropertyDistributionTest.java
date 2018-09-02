package edu.hm.hafner.java.uc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Test for IssuePropertyDistribution.
 */
class IssuePropertyDistributionTest {
    /**
     * Ensure no blank or null values.
     * @author Lukas Gollwitzer
     */
    @Test
    void shouldThrowException() {
        assertThatThrownBy(() -> new IssuePropertyDistribution(null)).isInstanceOf(NullPointerException.class);
    }

}