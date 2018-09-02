package edu.hm.hafner.java.db;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.java.uc.IssuesTestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test for IssuesTableGateway.
 * @author Stefan Spirkl
 */
class IssuesTableGatewayTest {

    @Test
    void findByPrimaryKey() {
        Issues<Issue> myTestIssue;
        myTestIssue =  IssuesTableGateway.findByPrimaryKey("12345"); // soooo many issues.
        assertThat(myTestIssue).isEqualTo(IssuesTableGateway.findByPrimaryKey("12345"));
    }

    /**
     * Ensure no blank or null values.
     * @author Stefan Spirkl
     */
    @Test
    void shouldThrowException() {
        assertThatThrownBy(() -> IssuesTableGateway.findByPrimaryKey(null)).isInstanceOf(NullPointerException.class);
    }
}