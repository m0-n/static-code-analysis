package edu.hm.hafner.java.uc;

import edu.hm.hafner.analysis.AbstractParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Test class for DatasetbyPriorities.
 */
class DatasetByPrioritiesTest {

    /**
     * Ensure no blank or null values.
     * @author Stefan Spirkl
     */
    @Test
    void shouldThrowException() {
        assertThatThrownBy(() -> new DatasetByPriorities(null, null, null)).isInstanceOf(NullPointerException.class);
    }
}