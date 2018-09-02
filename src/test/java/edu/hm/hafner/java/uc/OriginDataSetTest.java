package edu.hm.hafner.java.uc;

import edu.hm.hafner.java.db.EntityService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for OriginDataSet.
 */
class OriginDataSetTest {

    /**
     * Ensure no blank or null values.
     * @author Lukas Gollwitzer
     */
    @Test
    void shouldThrowException() {
        assertThatThrownBy(() -> new OriginDataSet(null, null, null, null, null)).isInstanceOf(NullPointerException.class);
    }
}