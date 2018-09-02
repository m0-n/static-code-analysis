package edu.hm.hafner.java.uc;

import edu.hm.hafner.analysis.AbstractParser;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AnalysisTool.
 */
class AnalysisToolTest {

    /**
     * Ensure no blank or null values.
     * @author Lukas Gollwitzer
     */
    @Test
    void shouldThrowException() {
        assertThatThrownBy(() -> new AnalysisTool(null, null, (AbstractParser) null)).isInstanceOf(NullPointerException.class);
    }
}