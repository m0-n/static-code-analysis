package edu.hm.hafner.java.uc;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Test class for IssuesSizeList.
 */
class IssuesSizeListTest {

    /**
     * Ensure no blank or null values.
     * @author Stefan Spirkl
     */
    @Test
    void shouldThrowException() {
        assertThatThrownBy(() -> new IssuesSizeList(null)).isInstanceOf(NullPointerException.class);
    }
}