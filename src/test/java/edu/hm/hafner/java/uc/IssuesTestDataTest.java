package edu.hm.hafner.java.uc;

import edu.hm.hafner.java.db.EntityService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test class for IssuesTestData.
 */
class IssuesTestDataTest {

    /**
     * Ensure no blank or null values.
     * @author Stefan Spirkl
     */
    @Test
    void shouldThrowException() {
        EntityService entityService = mock(EntityService.class);
        IssuesTestData issuesTestData = new IssuesTestData(entityService);
        assertThatThrownBy(() -> new IssuesTestData((EntityService)null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> issuesTestData.createTestData(null)).isInstanceOf(NullPointerException.class);
    }
}