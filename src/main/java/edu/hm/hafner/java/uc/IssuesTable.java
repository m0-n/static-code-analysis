package edu.hm.hafner.java.uc;

import java.util.ArrayList;
import java.util.List;

import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.util.Ensure;

/**
 * Model that provides the different sizes of a set of {@link Issues}.
 *
 * @author Ullrich Hafner
 * @see <a href="http://www.chartjs.org/docs/latest/charts/bar.html#dataset-properties">Bar Chart Dataset</a>
 */
@SuppressWarnings({"FieldCanBeLocal", "MismatchedQueryAndUpdateOfCollection"}) // Will be converted to Json
public class IssuesTable {
    private final List<List<String>> data = new ArrayList<>();

    /**
     * Adds a new row to this table.
     * @author Lukas Gollwitzer
     * @param issues
     *         the set of issues that should be added
     */
    public void addRow(final Issues<?> issues) {
        Ensure.that(issues).isNotNull();
        List<String> row = new ArrayList<>();
        row.add(issues.getOrigin());
        row.add(issues.getReference());
        row.add(String.valueOf(issues.getSize()));
        row.add(String.valueOf(issues.getHighPrioritySize()));
        row.add(String.valueOf(issues.getNormalPrioritySize()));
        row.add(String.valueOf(issues.getLowPrioritySize()));

        data.add(row);
    }
}
