package edu.hm.hafner.java.uc;

import edu.hm.hafner.util.Ensure;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a list of integers that represent the individual sizes of several set of issues.
 *
 * @author Ullrich Hafner
 */
@SuppressWarnings({"FieldCanBeLocal", "MismatchedQueryAndUpdateOfCollection"}) // Will be converted to Json
public class IssuesSizeList {
    private final List<Integer> data = new ArrayList<>();

    /**
     * Creates a new instance of {@link IssuesSizeList}.
     * @author Lukas Gollwitzer
     * @param data
     *         the elements of the list
     */
    public IssuesSizeList(final List<Integer> data) {
        Ensure.that(data).isNotNull();
        this.data.addAll(data);
    }
}
