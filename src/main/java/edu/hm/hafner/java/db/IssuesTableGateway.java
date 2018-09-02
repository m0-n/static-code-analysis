package edu.hm.hafner.java.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

import edu.hm.hafner.util.Ensure;
import org.springframework.stereotype.Controller;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.analysis.parser.pmd.PmdParser;

/**
 * Acts as gateway to the issues table.
 *
 * @author Ullrich Hafner
 */
@Controller
public class IssuesTableGateway {
    private static final Issues<Issue> TEST_DATA = createTestData();

    /**
     * Finds the set of issues with the specified ID.
     *
     * @param id
     *         the ID of the issues
     * @author Stefan Spirkl
     * @return the set of issues
     * @throws NoSuchElementException
     *         if the set of issues with the specified ID does not exist
     */
    public static Issues<Issue> findByPrimaryKey(final String id) {
        Ensure.that(id).isNotBlank();
        return TEST_DATA; // TODO: will be replaced with actual database call later on
    }

    /**
     * Creating test data.
     * @author Stefan Spirkl
     **/
    private static Issues<Issue> createTestData() {
        PmdParser parser = new PmdParser();
        try (InputStreamReader reader = new InputStreamReader(getTestReport())) {
            return parser.parse(reader);
        }
        catch (IOException ignored) {
            return new Issues<>();
        }
    }

    /**
     * Creating test report
     *  @author Stefan Spirkl
     * @return test report
     **/
    private static InputStream getTestReport() {
        return IssuesTableGateway.class.getResourceAsStream("/test/pmd.xml");
    }
}
