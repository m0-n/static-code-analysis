package edu.hm.hafner.java.db;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Issues;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Test class for EntityMapper.
 *
 * @author Lukas Gollwitzer
 */
class EntityMapperTest {

    /**
     * Ensure no blank or null values.
     *
     * @author Lukas Gollwitzer
     */
    @Test
    void shouldThrowException() {
        EntityMapper entityMapper = new EntityMapper();
        assertThatThrownBy(() -> entityMapper.map((Issue)null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> entityMapper.map((Issue)null, (IssueEntity) null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> entityMapper.map((IssueEntity)null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> entityMapper.map((Issues)null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> entityMapper.map((Issues)null, (IssuesEntity)null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> entityMapper.map((IssuesEntity)null)).isInstanceOf(NullPointerException.class);
    }

}