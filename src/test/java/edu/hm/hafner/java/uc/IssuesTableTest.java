package edu.hm.hafner.java.uc;

import edu.hm.hafner.analysis.Issues;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test class for IssuesTable.
 */
class IssuesTableTest {

    /**
     * Ensure no blank or null values.
     * @author Lukas Gollwitzer
     */
    @Test
    void shouldThrowException() {
        IssuesTable issuesTable = new IssuesTable();
        assertThatThrownBy(() -> issuesTable.addRow((Issues)null)).isInstanceOf(NullPointerException.class);
    }
}